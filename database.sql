CREATE DATABASE IF NOT EXISTS `amela_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;
USE `amela_management`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: amela_management
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `attendance_tbl`
--

DROP TABLE IF EXISTS `attendance_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance_tbl`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `check_in_time`  time(6)      DEFAULT NULL,
    `check_out_time` time(6)      DEFAULT NULL,
    `created_by`     bigint       DEFAULT NULL,
    `status`         bit(1) NOT NULL,
    `update_at`      datetime(6)  DEFAULT NULL,
    `update_by`      bigint       DEFAULT NULL,
    `user_id`        bigint       DEFAULT NULL,
    `check_day`      date         DEFAULT NULL,
    `note`           varchar(255) DEFAULT NULL,
    `created_at`     datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKl5aaa4t4uqjf85g0aai4hdtsv` (`user_id`),
    CONSTRAINT `FKl5aaa4t4uqjf85g0aai4hdtsv` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 29
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_tbl`
--

LOCK TABLES `attendance_tbl` WRITE;
/*!40000 ALTER TABLE `attendance_tbl`
    DISABLE KEYS */;
INSERT INTO `attendance_tbl`
VALUES (1, '18:37:31.009294', '23:46:20.280276', 1, _binary '', '2024-05-03 23:07:27.567195', 1, 1, '2024-04-25', NULL,
        NULL),
       (2, '08:11:07.737933', '14:39:06.925546', 1, _binary '', '2024-04-26 08:11:07.737933', 1, 1, '2024-04-26', NULL,
        NULL),
       (3, '20:04:40.681616', '22:39:06.925546', 1, _binary '', '2024-05-02 10:17:36.579848', 3, 1, '2024-04-28', NULL,
        NULL),
       (4, '10:41:17.535097', '14:39:06.925546', 1, _binary '', '2024-05-02 09:14:40.408413', 4, 1, '2024-04-30', NULL,
        NULL),
       (5, '14:04:54.136978', '20:58:07.890358', 1, _binary '', '2024-05-01 14:04:54.136978', 1, 1, '2024-05-01', NULL,
        NULL),
       (7, '10:14:00.000000', '23:11:00.000000', 1, _binary '', '2024-05-03 22:06:11.422220', 1, 1, '2024-05-02', '',
        NULL),
       (8, '10:47:00.000000', '23:02:21.768270', 1, _binary '', '2024-05-03 14:32:13.591562', 8, 1, '2024-05-03', '',
        NULL),
       (9, '13:51:00.000000', '15:57:32.558129', 1, _binary '', '2024-05-04 13:37:11.178143', 1, 1, '2024-05-04',
        'Hôm nay tôi đang test\r\n', NULL),
       (10, '08:00:00.000000', '17:00:00.000000', 1, _binary '', '2024-05-03 14:14:59.772221', 1, 4, '2024-05-03', '',
        NULL),
       (11, '08:00:00.000000', '17:00:00.000000', 1, _binary '', '2024-05-03 14:22:01.197915', 1, 4, '2024-05-04',
        'Hôm nay là onboard training', NULL),
       (12, '15:58:00.000000', '19:31:15.779000', 1, _binary '', '2024-05-13 23:31:05.424714', 1, 4, '2024-05-10', '',
        NULL),
       (14, '10:32:37.076703', '16:30:43.768787', 1, _binary '', '2024-05-06 10:32:37.076703', 1, 1, '2024-05-06',
        NULL, NULL),
       (15, '18:52:32.094787', '18:52:34.693562', 1, _binary '', '2024-05-07 18:52:32.094787', 1, 1, '2024-05-07',
        NULL, NULL),
       (16, '15:25:56.973488', '15:27:19.029006', 1, _binary '', '2024-05-08 15:25:56.973488', 1, 1, '2024-05-08',
        NULL, NULL),
       (17, '14:23:33.923507', '20:27:12.453695', 1, _binary '', '2024-05-09 14:23:33.923507', 1, 1, '2024-05-09',
        NULL, NULL),
       (18, '17:03:31.469502', '17:18:07.577851', 4, _binary '', '2024-05-09 17:03:31.469502', 4, 4, '2024-05-09',
        NULL, NULL),
       (19, '10:38:18.577764', '10:38:41.234974', 8, _binary '', '2024-05-10 10:38:18.577764', 8, 8, '2024-05-10',
        NULL, NULL),
       (22, '15:02:00.000000', '15:03:00.000000', 10, _binary '', '2024-05-13 15:03:30.937642', 10, 10, '2024-05-13',
        '', NULL),
       (23, '15:17:00.000000', '16:36:00.000000', 1, _binary '', '2024-05-13 23:23:03.654266', 1, 1, '2024-05-13', '',
        NULL),
       (24, '08:00:00.000000', '17:30:00.000000', 1, _binary '', '2024-05-13 23:26:47.485257', 1, 1, '2024-05-12',
        'Tạo mới chấm công', '2024-05-13 22:35:03.047576'),
       (25, '00:11:00.000000', '11:45:00.000000', 1, _binary '', '2024-05-14 11:45:56.563711', 1, 1, '2024-05-14', '',
        NULL),
       (26, '11:29:25.158000', '11:32:02.320000', 8, _binary '', '2024-05-14 11:29:25.158406', 8, 8, '2024-05-14',
        NULL, NULL),
       (27, '16:42:37.308000', '16:42:39.722000', 1, _binary '', '2024-05-15 16:42:37.307632', 1, 1, '2024-05-15',
        NULL, NULL),
       (28, '11:12:38.224000', '11:12:39.444000', 13, _binary '', '2024-05-16 11:12:38.225007', 13, 13, '2024-05-16',
        NULL, NULL);
/*!40000 ALTER TABLE `attendance_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_tbl`
--

DROP TABLE IF EXISTS `department_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department_tbl`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6)  DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `status`      bit(1) NOT NULL,
    `update_at`   datetime(6)  DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_tbl`
--

LOCK TABLES `department_tbl` WRITE;
/*!40000 ALTER TABLE `department_tbl`
    DISABLE KEYS */;
INSERT INTO `department_tbl`
VALUES (1, '2024-05-03 10:47:00.000000', 1, 'description', 'Faderless', _binary '', '2024-05-04 08:52:43.225823', 1),
       (3, '2024-05-03 10:47:00.000000', 1, 'description', 'Hades', _binary '', '2024-05-03 10:47:00.000000', 1),
       (4, '2024-05-03 10:47:00.000000', 1, 'description', 'Warrior', _binary '', '2024-05-03 10:47:00.000000', 1),
       (5, '2024-05-14 10:11:20.083520', 1, 'description', 'Phoneix', _binary '', '2024-05-14 10:11:20.083520', 1),
       (6, '2024-05-10 09:38:18.944593', 1, 'Write here', 'Boom', _binary '', '2024-05-10 09:38:18.944593', 1);
/*!40000 ALTER TABLE `department_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_tbl`
--

DROP TABLE IF EXISTS `group_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_tbl`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6)  DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `status`      bit(1) NOT NULL,
    `update_at`   datetime(6)  DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_tbl`
--

LOCK TABLES `group_tbl` WRITE;
/*!40000 ALTER TABLE `group_tbl`
    DISABLE KEYS */;
INSERT INTO `group_tbl`
VALUES (1, '2024-04-30 16:04:49.237322', 1, 'description here', 'Group Faderless Department', _binary '',
        '2024-05-14 17:03:23.532164', 1),
       (3, '2024-05-01 20:59:16.923127', 1, 'description here', 'Group Company', _binary '',
        '2024-05-16 14:26:41.726854', 1);
/*!40000 ALTER TABLE `group_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_position_tbl`
--

DROP TABLE IF EXISTS `job_position_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_position_tbl`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6)  DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `status`      bit(1) NOT NULL,
    `update_at`   datetime(6)  DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_position_tbl`
--

LOCK TABLES `job_position_tbl` WRITE;
/*!40000 ALTER TABLE `job_position_tbl`
    DISABLE KEYS */;
INSERT INTO `job_position_tbl`
VALUES (1, '2024-05-14 08:04:57.239078', 1, 'description', 'Developer', _binary '', '2024-05-14 08:04:57.239078', 1),
       (3, '2024-05-03 10:47:00.000000', 1, 'description', 'Tester', _binary '', '2024-05-03 10:47:00.000000', 1),
       (4, '2024-05-03 10:47:00.000000', 1, 'description', 'Bussiness analyst', _binary '',
        '2024-05-03 10:47:00.000000', 1),
       (5, '2024-05-14 10:09:14.888023', 1, 'description', 'Human Resources', _binary '', '2024-05-14 10:09:14.888023',
        1),
       (6, '2024-05-09 20:27:56.584931', 1, 'Write here ...', 'It Comtor', _binary '', '2024-05-14 08:51:18.734442',
        1);
/*!40000 ALTER TABLE `job_position_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_schedule_tbl`
--

DROP TABLE IF EXISTS `message_schedule_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message_schedule_tbl`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6)  DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `message`     text,
    `sender_name` varchar(255) DEFAULT NULL,
    `status`      bit(1) NOT NULL,
    `subject`     varchar(255) DEFAULT NULL,
    `update_at`   datetime(6)  DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    `group_id`    bigint       DEFAULT NULL,
    `publish_at`  datetime(6)  DEFAULT NULL,
    `viewers`     bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjgr0cguu1cli54k1jmd4xtgs1` (`group_id`),
    CONSTRAINT `FKjgr0cguu1cli54k1jmd4xtgs1` FOREIGN KEY (`group_id`) REFERENCES `group_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 55
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_schedule_tbl`
--

LOCK TABLES `message_schedule_tbl` WRITE;
/*!40000 ALTER TABLE `message_schedule_tbl`
    DISABLE KEYS */;
