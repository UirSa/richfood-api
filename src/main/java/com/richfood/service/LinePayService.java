package com.richfood.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.richfood.model.CouponsOrders;
import com.richfood.repository.CouponsOrdersRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LinePayService {
	@Autowired CouponsOrdersRepository couponsOrdersRepository;
	
    @Value("${linepay.channel.id}")
    private String channelId;

    @Value("${linepay.channel.secret}")
    private String channelSecret;

    @Value("${linepay.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    
    private Integer price;
    private Integer quantity;
    private Integer totalAmount;
    private CouponsOrders getLastOrder;
    
	String confirmUrl = "http://localhost:8080/api/linepay/confirm"; // 固定的跳轉網址
    String productName = "腹餓帶餐券"; // 您的商品名稱
    String productImageUrl = "https://play-lh.googleusercontent.com/U_5xg82-GrbN5OvoHtm87O1Vk-Z2SKk8liWBnxcy9EppQNZ3BlsHhTXekqwEMXNaz7Y=w40-h40-rw"; // 固定的商品圖URL
    

    public LinePayService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    public String requestPayment(int amount, String orderId) {
        String url = apiUrl + "/request";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-LINE-ChannelId", channelId);
        headers.set("X-LINE-ChannelSecret", channelSecret);
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));

        Map<String, Object> body = new HashMap<>();
        body.put("amount", amount);
        body.put("currency", "TWD");
        body.put("confirmUrl", confirmUrl);
        body.put("productName", productName);
        body.put("productImageUrl", productImageUrl);
        body.put("orderId", orderId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> info = (Map<String, Object>) response.getBody().get("info");
            Map<String, String> paymentUrl = (Map<String, String>) info.get("paymentUrl");
            return paymentUrl.get("web");
        }
        throw new RuntimeException("Failed to request payment.");
    }

    public String requestPaymentImmediate(Integer userId) {
        String url = apiUrl + "/request";
        //表頭生成
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-LINE-ChannelId", channelId);
        headers.set("X-LINE-ChannelSecret", channelSecret);
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        

	    // 取得訂單流水號
        Optional<CouponsOrders>lastOrder = couponsOrdersRepository.findTopByUserIdAndStatusIsFalseOrderByOrderIdDesc(userId);

        getLastOrder = lastOrder.get();
        Integer storeId=getLastOrder.getStoreId();
        Integer LastOrderId=getLastOrder.getOrderId();
        

	    // 生成完整訂單編號
	    String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    String userPart = String.format("%04d", userId);
	    String storePart = String.format("%04d", storeId);
	    String LastOrderIdPart=String.format("%06d", LastOrderId);
	    String payOrderId = datePart + userPart + storePart + LastOrderIdPart;
	    
	    price=getLastOrder.getPrice();
	    quantity=getLastOrder.getQuantity();
	    totalAmount=price*quantity;
       
        Map<String, Object> body = new HashMap<>();
        body.put("amount", totalAmount);
        body.put("currency", "TWD");
        body.put("confirmUrl", confirmUrl);
        body.put("productName", productName);
        body.put("productImageUrl", productImageUrl);
        body.put("orderId", payOrderId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> info = (Map<String, Object>) response.getBody().get("info");
            Map<String, String> paymentUrl = (Map<String, String>) info.get("paymentUrl");
            return paymentUrl.get("web");
        }
        throw new RuntimeException("Failed to request payment.");
    }
    
    

    public void confirmPayment(long transactionId) {
    	
    	String url = apiUrl + "/" + transactionId + "/confirm";
    	System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-LINE-ChannelId", channelId);
        headers.set("X-LINE-ChannelSecret", channelSecret);
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        
        Map<String, Object> body = new HashMap<>();
        body.put("amount", totalAmount);
        body.put("currency", "TWD");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        
        getLastOrder.setStatus(true);
        couponsOrdersRepository.save(getLastOrder);
       
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to confirm payment.");
        }
    }
}
