CREATE DATABASE IF NOT EXISTS `amela_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;
USE `amela_database`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: amela_database
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
    `check_day`      date         DEFAULT NULL,
    `check_in_time`  time(6)      DEFAULT NULL,
    `check_out_time` time(6)      DEFAULT NULL,
    `created_at`     datetime(6)  DEFAULT NULL,
    `created_by`     bigint       DEFAULT NULL,
    `note`           varchar(255) DEFAULT NULL,
    `status`         bit(1) NOT NULL,
    `update_at`      datetime(6)  DEFAULT NULL,
    `update_by`      bigint       DEFAULT NULL,
    `user_id`        bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKl5aaa4t4uqjf85g0aai4hdtsv` (`user_id`),
    CONSTRAINT `FKl5aaa4t4uqjf85g0aai4hdtsv` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
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
VALUES (1, '2024-05-22', '16:38:33.784000', '16:38:35.739000', NULL, 3, NULL, _binary '', '2024-05-22 16:38:33.783733',
        3, 3),
       (2, '2024-05-22', '17:12:00.000000', '18:07:00.000000', NULL, 1, '', _binary '', '2024-05-23 23:31:34.852039',
        1, 1),
       (3, '2024-05-23', '00:03:46.000000', '17:21:07.387000', NULL, 3, NULL, _binary '', '2024-05-23 00:03:46.240155',
        3, 3),
       (4, '2024-05-23', '21:35:32.807000', '22:54:20.181000', NULL, 1, NULL, _binary '', '2024-05-23 21:35:32.807192',
        1, 1),
       (5, '2024-05-10', '08:00:00.000000', '17:00:00.000000', '2024-05-23 22:53:55.881308', 1, '', _binary '',
        '2024-05-23 22:53:55.881308', 1, 1);
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
  AUTO_INCREMENT = 5
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
VALUES (1, '2024-04-24 14:22:48.274497', 1, 'description', 'Faderless', _binary '', '2024-04-24 14:22:48.274497', 1),
       (2, '2024-05-22 16:57:52.253165', 1, 'description', 'Hades', _binary '', '2024-05-22 16:57:52.253165', 1),
       (3, '2024-05-22 16:58:02.855618', 1, 'description', 'Warrior', _binary '', '2024-05-22 16:58:02.855618', 1),
       (4, '2024-05-22 16:58:28.376347', 1, 'description', 'Thor', _binary '', '2024-05-22 16:58:28.376347', 1);
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
  AUTO_INCREMENT = 3
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
VALUES (1, '2024-05-22 17:00:00.502519', 1, 'description', 'Developer Group', _binary '', '2024-05-23 20:56:14.482529',
        1);
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
VALUES (1, '2024-04-24 14:22:48.274497', 1, 'description', 'Developer', _binary '', '2024-04-24 14:22:48.274497', 1),
       (2, '2024-05-22 16:54:55.396010', 1, 'description', 'Business Analysist', _binary '',
        '2024-05-22 16:54:55.396010', 1),
       (3, '2024-05-22 16:55:08.125212', 1, 'description', 'Tester', _binary '', '2024-05-22 16:55:08.125212', 1),
       (4, '2024-05-22 16:55:18.055005', 1, 'description', 'Adminstrator', _binary '', '2024-05-22 16:55:18.055005',
        1),
       (5, '2024-05-22 16:56:31.204837', 1, 'description', 'Quality Assurance', _binary '',
        '2024-05-22 16:56:31.204837', 1),
       (6, '2024-05-22 16:57:19.040007', 1, 'description\r\n', 'Quality Control', _binary '',
        '2024-05-22 16:57:19.040007', 1);
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
    `publish_at`  datetime(6)  DEFAULT NULL,
    `sender_name` varchar(255) DEFAULT NULL,
    `status`      bit(1) NOT NULL,
    `subject`     varchar(255) DEFAULT NULL,
    `update_at`   datetime(6)  DEFAULT NULL,
    `update_by`   bigint       DEFAULT NULL,
    `viewers`     bigint       DEFAULT NULL,
    `group_id`    bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKjgr0cguu1cli54k1jmd4xtgs1` (`group_id`),
    CONSTRAINT `FKjgr0cguu1cli54k1jmd4xtgs1` FOREIGN KEY (`group_id`) REFERENCES `group_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
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
VALUES (1, '2024-05-22 15:41:06.503872', 1,
        '<p>Việc sử dụng phần mềm quản lý đã trở thành một xu hướng không thể thiếu đối với các doanh nghiệp ngày nay. Trong đó, phần mềm DMS là giải pháp tốt nhất cho việc quản lý phân phối sản phẩm. Nếu doanh nghiệp muốn điều hành và quản lý hoạt động kinh doanh hiệu quả thì không nên bỏ qua phần mềm này. Bài viết dưới đây được AMELA tổng hợp “tất tần tật” thông tin về phần mềm DMS. Hãy cùng tìm hiểu ngay!&nbsp;</p><h2><strong>Khái niệm cơ bản về phần mềm DMS cho doanh nghiệp hiện nay</strong></h2><figure class=\"image\"><img style=\"aspect-ratio:800/500;\" src=\"https://amela.vn/wp-content/uploads/2023/07/1.-Khai-niem-phan-mem-DMS-la-gi-.jpg\" alt=\"Khái niệm phần mềm DMS là gì?&nbsp;\" srcset=\"https://amela.vn/wp-content/uploads/2023/07/1.-Khai-niem-phan-mem-DMS-la-gi-.jpg 800w, https://amela.vn/wp-content/uploads/2023/07/1.-Khai-niem-phan-mem-DMS-la-gi--300x188.jpg 300w, https://amela.vn/wp-content/uploads/2023/07/1.-Khai-niem-phan-mem-DMS-la-gi--768x480.jpg 768w\" sizes=\"100vw\" width=\"800\" height=\"500\"></figure><p><i>Khái niệm phần mềm DMS là gì?</i></p><p>Quản lý phân phối là hoạt động quan trọng trong kinh doanh của bất kỳ doanh nghiệp nào. Để tối ưu hóa quá trình này, phần mềm DMS được ra đời để đáp ứng hiệu quả làm việc. Vậy phần mềm DMS là gì?&nbsp;</p><p>DMS là viết tắt của Distribution Management System. Đây là một phần mềm quản lý phân phối sản phẩm cần thiết cho các doanh nghiệp. Phần mềm DMS được thiết kế để hỗ trợ và tối ưu hóa quy trình với các công cụ và chức năng của hệ thống.&nbsp; DMS có khả năng quản lý và điều phối hiệu quả các hoạt động kinh doanh. Bằng cách thông qua việc quản lý các quy trình kinh doanh. Cụ thể như quản lý kho, quản lý đơn hàng, quản lý bán hàng, quản lý nhân viên và khách hàng.</p><h2><strong>Chức năng của phần mềm quản lý phân phối DMS là gì?</strong></h2><figure class=\"image\"><img style=\"aspect-ratio:800/500;\" src=\"https://amela.vn/wp-content/uploads/2023/07/2.-Mot-so-chuc-nang-quan-trong-cua-phan-mem-DMS-.jpg\" alt=\"Một số chức năng quan trọng của phần mềm DMS&nbsp;\" srcset=\"https://amela.vn/wp-content/uploads/2023/07/2.-Mot-so-chuc-nang-quan-trong-cua-phan-mem-DMS-.jpg 800w, https://amela.vn/wp-content/uploads/2023/07/2.-Mot-so-chuc-nang-quan-trong-cua-phan-mem-DMS--300x188.jpg 300w, https://amela.vn/wp-content/uploads/2023/07/2.-Mot-so-chuc-nang-quan-trong-cua-phan-mem-DMS--768x480.jpg 768w\" sizes=\"100vw\" width=\"800\" height=\"500\"></figure><p><i>Một số chức năng quan trọng của phần mềm DMS</i></p><h3><strong>1. Quản lý kho hàng:&nbsp;</strong></h3><p>DMS cho phép quản lý và kiểm soát quá trình nhập, xuất và lưu trữ hàng hóa trong kho. Nó cung cấp các công cụ để theo dõi số lượng hàng tồn kho, tạo phiếu nhập, xuất kho và thực hiện kiểm kê hàng hóa. Điều này giúp cho doanh nghiệp dự đoán được nhu cầu tiêu thụ của khách hàng. Từ đó, chủ động trong việc lên kế hoạch nhập hàng hợp lý. Đồng thời, DMS cũng cung cấp các chức năng quản lý vị trí lưu trữ và quản lý chuỗi cung ứng trong kho hàng.&nbsp;</p><h3><strong>2. Quản lý đơn hàng:&nbsp;</strong></h3><p>Phần mềm DMS cung cấp các công cụ để nhập thông tin đơn hàng, tạo và theo dõi tiến độ thực hiện đơn hàng. Điều này giúp tăng cường khả năng đáp ứng đơn hàng và giảm thiểu sai sót mỗi đơn hàng. Đồng thời, giúp doanh nghiệp tăng tính chuyên nghiệp và tăng sự tin tưởng của khách hàng.&nbsp;</p><h3><strong>3. Quản lý vận chuyển:&nbsp;</strong></h3><p>DMS cung cấp chức năng quản lý vận chuyển, bao gồm lựa chọn đơn vị vận chuyển, lập kế hoạch và theo dõi lịch trình vận chuyển. Nó giúp tối ưu hóa quá trình vận chuyển hàng hóa từ điểm gốc đến điểm đích. Đồng thời cung cấp thông tin về tình trạng vận chuyển của hàng hóa. Điều này giúp doanh nghiệp cải thiện quy trình vận chuyển. Ngoài ra, nó còn thời gian và chi phí vận chuyển để đáp ứng đúng thời gian cho khách hàng.</p><h3><strong>4. Quản lý thông tin khách hàng:</strong></h3><p>DMS cho phép doanh nghiệp lưu trữ và quản lý thông tin chi tiết về khách hàng. Điều này bao gồm thông tin cá nhân, lịch sử mua hàng, ưu đãi đặc biệt, và các thông tin liên quan khác. Phần mềm DMS giúp doanh nghiệp hiểu rõ nhu cầu và yêu cầu của khách hàng. Từ đó tăng cường khả năng phục vụ và xây dựng mối quan hệ khách hàng lâu dài.</p><h3><strong>5. Báo cáo và phân tích:&nbsp;</strong></h3><p>DMS cung cấp chức năng tạo báo cáo và phân tích dữ liệu liên quan đến quá trình quản lý và điều phối. Doanh nghiệp có thể tạo ra báo cáo về doanh số bán hàng, lợi nhuận, xu hướng thị trường và hiệu suất hoạt động. Điều này giúp doanh nghiệp đánh giá hiệu quả của quy trình quản lý phân phối. Nhờ đó tìm kiếm cơ hội cải tiến và đưa ra quyết định thông minh cho kinh doanh.</p><h3><strong>6. Tích hợp hệ thống:&nbsp;</strong></h3><figure class=\"image\"><img style=\"aspect-ratio:800/500;\" src=\"https://amela.vn/wp-content/uploads/2023/07/3.-DMS-co-kha-nang-tich-hop-voi-cac-he-thong-quan-ly-CRM-.jpg\" alt=\"DMS có khả năng tích hợp với các hệ thống quản lý CRM&nbsp;\" srcset=\"https://amela.vn/wp-content/uploads/2023/07/3.-DMS-co-kha-nang-tich-hop-voi-cac-he-thong-quan-ly-CRM-.jpg 800w, https://amela.vn/wp-content/uploads/2023/07/3.-DMS-co-kha-nang-tich-hop-voi-cac-he-thong-quan-ly-CRM--300x188.jpg 300w, https://amela.vn/wp-content/uploads/2023/07/3.-DMS-co-kha-nang-tich-hop-voi-cac-he-thong-quan-ly-CRM--768x480.jpg 768w\" sizes=\"100vw\" width=\"800\" height=\"500\"></figure><p><i>DMS có khả năng tích hợp với các hệ thống quản lý CRM</i></p><p>DMS có khả năng tích hợp với các hệ thống và ứng dụng khác trong doanh nghiệp.&nbsp; Ví dụ như hệ thống quản lý kho, hệ thống kế toán và hệ thống quản lý quan hệ khách hàng (CRM). Điều này giúp tạo ra một môi trường làm việc liên thông và tối ưu hóa quy trình kinh doanh trong toàn bộ doanh nghiệp.</p><h2><strong>Những lợi ích mà phần mềm DMS mang lại</strong></h2><p>Phần mềm quản lý phân phối (DMS) mang lại nhiều lợi ích quan trọng trong quá trình quản lý và điều phối hàng hóa, dịch vụ cho doanh nghiệp. Tham khảo những lợi ích chính mà phần mềm DMS có thể mang lại ở dưới đây:&nbsp;</p><h3><strong>Quản lý đặt hàng của khách hàng</strong></h3><p>Một trong những lợi ích quan trọng nhất mà phần mềm DMS mang lại cho doanh nghiệp là quản lý đặt hàng của khách hàng. Phần mềm DMS giúp quản lý quá trình đặt hàng từ khách hàng một cách hiệu quả. Ngoài ra, nó còn tổ chức và quản lý các hoạt động kinh doanh một cách chặt chẽ và hiệu quả hơn. Cụ thể như:&nbsp;</p><ul><li>Tăng cường khả năng theo dõi và quản lý để đáp ứng đơn hàng.&nbsp;</li><li>Tối ưu hóa nhanh chóng và chính xác quy trình đặt hàng.&nbsp;</li><li>Theo dõi và cập nhật trạng thái đơn hàng từ khi đặt hàng cho tới khi giao hàng.&nbsp;</li><li>Giảm thiểu thời gian và công sức trong quy trình xử lý đơn hàng.&nbsp;</li></ul><p>Nhờ lợi ích trong quá trình quản lý đơn hàng, mà doanh nghiệp có thể cải thiện hiệu quả và độ chính xác của quy trình đặt hàng.&nbsp;</p><h3><strong>Quản lý công nợ</strong></h3><figure class=\"image\"><img style=\"aspect-ratio:800/500;\" src=\"https://amela.vn/wp-content/uploads/2023/07/4.-DMS-gui-cac-cac-bao-cao-va-phan-tich-thong-tin-du-lieu-cho-doanh-nghiep-.jpg\" alt=\"DMS gửi các các báo cáo và phân tích thông tin dữ liệu cho doanh nghiệp&nbsp;\" srcset=\"https://amela.vn/wp-content/uploads/2023/07/4.-DMS-gui-cac-cac-bao-cao-va-phan-tich-thong-tin-du-lieu-cho-doanh-nghiep-.jpg 800w, https://amela.vn/wp-content/uploads/2023/07/4.-DMS-gui-cac-cac-bao-cao-va-phan-tich-thong-tin-du-lieu-cho-doanh-nghiep--300x188.jpg 300w, https://amela.vn/wp-content/uploads/2023/07/4.-DMS-gui-cac-cac-bao-cao-va-phan-tich-thong-tin-du-lieu-cho-doanh-nghiep--768x480.jpg 768w\" sizes=\"100vw\" width=\"800\" height=\"500\"></figure><p><i>DMS gửi các các báo cáo và phân tích thông tin dữ liệu cho doanh nghiệp</i></p><p>Việc quản lý công nợ là một trong những vấn đề quan trọng nhất của doanh nghiệp. Với phần mềm DMS, các doanh nghiệp có thể quản lý công nợ của khách hàng một cách chặt chẽ hơn. Điều này đảm bảo cho doanh nghiệp tránh các những rủi ro về tài chính. Để thực hiện những điều này, phần mềm xây dựng một hệ thống giúp doanh nghiệp:&nbsp;</p><ul><li>Tự động hóa quy trình thu hồi công nợ. Từ việc tạo và gửi các thông báo thanh toán cho khách hàng đến theo dõi các khoản thanh toán đã nhận được.</li><li>Giúp doanh nghiệp tăng cường quản lý dòng tiền và tài chính. Bằng cách theo dõi và ứng dụng các biện pháp để giảm công nợ quá hạn.</li><li>Xây dựng và duy trì mối quan hệ tốt hơn với khách hàng. Thông qua việc theo dõi và đáp ứng kịp thời các yêu cầu thanh toán từ khách hàng.&nbsp;&nbsp;</li><li>Cung cấp các báo cáo và phân tích liên quan đến công nợ, để đưa ra các biện pháp cải thiện.&nbsp;</li><li>Áp dụng các cấu hình bảo mật để bảo vệ thông tin quan trọng khỏi sự truy cập trái phép và lạm dụng.</li></ul><h3><strong>Quản lý chiết khấu, khuyến mãi và quy trình bán hàng</strong></h3><p>DMS giúp quản lý và theo dõi chiết khấu và khuyến mãi đối với khách hàng và đối tác. DMS cũng hỗ trợ quy trình bán hàng từ việc tạo đơn hàng, xác nhận giao hàng đến lập hóa đơn và thanh toán. Bằng cách phần mềm cung cấp các chức năng để:&nbsp;</p><ul><li>Quản lý và áp dụng các chương trình chiết khấu và khuyến mãi một cách hiệu quả. Điều này giúp doanh nghiệp tăng doanh số bán hàng và cải thiện lợi nhuận.</li><li>Tích hợp quy trình bán hàng, từ việc tạo đơn, xử lý đơn hàng, giao hàng và thanh toán. Nhờ vậy doanh nghiệp có thể tiết kiệm thời gian và tối đa hóa năng suất.</li><li>Xử lý tự động đơn hàng và quản lý lịch trình giao hàng. Việc này đảm bảo giảm thiểu sai sót và thời gian chờ đợi đơn hàng và tăng sự hài lòng của khách hàng.</li><li>Cung cấp các báo cáo và phân tích để theo dõi hiệu quả bán hàng. Từ đó, doanh nghiệp đưa ra được những chính sách và chiến lược bán hàng hiệu quả.&nbsp;</li></ul><h2><strong>Quy trình xây dựng và phát triển phần mềm DMS hiện nay</strong></h2><p>Để thiết kế một phần mềm DMS đáp ứng được nhu cầu của doanh nghiệp là điều không dễ dàng. Vậy nên, hiểu rõ quy trình xây dựng và phát triển phần mềm DMS là việc rất cần thiết.&nbsp;</p><h3><strong>Xác định mục tiêu mà doanh nghiệp đang hướng đến</strong></h3><p>Trước khi xây dựng phần mềm DMS, doanh nghiệp cần phải xác định mục tiêu đang hướng. Mục tiêu của doanh nghiệp có thể là tăng cường khả năng quản lý tài liệu, giảm thiểu thời gian tìm kiếm thông tin, tăng cường tính bảo mật cho tài liệu hoặc giảm thiểu chi phí cho việc quản lý tài liệu. Việc này giúp cho đơn vị phát triển có thể xây dựng phần mềm DMS đáp ứng đúng nhu cầu của doanh nghiệp.</p><h3><strong>Lựa chọn nhà cung cấp (NCC) phù hợp</strong></h3><p>Việc lựa chọn nhà cung cấp phù hợp là bước quan trọng trong quy trình xây dựng phần mềm DMS. Doanh nghiệp có thể tham khảo dịch vụ thiết kế phần mềm của AMELA. Đơn vị sẽ hỗ trợ doanh nghiệp từ tư vấn đến vận hành phần mềm sao cho hiệu quả nhất.&nbsp;&nbsp;</p><p>Xem thêm: <a href=\"https://amela.vn/ve-chung-toi/\"><strong>Thông tin về AMELA</strong></a>&nbsp;</p><p>Hoặc trong quá trình doanh nghiệp tìm kiếm một nhà cung cấp phù hợp. Bạn cần xem xét các yếu tố như chất lượng sản phẩm, uy tín của nhà cung cấp, giá thành sản phẩm và khả năng hỗ trợ sau bán hàng. Bởi vì xây dựng phần mềm là dịch vụ cần phải được cập nhật liên tục trong tương lai. Việc này giúp cho phần mềm theo kịp và đáp ứng được nhu cầu của doanh nghiệp.&nbsp;</p><h3><strong>Tiến hành xây dựng và thử nghiệm&nbsp;</strong></h3><p>Sau khi đã xác định được mục tiêu và lựa chọn được nhà cung cấp phù hợp. Bước tiếp theo là tiến hành xây dựng và thử hiện phần mềm DMS. Quá trình này bao gồm các bước sau:</p><ul><li>Nhà phát triển sẽ tiến hành thiết kế giao diện và các tính năng của phần mềm DMS.</li><li>Sau khi đã hoàn thành thiết kế, nhà phát triển sẽ tiến hành lập trình để tạo ra sản phẩm cuối cùng.</li><li>Nhà phát triển sẽ tiến hành kiểm thử để đảm bảo rằng sản phẩm hoạt động tốt trên các nền tảng khác nhau.</li><li>Phát hành sản phẩm sau khi đã hoàn thành kiểm thử.&nbsp;</li></ul><h3><strong>Áp dụng cho toàn hệ thống</strong></h3><figure class=\"image\"><img style=\"aspect-ratio:800/500;\" src=\"https://amela.vn/wp-content/uploads/2023/07/5.-Tien-hanh-ap-dung-cho-toan-bo-he-thong-.jpg\" alt=\"Tiến hành áp dụng phần mềm DMS cho toàn bộ hệ thống&nbsp;\" srcset=\"https://amela.vn/wp-content/uploads/2023/07/5.-Tien-hanh-ap-dung-cho-toan-bo-he-thong-.jpg 800w, https://amela.vn/wp-content/uploads/2023/07/5.-Tien-hanh-ap-dung-cho-toan-bo-he-thong--300x188.jpg 300w, https://amela.vn/wp-content/uploads/2023/07/5.-Tien-hanh-ap-dung-cho-toan-bo-he-thong--768x480.jpg 768w\" sizes=\"100vw\" width=\"800\" height=\"500\"></figure><p><i>Tiến hành áp dụng phần mềm DMS cho toàn bộ hệ thống</i></p><p>Sau khi đã hoàn thành quá trình xây dựng và thử nghiệm, phần mềm DMS sẽ được áp dụng cho toàn hệ thống của doanh nghiệp. Trong quá trình áp dụng, doanh nghiệp cần cung cấp đầy đủ thông tin cho nhân viên về cách sử dụng phần mềm DMS để đảm bảo tính hiệu quả của sản phẩm.&nbsp;</p><h2><strong>Tổng kết</strong></h2><p>Hiện nay, các nhà cung cấp phần mềm DMS rất đa dạng, từ các nhà cung cấp lớn đến các nhà cung cấp nhỏ. Trong đó, AMELA là công ty hàng đầu trong lĩnh vực thiết kế phần mềm được nhiều khách hàng tin tưởng. Đơn vị không chỉ phục vụ khách hàng trong nước mà còn mở rộng hoạt động quốc tế. Vậy nên, đây là một lựa chọn đáng tin cậy nếu như bạn chưa biết tìm nhà cung cấp nào. Để được hỗ trợ nhanh nhất, khách hàng liên hệ cho AMELA qua các kênh:&nbsp;</p><ul><li><strong>Hotline: </strong>(+84)963 336 334</li><li><strong>Email: </strong><a href=\"mailto:info@amela.vn\"><strong>info@amela.vn</strong></a></li></ul><p><strong>Để lại thông tin tại:</strong><a href=\"https://amela.vn/lien-he/\"><strong> https://amela.vn/lien-he/</strong></a></p><p><strong>Biên tập: Ha Anh Nguyen</strong></p>',
        '2024-05-22 15:59:00.000000', 'Nguyễn Tiền Khôi', _binary '',
        'Phần mềm DMS – Quản lý phân phối mà doanh nghiệp nào cũng cần có', '2024-05-22 15:42:27.442351', 1, 15, NULL),
       (2, '2024-05-23 16:03:11.123365', 1,
        '<p>Buổi chia sẻ trực tiếp giữa các AMers và Ban lãnh đạo nhà A diễn ra vào ngày 15/9 vừa qua.</p><p>Tại buổi chia sẻ, các thành viên nhà A cũng được nghe từ chính CEO Dương Minh Khoa và COO Trần Văn Tấn về các vấn đề liên quan đến chiến lược và tầm nhìn phát triển của AMELA. Đồng thời các A-BOM cũng đã “gỡ rối” nhiều vấn đề còn băn khoăn trong quá trình làm việc của các AMers.</p><figure class=\"image\"><img style=\"aspect-ratio:1024/683;\" src=\"https://amela.vn/wp-content/uploads/2024/03/378544736_820336323428153_6292569481613501989_n-1024x683.jpg\" alt=\"\" srcset=\"https://amela.vn/wp-content/uploads/2024/03/378544736_820336323428153_6292569481613501989_n-1024x683.jpg 1024w, https://amela.vn/wp-content/uploads/2024/03/378544736_820336323428153_6292569481613501989_n-300x200.jpg 300w, https://amela.vn/wp-content/uploads/2024/03/378544736_820336323428153_6292569481613501989_n-768x512.jpg 768w, https://amela.vn/wp-content/uploads/2024/03/378544736_820336323428153_6292569481613501989_n-1536x1025.jpg 1536w, https://amela.vn/wp-content/uploads/2024/03/378544736_820336323428153_6292569481613501989_n.jpg 2048w\" sizes=\"100vw\" width=\"1024\" height=\"683\"></figure><figure class=\"image\"><img style=\"aspect-ratio:1024/683;\" src=\"https://amela.vn/wp-content/uploads/2024/03/378202458_820336313428154_7586408640729639129_n-1024x683.jpg\" alt=\"\" srcset=\"https://amela.vn/wp-content/uploads/2024/03/378202458_820336313428154_7586408640729639129_n-1024x683.jpg 1024w, https://amela.vn/wp-content/uploads/2024/03/378202458_820336313428154_7586408640729639129_n-300x200.jpg 300w, https://amela.vn/wp-content/uploads/2024/03/378202458_820336313428154_7586408640729639129_n-768x512.jpg 768w, https://amela.vn/wp-content/uploads/2024/03/378202458_820336313428154_7586408640729639129_n-1536x1025.jpg 1536w, https://amela.vn/wp-content/uploads/2024/03/378202458_820336313428154_7586408640729639129_n.jpg 2048w\" sizes=\"100vw\" width=\"1024\" height=\"683\"></figure><figure class=\"image\"><img style=\"aspect-ratio:1024/683;\" src=\"https://amela.vn/wp-content/uploads/2024/03/379497736_820325100095942_6892489718867748861_n-1024x683.jpg\" alt=\"\" srcset=\"https://amela.vn/wp-content/uploads/2024/03/379497736_820325100095942_6892489718867748861_n-1024x683.jpg 1024w, https://amela.vn/wp-content/uploads/2024/03/379497736_820325100095942_6892489718867748861_n-300x200.jpg 300w, https://amela.vn/wp-content/uploads/2024/03/379497736_820325100095942_6892489718867748861_n-768x512.jpg 768w, https://amela.vn/wp-content/uploads/2024/03/379497736_820325100095942_6892489718867748861_n-1536x1025.jpg 1536w, https://amela.vn/wp-content/uploads/2024/03/379497736_820325100095942_6892489718867748861_n.jpg 2048w\" sizes=\"100vw\" width=\"1024\" height=\"683\"></figure><figure class=\"image\"><img style=\"aspect-ratio:1024/683;\" src=\"https://amela.vn/wp-content/uploads/2024/03/379798781_820325076762611_3109267340383511592_n-1024x683.jpg\" alt=\"\" srcset=\"https://amela.vn/wp-content/uploads/2024/03/379798781_820325076762611_3109267340383511592_n-1024x683.jpg 1024w, https://amela.vn/wp-content/uploads/2024/03/379798781_820325076762611_3109267340383511592_n-300x200.jpg 300w, https://amela.vn/wp-content/uploads/2024/03/379798781_820325076762611_3109267340383511592_n-768x512.jpg 768w, https://amela.vn/wp-content/uploads/2024/03/379798781_820325076762611_3109267340383511592_n-1536x1025.jpg 1536w, https://amela.vn/wp-content/uploads/2024/03/379798781_820325076762611_3109267340383511592_n.jpg 2048w\" sizes=\"100vw\" width=\"1024\" height=\"683\"></figure><p>Tại buổi chia sẻ, có rất nhiều câu hỏi đã được đặt ra và tất cả đều có được câu trả lời cho vấn đề của riêng mình.</p><p>Một lần nữa xin cảm ơn các AMers đã quan tâm đến buổi Opentalk “AMers hỏi A-BOM trả lời”. Hẹn các AMers trong buổi Opentalk tiếp theo.</p><figure class=\"image\"><img style=\"aspect-ratio:1024/683;\" src=\"https://amela.vn/wp-content/uploads/2024/03/378332520_820325130095939_5927559206055311713_n-1024x683.jpg\" alt=\"\" srcset=\"https://amela.vn/wp-content/uploads/2024/03/378332520_820325130095939_5927559206055311713_n-1024x683.jpg 1024w, https://amela.vn/wp-content/uploads/2024/03/378332520_820325130095939_5927559206055311713_n-300x200.jpg 300w, https://amela.vn/wp-content/uploads/2024/03/378332520_820325130095939_5927559206055311713_n-768x512.jpg 768w, https://amela.vn/wp-content/uploads/2024/03/378332520_820325130095939_5927559206055311713_n-1536x1025.jpg 1536w, https://amela.vn/wp-content/uploads/2024/03/378332520_820325130095939_5927559206055311713_n.jpg 2048w\" sizes=\"100vw\" width=\"1024\" height=\"683\"></figure><p><br>&nbsp;</p>',
        '2024-05-24 16:02:00.000000', 'Administrator', _binary '', 'AMers hỏi A-BOM trả lời',
        '2024-05-23 21:19:32.239338', 1, 4, NULL),
       (5, '2024-05-23 18:54:27.213415', 1,
        '<p>Message [{{name}}, {{age}}, {{address}}, {{code}}, {{email}}, {{phone}}, {{username}}, {{username}}, {{username}}, {{department}}, {{position}}]</p>',
        '2024-05-23 18:55:00.000000', 'Administrator', _binary '', 'Xin chào mọi người ', '2024-05-23 18:54:27.213415',
        1, 1, 1),
       (6, '2024-05-23 18:59:50.143215', 1, '<p>AAAAAAAAAAAAAAAAA</p>', '2024-05-23 18:59:00.000000', 'Administrator',
        _binary '', 'test 1', '2024-05-23 18:59:50.143215', 1, 1, 1),
       (7, '2024-05-23 21:10:27.023827', 1,
        '<p>Message [{{name}}, {{age}}, {{address}}, {{code}}, {{email}}, {{phone}}, {{username}}, {{username}}, {{username}}, {{department}}, {{position}}]</p>',
        '2024-05-23 21:12:00.000000', 'Administrator', _binary '', 'Nguyễn Hồng Minh', '2024-05-23 21:11:05.050068', 1,
        4, NULL);
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
VALUES (1, '2024-04-24 14:22:48.274497', 1, 'description', 'ADMIN', _binary '', '2024-04-24 14:22:48.274497', 1),
       (2, '2024-04-24 14:22:48.274497', 1, 'description', 'USER', _binary '', '2024-04-24 14:22:48.274497', 1);
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
  AUTO_INCREMENT = 11
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
VALUES (1, 1, 2),
       (9, 1, 1),
       (10, 1, 3);
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
  AUTO_INCREMENT = 30
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
VALUES (1, 1, 3),
       (25, 7, 4),
       (26, 2, 1),
       (27, 2, 2),
       (28, 2, 3),
       (29, 2, 4);
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
    `activated`        bit(1)       NOT NULL,
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
    `google_id`        varchar(255) DEFAULT NULL,
    `is_edit_username` bit(1)       NOT NULL,
    `lastname`         varchar(255) DEFAULT NULL,
    `password`         varchar(255) DEFAULT NULL,
    `phone`            varchar(255) DEFAULT NULL,
    `update_at`        datetime(6)  DEFAULT NULL,
    `update_by`        bigint       DEFAULT NULL,
    `username`         varchar(255) NOT NULL,
    `department_id`    bigint       DEFAULT NULL,
    `job_position_id`  bigint       DEFAULT NULL,
    `role_id`          bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_4jy6gy5w2iw0idxxvbmh46mmx` (`code`),
    UNIQUE KEY `UK_i4ygcc30htflmb5xe5mjcydid` (`email`),
    UNIQUE KEY `UK_xkjl2orevvtyrqqshcot355j` (`username`),
    KEY `FK3ym8qer0cptlwfb8j2bx6l8gu` (`department_id`),
    KEY `FKj3rqu4q3l4wn0l3i0lm0av5gr` (`job_position_id`),
    KEY `FK9xkyfi057wjvaal9c8dk6k2j7` (`role_id`),
    CONSTRAINT `FK3ym8qer0cptlwfb8j2bx6l8gu` FOREIGN KEY (`department_id`) REFERENCES `department_tbl` (`id`),
    CONSTRAINT `FK9xkyfi057wjvaal9c8dk6k2j7` FOREIGN KEY (`role_id`) REFERENCES `role_tbl` (`id`),
    CONSTRAINT `FKj3rqu4q3l4wn0l3i0lm0av5gr` FOREIGN KEY (`job_position_id`) REFERENCES `job_position_tbl` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
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
VALUES (1, _binary '', 'Hà Nam', 'nguyentienkhoi.it1.jpg', 'A000001', '2024-04-24 14:22:48.274497', 1, '2001-09-15',
        'nguyentienkhoi.it@gmail.com', _binary '', 'Nguyễn', 1, '118047257070180513473', _binary '', 'Khôi',
        '$2a$10$YhvnNULGJmqIFdClvmqJM.0asTwxWg27YgylqMXfzn1Il07bj4zDS', '0355166404', '2024-04-24 14:22:48.274497', 1,
        'nguyentienkhoi', 1, 1, 1),
       (2, _binary '', 'Bắc Ninh', 'avatar.jpg', 'A000002', '2024-05-22 15:23:56.025699', 1, '2001-07-09',
        'khointhe153249@fpt.edu.vn', _binary '', 'Lương', 1, '114565997441878025943', _binary '', 'Bá Phước',
        '$2a$10$9eKodC4K/UFtf6XxQZu4..OqefB7VFRT8nJ2bxzL3/pgvl44MKUEi', '0322948573', '2024-05-22 16:58:49.476074', 1,
        'khointhe1532492', 2, 1, 2),
       (3, _binary '', 'Hà Giang', 'avatar.jpg', 'A000003', '2024-05-22 15:34:10.224362', 1, '2001-02-01',
        'nguyentienkhoi.dotnet@gmail.com', _binary '', 'Thạch', 1, '105922713109184227545', _binary '', 'Đức Bình',
        '$2a$10$6XC6z5ScqzjKSbcgm8ecfeG2b/SDaoF4oF3Cf4wGsgTqIKKOKwGvu', '0395847582', '2024-05-24 08:53:57.288899', 1,
        'nguyentienkhoi.dotnet3', 3, 1, 2),
       (4, _binary '', 'Hải Phòng', 'hongminhsn18124.jpg', 'A000004', '2024-05-23 20:58:41.454093', 1, '2003-12-18',
        'hongminhsn1812@gmail.com', _binary '', 'Nguyễn', 0, NULL, _binary '', 'Hồng Minh',
        '$2a$10$cfeb5FqPhB2ladtLKeX3VOqdyV.rlN9AmiSTyHtxj3DKWgl4PjbSu', '0395847584', '2024-05-24 08:19:55.572725', 1,
        'hongminhsn18124', 2, 2, 2);
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
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verify_token_tbl`
--

LOCK TABLES `verify_token_tbl` WRITE;
/*!40000 ALTER TABLE `verify_token_tbl`
    DISABLE KEYS */;
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

-- Dump completed on 2024-05-24 10:38:37