INSERT INTO `message_schedule_tbl`
VALUES (14, '2024-05-05 20:16:01.604474', 1,
        '<p><br><strong>Kính gửi Quý khách hàng NGUYEN TIEN KHOI !</strong><br><br>ACB trân trọng cảm ơn Quý khách đã đăng ký và sử dụng dịch vụ Ngân hàng số.<br><br>Mật khẩu đăng nhập tạm thời dịch vụ Ngân hàng số đã được gửi đến số điện thoại mà Quý khách đã đăng ký với Ngân hàng.<br><br>Hoặc Quý khách có thể xem thông tin mật khẩu đăng nhập tạm thời dịch vụ Ngân hàng số trong tập tin đính kèm email này.<br><br>Mật khẩu mở tập tin: <strong>6 ký tự sau cùng</strong> của số CMND/CCCD/hộ chiếu của quý khách.<br><br><i>Ví dụ: Số CMND của Quý khách là 075184<strong>123456</strong></i>, vậy mật khẩu mở tập tin của Quý khách là 6 ký tự sau cùng là <strong>123456</strong><br><br>Quý khách vui lòng truy cập trang Ngân hàng số của ACB tại <a href=\"https://online.acb.com.vn/\">https://online.acb.com.vn</a> hoặc ứng dụng ACB ONE của ACB sử dụng tên truy cập và mật khẩu tạm thời để đăng nhập.<br><br>Sau khi đăng nhập, Quý khách cần thực hiện thay đổi lại mật khẩu mới để đảm bảo bảo mật thông tin giao dịch của Quý khách.<br><br>&nbsp;</p><p><strong>Thông tin quan trọng!</strong></p><ul><li>Xin vui lòng bảo mật thông tin về tên truy cập và mật khẩu đăng nhập của Quý khách.</li><li>Không chia sẻ thông tin tài khoản này với bất kỳ ai dưới bất kỳ hình thức nào.</li><li>Xin vui lòng thận trọng với tình trạng website giả mạo ngân hàng. Quý khách chỉ thực hiện đăng nhập vào đúng địa chỉ <a href=\"https://online.acb.com.vn/\">https://online.acb.com.vn</a> hoặc ứng dụng ACB ONE.</li><li>Để tìm hiểu thêm về những biện pháp an toàn khi thực hiện giao dịch Ngân hàng số ACB ONE, Quý khách vui lòng tham khảo thêm các hướng dẫn tại <a href=\"https://www.acb.com.vn/\">www.acb.com.vn.</a></li></ul><p><br>Đây là email thông báo tự động, Quý khách vui lòng không phản hồi email này.<br><br>Thông tin liên hệ, hỗ trợ: Trung Tâm Dịch Vụ Khách Hàng - Contact Center 24/7 của ACB theo số 1900 54 54 86 - 028.38247247.<br><br>Trân trọng.<br><br>&nbsp;</p><p>----------------------------------------------------------------------------------------------------------------------------------------------------------------</p><p><br><br><i><strong>Dear Mr./Ms. NGUYEN TIEN KHOI !</strong></i><br><br><i>Thank you for signing up and using our internet banking service.</i><br><br><i>Your temporary password to access our internet banking has been sent to your cellphone number registered with ACB. Or you may find the temporary password in this email attachment.</i><br><br><i>Password for the encrypted attachment: <strong>the last 06 digits</strong> of your ID/Passport number.</i><br><br><i>Example: If your ID number is 075184<strong>123456</strong>, the password shall be the last 06 digits of Your ID number,<strong>123456</strong>.</i><br><br><i>Please use your username and temporary password to log into your ACB ONE account at </i><a href=\"https://online.acb.com.vn/\"><i>https://online.acb.com.vn</i></a><i> or via ACB ONE App.</i><br><br><i>After successfully signing in, you are required to change the password for your information security.</i><br><br>&nbsp;</p><p><i><strong>Important notice!</strong></i></p><ul><li><i>Please keep Your username and password secured.</i></li><li><i>Do not share this account information with anyone in any forms.</i></li><li><i>Please beware of fake/fraudulent/scam/phishing websites. You must only log into website </i><a href=\"https://online.acb.com.vn/\"><i>https://online.acb.com.vn</i></a><i> or ACB ONE App to use ACB internet banking service.</i></li><li><i>For more information about security measures of online banking transactions, please go to </i><a href=\"https://www.acb.com.vn/\"><i>www.acb.com.vn.</i></a></li></ul><p><br><i>This is an automated email, please do not reply to it.</i><br><br><i>Our 24/7 Contact Center: 1900 54 54 86 – 028. 38247247.</i><br><br><i>Sincerely yours.</i></p>',
        'Administrator', _binary '', 'Cap lai mat khau Ngan hang so ACB ONE', '2024-05-09 08:41:14.615453', 1, NULL,
        '2024-05-10 20:15:00.000000', 26),
       (15, '2024-05-05 20:36:42.637774', 1,
        '<p><i>Bạn đang đọc quyển sách này vì hai lý do. Thứ nhất, bạn là một lập trình viên. Thứ hai, bạn muốn trở thành một lập trình viên giỏi. Tuyệt vời! Chúng tôi cần lập trình viên giỏi. ????</i></p><p><i>Đây là một quyển sách nói về cách để bạn code tốt hơn, và nó chứa đầy code. Chúng ta sẽ xem xét code từ nhiều phương diện, từ trên xuống dưới, từ dưới lên trên, và từ trong ra ngoài. Khi xong việc, chúng ta sẽ được biết thêm rất nhiều về code. Hơn nữa, chúng ta sẽ nói về sự khác biệt giữa code \"xịn\" (good code) và code \"rởm\" (bad code), biết cách tạo nên code \"xịn\", và học cách hô biến code \"rởm\" thành code \"xịn\".</i></p><h2><strong>Sẽ (vẫn) có code</strong></h2><p>Nhiều người cho rằng việc viết code, sau vài năm nữa sẽ không còn là vấn đề, rằng chúng ta nên quan tâm đến những mô hình và các yêu cầu. Thực tế, một số người cho rằng việc viết code đang dần đến lúc phải kết thúc, code sẽ được tạo ra thay vì được viết hay gõ. Và các lập trình viên \"gà mờ\" sẽ phải tìm công việc khác vì khách hàng của họ có thể tạo nên một chương trình chỉ bằng cách nhập vào các thông số cần thiết...</p><p>Oh sh*t, rõ là vô lý! Code sẽ không bao giờ bị loại bỏ vì chúng đại diện cho nội dung của các yêu cầu của khách hàng. Ở một mức độ nào đó, những nội dung đó không thể bỏ qua hoặc trừu tượng hóa; chúng phải được thiết lập. Việc thiết lập các nội dung mà máy tính có thể hiểu và thi hành, được gọi là <i>lập trình,</i> và <i>lập trình</i> thì cần có <i>code.</i></p><p>Tôi hy vọng mức độ trừu tượng của các ngôn ngữ lập trình và số lượng các DSL (Domain-Specific Language – ngôn ngữ chuyên biệt dành cho các vấn đề cụ thể) sẽ tăng lên. Đó là một dấu hiệu tốt. Nhưng dù điều đó xảy ra, nó vẫn không \"đá đít\" code. Các đặc điểm kỹ thuật được viết bằng những ngôn ngữ bậc cao và DSL vẫn là code. Nó vẫn cần sự nghiêm ngặt, chính xác, và theo đúng các nguyên tắc, tường tận đến nỗi một cỗ máy có thể hiểu và thực thi nó.</p><p>Những người nghĩ rằng việc viết code đang đi đến ngày tàn cũng giống như việc một nhà toán học hy vọng khám phá ra một thể loại toán học mới không chứa nguyên tắc, định lý hay bất kỳ công thức nào. Họ hy vọng một ngày nào đó, các lập trình viên sẽ tạo ra những cỗ máy có thể làm bất cứ điều gì họ muốn (chứ không cần ra lệnh bằng giọng nói). Những cỗ máy này phải có khả năng hiểu họ tốt đến nỗi, chúng có khả năng dịch những yêu cầu mơ hồ thành các chương trình hoàn hảo, đáp ứng chính xác những yêu cầu đó.</p><p>Dĩ nhiên, chuyện đó chỉ xảy ra trong phim viễn tưởng. Ngay cả con người, với tất cả các giác quan và sự sáng tạo, cũng không thể thành công trong việc hiểu những cảm xúc mơ hồ của người khác. Thật vậy, nếu được hỏi quá trình phân tích các yêu cầu của khách hàng đã dạy cho chúng tôi điều gì, thì câu trả lời là các yêu cầu được chỉ định rõ ràng, trông giống như code và có thể hoạt động trong quá trình kiểm tra.</p><p>Hãy nhớ một điều rằng code thực sự là một ngôn ngữ mà trong đó, công việc cuối cùng của chúng ta là thể hiện các yêu cầu. Chúng tôi có thể tạo ra các ngôn ngữ gần với các yêu cầu, hoặc tạo ra các công cụ cho phép phân tích cú pháp và lắp ráp chúng vào các chương trình. Nhưng chúng tôi sẽ không bao giờ bỏ qua các yêu cầu cần thiết – vì vậy, code sẽ luôn tồn tại.</p><h2><strong>Code tồi, Code \"rởm\"</strong></h2><p>Gần đây, tôi có đọc phần mở đầu của quyển <i>Implementation Patterns.1</i> của Kent Beck. Ông ấy nói rằng <i>\"...cuốn sách này dựa trên một tiền đề khá mong manh: đó là vấn đề code sạch...\"</i> Mong manh ư? Tôi không đồng ý chút nào. Tôi nghĩ tiền đề đó là một trong những tiền đề mạnh mẽ nhất, nhận được sự ủng hộ lớn nhất từ các nhân viên (và tôi nghĩ là Kent biết điều đó). Chúng tôi biết các vấn đề về code sạch vì chúng tôi đã phải đối mặt với nó quá lâu rồi.</p><p>Tôi có biết một công ty, vào cuối những năm 80, đã phát hành một ứng dụng <i>X.</i> Nó rất phổ biến, và nhiều chuyên gia đã mua và sử dụng nó. Nhưng sau đó, các chu kỳ cập nhật bắt đầu bị kéo dài ra, nhiều lỗi thì không được sửa từ phiên bản này qua phiên bản khác, thời gian tải và sự cố cũng theo đó mà tăng lên. Tôi vẫn nhớ ngày mà tôi đã ngưng sử dụng sản phẩm trong sự thất vọng và không dùng lại nó một lần nào nữa. Chỉ một thời gian sau, công ty đó cũng ngừng hoạt động.</p><p>Hai mươi năm sau, tôi gặp một trong những nhân viên ban đầu của công ty đó và hỏi anh ta chuyện gì đã xảy ra. Câu trả lời đã khiến tôi lo sợ : Họ đưa sản phẩm ra thị trường cùng với một đống code hỗn độn trong đó. Khi các tính năng mới được thêm vào ngày càng nhiều, code của chương trình lại ngày càng tệ, tệ đến mức họ không thể kiểm soát được nữa, và đặt dấu chấm hết cho công ty. Và, tôi gọi những dòng code đó là code \"rởm\".</p><p>Bạn đã bao giờ bị những dòng code \"rởm\" gây khó dễ chưa? Nếu là lập trình viên hẳn bạn đã từng trải nghiệm cảm giác đó vài lần. Chúng tôi có một cái tên cho nó, đó là <i>bơi</i> (từ gốc: wade – làm việc vất vả). Chúng tôi <i>bơi</i> qua những dòng code tởm lợm, <i>bơi</i> trong một mớ lộn xộn những cái bug được giấu kín. Chúng tôi cố gắng theo cách của chúng tôi, hy vọng tìm thấy những gợi ý, những manh mối hay biết chuyện gì đang xảy ra với code; nhưng tất cả những gì chúng tôi thấy là những dòng code ngày càng vô nghĩa.</p><p>Nếu bạn đã từng bị những dòng code \"rởm\" cản trở như tôi miêu tả, vậy thì – tại sao bạn lại tạo ra nó?</p><p>Bạn đã thử đi nhanh? Bạn đã vội vàng? Có lẽ vậy thật. Hoặc bạn cảm thấy bạn không có đủ thời gian để hoàn thành nó; hay sếp sẽ nổi điên với bạn nếu bạn dành thời gian để dọn dẹp đống code lộn xộn đó. Cũng có lẽ bạn đã quá mệt mỏi với cái chương trình chết tiệt này và muốn kết thúc nó ngay. Hoặc có thể bạn đã xem xét phần tồn đọng của những thứ khác mà bạn đã hứa sẽ hoàn thành và nhận ra rằng bạn cần phải kết hợp module này với nhau để bạn có thể chuyển sang phần tiếp theo. Yeah! Chúng ta đã tạo ra con quỷ như thế đó.</p><p>Tất cả chúng ta đều nhìn vào đống lộn xộn mà chúng ta vừa tạo ra, và chọn <i>một ngày đẹp trời nào đó</i> để giải quyết nó. Tất cả chúng ta đều cảm thấy nhẹ nhõm khi thấy \"chương trình lộn xộn\" của chúng ta hoạt động và cho rằng: thà có còn hơn không. Tất cả chúng ta cũng đã từng tự nhủ rằng, <i>sau này</i> chúng ta sẽ trở lại và dọn dẹp mớ hỗ lốn đó. Dĩ nhiên, trong những ngày như vậy chúng ta không biết đến quy luật của LeBlanc: <i>SAU NÀY đồng nghĩa với KHÔNG BAO GIỜ!</i></p><h2><strong>Cái giá của sự lộn xộn</strong></h2><p>Nếu bạn là một lập trình viên đã làm việc trong 2 hoặc 3 năm, rất có thể bạn đã bị mớ code lộn xộn của người khác kéo bạn lùi lại. Nếu bạn đã là một lập trình viên lâu hơn 3 năm, rất có thể bạn đã tự làm chậm sự phát triển của bản thân bằng đống code do bạn tạo ra. Trong khoảng 1 hoặc 2 năm, các đội đã di chuyển rất nhanh ngay từ khi bắt đầu một dự án, thay vì phải di chuyển thận trọng như cách họ nhìn nhận nó. Vì vậy, mọi thay đổi mà họ tác động lên code sẽ phá vỡ vài đoạn code khác. Không có thay đổi nào là không quan trọng. Mọi sự bổ sung hoặc thay đổi chương trình đều tạo ra các mớ boòng boong, các nút thắt,... Chúng ta cố gắng hiểu chúng chỉ để tạo ra thêm sự thay đổi, và lặp lại việc tạo ra chính chúng. Theo thời gian, code của chúng ta trở nên quá \"cao siêu\" mà không thành viên nào có thể hiểu nổi. Chúng ta không thể \"làm sạch\" chúng, hoàn toàn không có cách nào cả ?.</p><p>Khi đống code lộn xộn được tạo ra, hiệu suất của cả đội sẽ bắt đầu tuột dốc về phía 0. Khi hiệu suất giảm, người quản lý làm công việc của họ - đưa vào nhóm nhiều thành viên mới với hy vọng cải thiện tình trạng. Nhưng những nhân viên mới lại thường không nắm rõ cách hoạt động hoặc thiết kế của hệ thống, họ cũng không chắc thay đổi nào sẽ là phù hợp cho dự án. Hơn nữa, họ và những người cũ trong nhóm phải chịu áp lực khủng khiếp cho tình trạng tồi tệ của nhóm. Vậy là, càng làm việc, họ càng tạo ra nhiều code rối, và đưa cả nhóm (một lần nữa) dần tiến về cột mốc 0.</p><h3><strong>Đập đi xây lại</strong></h3><p>Cuối cùng, cả nhóm quyết định nổi loạn. Họ thông báo cho quản lý rằng họ không thể tiếp tục phát triển trên nền của đống code lộn xộn này nữa, rằng họ muốn <i>thiết kế lại dự án</i>. Dĩ nhiên ban quản lý không muốn mất thêm tài nguyên cho việc tái khởi động dự án, nhưng họ cũng không thể phủ nhận sự thật rằng hiệu suất làm việc của cả nhóm quá <i>tàn tạ</i>. Cuối cùng, họ chiều theo yêu cầu của các lập trình viên và cho phép bắt đầu lại dự án.</p><p>Một nhóm mới được chọn. Mọi người đều muốn tham gia nhóm này vì nó năng động và đầy sức sống. Nhưng chỉ những người giỏi nhất mới được chọn, những người khác phải tiếp tục duy trì dự án hiện tại.</p><p>Và bây giờ, hai nhóm đang trong một cuộc đua. Nhóm mới phải xây dựng một hệ thống mới với mọi chức năng của hệ thống cũ, không những vậy họ phải theo kịp với những thay đổi dành cho hệ thống cũ. Ban quản lý sẽ không thay thế hệ thống cũ cho đến khi hệ thống mới làm được tất cả công việc của hệ thống cũ đang làm.</p><p>Cuộc đua này có thể diễn ra trong một thời gian rất dài. Tôi đã từng thấy một cuộc đua như vậy, nó mất đến 10 năm để kết thúc. Và vào thời điểm đó, các thành viên ban đầu của <i>nhóm mới</i> đã nghỉ việc, và các thành viên hiện tại đang yêu cầu thiết kế lại hệ thống vì code của nó đã trở thành một mớ lộn xộn.</p><p>Nếu bạn đã từng trải qua, dù chỉ một phần nhỏ của câu chuyện bên trên, hẳn bạn đã biết rằng việc dành thời gian để giữ cho code sạch đẹp không chỉ là câu chuyện về chi phí, mà đó còn là vấn đề sống còn của lập trình viên chuyên nghiệp.</p><h3><strong>Thay đổi cách nhìn</strong></h3><p>Bạn đã bao giờ <i>bơi</i> trong một đống code lộn tùng phèo để nhận ra thay vì cần một giờ để xử lý nó, bạn lại tốn vài tuần? Hay thay vì ngồi lọ mọ sửa lỗi trong hàng trăm module, bạn chỉ cần tác động lên một dòng code. Nếu thật vậy, bạn không hề cô đơn, ngoài kia có hàng trăm ngàn lập trình viên như bạn.</p><p>Tại sao chuyện này lại xảy ra? Tại sao code đẹp lại nhanh chóng trở thành đống lộn xộn được? Chúng tôi có rất nhiều lời giải thích dành cho nó. Chúng tôi phàn nàn vì cho rằng các yêu cầu đã thay đổi theo hướng ngăn cản thiết kế ban đầu của hệ thống. Chúng tôi rên ư ử vì lịch làm việc quá bận rộn. Chúng tôi chửi rủa những nhà quản lý ngu ngốc và những khách hàng bảo thủ và cả những cách tiếp thị vô dụng. Nhưng thưa Dilbert, lỗi không nằm ở mục tiêu mà chúng ta hướng đến, lỗi nằm ở chính chúng ta, do chúng ta không chuyên nghiệp.</p><p>Đây có thể là một sự thật không mấy dễ chịu. Bằng cách nào những đống code rối tung rối mù này lại là lỗi của chúng tôi? Các yêu cầu vô lý thì sao? Còn lịch làm việc dày đặc? Và những tên quản lý ngu ngốc, hay các cách tiếp thị vô dụng – Không ai chịu trách nhiệm cả à?</p><p>Câu trả lời là KHÔNG. Các nhà quản lý và các nhà tiếp thị tìm đến chúng tôi vì họ cần thông tin để tạo ra những lời hứa và cam kết của chương trình; và ngay cả khi họ không tìm chúng tôi, chúng tôi cũng không ngại nói cho họ biết suy nghĩ của mình. Khách hàng tìm đến chúng tôi để xác thực các yêu cầu phù hợp với hệ thống. Các nhà quản lý tìm đến chúng tôi để giúp tạo ra những lịch trình làm việc phù hợp. Chúng tôi rất mệt mỏi trong việc lập kế hoạch cho dự án và phải nhận rất nhiều trách nhiệm khi thất bại, đặc biệt là những thất bại liên quan đến code lởm.</p><p>\"Nhưng khoan!\" – bạn nói – \"Nếu tôi không làm những gì mà sếp tôi bảo, tôi sẽ bị sa thải\". Ồ, không hẳn vậy đâu. Hầu hết những ông sếp đều muốn sự thật, ngay cả khi họ hành động trông không giống như vậy. Những ông sếp đều muốn chương trình được code đẹp, dù họ đang bị ám ảnh bởi lịch trình dày đặt. Họ có thể thay đổi lịch trình và cả những yêu cầu, đó là công việc của họ. Đó cũng là công việc <i>của bạn</i> – bảo vệ code bằng niềm đam mê.</p><p>Để giải thích điều này, hãy tưởng tượng bạn là bác sĩ và có một bệnh nhân yêu cầu bạn hãy ngưng việc rửa tay để chuẩn bị cho phẫu thuật, vì việc rửa tay mất quá nhiều thời gian. Rõ ràng bệnh nhân là thượng đế, nhưng bác sĩ sẽ luôn từ chối yêu cầu này. Tại sao? Bởi vì bác sĩ biết nhiều hơn bệnh nhân về những nguy cơ về bệnh tật và nhiễm trùng. Rõ là ngu ngốc khi bác sĩ lại đồng ý với những yêu cầu như vậy.</p><p>Tương tự như vậy, quá là nghiệp dư cho các lập trình viên luôn tuân theo các yêu cầu của sếp – những người không hề biết về nguy cơ của việc tạo ra một chương trình đầy code rối.</p><h3><strong>Vấn đề nan giải</strong></h3><p>Các lập trình viên phải đối mặt với một vấn đề nan giải về các giá trị cơ bản. Những lập trình viên với hơn 1 năm kinh nghiệm biết rằng đống code lộn xộn đó đã kéo họ xuống. Tuy nhiên, tất cả họ đều cảm thấy áp lực khi tìm cách giải quyết nó theo đúng hạn. Tóm lại, họ không dành thời gian để tạo nên hướng đi vững vàng.</p><p>Các chuyên gia thật sự biết rằng phần thứ hai của vấn đề là sai, đống code lộn xộn kia sẽ không thể giúp bạn hoàn thành công việc đúng hạn. Thật vậy, sự lộn xộn đó sẽ làm chậm bạn ngay lập tức, và buộc bạn phải trễ thời hạn. Cách duy nhất để hoàn thành đúng hạn – cách duy nhất để bước đi vững vàng – là giữ cho code luôn sạch sẽ nhất khi bạn còn có thể.</p><h3><strong>Kỹ thuật làm sạch code?</strong></h3><p>Giả sử bạn tin rằng code lởm là một chướng ngại đáng kể, giả sử bạn tin rằng cách duy nhất để có hướng đi vững vàng là giữ sạch code của bạn, thì bạn cần tự hỏi bản thân mình : \"Làm cách nào để viết code cho sạch?\". Nếu bạn không biết ý nghĩa của việc code sạch, tốt nhất bạn không nên viết nó.</p><p>Tin xấu là, việc tạo nên code sạch sẽ giống như cách chúng ta vẽ nên một bức tranh. Hầu hết chúng ta đều nhận ra đâu là tranh đẹp, đâu là tranh xấu – nhưng điều đó không có nghĩa là chúng ta biết cách vẽ. Vậy nên, việc bạn có thể lôi ra vài dòng code đẹp trong đống code lởm không có nghĩa là chúng ta biết cách viết nên những dòng code sạch.</p><p>Viết code sạch sẽ yêu cầu sự khổ luyện liên tục những kỹ thuật nhỏ khác nhau, và sự cần cù sẽ được đền đáp bằng cảm giác \"sạch sẽ\" của code. <i>Cảm giác (hay giác quan)</i> này chính là chìa khóa, một số người trong chúng ta được Chúa ban tặng ngay từ khi sinh ra, một số người khác thì phải đấu tranh để có được nó. Nó không chỉ cho phép chúng ta xem xét code đó là <i>xịn</i> hay <i>lởm,</i> mà còn cho chúng ta thấy những kỹ thuật đã được áp dụng như thế nào.</p><p>Một lập trình viên không có <i>giác quan code</i> sẽ không biết phải làm gì khi nhìn vào một đống code rối. Ngược lại, những người có <i>giác quan code</i> sẽ bắt đầu nhìn ra các cách để thay đổi nó. <i>Giác quan code</i> sẽ giúp lập trình viên chọn ra cách tốt nhất, và vạch ra con đường đúng đắn để hoàn thành công việc.</p><p>Tóm lại, một lập trình viên viết code \"sạch đẹp\" thật sự là một nghệ sĩ. Họ có thể tạo ra các hệ thống thân thiện chỉ từ một màn hình trống rỗng.</p><h3><strong>Code sạch là cái chi chi?</strong></h3><p>Có thể là có rất nhiều định nghĩa. Vì vậy, chúng tôi phỏng vấn một số lập trình viên nổi tiếng và giàu kinh nghiệm về khái niệm này:</p><p><strong>Bjarne Stroustrup – cha đẻ của ngôn ngữ C++, và là tác giả của quyển </strong><i><strong>The C++ Programming Language</strong></i><strong>:</strong></p><p>\"<i>Tôi thích code của tôi trông thanh lịch và hiệu quả. Sự logic nên được thể hiện rõ ràng để làm cho các lỗi khó lẫn trốn, sự phụ thuộc được giảm thiểu để dễ bảo trì, các lỗi được xử lý bằng các chiến lược rõ ràng, và hiệu năng thì gần như tối ưu để không lôi kéo người khác tạo nên code rối bằng những cách tối ưu hóa tạm bợ. Code sạch sẽ tạo nên những điều tuyệt vời\".</i></p><p>Bjarne sử dụng từ <i>thanh lịch.</i> Nó khá chính xác. Từ điển trong Macbook của tôi giải thích về nó như sau: vẻ đẹp duyên dáng hoặc phong cách dễ chịu, đơn giản nhưng <i>làm hài lòng</i> mọi người. Hãy chú ý đến nội dung <i>làm hài lòng.</i> Rõ ràng Bjarne cho rằng code sạch sẽ dễ đọc hơn. Đọc nó sẽ làm cho bạn mỉm cười nhẹ nhàng như một chiếc hộp nhạc.</p><p>Bjarne cũng đề cập đến sự hiệu quả – hai lần. Không có gì bất ngờ từ người phát minh ra C++, nhưng tôi nghĩ còn nhiều điều hơn là mong muốn đạt được hiệu suất tuyệt đối. Các tài nguyên bị lãng phí, chuyện đó chẳng dễ chịu chút nào. Và bây giờ hãy để ý đến từ mà Bjarne dùng để miêu tả hậu quả – <i>lôi kéo.</i> Có một sự thật là, code lởm \"thu hút\" những đống code lởm khác. Khi ai đó thay đổi đống code đó, họ có xu hướng làm cho nó tệ hơn.</p><p>[...]</p><p>Bjarne cũng đề cập đến việc xử lý lỗi phải được thực hiện đầy đủ. Điều này tạo nên thói quen chú ý đến từng chi tiết nhỏ. Việc xử lý lỗi qua loa sẽ khiến các lập trình viên bỏ qua các chi tiết nhỏ: nguy cơ tràn bộ nhớ, hiện tượng tranh giành dữ liệu (race condition), hay đặt tên không phù hợp,...Vậy nên, việc code sạch sẽ tạo được tính kỹ lưỡng cho các lập trình viên.</p><p>Bjarne kết thúc cuộc phỏng vấn bằng khẳng định <i>code sạch sẽ tạo nên những điều tuyệt vời.</i> Không phải ngẫu nhiên mà tôi lại nói – những nguyên tắc về thiết kế phần mềm được cô đọng lại trong lời khuyên đơn giản này. Tác giả sau khi viết đã cố gắng truyền đạt tư tưởng này. Code rởm đã tồn tại đủ lâu, và không có lý do gì để giữ nó tiếp tục. Bây giờ, code sạch sẽ được tập trung phát triển. Mỗi hàm, mỗi lớp, mỗi mô-đun thể hiện sự độc lập, và không bị <i>ô nhiễm</i> bởi những thứ quanh nó.</p><p><strong>Grady Booch, tác giả quyển Object Oriented Analysis and Design with Applications</strong></p><p>\"<i>Code sạch đơn giản và rõ ràng. Đọc nó giống như việc bạn đọc một đoạn văn xuôi. Code sạch sẽ thể hiện rõ ràng ý đồ của lập trình viên, đồng thời mô tả rõ sự trừu tượng và các dòng điều khiển đơn giản\".</i></p><p>[...]</p><p><strong>Dave Thomas, người sáng lập OTI, godfather of the Eclipse strategy:</strong></p><p>\"<i>Code sạch có thể được đọc và phát triển thêm bởi những lập trình viên khác. Nó đã được kiểm tra, nó có những cái tên ý nghĩa, nó cho bạn thấy cách để làm việc. Nó giảm thiểu sự phụ thuộc giữa các đối tượng với những định nghĩa rõ ràng, và cung cấp các API cần thiết. Code nên được hiểu theo cách diễn đạt, không phải tất cả thông tin cần thiết đều có thể được thể hiện rõ ràng chỉ bằng code\".</i></p><p>[...]</p><p><strong>Michael Feathers, tác giả quyển Working Effectively with Legacy Code:</strong></p><p>\"<i>Tôi có thể liệt kê tất cả những phẩm chất mà tôi thấy trong code sạch, nhưng tất cả chúng được bao quát bởi một điều – code sạch trông như được viết bởi những người tận tâm. Dĩ nhiên, bạn cho rằng bạn sẽ làm nó tốt hơn. Điều đó đã được họ (những người tạo ra code sạch) nghĩ đến, và nếu bạn cố gắng \"rặn\" ra những cải tiến, nó sẽ đưa bạn về lại vị trí ban đầu. Ngồi xuống và tôn trọng những dòng code mà ai đó đã để lại cho bạn – những dòng code được viết bởi một người đầy tâm huyết với nghề\".</i></p><p>[...]</p><p><strong>Ward Cunningham, người tạo ra Wiki:</strong></p><p>\"<i>Bạn biết bạn đang làm việc cùng code sạch là khi việc đọc code hóa ra yomost hơn những gì bạn mong đợi. Bạn có thể gọi nó là code đẹp khi những dòng code đó trông giống như cách mà bạn trình bày và giải quyết vấn đề\".</i></p><p>[...]</p><h2><strong>Những môn phái</strong></h2><p>Còn tôi (chú Bob) thì sao? Tôi nghĩ code sạch là gì? Cuốn sách này sẽ nói cho bạn biết, đảm bảo chi tiết đến mức mệt mỏi những gì tôi và các đồng nghiệp nghĩ về code sạch. Chúng tôi sẽ cho bạn biết những gì chúng tôi nghĩ về tên biến sạch, hàm sạch, lớp sạch,...Chúng tôi sẽ trình bày những ý kiến này dưới dạng tuyệt đối, và chúng tôi sẽ không xin lỗi vì sự ngông cuồng này. Đối với chúng tôi, ngay lúc này, điều đó là tuyệt đối. Đó chính là trường phái của chúng tôi về code sạch.</p><p>Không có môn võ nào là hay nhất, cũng không có kỹ thuật nào là \"vô đối\" trong võ thuật. Thường thì các võ sư bậc thầy sẽ hình thành trường phái riêng của họ và thu nhận đệ tử để truyền dạy. Vì vậy, chúng ta thấy Nhu thuật Brazil (Jiu Jitsu) được sáng tạo và truyền dạy bởi dòng tộc Gracie ở Brazil. Chúng ta thấy Hakko Ryu Jiu Jitsu (một môn nhu thuật của Nhật Bản) được thành lập và truyền dạy bởi Okuyama Ryuho ở Tokyo. Chúng ta thấy Triệt Quyền Đạo, được phát triển và truyền dạy bởi Lý Tiểu Long tại Hoa Kỳ.</p><p>Môn đồ của các môn phái này thường đắm mình trong những lời dạy của sư phụ. Họ dấn thân để khám phá kiến thức mà sư phụ dạy, và thường loại bỏ giáo lý của ông thầy khác. Sau đó, khi kỹ năng của họ phát triển, họ có thể tìm một sư phụ khác để mở rộng kiến thức và va chạm thực tế nhiều hơn. Một số khác tiếp tục hoàn thiện kỹ năng của mình, khám phá các kỹ thuật mới và thành lập võ đường của riêng họ.</p><p>Không một giáo lý của môn phái nào là đúng hoàn toàn. Tuy nhiên trong một môn phái, chúng ta chấp nhận những lời dạy và những kỹ thuật đó là đúng. Sau tất cả, vẫn có cách để áp dụng đúng Triệt Quyền Đạo hay Nhu thuật. Nhưng việc đó không làm những lời dạy của môn phái khác mất tác dụng.</p><p>Hãy xem quyển sách này là một quyển bí kíp về <i>Môn phái Code sạch.</i> Các kỹ thuật và lời khuyên bên trong giúp bạn thể hiện khả năng của mình. Chúng tôi sẵn sàng khẳng định nếu bạn làm theo những lời khuyên này, bạn sẽ được hưởng những lợi ích như chúng tôi, bạn sẽ học được cách tạo nên những dòng code sạch sẽ và đầy chuyên nghiệp. Nhưng làm ơn đừng nghĩ chúng tôi đúng tuyệt đối, còn có những bậc thầy khác, họ sẽ đòi hỏi bạn phải chuyên nghiệp hơn. Điều đó sẽ giúp bạn học hỏi khá nhiều từ họ đấy.</p><p>Sự thật là, nhiều lời khuyên trong quyển sách này đang gây tranh cãi. Bạn có thể không đồng ý với tất cả chúng, hoặc một vài trong số đó. Không sao, chúng tôi không thể yêu cầu việc đó được. Mặt khác, các lời khuyên trong sách là những thứ mà chúng tôi phải trải qua quá trình suy nghĩ lâu dài và đầy khó khăn mới có được. Chúng tôi đã học được nó qua hàng chục năm làm việc, thí nghiệm và sửa lỗi. Vậy nên, cho dù bạn đồng ý hay không, đó sẽ là hành động sỉ nhục nếu bạn không xem xét, và tôn trọng quan điểm của chúng tôi.</p><h2><strong>Chúng ta là tác giả</strong></h2><p>Trường <a href=\"https://hashnode.com/@author\"><i>@author</i></a> của Javadoc cho chúng ta biết chúng ta là ai – chúng ta là tác giả. Và tác giả thì phải có đọc giả. Tác giả có trách nhiệm giao tiếp tốt với các đọc giả của họ. Lần sau khi viết một dòng code, hãy nhớ rằng bạn là tác giả - đang viết cho những đọc giả, những người đánh giá sự cố gắng của bạn.</p><p>Và bạn hỏi: Có bao nhiêu code thật sự được đọc cơ chứ? Nỗ lực viết nó để làm gì?</p><p>Bạn đã bao giờ xem lại những lần chỉnh sửa code chưa? Trong những năm 80 và 90, chúng tôi đã có những chương trình như Emacs, cho phép theo dõi mọi thao tác bàn phím. Bạn nên làm việc trong một giờ rồi sau đó xem lại các phiên bản chỉnh sửa – như cách xem một bộ phim được tua nhanh. Và khi tôi làm điều này, kết quả thật bất ngờ.</p><p>Đa phần là hành động cuộn và điều hướng sang những mô-đun khác:</p><p><i>Bob vào mô-đun.</i></p><p><i>Anh ấy cuộn xuống chức năng cần thay đổi.</i></p><p><i>Anh ấy dừng lại, xem xét các biện pháp giải quyết.</i></p><p><i>Ồ, anh ấy cuộn lên đầu mô-đun để kiểm tra việc khởi tạo biến.</i></p><p><i>Bây giờ anh ta cuộn xuống và bắt đầu gõ.</i></p><p><i>Ooops, anh ấy xóa chúng rồi.</i></p><p><i>Anh ấy nhập lại.</i></p><p><i>Anh ấy lại xóa.</i></p><p><i>Anh ấy lại nhập một thứ gì đó, rồi lại xóa.</i></p><p><i>Anh ấy kéo xuống hàm khác đang gọi hàm mà anh ta chỉnh sửa để xem nó được gọi ra sao.</i></p><p><i>Anh ấy cuộn ngược lại, và gõ những gì anh vừa xóa.</i></p><p><i>Bob tạm ngưng.</i></p><p><i>Anh ta lại xóa nó.</i></p><p><i>Anh ta mở một cửa sổ khác và nhìn vào lớp con, xem hàm đó có bị ghi đè (overriding) hay không.</i></p><p>...</p><p>Thật sự lôi cuốn. Và chúng tôi nhận ra thời gian đọc code luôn gấp 10 lần thời gian viết code. Chúng tôi liên tục đọc lại code cũ như một phần trong những nỗ lực để tạo nên code mới.</p><p>Vì quá mất thời gian nên chúng tôi muốn việc đọc code trở nên dễ dàng hơn, ngay cả khi nó làm cho việc viết code khó hơn. Dĩ nhiên không có cách nào để viết code mà không đọc nó, do đó làm nó dễ đọc hơn, cũng là cách làm nó dễ viết hơn.</p><p>Không còn cách nào đâu. Bạn không thể mở rộng code nếu bạn không đọc được code. Code bạn viết hôm nay sẽ trở nên khó hoặc dễ mở rộng tùy vào cách viết của bạn. Vậy nên, nếu muốn chắc chắn, nếu muốn hoàn thành nhanh, nếu bạn muốn code dễ viết, dễ mở rộng, dễ thay đổi, hãy làm cho nó dễ đọc.</p><h2><strong>Nguyên tắc của hướng đạo sinh</strong></h2><p>Nhưng vẫn chưa đủ. Code phải được giữ sạch theo thời gian. Chúng ta đều thấy code \"bốc mùi\" và suy thoái theo thời gian. Vì vậy, chúng ta phải có hành động tích cực trong việc ngăn chặn sự suy thoái đó.</p><p>Các hướng đạo sinh của Mỹ có một nguyên tắc đơn giản mà chúng ta có thể áp dụng cho vấn đề này:</p><p><i>Khi bạn rời đi, khu cắm trại phải sạch sẽ hơn cả khi bạn đến.</i></p><p>Nếu chúng ta làm cho code sạch hơn mỗi khi chúng ta kiểm tra nó, nó sẽ không thể lên mùi. Việc dọn dẹp không phải là thứ gì đó to tát: đặt lại một cái tên khác tốt hơn cho biến, chia nhỏ một hàm quá lớn, đá đít vài sự trùng lặp không cần thiết, dọn dẹp vài điều kiện if,...</p><p>Liên tục cải thiện code, làm cho code của dự án tốt dần theo thời gian chính là một phần quan trọng của sự chuyên nghiệp.</p><h2><strong>Prequel and Principles</strong></h2><p>Với cách nhìn khác, quyển sách này là một \"tiền truyện\" của một quyển sách khác mà tôi đã viết vào năm 2002, nó mang tên Agile Software Development: Principles, Patterns, and Practices (PPP). Quyển PPP liên quan đến các nguyên tắc của thiết kế hướng đối tượng, và các phương pháp được sử dụng bởi các lập trình viên chuyên nghiệp. Nếu bạn chưa đọc PPP, thì đó là quyển sách kể tiếp câu chuyện của quyển sách này. Nếu đã đọc, bạn sẽ thấy chúng giống nhau ở vài đoạn code.</p><p>[...]</p><h2><strong>Kết luận</strong></h2><p>Một quyển sách về nghệ thuật không hứa đưa bạn thành nghệ sĩ, tất cả những gì nó làm được là cung cấp cho bạn những kỹ năng, công cụ, và quá trình suy nghĩ mà các nghệ sĩ đã sử dụng. Vậy nên, quyển sách này không hứa sẽ làm cho bạn trở thành một lập trình viên giỏi, cũng không hứa sẽ mang đến cho bạn <i>giác quan code.</i> Tất cả những gì nó làm là cho bạn thấy phương pháp làm việc của những lập trình viên hàng đầu, cùng với các kỹ năng, thủ thuật, công cụ,...mà họ sử dụng.</p><p>Như những quyển sách về nghệ thuật khác, quyển sách này đầy đủ chi tiết. Sẽ có rất nhiều code. Bạn sẽ thấy code tốt và code tồi. Bạn sẽ thấy cách chuyển code tồi thành code tốt. Bạn sẽ thấy một danh sách các cách giải quyết, các nguyên tắc và kỹ năng. Có rất nhiều ví dụ cho bạn. Còn sau đó thì, tùy bạn.</p><p>Hãy nhớ tới câu chuyện vui về nghệ sĩ violin đã bị lạc trên đường tới buổi biểu diễn. Anh hỏi một ông già trên phố làm thế nào để đến Carnegie Hall (nơi được xem là thánh đường âm nhạc). Ông già nhìn người nghệ sĩ và cây violin được giấu dưới cánh tay anh ta, nói to: <i>Luyện tập, con trai. Là luyện tập!</i></p>',
        'Administrator', _binary '', 'Chương 1: Code Sạch', '2024-05-09 10:28:13.196889', 4, 1,
        '2024-05-09 08:27:00.000000', 57),
       (16, '2024-05-05 22:10:40.052066', 1,
        '<h2><strong>Thủ thành Bùi Tiến Dũng có trận đấu xuất sắc, góp công lớn vào chiến thắng 1-0 của LPBank HAGL trước Thể Công Viettel. Trận đấu mà tân HLV ĐT Việt Nam Kim Sang Sik dự khán sân Hàng Đẫy, tối 5/5.</strong></h2><p><strong>Ghi bàn:</strong></p><p>HAGL: Thanh Bình (27\', phản lưới)</p><p><strong>Đội hình xuất phát Thể Công Viettel vs LPBank HAGL</strong></p><p><strong>Thể Công Viettel:</strong> Thế Tài (25), Thanh Bình (3), Tiến Dũng (4), Jaha (7), Hoàng Đức (28), Văn Khang (11), Tiến Anh (86), Đức Chiến (21), Tuấn Tài (12), Joao Pedro (77), Pedro Henrique (Văn Hào 23\').<br>&nbsp;</p><p><a href=\"https://vietnamnet.vn/hagl-tag7937402526507091206.html\"><strong>LPBank HAGL</strong></a><strong>:</strong> &nbsp;Tiến Dũng (36), Văn Sơn (2), Jairo Filho (33), Gabriel Ferreira (22), Tấn Tài (17), Minh Vương (10), Ngọc Quang (8), Quang Nho (86), Thanh Bình (9), Thanh Sơn (Đức Việt 17\'), Joao Veras (45).</p><p>&nbsp;</p>',
        'Administrator', _binary '', 'Bùi Tiến Dũng giúp LPBank HAGL hạ Thể Công ở trận HLV Kim Sang Sik dự khán',
        '2024-05-09 08:41:21.292192', 1, 1, '2024-05-07 11:36:00.000000', 83),
       (17, '2024-05-05 22:13:12.494935', 1,
        '<p>This is video: <a href=\"https://www.youtube.com/watch?v=_8ldAdQd9WU&amp;list=RDjfRActM9oUs&amp;index=11\">https://www.youtube.com/watch?v=_8ldAdQd9WU&amp;list=RDjfRActM9oUs&amp;index=11</a></p>',
        'Nguyễn Hồng Minh', _binary '', 'Mentor amela', '2024-05-05 22:13:12.494935', 1, 3,
        '2024-05-05 22:16:00.000000', 20),
       (18, '2024-05-06 09:18:42.185991', 1,
        '<p>Một điểm đáng chú ý nữa của dự thảo Thông tư là NHNN bổ sung, chỉnh sửa quy định tổ chức tín dụng niêm yết công khai lãi suất tiền gửi bằng VND tại địa điểm giao dịch hợp pháp thuộc mạng lưới hoạt động của tổ chức tín dụng và đăng tải trên trang thông tin điện tử (nếu có) của tổ chức tín dụng, để đồng bộ với quy định tại các Thông tư quy định về tiền gửi của NHNN.</p><p>Hiện nay NHNN quy định mức trần lãi suất tiền gửi không kỳ hạn (tức là tiền gửi trong tài khoản thanh toán là 0,5%/năm) và trần lãi suất của các kỳ hạn dưới 6 tháng là 4,75%/năm.</p><p><strong>Vẫn có ngân hàng vượt trần lãi suất</strong></p><p>Dù quy định hiện hành về trần lãi suất tiền gửi không kỳ hạn và tiền gửi kỳ hạn dưới 6 tháng đã có, nhưng vẫn có ngân hàng cố tình “vượt rào”.</p><p>Chia sẻ với Pháp Luật TP.HCM, một lãnh đạo <a href=\"https://www.24h.com.vn/doanh-nghiep-c849.html\">doanh nghiệp</a> nhỏ tại TP.HCM cho biết: Theo biểu lãi suất niêm yết công khai của một ngân hàng thương mại thì tiền gửi kỳ hạn 3 tháng chỉ được chi trả mức lãi suất 3%/năm, ngay cả kỳ hạn 12 tháng cũng chỉ được 4,5%/năm.</p><p>\"Thế nhưng khi biết tôi muốn gửi kỳ hạn 3 tháng với số tiền lên đến 45 tỉ đồng, lãnh đạo chi nhánh ngân hàng trên tuyên bố sẵn sàng duyệt cho tôi mức lãi suất lên đến 6%/năm. Đây không chỉ là mức lãi suất cao kỷ lục ở thời điểm hiện tại đối với kỳ hạn 3 tháng mà nó còn vượt trần lãi suất mà NHNN quy định\", vị lãnh đạo doanh nghiệp bình luận.</p><p>Ông còn thông tin thêm: Phía ngân hàng làm cho ông một sổ tiết kiệm kỳ hạn 12 tháng với lãi suất 6%/năm, trong khi thực tế ông chỉ gửi 3 tháng. Đến thời điểm đáo hạn sổ tiết kiệm, ngân hàng vẫn cho ông rút toàn bộ tiền gốc và nhận lãi đầy đủ như cam kết, đồng thời họ làm một hợp đồng cho vay với thời hạn là 9 tháng...</p><p>\"Song song với các thủ tục ký nhận hợp đồng vay vốn, ngân hàng còn cho tôi ký trước một loạt giấy tờ liên quan đến việc tất toán khoản vay. Tất cả mọi thủ tục đều được thực hiện một cách nhanh chóng”, vị này chia sẻ.</p><p><strong>Siết chất lượng tín dụng quan trọng hơn siết huy động</strong></p><p>Lý giải về việc vì sao ngay tại thời điểm này, khi mà nhu cầu vốn của nền kinh tế không cao mà vẫn có ngân hàng cố tình vi phạm vượt trần lãi suất mà NHNN quy định, Tổng giám đốc một ngân hàng thương mại nêu quan điểm:</p><p>Trong bối cảnh hiện tại, ngân hàng nào dám vượt trần lãi suất huy động thì có thể do họ đang rất cần vốn để đảo nợ cho trái phiếu doanh nghiệp, chứ dư nợ tín dụng cho hoạt động sản xuất tăng không đáng kể. Hoặc có thể ngân hàng đó đang căng thẳng về thanh khoản, do khách hàng vay vốn mà không có khả năng trả nợ khi đến hạn dẫn đến ngân hàng phải “nuôi” khách hàng tiền gửi để luôn luôn đảm bảo tính thanh khoản.</p><p>Bản chất của việc bị mất thanh khoản là do dòng tiền không luân chuyển, ngân hàng cho vay ra nhưng không thu được vốn về. Giờ đây cộng thêm với việc bị cấm vượt trần lãi suất huy động dưới mọi hình thức thì thanh khoản của ngân hàng đó vốn đã căng thẳng, sẽ càng trở nên căng thẳng hơn.</p><p>“Trong dự thảo Thông tư mới mà NHNN đang lấy ý kiến có việc nghiêm cấm tình trạng vượt trần lãi suất quy định. Theo đó, những khoản như lãi suất ưu đãi, quà tặng khuyến mãi, voucher… cũng phải được cộng vào lãi suất để tính mức trần. Và đương nhiên, ngân hàng nào đang trong tình trạng căng thẳng thanh khoản sẽ chịu tác động mạnh nhất từ những quy định này”, vị lãnh đạo nhà băng nhấn mạnh.</p><p>Bà Nguyễn Thị Phượng, Phó Tổng giám đốc Agribank cho biết: Về mặt nguyên tắc, bên cạnh lãi suất đã công bố, mỗi ngân hàng luôn thiết kế những gói tín dụng, hoặc các chương trình ưu đãi dành cho nhóm khách hàng ưu tiên.</p><p>Chẳng hạn, khách hàng vay có dòng tiền tốt, tài sản đảm bảo tốt, dự án kinh doanh tiềm năng sẽ được hưởng mức lãi suất tốt hơn so với các những khách có điều kiện vay không tốt bằng. Tương tự, các khách hàng tiền gửi cũng như vậy. Ngoài lãi suất tiền gửi niêm yết, khách hàng nào có số tiền gửi lớn vẫn sẽ được ngân hàng trả lãi suất hấp dẫn hơn.</p><p>Nhưng dù là chính sách ưu tiên đối với sản phẩm hay chính sách ưu tiên đối với khách hàng thì các ngân hàng cũng không được phép vượt trần lãi suất mà NHNN quy định.</p><p>Bà Phượng nhấn mạnh: “Một khi ngân hàng nào cố tình lách để huy động vốn cũng sẽ dễ dàng bị phát hiện trong quá trình hậu kiểm. Bởi chỉ cần dựa trên những tính toán từ lãi suất huy động thực tế cộng với các loại chi phí khuyến mãi, cơ quan chức năng sẽ biết được lãi suất huy động của từng ngân hàng có vi phạm quy định về trần lãi suất hay không”.</p><p>TS Nguyễn Hữu Huân, chuyên gia <a href=\"https://www.24h.com.vn/kinh-doanh-c161e4008.html\">tài chính</a> ngân hàng phân tích: Trong thời điểm hiện nay khi tiền trong hệ thống vẫn dư thừa, tín dụng vẫn tăng rất chậm thì những quy định về trần lãi suất không có nhiều ý nghĩa. Thực tế cho thấy là lãi suất không kỳ hạn và lãi suất dưới 6 tháng đang rất thấp so với mức trần mà NHNN đưa ra.</p><p>Thế nhưng, trong thời gian tới, khi tín dụng tăng trưởng xoay chiều, các ngân hàng có thể sẽ phải tăng lãi suất huy động thì với các quy định về trần lãi suất sẽ giúp ngăn chặn các ngân hàng lách luật, qua đó tạo sự công bằng minh bạch giữa các ngân hàng trong việc huy động vốn.</p><p>\"Bên cạnh đó, NHNN cũng cần đưa ra mức trần lãi suất ở từng thời điểm như thế nào cho hợp lý, để ngân hàng nhỏ không cần phải lách luật vẫn có thể cạnh tranh lãi suất, nhằm duy trì thanh khoản, qua đó cũng đảm bảo an toàn hệ thống”, TS Huân nêu quan điểm.</p>',
        'Administrator', _binary '', 'Có ngân hàng \'đi đêm\' lãi suất tiết kiệm, cao gấp đôi so với niêm yết',
        '2024-05-14 11:48:19.759576', 1, NULL, '2024-05-30 09:18:00.000000', 26),
       (19, '2024-05-07 11:12:10.322018', 1,
        '<h4><strong>Do các bị cáo không phải chịu trách nhiệm bồi thường dân sự nên HĐXX tuyên trả lại số tiền hơn 183 tỷ đồng cho bà Phạm Thị Nụ (vợ ông Trần Quí Thanh).</strong></h4><p>Sau gần nửa tháng kết thúc phiên xét xử đối với bị cáo Trần Quí Thanh và 2 con gái, TAND TP.HCM vừa tống đạt bản án sơ thẩm đến các bị cáo, người liên quan.</p><p>Theo Bản án sơ thẩm, qua xét hỏi, tranh luận tại phiên tòa, các bị cáo Trần Quí Thanh, Trần Uyên Phương, Trần Ngọc Bích đã thừa nhận hành vi phạm tội của mình. Lời khai nhận tội của các bị cáo phù hợp với các tài liệu, chứng cứ có trong hồ sơ vụ án.</p><p>Vì vậy, có căn cứ kết luận, về bản chất của vụ án, các bị cáo đã có hành vi bằng giao dịch dân sự về vay tài sản (vay tiền) đối với các bị hại gồm: Lâm Sơn Hoàng, Nguyễn Huy Đông, Nguyễn Văn Chung và Đặng Thị Kim Oanh là không trái luật.</p><figure class=\"image\"><picture><source srcset=\"https://static-images.vnncdn.net/files/publish/2024/5/7/w-tranquithanh-1-287.gif?width=0&amp;s=iUg9xdO_-XIi34kigGCJlg\" media=\"(max-width: 1023px)\"><img src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" alt=\"W-tranquithanh-1.gif\" srcset=\"https://static-images.vnncdn.net/files/publish/2024/5/7/w-tranquithanh-1-287.gif?width=0&amp;s=iUg9xdO_-XIi34kigGCJlg\" sizes=\"100vw\" width=\"1\" height=\"1\"></picture><figcaption><i>Bị cáo Trần Quí Thanh tại phiên sơ thẩm</i></figcaption></figure><p>Tuy nhiên, để đảm bảo cho khoản tiền vốn vay, tiền lãi phát sinh, các bị cáo cùng các bị hại đã thỏa thuận ký kết các hợp đồng về việc chuyển nhượng tài sản (quyền sử dụng đất, cổ phần của dự án), đây là các hợp đồng giả tạo, trái pháp luật.</p><p>Sau khi đứng tên trên giấy tờ chuyển nhượng, các bị cáo đã nảy sinh ý định chiếm đoạt tài sản của các bị hại nên đã dùng thủ đoạn gây khó khăn, nại ra lý do bất hợp lý để từ chối việc thanh toán hoặc buộc người vay phải trả thêm tiền lãi, tiền phạt, không cho trả tiền gốc…để chiếm đoạt tài sản.&nbsp;</p><p>Tổng số tài sản mà các bị cáo chiếm đoạt của các bị hại là hơn 1.048 tỷ đồng.</p><p>Căn cứ các tình tiết tăng nặng, giảm nhẹ, HĐXX &nbsp;tuyên phạt bị cáo Trần Quí Thanh 8 năm tù; bị cáo Trần Uyên Phương 4 năm tù và Trần Ngọc Bích 3 năm tù nhưng cho hưởng án treo cùng về tội \"Lạm dụng tín nhiệm chiếm đoạt tài sản\".</p><p>HĐXX cũng tuyên hủy các hợp đồng chuyển nhượng tài sản được các cơ quan có thẩm quyền công nhận, được công chứng chứng thực, cùng các giấy tờ đã giao kết có liên quan giữa các bị cáo và bị hại, người có quyền lợi, nghĩa vụ liên quan.</p><p>Cạnh đó, tòa không chấp nhận yêu cầu của bà Đặng Thị Kim Oanh về việc buộc ông Trần Quí Thanh phải bồi thường thiệt hại về lợi thế kinh doanh kể từ ngày bị chiếm đoạt tài sản với số tiền 531,3 tỷ đồng.</p><figure class=\"image\"><picture><source srcset=\"https://static-images.vnncdn.net/files/publish/2024/5/7/w-uyenphuong-1-288.gif?width=0&amp;s=WSEqsBfBRmQqXO44GM31Aw\" media=\"(max-width: 1023px)\"><img src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\" alt=\"W-uyenphuong-1.gif\" srcset=\"https://static-images.vnncdn.net/files/publish/2024/5/7/w-uyenphuong-1-288.gif?width=0&amp;s=WSEqsBfBRmQqXO44GM31Aw\" sizes=\"100vw\" width=\"1\" height=\"1\"></picture><figcaption><i>Bị cáo Trần Uyên Phương</i></figcaption></figure><p>Theo HĐXX, bà Oanh không có tài liệu, chứng cứ chứng minh thiệt hại thực tế đã xảy ra, việc yêu cầu tính thiệt hại theo mức lãi suất 10% trên giá trị tài sản được định giá là không có cơ sở nên không được chấp nhận.</p><p>Về dân sự, do các bị cáo không phải chịu trách nhiệm bồi thường dân sự nên HĐXX tuyên trả lại số tiền hơn 183 tỷ đồng cho bà Phạm Thị Nụ (vợ bị cáo Trần Quí Thanh và là mẹ của hai bị cáo Phương, Bích) đã nộp để khắc phục hậu quả của các bị cáo.</p><p>Ngoài mức án trên, mỗi bị cáo phải chịu án phí 200 ngàn đồng.</p><p>Các bị hại cũng phải chịu tiền án phí, trong đó bị hại Lâm Sơn Hoàng phải chịu 223 triệu đồng ; Nguyễn Huy Đông hơn 186 triệu đồng; Nguyễn Văn Chung hơn 142 triệu đồng và Đặng Thị Kim Oanh hơn 982 triệu đồng.</p><p>Ngoài ra, những người liên quan cũng phải chịu án phí, trong đó người cao nhất là 173 triệu đồng, người thấp nhất là 24 triệu đồng.</p>',
        'Administrator', _binary '', 'Vợ ông Trần Quí Thanh được nhận lại hơn 183 tỷ đồng',
        '2024-05-09 17:22:37.704212', 1, 1, '2024-05-30 11:11:00.000000', 37),
       (20, '2024-05-09 14:11:41.160718', 1,
        '<p><a href=\"https://amela.vn/wp-content/uploads/2023/11/1102-01-2048x1327.jpg\">https://amela.vn/wp-content/uploads/2023/11/1102-01-2048x1327.jpg</a>&nbsp;</p><p>Tại các nước phát triển trên thế giới, hệ thống ERP là một mắt xích quan trọng trong quy trình làm việc của một doanh nghiệp. Nếu doanh nghiệp của bạn đã và đang đang triển khai hệ thống ERP, hẳn bạn đã thấy rõ được giải pháp này góp phần vào việc phát triển đường dài cho doanh nghiệp ra sao. Còn nếu bạn chưa biết đến ERP, thì hãy cùng <a href=\"https://amela.vn/amela-cong-ty-cong-nghe-thong-tin-chuyen-nghiep-hang-dau-hien-nay/\"><strong>AMELA</strong></a> tìm hiểu xem ERP là gì? Hiện nay có công cụ ERP nào phổ biến và dễ sử dụng?</p><figure class=\"image\"><img style=\"aspect-ratio:1024/663;\" src=\"https://amela.vn/wp-content/uploads/2023/11/1102-01-1024x663.jpg\" alt=\"Giới Thiệu Đối Tác Triển Khai Phần Mềm Odoo – Quản Trị Doanh Nghiệp Với 10,000 Module \" srcset=\"https://amela.vn/wp-content/uploads/2023/11/1102-01-1024x663.jpg 1024w, https://amela.vn/wp-content/uploads/2023/11/1102-01-300x194.jpg 300w, https://amela.vn/wp-content/uploads/2023/11/1102-01-768x498.jpg 768w, https://amela.vn/wp-content/uploads/2023/11/1102-01-1536x995.jpg 1536w, https://amela.vn/wp-content/uploads/2023/11/1102-01-2048x1327.jpg 2048w, https://amela.vn/wp-content/uploads/2023/11/1102-01-385x250.jpg 385w\" sizes=\"100vw\" width=\"1024\" height=\"663\"></figure><h2><strong>1. ERP là gì?</strong></h2><p>Mặc dù đã có một bài giải thích chi tiết về <a href=\"https://amela.vn/phan-mem-erp-la-gi-tai-sao-phan-mem-nay-lai-pho-bien-nhu-vay/\"><strong>ERP là gì?</strong></a> nhưng AMELA sẽ tóm tắt lại một vài ý chính như sau:</p><p>ERP viết tắt của Enter Resource Planning. Trong đó R và P đã thể hiện được trọn vẹn đặc điểm của nó.</p><p><strong>R: Resource (Tài nguyên)</strong>. Trong kinh doanh, resource là nguồn lực nói chung bao gồm cả tài chính, nhân lực và công nghệ. Tuy nhiên, trong ERP, resource còn có nghĩa là tài nguyên. Việc&nbsp;ứng dụng ERP vào hoạt động quản trị công ty đòi hỏi chúng ta phải biến nguồn lực này thành tài nguyên:&nbsp;</p><ul><li>Làm cho mọi phòng ban đều có khả năng khai thác nguồn lực phục vụ cho công ty.</li><li>Hoạch định và xây dựng lịch trình khai thác nguồn lực của các bộ phận sao cho giữa các bộ phận luôn có sự phối hợp nhịp nhàng.</li><li>Thiết lập các quy trình khai thác đạt hiệu quả cao nhất.</li><li>Luôn cập nhật thông tin một cách chính xác, kịp thời về tình trạng nguồn lực của công ty.</li><li>Muốn biến nguồn lực thành tài nguyên, chúng ta phải trải qua một thời kỳ “lột xác”, nghĩa là cần thay đổi văn hóa kinh doanh cả bên trong và ngoài công ty.</li></ul><p><strong>P: Planning (Hoạch định):</strong> Planning là khái niệm quen thuộc trong quản trị kinh doanh. Điều cần quan tâm ở đây là hệ ERP hỗ trợ công ty lên kế hoạch ra sao?</p><p>Trước hết, ERP tính toán và dự báo các khả năng có thể phát sinh trong quá trình điều hành sản xuất/kinh doanh của công ty. Chẳng hạn, ERP giúp nhà máy tính toán chính xác kế hoạch cung ứng nguyên vật liệu cho mỗi đơn hàng dựa trên tổng nhu cầu nguyên vật liệu, tiến độ, năng suất, khả năng cung ứng… Cách làm này cho phép công ty luôn có đủ vật tư sản xuất, mà vẫn không để lượng tồn kho quá lớn gây đọng vốn.</p><p>Hệ thống giải pháp ERP còn là công cụ hỗ trợ trong việc lên kế hoạch cho các nội dung công việc, nghiệp vụ cần thiết trong quá trình sản xuất kinh doanh, chẳng hạn như hoạch định chính sách giá, chiết khấu, các hình thức mua hàng, hỗ trợ tính toán ra phương án mua nguyên liệu, tính được mô hình sản xuất tối ưu… Đây là biện pháp giúp bạn giảm thiểu sai sót trong các xử lý nghiệp vụ. Hơn nữa, ERP tạo ra mối liên kết văn phòng công ty – đơn vị thành viên, phòng ban – phòng ban và trong nội bộ các phòng ban, hình thành nên các quy trình xử lý nghiệp vụ mà mọi nhân viên trong công ty phải tuân theo.</p><p>Triển khai hệ thống ERP là quá trình tin học hóa toàn diện các hoạt động của doanh nghiệp dựa trên các qui trình quản lý tiên tiến. Mọi hoạt động của doanh nghiệp sẽ do phần mềm máy tính hỗ trợ và thực hiện các qui trình xử lý một cách tự động hoá, giúp cho các doanh nghiệp quản lý các hoạt động then chốt, bao gồm: kế toán, phân tích tài chính, quản lý mua hàng, quản lý tồn kho, hoạch định và quản lý sản xuất, quản lý quan hệ với khách hàng, quản lý nhân sự, theo dõi đơn hàng, quản lý bán hàng,… Mục tiêu tổng quát của hệ thống này là đảm bảo các nguồn lực thích hợp của doanh nghiệp như nhân lực, vật tư, máy móc và tiền bạc có sẵn với số lượng đủ khi cần, bằng cách sử dụng các công cụ hoạch định và lên kế hoạch.</p><h2><strong>2. Đặc điểm nổi bật của hệ thống ERP</strong></h2><p>Đặc điểm nổi bật của ERP là một hệ thống phần mềm có thể mở rộng và phát triển theo thời gian theo từng loại hình doanh nghiệp mà không làm ảnh hưởng đến cấu trúc của chương trình.</p><p>ERP loại bỏ các hệ thống máy tính riêng lẻ ở các bộ phận trong một doanh nghiệp: Tài chính, Nhân sự, Kinh Doanh, Sản xuất, Kho… ERP sẽ thay thế chúng bằng một chương trình phần mềm hợp nhất phân chia theo các phân hệ phần mềm khác nhau và tạo nên một mối quan hệ thống nhất với nhau. Hệ thống ERP tối ưu rất linh động trong việc cài đặt các phân hệ theo yêu cầu doanh nghiệp.</p><p>Các tính năng kỹ thuật quan trọng cần phải có của phần mềm ERP là: cho phép quản lý đa tiền tệ, quản lý nhiều công ty, nhiều chi nhánh, có giao diện đa ngôn ngữ, cho phép copy vào/ra (import/export) ra/vào EXCEL, có khả năng phân tích dữ liệu Drill-Down…</p><p>Mua một giải pháp ERP, chúng ta nhận được cùng một lúc 3 sản phẩm: Một là “Ý tưởng quản trị”, hai là “Chương trình phần mềm” và ba là “Phương tiện kết nối” để xây dựng mạng máy tính tích hợp. Với hệ thống phần mềm thống nhất, đa năng, quán xuyến mọi lĩnh vực hoạt động từ kế hoạch hóa, thống kê, kiểm toán, phân tích, điều hành, hệ thống ERP giúp theo dõi, quản lý thông suốt, tăng tính năng động, đảm bảo cho doanh nghiệp phản ứng kịp thời trước những thay đổi liên tục của môi trường bên ngoài. Trên thế giới, hiện có rất nhiều công ty lớn triển khai thành công hệ thống ERP cho hoạt động quản lý sản xuất kinh doanh của mình. Việc triển khai thành công ERP sẽ tiết kiệm chi phí, tăng khả năng cạnh tranh, đem lại cho doanh nghiệp lợi ích lâu dài.</p><h2><strong>3. Có những phần mềm triển khai ERP nào?</strong></h2><p><a href=\"https://aegona.vn/ho-tro-trien-khai-phan-mem-odoo-erp/\"><strong>Phần mềm Odoo</strong></a> là một trong những công cụ quản trị doanh nghiệp (ERP) mã nguồn mở phổ biến và tiện ích nhất thế giới. Từ 2005, Odoo đã phát triển 10,000 module ứng dụng hỗ trợ vận hành cho nhiều doanh nghiệp.</p><p>Theo đó, các đối tác công nghệ như Aegona đã triển khai phần mềm Odoo tại thị trường Việt Nam. Mời bạn đọc bài viết dưới đây để hiểu thêm chi tiết nhé!</p><h3><strong>Odoo là gì? Thông tin tổng quan về phần mềm Odoo</strong></h3><p>Odoo là giải pháp phần mềm ERP mã nguồn mở, tức là phần mềm này cho phép doanh nghiệp tùy chỉnh và nâng cấp vô hạn. Cụ thể hơn, Odoo tích hợp các module phục vụ doanh nghiệp bằng các tính năng cơ bản như: POS, CRM, trang web, bán hàng, quản lý kho, quản lý nhân sự, kế toán, hoạt động marketing…</p><p>Odoo cho phép doanh nghiệp tự do chỉnh sửa hay bổ sung các tính năng mới hoặc tạo ra các phân hệ mới chỉ với vài cú nhấp chuột. Doanh nghiệp có thể phát triển dựa trên 1 số gợi ý trong kho ứng dụng của Odoo mà mà không cần phải mất công sức nghiên cứu, tạo mới.</p><h3><strong>Ưu điểm của Odoo là gì?</strong></h3><p>Phần mềm Odoo sở hữu rất nhiều ưu điểm mà bất cứ doanh nghiệp ở quy mô nào cũng mong cầu trong thời đại chuyển đổi số. Nhất là ở bối cảnh doanh nghiệp không có nhiều chi phí đầu tư hay lần đầu tiên tìm đến các giải pháp phần mềm trong khâu vận hành doanh nghiệp.</p><h4><strong>1. Chi phí triển khai thấp</strong></h4><p>Đầu tiên, Odoo là mã nguồn mở nên phần mềm Odoo ERP không cần đầu tư quá nhiều tiền để triển khai. Chi phí chính triển khai sẽ tùy thuộc vào quy mô dự án doanh nghiệp cần, đặc thù ngành nghề kinh doanh, cũng như mức độ phức tạp và số lượng các Module, ứng dụng được liên kết với phần mềm.</p><p>Thông thường, chi phí triển khai phần mềm hệ thống Ooo sẽ bao gồm một số chi phí sau: Chi phí cài đặt, chi phí quản lý máy chủ và các module ứng dụng, chi phí cấu hình, các khoản phí phát sinh như: tùy chỉnh và nâng cấp cho phù hợp với doanh nghiệp, chi phí đào tạo triển khai, hỗ trợ sử dụng và xử lý rủi ro.</p><h4><strong>2. Phần mềm Odoo ERP mở rộng cùng kho tiện ích phong phú</strong></h4><p>Phần mềm Odoo có một kho hệ thống đa dạng hỗ trợ nhiều bộ phận trọng yếu trong doanh nghiệp như: kế toán, chăm sóc khách hàng, marketing, quản lý dự án, bán hàng, quản lý kho bãi, quản lý nhân sự,… Có thể nói, với hơn 10,000 module, phần mềm Odoo có thể đáp ứng được gần như mọi nhu cầu vận hành của doanh nghiệp.</p><p>Nhờ việc tích hợp và kết nối với module có sẵn nhanh chóng, doanh nghiệp không phải mất thời gian chờ đợi mà hoàn toàn có sử dụng ngay. Ngoài tích hợp tính năng, doanh nghiệp cũng có thể yêu cầu bổ sung và xây dựng bất cứ tính năng nào khác theo nhu cầu.</p><figure class=\"image\"><img style=\"aspect-ratio:780/480;\" src=\"https://amela.vn/wp-content/uploads/2023/11/Kho-ung-dung-quan-tri-tien-ich-cua-Odoo.jpg\" alt=\"Kho ứng dụng quản trị tiện ích của Odoo\" srcset=\"https://amela.vn/wp-content/uploads/2023/11/Kho-ung-dung-quan-tri-tien-ich-cua-Odoo.jpg 780w, https://amela.vn/wp-content/uploads/2023/11/Kho-ung-dung-quan-tri-tien-ich-cua-Odoo-300x185.jpg 300w, https://amela.vn/wp-content/uploads/2023/11/Kho-ung-dung-quan-tri-tien-ich-cua-Odoo-768x473.jpg 768w\" sizes=\"100vw\" width=\"780\" height=\"480\"></figure><p><i>Kho ứng dụng quản trị tiện ích của Odoo</i></p><h4><strong>3. Thân thiện với người dùng</strong></h4><p>Phần mềm Odoo sở hữu thiết kế tối giản, dễ nhìn. Các module được sắp xếp gọn gàng và hợp lý tùy theo nhu cầu sử dụng. Với một người lần đầu tiên tiếp xúc với Odoo thì không đến vài ngày để sử dụng thành thạo phần mềm này.</p><h4><strong>4. Tính ứng dụng cao</strong></h4><p>Nhờ vào kho ứng dụng đa dạng được đề cập ở trên, Odoo có thể thích ứng được mọi loại hình của doanh nghiệp, nhất là đối với các doanh nghiệp SMEs.</p><p>Ban đầu người dùng có thể trải nghiệm thử các module cơ bản để quản trị doanh nghiệp. Theo thời gian, doanh nghiệp có thể mở khóa thêm các module khác trong kho ứng dụng, hoặc tích hợp 1 module thiết kế riêng tùy theo nhu cầu phát sinh và phát triển của doanh nghiệp.</p><p>Mà những điều bạn cần chỉ là một đội ngũ lập trình viên chuyên nghiệp để tùy chỉnh và tạo mới các tính năng theo ý muốn như Aegona Việt Nam.</p><p><strong>&gt;&gt; Đọc thêm: </strong><a href=\"https://aegona.vn/odoo-la-gi-uu-nhuoc-diem-cua-phan-mem-odoo-doanh-nghiep-nen-biet/\"><strong>Hạn chế của phần mềm Odoo</strong></a></p><h2><strong>Giới thiệu Công ty phần mềm Aegona chuyên cung cấp triển khai dịch vụ tùy chỉnh và cài đặt các tính năng Odoo theo nhu cầu</strong></h2><p>Công ty Aegona là đơn vị chuyên lập trình và cài đặt mở rộng các tính năng của phần mềm mã nguồn mở Odoo tại TP. Hồ Chí Minh. Với đội ngũ nhân sự giàu kinh nghiệm đã và đang hợp tác với nhiều khách hàng nội địa và nước ngoài, Aegona tự tin biến mọi ý tưởng kinh doanh chuyển đổi số của bạn thành hiện thực thông qua nền tảng Odoo.</p><p><strong>Dịch vụ hỗ trợ tư vấn phát triển phần mềm Odoo của Aegona bao gồm:&nbsp;</strong></p><ul><li>Tư vấn về kế hoạch phát triển phần mềm Odoo phù hợp cho doanh nghiệp.</li><li>Hỗ trợ xử lý sự cố và đào tạo nhân viên liên quan.</li><li>Giúp cài đặt, lập trình các ứng dụng module tùy chỉnh theo nhu cầu mở rộng của doanh nghiệp.</li><li>Tư vấn về việc mở rộng và phát triển kinh doanh bằng công nghệ chuyển đổi số.</li></ul><p>Bài viết trên đã tổng quan một số thông tin về phần mềm Odoo, Odoo là gì và ưu điểm của nó. Nếu bạn quan tâm đến các dịch vụ cung cấp <a href=\"https://aegona.vn/ho-tro-trien-khai-phan-mem-odoo-erp/\"><strong>hỗ trợ tư vấn phần mềm Odoo</strong></a> của <strong>Aegona</strong>, bạn có thể truy cập trang web sau đây để biết thêm thông tin chi tiết.&nbsp;</p><p><strong>Liên hệ với Aegona Việt Nam qua các hình thức sau:</strong></p><p><strong>Website:</strong> <a href=\"https://68creative.vn/www.aegona.vn\"><strong>www.aegona.vn</strong></a></p><p><strong>Email:</strong> contact@aegona.com</p><p><strong>Điện thoại:</strong> Office: 028 7109 2939&nbsp; –&nbsp; Hotline: 0914 518869</p><p><strong>Địa chỉ:</strong> Tòa nhà Orbital (QTSC9), Công viên phần mềm Quang Trung, phường Tân Chánh Hiệp, quận 12, thành phố Hồ Chí Minh</p>',
        'Administrator', _binary '',
        'Giới Thiệu Đối Tác Triển Khai Phần Mềm Odoo – Quản Trị Doanh Nghiệp Với 10,000 Module',
        '2024-05-09 17:23:26.436536', 1, 1, '2024-05-09 14:10:00.000000', 29),
       (23, '2024-05-14 08:35:14.921057', 1,
        '<figure class=\"media\"><oembed url=\"https://www.youtube.com/watch?v=gt3U934kyPQ&amp;list=RDMMYWpsynwCZtM&amp;index=2\"></oembed></figure><p>This is video youtube</p>',
        'HÀ ANH TUẤN', _binary '', '(Lyric Video) | XUÂN THÌ | HÀ ANH TUẤN', '2024-05-14 08:35:14.921057', 1, 1,
        '2024-05-14 08:36:00.000000', 3),
       (25, '2024-05-14 16:34:02.782234', 1, '<p>schedule schedule schedule</p>', 'Administrator', _binary '',
        'schedule', '2024-05-14 16:34:02.782234', 1, 1, '2024-05-14 16:35:00.000000', 0),
       (26, '2024-05-14 16:36:13.132783', 1, '<p>test scheduletest schedule</p>', 'Administrator', _binary '',
        'test schedule', '2024-05-14 16:38:32.758101', 1, 3, '2024-05-14 16:39:00.000000', 0),
       (27, '2024-05-14 17:04:42.229180', 1, '<p><strong>Xin chào bạn {{name}}</strong></p>', 'Administrator',
        _binary '', 'xin chào các bạn nhỏ ạ', '2024-05-14 17:04:56.301095', 1, 3, '2024-05-14 17:05:00.000000', 1),
       (28, '2024-05-14 17:16:22.762931', 1, '<p><strong>ádfghjklkjhgfds</strong></p>', 'Administrator', _binary '',
        'Tự Nhiên Xã Hội', '2024-05-14 17:16:32.227696', 1, 3, '2024-05-14 17:18:00.000000', 0),
       (29, '2024-05-14 21:49:46.604543', 1, '<p>DEV LƯỚT OMETV GẶP 2 BẠN NGƯỜI NGA ĐÁNG YÊU XỈU (PHỤ ĐỀ)</p>',
        'Administrator', _binary '', 'DEV LƯỚT OMETV GẶP 2 BẠN NGƯỜI NGA ĐÁNG YÊU XỈU (PHỤ ĐỀ)',
        '2024-05-14 21:49:46.604625', 1, 3, '2024-05-14 21:50:00.000000', 0),
       (30, '2024-05-14 21:54:07.386539', 1,
        '<h2><strong>♬ Tây Tiến - Vidic X HTropix (Official Lyric Video) | Khi Văn Học Thành Bài Hát Siêu Hay</strong></h2>',
        'Administrator', _binary '',
        '♬ Tây Tiến - Vidic X HTropix (Official Lyric Video) | Khi Văn Học Thành Bài Hát Siêu Hay',
        '2024-05-14 21:54:18.903201', 1, 3, '2024-05-14 21:56:00.000000', 1),
       (31, '2024-05-14 22:02:24.687687', 1, '<h2><strong>Your clock is 0.2 seconds ahead.</strong></h2>',
        'Administrator', _binary '', 'Your clock is 0.2 seconds ahead.', '2024-05-14 22:02:36.458473', 1, 3,
        '2024-05-14 22:05:00.000000', 1),
       (32, '2024-05-15 08:21:19.153905', 1,
        '<h2><strong>HLV Kim Sang Sik vẫn chưa thể hoàn thiện đội ngũ trợ lý bất chấp thời gian tuyển Việt Nam tập trung chuẩn bị vòng loại World Cup 2026 đang đến rất gần.</strong></h2><p><strong>Trợ lý cho ông Kim Sang Sik chưa lộ diện...</strong></p><p>Hơn 1 tuần sau khi chính thức được bổ nhiệm ngồi \'ghế nóng\', đội ngũ trợ lý cho HLV Kim Sang Sik ở <a href=\"https://vietnamnet.vn/hlv-kim-sang-sik-tag12292919738489252851.html\"><strong>tuyển Việt Nam</strong></a> vẫn chưa lộ diện, ngoại trừ một cái tên Choi Won-Kwon.</p><p>Điều này có phần gây sốt ruột lẫn thắc mắc từ người hâm mộ, bởi thời gian để vị tân thuyền trưởng người Hàn Quốc chuẩn bị cho trận ra mắt là không nhiều, với việc tuyển Việt Nam có 2 trận đấu ở vòng loại 2 World Cup 2026 trong kỳ FIFA Days tháng 6.</p>',
        'Administrator', _binary '', 'Tuyển Việt Nam: Băn khoăn về đội ngũ trợ lý cho HLV Kim Sang Sik',
        '2024-05-15 08:21:30.420405', 1, 3, '2024-05-15 08:23:00.000000', 0),
       (33, '2024-05-15 10:50:12.692651', 1,
        '<h2><strong>Liên quan vụ việc hơn 300 công nhân Công ty TNHH Shinwon Ebenezer Việt Nam nhập viện điều trị sau bữa cơm trưa, Bộ Y tế đề nghị đình chỉ ngay hoạt động bếp ăn tập thể công ty này, tổ chức điều tra xác định rõ nguyên nhân.</strong></h2><p>Ngày 15/5, Cục An toàn thực phẩm Bộ Y tế cho biết đã nhận được thông tin về việc nghi ngờ ngộ độc thực phẩm xảy ra tại Công ty TNHH Shinwon Ebenezer Việt Nam, khu công nghiệp Khai Quang, thành phố Vĩnh Yên, tỉnh Vĩnh Phúc làm hơn 300 công nhân đi viện điều trị.</p><p>Cục An toàn thực phẩm đề nghị Sở Y tế tỉnh Vĩnh Phúc khẩn trương chỉ đạo các cơ sở y tế có bệnh nhân đang điều trị, tập trung nguồn lực tích cực điều trị cho các bệnh nhân, không để các bệnh nhân có diễn biến nặng ảnh hưởng đến sức khỏe và tính mạng, trong trường hợp cần thiết có thể xin hỗ trợ hội chẩn chuyên môn với tuyến trên.</p><p>\"Đình chỉ ngay hoạt động bếp ăn tập thể Công ty TNHH Shinwon Ebenezer Việt Nam, tổ chức điều tra xác định rõ nguyên nhân vụ việc theo quy định, truy xuất nguồn gốc thực phẩm, lấy mẫu thực phẩm, bệnh phẩm để xét nghiệm tìm nguyên nhân\", chỉ đạo của Bộ Y tế nêu rõ. Đồng thời, phát hiện, xử lý nghiêm các vi phạm quy định về an toàn thực phẩm (nếu có) và công khai kết quả để kịp thời cảnh báo cho cộng đồng.</p><p>Cơ quan của Bộ Y tế cũng đề nghị Sở Y tế tỉnh Vĩnh Phúc tăng cường kiểm tra, giám sát, hướng dẫn các bếp ăn tập thể, cơ sở kinh doanh dịch vụ ăn uống đảm bảo vệ sinh, điều kiện an toàn thực phẩm, thực hiện nghiêm việc quản lý nguồn gốc nguyên liệu thực phẩm, kiểm thực ba bước, lưu mẫu thực phẩm và vệ sinh trong các khâu chế biến; tuyên truyền, giáo dục người dân kiến thức an toàn thực phẩm và biện pháp phòng chống ngộ độc thực phẩm.</p><p>Trước đó, <i>VietNamNet</i> đưa tin chiều 14/5, Bệnh viện Hữu nghị Lạc Việt (tỉnh Vĩnh Phúc) tiếp nhận nhiều công nhân có biểu hiện ngộ độc thực phẩm, ước tính khoảng 200 người.&nbsp;Ngoài ra, nhiều công nhân khác được xe cứu thương chở đến cấp cứu tại Bệnh viện Đa khoa tỉnh Vĩnh Phúc.</p><p>Khoảng 16h30, một công nhân của Công ty Shinwon cho biết rất nhiều người có biểu hiện chóng mặt, buồn nôn. Bữa trưa diễn ra lúc 12h30, gồm các món: súp lơ xào, dưa chua, thịt xào và canh rau giá. Sau bữa ăn, nhiều người cảm thấy có biểu hiện bất thường và nôn ói.</p>',
        'Administrator', _binary '', 'Hơn 300 công nhân ở Vĩnh Phúc đi viện sau bữa cơm trưa, Bộ Y tế chỉ đạo khẩn',
        '2024-05-15 10:50:12.692651', 1, 1, '2024-05-15 10:51:00.000000', 3),
       (34, '2024-05-15 11:03:25.201650', 1,
        '<h2><strong>Ăn sáng tại nhà đảm bảo an toàn thực phẩm, đủ dinh dưỡng và gắn kết các thành viên trong gia đình.</strong></h2><h3><strong>[{{name}}, {{age}}, {{address}}, {{code}}, {{email}}, {{phone}}, {{username}}, {{username}}, {{username}}, {{department}}, {{position}}]</strong></h3><p><i>Thời gian gần đây liên tục xảy ra các vụ ngộ độc thực phẩm, tôi chuyển sang nấu ăn sáng cho cả nhà. Món ăn được tôi chuẩn bị từ hôm trước, sáng đun lại. Tuy nhiên, chồng tôi cho rằng ăn cơm vào buổi sáng khó nuốt nên ra ngoài làm bát phở cho nhanh. Con nhỏ thường chỉ uống một cốc sữa, các bé không muốn ăn thêm. Xin chuyên gia tư vấn một bữa sáng cần chuẩn bị như thế nào đủ dinh dưỡng, đảm bảo an toàn sức khỏe? (Nguyễn Bích Hằng - Thanh Xuân, Hà Nội).</i></p><p><strong>Tiến sĩ, bác sĩ Nguyễn Trọng Hưng - Trưởng khoa Tư vấn dinh dưỡng người lớn, Viện Dinh dưỡng quốc gia (Hà Nội) tư vấn:</strong></p><p>Bữa sáng là bữa ăn quan trọng trong ngày, giúp cơ thể nạp năng lượng sau một đêm nghỉ ngơi. Bữa ăn này cần cung cấp 30-40% tổng năng lượng hằng ngày cho một người. Bữa sáng đủ chất bao gồm 4 nhóm chính như tinh bột, chất béo, chất đạm và vitamin cần thiết.&nbsp;</p><p>Trước nguy cơ mất an toàn thực phẩm, bạn tự chuẩn bị một bữa sáng cho gia đình là tốt nhất. Các chuyên gia dinh dưỡng đưa ra 3 lợi ích của ăn sáng tại nhà như sau:</p><p>Thứ nhất, ăn tại nhà đủ chất hơn là thực phẩm mua tại các quán ăn sáng như xôi, bánh mỳ. Thậm chí, các món phổ biến như xúc xích, bánh rán… được xếp và thực phẩm kém an toàn, lâu dài không tốt cho <a href=\"https://vietnamnet.vn/suc-khoe\">sức khỏe</a>.&nbsp;</p><p>Thứ hai, bạn tự nấu ăn sáng sẽ kiểm soát năng lượng đầu vào tốt hơn cho các thành viên trong gia đình, giảm nguy cơ béo phì.</p><p>Thứ ba, cả gia đình cùng ăn sáng tăng tính gắn kết giữa các thành viên.&nbsp;</p><p><strong>Lưu ý khi chuẩn bị bữa sáng</strong></p><p>Thời gian chuẩn bị cho bữa sáng eo hẹp hơn, bạn không cần phải nấu đủ cơm, canh, thịt cá. Bạn có thể thay đổi bằng các món bún, phở, bánh mì sốt vang, xôi. Khi chế biến, bạn cho thêm thịt, trứng, cá, các loại rau sống, dưa chuột. Các món như thịt rang, thịt xào, trứng rán nấu nhanh, dễ ăn, đủ chất.&nbsp;</p><p>Với trẻ nhỏ, một cốc sữa bột đảm bảo đủ 300-400 calo tương đương bát phở nhỏ, đủ năng lượng cho trẻ hoạt động.&nbsp;</p><p>Bạn nên hạn chế sử dụng lại thức ăn thừa. Thực phẩm từ ngày hôm trước nấu lại mất dinh dưỡng, dễ ôi thiu, tăng nguy cơ nhiễm vi sinh vật trong quá trình bảo quản.</p>',
        'ái chà chà', _binary '', 'Ba lợi ích khi ăn sáng tại nhà', '2024-05-15 11:03:25.201650', 1, NULL,
        '2024-05-15 11:04:00.000000', 0),
       (35, '2024-05-15 11:09:58.683825', 1,
        '<h3>Message [{{name}}, {{age}}, {{address}}, {{code}}, {{email}}, {{phone}}, {{username}}, {{username}}, {{username}}, {{department}}, {{position}}]</h3><h2><strong>Cô gái trẻ 24 tuổi đến spa nhờ tiêm meso vào da. Bất ngờ, cô thấy đau buốt, da cứng nên yêu cầu ngừng lại và phát hiện mình bị tiêm nhầm dung dịch sữa rửa mặt.</strong></h2><p>Trung tâm Chống độc, Bệnh viện Bạch Mai (Hà Nội), đã tiếp nhận một nữ bệnh nhân (24 tuổi, trú tại Thái Bình) chuyển lên từ tuyến dưới với tổn thương do <a href=\"https://vietnamnet.vn/suc-khoe\">tiêm nhầm</a> sửa rửa mặt vào da.</p><p>Cụ thể, tối 7/5, nữ bệnh nhân này chiết sữa rửa mặt vào túi và mang theo meso cùng dụng cụ tới một spa gần nhà nhờ tiêm.</p><p>Trong quá trình tiêm, cô gái thấy đau buốt, meso tiêm vào không tan ra như những lần trước, da mặt cứng nên yêu cầu ngừng lại. Kiểm tra, khách hàng tá hỏa phát hiện bản thân đã bị tiêm nhầm sữa rửa mặt.&nbsp;Ngay sau đó, bệnh nhân tới bệnh viện ở địa phương kiểm tra và được giới thiệu chuyển lên Bệnh viện Bạch Mai.&nbsp;</p><p>Tiến sĩ, bác sĩ Nguyễn Trung Nguyên, Giám đốc Trung tâm chống độc, Bệnh viện Bạch Mai, cho biết bệnh nhân nhập viện với biểu hiện đau nhức nhiều vùng mặt, gò má phải sưng nhiều, có khối cứng dưới da, dưới mi mắt phải có vùng thâm đen, trán có nhiều điểm sưng tại mũi kim tiêm, bầm tím không đều. Sau nhiều ngày điều trị, tình trạng nữ bệnh nhân đã được cải thiện, bớt sưng. &nbsp;</p><p>Các thủ thuật được chủ spa tiêm cho nữ bệnh nhân đều thực hiện “chui”. Đây chỉ là cơ sở chăm sóc da thông thường, không phải phòng khám hay thẩm mỹ viện.&nbsp;</p>',
        'Administrator', _binary '', 'Một người phụ nữ bị tiêm sữa rửa mặt vào da', '2024-05-15 11:09:58.683825', 1,
        NULL, '2024-05-15 11:10:00.000000', 0),
       (36, '2024-05-15 11:11:04.373054', 1, '<p>ádfghjkjhgfdsdfghjklkjhgfds</p>', 'Administrator', _binary '',
        'Tự Nhiên Xã Hội', '2024-05-15 11:11:04.373054', 1, 1, '2024-05-15 11:12:00.000000', 1),
       (37, '2024-05-15 11:18:16.518112', 1, '<p>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</p>', 'Administrator',
        _binary '', 'Tự Nhiên Xã Hội', '2024-05-15 11:18:16.518112', 1, NULL, '2024-05-15 11:19:00.000000', 0),
       (38, '2024-05-15 11:23:35.801694', 1, '<p>asdfghjkl;ádfghjk</p>', 'Administrator', _binary '',
        'Tự Nhiên Xã Hội', '2024-05-15 11:23:35.801694', 1, NULL, '2024-05-15 11:24:00.000000', 1),
       (39, '2024-05-15 11:39:09.750292', 1, '<p>ádfghjkjhgfdsdfghjklkjhgfd</p>', 'Administrator', _binary '',
        'Tự Nhiên Xã Hội', '2024-05-15 11:39:16.580231', 1, NULL, '2024-05-15 12:38:00.000000', 0),
       (40, '2024-05-15 11:48:12.906546', 1, '<p>aqwsdfghjkjhgfdsdfghjk</p>', 'Administrator', _binary '',
        'Tự Nhiên Xã Hội', '2024-05-15 11:48:12.906546', 1, NULL, '2024-05-15 11:49:00.000000', 4),
       (41, '2024-05-15 11:52:42.540730', 1, '<p>ádfghjkjhgfdsdfghjk</p>', 'Nguyễn Hồng Minh', _binary '',
        'Bùi Tiến Dũng giúp LPBank HAGL hạ Thể Công ở trận HLV Kim Sang Sik dự khán', '2024-05-15 11:52:46.447403', 1,
        NULL, '2024-05-15 12:52:00.000000', 0),
       (42, '2024-05-15 13:44:44.133685', 1, '<p>adadsfsfafafafafafafa</p>', 'Administrator', _binary '',
        'người đẹp nhất', '2024-05-15 13:44:44.133685', 1, NULL, '2024-05-15 13:45:00.000000', 0),
       (43, '2024-05-15 13:46:36.911791', 1,
        '<p>We are extremely happy to announce that a new news has been updated on AMELA.</p><p><br>&nbsp;</p>',
        'Administrator', _binary '', 'We are extremely happy to announce that a new news has been updated on AMELA.',
        '2024-05-15 13:46:36.911791', 1, NULL, '2024-05-31 13:47:00.000000', 2),
       (44, '2024-05-15 13:50:18.113461', 1, '<p>Tự Nhiên Xã Hộiv</p>', 'Administrator', _binary '', 'Tự Nhiên Xã Hội',
        '2024-05-15 13:50:18.113461', 1, NULL, '2024-05-15 13:51:00.000000', 0),
       (45, '2024-05-15 13:53:43.629070', 1, '<p>Tự Nhiên Xã Hội</p>', 'Administrator', _binary '', 'Tự Nhiên Xã Hội',
        '2024-05-15 13:53:43.630080', 1, NULL, '2024-05-15 13:54:00.000000', 0),
       (46, '2024-05-15 13:58:47.261404', 1, '<p>ádfghjkjhgfdssdfghjklkjhgfdfgh</p>', 'Administrator', _binary '',
        'Nguyễn Hồng Minh', '2024-05-15 13:58:47.261404', 1, NULL, '2024-05-15 13:59:00.000000', 0),
       (47, '2024-05-15 14:01:15.014382', 1, '<p>Nguyễn Hồng Minh</p>', 'Administrator', _binary '',
        'Nguyễn Hồng Minh', '2024-05-15 14:01:15.014382', 1, NULL, '2024-05-15 14:02:00.000000', 0),
       (48, '2024-05-15 14:02:38.405636', 1, '<p>Nguyễn Hồng Minh</p>', 'Administrator', _binary '', 'Tự Nhiên Xã Hội',
        '2024-05-15 14:02:38.405636', 1, NULL, '2024-05-15 14:03:00.000000', 0),
       (49, '2024-05-15 14:06:38.708148', 1, '<p>Tự Nhiên Xã Hội</p>', 'Administrator', _binary '', 'Tự Nhiên Xã Hội',
        '2024-05-15 14:06:38.708148', 1, NULL, '2024-05-15 14:07:00.000000', 0),
       (50, '2024-05-15 14:21:57.540227', 1, '<p>Tự Nhiên Xã Hội</p>', 'Administrator', _binary '', 'Tự Nhiên Xã Hội',
        '2024-05-15 14:21:59.640057', 1, NULL, '2024-05-15 14:22:00.000000', 0),
       (51, '2024-05-15 14:27:19.349095', 1, '<p>Tự Nhiên Xã Hội</p>', 'Administrator', _binary '', 'Tự Nhiên Xã Hội',
        '2024-05-15 14:27:19.349095', 1, NULL, '2024-05-15 14:28:00.000000', 1),
       (52, '2024-05-15 14:45:49.470525', 1, '<p>Xin lỗi test</p>', 'Xin lỗi test', _binary '', 'Xin lỗi test',
        '2024-05-15 14:45:49.470525', 1, 3, '2024-05-15 14:46:00.000000', 2),
       (53, '2024-05-15 15:32:36.274559', 1, '<p>Xin lỗi test</p>', 'Xin lỗi test', _binary '', 'Xin lỗi test',
        '2024-05-15 15:32:36.274559', 1, 3, '2024-05-15 19:46:00.000000', 0),
       (54, '2024-05-15 15:39:19.848039', 1,
        '<p>We are extremely happy to announce that a new news has been updated on AMELA.</p><p><br>&nbsp;</p>',
        'Administrator', _binary '', 'We are extremely happy to announce that a new news has been updated on AMELA.',
        '2024-05-15 15:39:19.848039', 1, NULL, '2024-05-15 15:40:00.000000', 1);
