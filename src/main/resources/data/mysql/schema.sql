#
# SQL Export
# Created by Querious (201012)
# Created: 2017年11月18日 GMT+8上午10:38:00
# Encoding: Unicode (UTF-8)
#


DROP DATABASE IF EXISTS `club`;
CREATE DATABASE `club` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_unicode_ci;
USE `club`;




SET @PREVIOUS_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `student_no_sign_course`;
DROP TABLE IF EXISTS `student_images_set`;
DROP TABLE IF EXISTS `student_course`;
DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `sequence`;
DROP TABLE IF EXISTS `reserved_student_reserved_course`;
DROP TABLE IF EXISTS `productorder`;
DROP TABLE IF EXISTS `productcategory`;
DROP TABLE IF EXISTS `productcart_products`;
DROP TABLE IF EXISTS `productcart_image_collection`;
DROP TABLE IF EXISTS `productcart_derived_products`;
DROP TABLE IF EXISTS `productcart`;
DROP TABLE IF EXISTS `product_product_images`;
DROP TABLE IF EXISTS `order_product`;
DROP TABLE IF EXISTS `order_imagecollection`;
DROP TABLE IF EXISTS `order_derivedproduct`;
DROP TABLE IF EXISTS `image_imagecollection`;
DROP TABLE IF EXISTS `imagecollection`;
DROP TABLE IF EXISTS `derprod`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `image`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `course`;


CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `max_seat` int(11) NOT NULL,
  `time_from` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time_to` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_activated` bit(1) NOT NULL,
  `mobile_phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `open_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cart_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1a2bv2chaa6x1ao2xh5yrfgok` (`cart_id`),
  CONSTRAINT `FK1a2bv2chaa6x1ao2xh5yrfgok` FOREIGN KEY (`cart_id`) REFERENCES `productcart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `data` longblob NOT NULL,
  `date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thumbnail` longblob,
  `courese_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKel6a0yp9oty7l58l3dv40reh0` (`courese_id`),
  CONSTRAINT `FKel6a0yp9oty7l58l3dv40reh0` FOREIGN KEY (`courese_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_flag` bit(1) NOT NULL,
  `class_period` int(11) NOT NULL,
  `derived_product_flag` bit(1) NOT NULL,
  `image_collection_flag` bit(1) NOT NULL,
  `long_product_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `product_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_price` double NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4d8llfhcjqt2uems0ev0s2lkl` (`category_id`),
  CONSTRAINT `FK4d8llfhcjqt2uems0ev0s2lkl` FOREIGN KEY (`category_id`) REFERENCES `productcategory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `derprod` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5h3iiwf72jgn919sdlxe8wb48` (`image_id`),
  KEY `FK2126jce78rnnabsusok1vmm2` (`product_id`),
  CONSTRAINT `FK2126jce78rnnabsusok1vmm2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK5h3iiwf72jgn919sdlxe8wb48` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `imagecollection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `collection_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `collection_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnryqxlcmcut4n6qa4d3np4n04` (`product_id`),
  CONSTRAINT `FKnryqxlcmcut4n6qa4d3np4n04` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `image_imagecollection` (
  `imagecollection_id` bigint(20) NOT NULL,
  `image_id` bigint(20) NOT NULL,
  PRIMARY KEY (`image_id`,`imagecollection_id`),
  KEY `FKkaitke2aoicp57dv7cb6ac3ts` (`imagecollection_id`),
  CONSTRAINT `FKkaitke2aoicp57dv7cb6ac3ts` FOREIGN KEY (`imagecollection_id`) REFERENCES `imagecollection` (`id`),
  CONSTRAINT `FKb2d637n0onwe8fde04uhy4w3f` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `order_derivedproduct` (
  `order_id` bigint(20) NOT NULL,
  `copies_in_order` int(11) DEFAULT NULL,
  `derivedproduct_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`,`derivedproduct_id`),
  KEY `FK4ctpl8lwnyyqllltbbm7lgfef` (`derivedproduct_id`),
  CONSTRAINT `FKfpdva06pxremb560yhae8x0km` FOREIGN KEY (`order_id`) REFERENCES `productorder` (`id`),
  CONSTRAINT `FK4ctpl8lwnyyqllltbbm7lgfef` FOREIGN KEY (`derivedproduct_id`) REFERENCES `derprod` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `order_imagecollection` (
  `order_id` bigint(20) NOT NULL,
  `copies_in_order` int(11) DEFAULT NULL,
  `imagecollection_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`,`imagecollection_id`),
  KEY `FK76hgurd5s7tm3xk5djmaot943` (`imagecollection_id`),
  CONSTRAINT `FKd940bmna759ehyt71k9k0iql2` FOREIGN KEY (`order_id`) REFERENCES `productorder` (`id`),
  CONSTRAINT `FK76hgurd5s7tm3xk5djmaot943` FOREIGN KEY (`imagecollection_id`) REFERENCES `imagecollection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `order_product` (
  `order_id` bigint(20) NOT NULL,
  `copies_in_order` int(11) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `FKhnfgqyjx3i80qoymrssls3kno` (`product_id`),
  CONSTRAINT `FK1gd0xsctlhkulnky301nggh0d` FOREIGN KEY (`order_id`) REFERENCES `productorder` (`id`),
  CONSTRAINT `FKhnfgqyjx3i80qoymrssls3kno` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `product_product_images` (
  `product_id` bigint(20) NOT NULL,
  `product_images_id` bigint(20) NOT NULL,
  PRIMARY KEY (`product_id`,`product_images_id`),
  UNIQUE KEY `UK_5vj3i7hktqk0bbtsidjqpv8yo` (`product_images_id`),
  CONSTRAINT `FKjcpr41wjqs2rxs1cs4460gn6h` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKalmjvw8bg4mae7yfflgeuvei7` FOREIGN KEY (`product_images_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `productcart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoixxn30yj18fn973ixes1v28y` (`customer_id`),
  CONSTRAINT `FKoixxn30yj18fn973ixes1v28y` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `productcart_derived_products` (
  `product_cart_id` bigint(20) NOT NULL,
  `derived_products_id` bigint(20) NOT NULL,
  PRIMARY KEY (`product_cart_id`,`derived_products_id`),
  UNIQUE KEY `UK_7155brit20slvamm0oyr8i54q` (`derived_products_id`),
  CONSTRAINT `FKbi2hq81ypk2kuii7f6weuqouc` FOREIGN KEY (`product_cart_id`) REFERENCES `productcart` (`id`),
  CONSTRAINT `FK9s6ptq9h43pg6i7flr2gv0n58` FOREIGN KEY (`derived_products_id`) REFERENCES `derprod` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `productcart_image_collection` (
  `product_cart_id` bigint(20) NOT NULL,
  `image_collection_id` bigint(20) NOT NULL,
  PRIMARY KEY (`product_cart_id`,`image_collection_id`),
  UNIQUE KEY `UK_3vdqa7d9c5e1ik7v0ur1st68j` (`image_collection_id`),
  CONSTRAINT `FKb30y8livry96vab6s5myfmsm8` FOREIGN KEY (`product_cart_id`) REFERENCES `productcart` (`id`),
  CONSTRAINT `FKrmkfgh1cn0nd3n32a7lonxy7w` FOREIGN KEY (`image_collection_id`) REFERENCES `imagecollection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `productcart_products` (
  `product_cart_id` bigint(20) NOT NULL,
  `products_id` bigint(20) NOT NULL,
  PRIMARY KEY (`product_cart_id`,`products_id`),
  UNIQUE KEY `UK_noptafdbappfl64aubyk9ynom` (`products_id`),
  CONSTRAINT `FKidywstll56tsecddc6h0hggou` FOREIGN KEY (`product_cart_id`) REFERENCES `productcart` (`id`),
  CONSTRAINT `FKo2vt7nc101rvwq6da0gsskm8b` FOREIGN KEY (`products_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `productcategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kvnx6xsj7mgeu77inwih66dhb` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `productorder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_amount` double NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6vh00u13alkadkag43vx6gle7` (`customer_id`),
  CONSTRAINT `FK6vh00u13alkadkag43vx6gle7` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `reserved_student_reserved_course` (
  `reserved_course_id` bigint(20) NOT NULL,
  `reserved_student_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`reserved_student_id`,`reserved_course_id`),
  KEY `FK8t1rpy0ootybf8iqx4tw5ca1r` (`reserved_course_id`),
  CONSTRAINT `FK8t1rpy0ootybf8iqx4tw5ca1r` FOREIGN KEY (`reserved_course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK244j6mxl9ywotkuvljpiosthb` FOREIGN KEY (`reserved_student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `sequence` (
  `seq_name` varchar(50) NOT NULL,
  `next_val` bigint(20) NOT NULL,
  `increment_val` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `student` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `birthday` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `class_period` int(11) NOT NULL,
  `done_periods` int(11) NOT NULL,
  `is_child` bit(1) NOT NULL,
  `left_periods` int(11) NOT NULL,
  `student_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm44t4smj0reey6kau75yxm9k2` (`customer_id`),
  CONSTRAINT `FKm44t4smj0reey6kau75yxm9k2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `student_course` (
  `course_id` bigint(20) NOT NULL,
  `student_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`student_id`,`course_id`),
  KEY `FKejrkh4gv8iqgmspsanaji90ws` (`course_id`),
  CONSTRAINT `FKejrkh4gv8iqgmspsanaji90ws` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FKq7yw2wg9wlt2cnj480hcdn6dq` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `student_images_set` (
  `student_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `images_set_id` bigint(20) NOT NULL,
  PRIMARY KEY (`student_id`,`images_set_id`),
  UNIQUE KEY `UK_slugq596bfswukw674lom6t1q` (`images_set_id`),
  CONSTRAINT `FKfr4ngd0gce94s7j5w3pdr9lcy` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKipfw2hqiaqxr1v4o004mm7fno` FOREIGN KEY (`images_set_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `student_no_sign_course` (
  `no_sign_course_id` bigint(20) NOT NULL,
  `student_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`student_id`,`no_sign_course_id`),
  KEY `FKbwb6vj3x32i2dicma0l06tcpm` (`no_sign_course_id`),
  CONSTRAINT `FKbwb6vj3x32i2dicma0l06tcpm` FOREIGN KEY (`no_sign_course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `FK9xtoyto98m4fjm8s3qbxw6dw4` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




SET FOREIGN_KEY_CHECKS = @PREVIOUS_FOREIGN_KEY_CHECKS;


