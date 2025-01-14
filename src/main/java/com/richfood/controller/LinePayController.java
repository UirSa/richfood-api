package com.richfood.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.dto.ConfirmPaymentRequest;
import com.richfood.dto.PaymentRequest;
import com.richfood.service.LinePayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/linepay")
public class LinePayController {

    private final LinePayService linePayService;

    public LinePayController(LinePayService linePayService) {
        this.linePayService = linePayService;
    }
    //這裡的功能是為了取得付款網址 前端需跳轉 取得的字串才能付款 要求只要有總金額跟訂單編號兩項資料進來就可以了，
//    @PostMapping("/request")
//    public ResponseEntity<String> requestPayment(@RequestBody PaymentRequest request) {
//       
//        String paymentUrl = linePayService.requestPayment(
//            request.getAmount(),//總價格
//            request.getOrderId()
//        );
//        return ResponseEntity.ok(paymentUrl);
//    }
    
    @GetMapping("/confirm")
    public void confirmPayment(@RequestParam("transactionId") long transactionId,HttpServletResponse response) {
    	System.out.println(transactionId);
        linePayService.confirmPayment(transactionId);
        
        String redirectUrl = "http://localhost:9000/payment-success";//付款完跳轉回首頁之類的
        try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
    }
    
}