/*!40000 ALTER TABLE `message_schedule_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_tbl`
--

DROP TABLE IF EXISTS `role_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_tbl`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6)  DEFAULT NULL,
    `created_by`  bigint       DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `status`      bit(1) NOT NULL,
    `update_at`   datetime(6)  DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_tbl`
--

LOCK TABLES `role_tbl` WRITE;
/*!40000 ALTER TABLE `role_tbl`
    DISABLE KEYS */;
INSERT INTO `role_tbl`
VALUES (1, NULL, 1, 'description', 'ADMIN', _binary '', NULL, 1),
       (2, NULL, 1, 'description', 'USER', _binary '', NULL, 1);
/*!40000 ALTER TABLE `role_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group_tbl`
--

DROP TABLE IF EXISTS `user_group_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group_tbl`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `group_id` bigint DEFAULT NULL,
    `user_id`  bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKqggr9pa8fq69gyxkj5e9ccx1b` (`group_id`),
    KEY `FKgsry4qa3qst0spwc1xx7kqysl` (`user_id`),
    CONSTRAINT `FKgsry4qa3qst0spwc1xx7kqysl` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`),
    CONSTRAINT `FKqggr9pa8fq69gyxkj5e9ccx1b` FOREIGN KEY (`group_id`) REFERENCES `group_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_tbl`
--

LOCK TABLES `user_group_tbl` WRITE;
/*!40000 ALTER TABLE `user_group_tbl`
    DISABLE KEYS */;
