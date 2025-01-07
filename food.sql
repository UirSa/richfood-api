-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2024-12-31 03:24:53
-- 伺服器版本： 10.4.32-MariaDB
-- PHP 版本： 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `food`
--

-- --------------------------------------------------------

--
-- 資料表結構 `business_hours`
--

CREATE TABLE `business_hours` (
  `restaurant_id` int(11) NOT NULL,
  `day_of_week` varchar(20) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `business_hours`
--

INSERT INTO `business_hours` (`restaurant_id`, `day_of_week`, `start_time`, `end_time`) VALUES
(1, '星期一', '11:00:00', '00:00:00'),
(1, '星期三', '11:00:00', '00:00:00'),
(1, '星期二', '11:00:00', '00:00:00'),
(1, '星期五', '11:00:00', '00:00:00'),
(1, '星期六', '11:00:00', '00:00:00'),
(1, '星期四', '11:00:00', '00:00:00'),
(1, '星期日', '11:00:00', '00:00:00'),
(2, '星期一', '11:00:00', '14:00:00'),
(2, '星期一', '17:00:00', '21:00:00'),
(2, '星期三', '11:00:00', '14:00:00'),
(2, '星期三', '17:00:00', '21:00:00'),
(2, '星期二', '11:00:00', '14:00:00'),
(2, '星期二', '17:00:00', '21:00:00'),
(2, '星期五', '11:00:00', '14:00:00'),
(2, '星期五', '17:00:00', '21:00:00'),
(2, '星期六', '11:00:00', '14:00:00'),
(2, '星期六', '17:00:00', '21:00:00'),
(2, '星期四', '11:00:00', '14:00:00'),
(2, '星期四', '17:00:00', '21:00:00'),
(2, '星期日', '11:00:00', '14:00:00'),
(2, '星期日', '17:00:00', '21:00:00'),
(3, '星期一', '14:00:00', '23:00:00'),
(3, '星期三', '14:00:00', '23:00:00'),
(3, '星期二', '14:00:00', '23:00:00'),
(3, '星期五', '14:00:00', '23:00:00'),
(3, '星期六', '14:00:00', '23:00:00'),
(3, '星期四', '14:00:00', '23:00:00'),
(3, '星期日', '14:00:00', '23:00:00'),
(4, '星期一', '11:30:00', '14:00:00'),
(4, '星期一', '17:00:00', '20:30:00'),
(4, '星期三', '11:30:00', '14:00:00'),
(4, '星期三', '17:00:00', '20:30:00'),
(4, '星期二', '11:30:00', '14:00:00'),
(4, '星期二', '17:00:00', '20:30:00'),
(4, '星期五', '11:30:00', '14:00:00'),
(4, '星期五', '17:00:00', '20:30:00'),
(4, '星期六', '11:30:00', '14:30:00'),
(4, '星期六', '17:00:00', '20:30:00'),
(4, '星期四', '11:30:00', '14:00:00'),
(4, '星期四', '17:00:00', '20:30:00'),
(4, '星期日', '11:30:00', '14:30:00'),
(4, '星期日', '17:00:00', '20:30:00'),
(5, '星期一', '11:00:00', '00:00:00'),
(5, '星期三', '11:00:00', '00:00:00'),
(5, '星期二', '11:00:00', '00:00:00'),
(5, '星期五', '11:00:00', '00:00:00'),
(5, '星期六', '11:00:00', '00:00:00'),
(5, '星期四', '11:00:00', '00:00:00'),
(5, '星期日', '11:00:00', '00:00:00'),
(6, '星期一', '08:30:00', '22:00:00'),
(6, '星期三', '08:30:00', '22:00:00'),
(6, '星期二', '08:30:00', '22:00:00'),
(6, '星期五', '08:30:00', '22:00:00'),
(6, '星期六', '08:00:00', '22:00:00'),
(6, '星期四', '08:30:00', '22:00:00'),
(6, '星期日', '08:00:00', '22:00:00');

-- --------------------------------------------------------

--
-- 資料表結構 `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, '新開幕'),
(2, '火鍋'),
(3, '早午餐'),
(4, '小吃'),
(5, '餐酒館'),
(6, '酒吧'),
(7, '精緻高級'),
(8, '約會餐廳'),
(9, '甜點'),
(10, '燒肉'),
(11, '居酒屋'),
(12, '日本料理'),
(13, '義式料理'),
(14, '中式料理'),
(15, '韓式'),
(16, '泰式'),
(17, '港式料理'),
(18, '美式料理'),
(19, '冰品飲料'),
(20, '蛋糕'),
(21, '飲料店'),
(22, '吃到飽'),
(23, '和菜'),
(24, '牛肉麵'),
(25, '牛排'),
(26, '咖啡'),
(27, '素食'),
(28, '寵物友善'),
(29, '景觀餐廳'),
(30, '親子餐廳'),
(31, '拉麵'),
(32, '咖哩'),
(33, '宵夜'),
(34, '早餐'),
(35, '午餐'),
(36, '晚餐'),
(37, '下午茶');

-- --------------------------------------------------------

--
-- 資料表結構 `restaurants`
--

CREATE TABLE `restaurants` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `country` varchar(100) NOT NULL,
  `district` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `score` decimal(3,2) NOT NULL,
  `average` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `restaurants`
--

INSERT INTO `restaurants` (`id`, `name`, `description`, `country`, `district`, `address`, `score`, `average`, `image`, `phone`) VALUES
(1, '老井極上燒肉 崇德店', '“炭火炙燒，肉品新鮮美味，附餐豐富，還有握壽司、烤雞肉串、飲料無限暢飲。”', '臺中市', '北屯區', '崇德路二段256號', 4.80, 1900, 'https://lh3.googleusercontent.com/UIC6wnaeNyCJB9-maM_umXObYF69KtAVvy3fh1kVW0wki9zPMZ9l3uSv0PV5GYFrRKRP6RjTJaWJQ7hBNxm3o46YrsBHJj71OAIzjpFTqTHf=s200', '0422478590'),
(2, '佐原拉麵', '“佐原拉麵以香濃豚骨湯頭和超值叉燒飯聞名，鄰近學區，是台中北區高CP值的日式拉麵首選。”', '臺中市', '北區', '五權路379號', 4.90, 280, 'https://lh3.googleusercontent.com/n22rvIHdqPh2gLVyAzBEJEN75_Rl4Fw9smomO4s4JhoL7bqerjvwVJbe6lom6OV4Sz9cpkbrENLKIHLMuz7SJY_bAgJXy_Q=s200', '0422021198'),
(3, '天使雞排 一中店', '“天使雞排以厚實多汁的雞排聞名，在台中一中街擁有高人氣，網路票選更榮獲全台第一名，是許多人宵夜首選。”', '臺中市', '北區', '一中街60號', 4.90, 140, 'https://lh3.googleusercontent.com/qTQStAUeou4MY7ylidcsXIf59WN6zW33qZhp0sGSHEP4rJVdeKmNJmwmCMBYTGRtvwTjtSjgg2q4CdUwJrB0_uqUhBzhpQo=s200', '0902020622'),
(4, '嵐山熟成牛かつ專売 台中西區健行店', '“台中人氣炸牛排專賣店，主打浮誇系布丁，還有季節限定草莓甜點及夏季限定飲品，滿足味蕾與視覺享受。”', '臺中市', '西區', '健行路1058號', 4.80, 814, 'https://lh3.googleusercontent.com/faN5tFwuMDVBSNFC5dQ1oXtJLiq6btpImMS1gZAsaJwfd1wizgh02H_MuMppHmt6SUV1x_YDI7A2J6O5zor61R7SP_b7WUBhXc-Z9MINPYIg=s200', '0423230222'),
(5, '屋馬燒肉 中友店', '“台中超人氣燒肉店，提供多樣化套餐與隱藏版美食，深夜營業滿足您的味蕾。”', '台中市', '北區', '育才北路69號', 4.70, 1300, 'https://lh3.googleusercontent.com/-LqD0FUYYy9EvEDpN_QKBec_w3roUNcSmLGEVdZGG0SOVDChkj-rprwhMPYW8lZ5NLclo9sm7iHh1bDsF9kzOQW0iZBodQ=s200', '0422260888'),
(6, 'Burger Joint 7分so 美式廚房 - 崇德店', '“舒適環境，現做美式風味，經典漢堡、多樣餐點，適合全家大小。”', '臺中市', '北區', '崇德路一段518號', 4.80, 300, 'https://lh3.googleusercontent.com/djk68Wa6c5HSoXGcJo-9bVEXdHQimmC09SYLaUet8k09Ye4FHdmgox5njJtYHVkS686VrRbz_6t4Tp3G814tGDmMQCHVxrA=s200', '0422373939');

-- --------------------------------------------------------

--
-- 資料表結構 `restaurant_categories`
--

CREATE TABLE `restaurant_categories` (
  `restaurant_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `restaurant_categories`
--

INSERT INTO `restaurant_categories` (`restaurant_id`, `category_id`) VALUES
(1, 10),
(1, 12),
(1, 33),
(1, 35),
(2, 12),
(2, 31),
(2, 35),
(2, 36),
(3, 4),
(3, 36),
(4, 8),
(4, 10),
(4, 12),
(4, 25),
(5, 8),
(5, 10),
(5, 33),
(5, 35),
(6, 18),
(6, 35),
(6, 36),
(6, 37);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `business_hours`
--
ALTER TABLE `business_hours`
  ADD PRIMARY KEY (`restaurant_id`,`day_of_week`,`start_time`),
  ADD KEY `restaurant_id` (`restaurant_id`);

--
-- 資料表索引 `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- 資料表索引 `restaurants`
--
ALTER TABLE `restaurants`
  ADD PRIMARY KEY (`id`);

--
-- 資料表索引 `restaurant_categories`
--
ALTER TABLE `restaurant_categories`
  ADD PRIMARY KEY (`restaurant_id`,`category_id`),
  ADD KEY `restaurant_id` (`restaurant_id`),
  ADD KEY `category_id` (`category_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `restaurants`
--
ALTER TABLE `restaurants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `business_hours`
--
ALTER TABLE `business_hours`
  ADD CONSTRAINT `bhfk_restaurantid` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的限制式 `restaurant_categories`
--
ALTER TABLE `restaurant_categories`
  ADD CONSTRAINT `rc_fk_cate_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rc_fk_rest_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