INSERT INTO `user_group_tbl`
VALUES (6, 1, 4),
       (7, 1, 1),
       (9, 1, 8),
       (10, 3, 9),
       (11, 3, 8),
       (12, 3, 7),
       (13, 3, 6),
       (14, 3, 11);
/*!40000 ALTER TABLE `user_group_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_message_schedule_tbl`
--

DROP TABLE IF EXISTS `user_message_schedule_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_message_schedule_tbl`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `message_schedule_id` bigint DEFAULT NULL,
    `user_id`             bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKsge9vcalb4vgv767c7md3eqjk` (`message_schedule_id`),
    KEY `FKrqtd0mrv98uyhp7j1uvsgekiq` (`user_id`),
    CONSTRAINT `FKrqtd0mrv98uyhp7j1uvsgekiq` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`),
    CONSTRAINT `FKsge9vcalb4vgv767c7md3eqjk` FOREIGN KEY (`message_schedule_id`) REFERENCES `message_schedule_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 51
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_message_schedule_tbl`
--

LOCK TABLES `user_message_schedule_tbl` WRITE;
/*!40000 ALTER TABLE `user_message_schedule_tbl`
    DISABLE KEYS */;
INSERT INTO `user_message_schedule_tbl`
VALUES (30, 14, 4),
       (31, 14, 1),
       (32, 18, 4),
       (33, 34, 9),
       (34, 35, 9),
       (35, 37, 9),
       (36, 38, 11),
       (37, 39, 11),
       (38, 40, 11),
       (39, 41, 11),
       (40, 42, 11),
       (41, 43, 11),
       (42, 44, 11),
       (43, 45, 11),
       (44, 46, 11),
       (45, 47, 11),
       (46, 48, 11),
       (47, 49, 11),
       (48, 50, 11),
       (49, 51, 11),
       (50, 54, 11);
/*!40000 ALTER TABLE `user_message_schedule_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tbl`
--

DROP TABLE IF EXISTS `user_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tbl`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `address`          varchar(255) DEFAULT NULL,
    `avatar`           varchar(255) DEFAULT NULL,
    `code`             varchar(255) NOT NULL,
    `created_at`       datetime(6)  DEFAULT NULL,
    `created_by`       bigint       DEFAULT NULL,
    `date_of_birth`    date         DEFAULT NULL,
    `email`            varchar(255) NOT NULL,
    `enabled`          bit(1)       NOT NULL,
    `firstname`        varchar(255) DEFAULT NULL,
    `gender`           int          NOT NULL,
    `lastname`         varchar(255) DEFAULT NULL,
    `password`         varchar(255) DEFAULT NULL,
    `phone`            varchar(255) DEFAULT NULL,
    `update_at`        datetime(6)  DEFAULT NULL,
    `update_by`        bigint       DEFAULT NULL,
    `username`         varchar(255) NOT NULL,
    `department_id`    bigint       DEFAULT NULL,
    `job_position_id`  bigint       DEFAULT NULL,
    `role_id`          bigint       DEFAULT NULL,
    `activated`        bit(1)       NOT NULL,
    `is_edit_username` bit(1)       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_4jy6gy5w2iw0idxxvbmh46mmx` (`code`),
    UNIQUE KEY `UK_i4ygcc30htflmb5xe5mjcydid` (`email`),
    UNIQUE KEY `UK_xkjl2orevvtyrqqshcot355j` (`username`),
    KEY `FK3ym8qer0cptlwfb8j2bx6l8gu` (`department_id`),
    KEY `FKj3rqu4q3l4wn0l3i0lm0av5gr` (`job_position_id`),
    KEY `FK9xkyfi057wjvaal9c8dk6k2j7` (`role_id`),
    CONSTRAINT `FK3ym8qer0cptlwfb8j2bx6l8gu` FOREIGN KEY (`department_id`) REFERENCES `department_tbl` (`id`),
    CONSTRAINT `FK9xkyfi057wjvaal9c8dk6k2j7` FOREIGN KEY (`role_id`) REFERENCES `role_tbl` (`id`),
    CONSTRAINT `FKj3rqu4q3l4wn0l3i0lm0av5gr` FOREIGN KEY (`job_position_id`) REFERENCES `job_position_tbl` (`id`),
    CONSTRAINT `user_tbl_chk_1` CHECK ((`gender` between 0 and 1))
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tbl`
--

LOCK TABLES `user_tbl` WRITE;
/*!40000 ALTER TABLE `user_tbl`
    DISABLE KEYS */;
INSERT INTO `user_tbl`
VALUES (1, 'Hà Nam', '/image/avatar.jpg', 'A000001', '2024-04-24 14:22:48.274497', 1, '2001-09-15',
        'nguyentienkhoi.it@gmail.com', _binary '', 'Nguyễn', 1, 'Tiền Khôi',
        '$2a$10$BWvrs8Mwst9AAYRxk8zt8upDsPVnjaq6gp90.6o/VthYqvAbRnokq', '0355166404', '2024-05-13 15:00:38.099807', 1,
        'nguyentienkhoi', 3, 1, 1, _binary '', _binary ''),
       (4, 'Hải Phòng', '/image/avatar.jpg', 'A000002', '2024-04-24 14:22:48.274497', 1, '2003-12-18',
        'hongminhsn1812@gmail.com', _binary '', 'Nguyễn', 0, 'Hồng Minh',
        '$2y$10$wP8VbstCeG16DJPB3WVFienk46N1pMJ96HYhPPW60yUeowkaFtNGm', '0354968597', '2024-05-15 08:30:21.444280', 1,
        'nguyenhongminh', 1, 1, 2, _binary '', _binary ''),
       (6, 'Hải Phòng', '/image/avatar.jpg', 'A000003', '2024-05-08 17:10:59.637370', 1, '2001-12-09',
        'kazavn02@gmail.com', _binary '', 'Nguyễn', 1, 'Khôi',
        '$2a$10$wk/u7jKZ2eyBL3/OrpGY9e4x0u75R4sfrVPy/UcOU0ldEkMUXr1l6', '0355166406', '2024-05-10 16:59:05.969259', 1,
        'kazavn02', 4, 1, 2, _binary '', _binary ''),
       (7, 'Hải Phòng', '/image/avatar.jpg', 'A000004', '2024-05-10 09:03:43.172219', 1, '2001-02-01',
        'kazavn01@gmail.com', _binary '', 'Nguyễn', 1, 'Văn An',
        '$2a$10$ypGoxlVUV3P.4FUug15eYextlf08ZFyJMy2V2erKHF5d6VOg36kjq', '0355166241', '2024-05-10 09:03:43.172219', 7,
        'nguyenvanana', 5, 5, 2, _binary '', _binary '\0'),
       (8, 'Ngọc Lũ, Bình Lục, Hà Nam', '/image/avatar.jpg', 'A000005', '2024-05-10 10:36:12.425245', 1, '2001-02-28',
        'buitheanh2802@gmail.com', _binary '', 'Bùi t', 1, 'Thế Anh',
        '$2a$10$hcIHAYjZbdP49K2UNtmCte6fIKLE5TNMujw0wUD8H.YDhY8F1Bq62', '0396985938', '2024-05-16 09:07:52.086494', 1,
        'buitheanh', 4, 4, 2, _binary '', _binary ''),
       (9, 'Bắc Ninh', '/image/avatar.jpg', 'A000006', '2024-05-10 16:24:01.699289', 1, '2001-02-28',
        'nguyenquyentdbn2708@gmail.com', _binary '', 'Nguyễn', 0, 'Quyên',
        '$2a$10$tv7q4VFPJBQV.K/mENPr4ulMHdX5A5yIRMD/fmU/A3EJ3fqFGSk7q', '0399584768', '2024-05-13 23:58:55.024217', 1,
        'lequyen', 1, 5, 2, _binary '', _binary ''),
       (10, 'Thủy Triều, Thủy Nguyên, Hải Phòng', '/image/avatar.jpg', 'A000007', '2024-05-13 14:51:57.099415', 1,
        '2003-12-18', 'admin@gmail.com', _binary '', 'Nguyễn', 0, 'Hồng Minh',
        '$2a$10$LofgMCWYH1HO5Vu8S1fS5e4dwQ.NzNHhp2DDcyZciHwXhrDu2ZOhG', '0351723128', '2024-05-13 23:56:39.380802', 1,
        'admin', 3, 4, 1, _binary '', _binary ''),
       (11, 'Hải Phòng', '/image/avatar.jpg', 'A000008', '2024-05-15 08:32:31.637308', 1, '2000-05-15',
        'nguyenlehungsc@gmail.com', _binary '', 'Nguyen', 1, 'Le Hung',
        '$2a$10$GXOoY2W3mUisKtARXTE8lukcgbVWbI.pBCtogumFw2YBYd0MQiVIO', '0355166424', '2024-05-15 08:32:31.637308', 1,
        'lehung', 6, 3, 2, _binary '', _binary ''),
       (12, '', '/image/avatar.jpg', 'A000009', '2024-05-16 09:09:26.246981', 1, '2000-05-16', 'nguyenquyen@gmail.com',
        _binary '\0', 'Nguyen', 0, 'quyen', '$2a$10$iBVZRh6fIbFoX5VCj5LBtud0pfjKHFuOlKzpZaeNCF59Jx08QEtcC',
        '03551664032', '2024-05-16 09:27:00.841621', 1, 'quyen', 1, 1, 2, _binary '\0', _binary ''),
       (13, 'Hòa Lạc', '/image/avatar.jpg', 'A000010', '2024-05-16 09:13:13.224192', 1, '2000-01-01',
        'nguyenbao@gmail.com', _binary '', 'Nguyen', 0, 'quyen',
        '$2a$10$xLDp5Mk0k3AZi3THR6loGeH5ShU91AVV02glVAL6N3J1XJxcPeoh2', '02948376846', '2024-05-16 09:13:13.224192', 13,
        'nguyenbao', 1, 1, 2, _binary '', _binary '\0'),
       (14, 'Hà Nam', '/image/avatar.jpg', 'A000011', '2024-05-16 10:15:28.774330', 1, '2001-01-01',
        'levanlieu@gmail.com', _binary '\0', 'Lương', 1, 'Văn Liệu',
        '$2a$10$gxf2CmgmTgmMpBIFprw7AeRC.2EP/NJYgYJIhBZiQ1MXvV7.dxX/y', '0192893847', '2024-05-16 10:15:28.774330', 1,
        'levanlieu14', 3, 4, 2, _binary '', _binary '');
/*!40000 ALTER TABLE `user_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verify_token_tbl`
--

DROP TABLE IF EXISTS `verify_token_tbl`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verify_token_tbl`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)  DEFAULT NULL,
    `expire_at`  datetime(6)  DEFAULT NULL,
    `token`      varchar(255) DEFAULT NULL,
    `user_id`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK6yryw9xt8rm069p8tdogea2o6` (`user_id`),
    CONSTRAINT `FK6yryw9xt8rm069p8tdogea2o6` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 57
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verify_token_tbl`
--

LOCK TABLES `verify_token_tbl` WRITE;
/*!40000 ALTER TABLE `verify_token_tbl`
    DISABLE KEYS */;
INSERT INTO `verify_token_tbl`
VALUES (52, '2024-05-15 08:32:31.720075', '2024-05-15 09:32:31.720075', '28a931cb-94bb-4d36-a572-ad1110b8446f', 11),
       (56, '2024-05-16 10:15:28.884203', '2024-05-16 11:15:28.884203', '35b81593-3dfb-4cf1-9c84-35644c9705b2', 14);
/*!40000 ALTER TABLE `verify_token_tbl`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2024-05-16 14:41:40
