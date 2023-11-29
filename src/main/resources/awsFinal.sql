/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DELETE FROM `comments`;
INSERT INTO `comments` (`id`, `creator`, `replyto`, `text`, `postid`) VALUES
                                                                          (4, 13, NULL, 'ADIOS', 43),
                                                                          (5, 13, 4, 'HOLA', 43),
                                                                          (6, 13, 4, '', 12),
                                                                          (7, 13, NULL, 'asdf', 12),
                                                                          (8, 13, 7, 'probando comentarios', 12),
                                                                          (9, 13, 7, 'proband respuestas', 12),
                                                                          (10, 13, 7, 'no entiendo', 12),
                                                                          (11, 13, 4, 'ya funciona', 12),
                                                                          (12, 13, 7, 'Testing', 12),
                                                                          (13, 13, 4, 'testing comments by JR', 12),
                                                                          (14, 13, 4, 'hmm?', 12),
                                                                          (15, 13, 7, 'test by WN', 12),
                                                                          (16, 13, 4, 'reply by WN', 12),
                                                                          (17, 13, 4, 'respuesta', 12);

DELETE FROM `follows`;
INSERT INTO `follows` (`User`, `Follows`) VALUES
                                              (14, 13),
                                              (14, 14),
                                              (14, 15),
                                              (14, 16),
                                              (14, 17),
                                              (13, 18),
                                              (14, 22);

DELETE FROM `likes`;
INSERT INTO `likes` (`user`, `post`) VALUES
                                         (13, 12),
                                         (14, 45),
                                         (14, 50),
                                         (14, 51),
                                         (14, 52);

DELETE FROM `posts`;
INSERT INTO `posts` (`id`, `creator`, `serverName`, `firstStep`, `image`, `description`) VALUES
                                                                                             (12, 13, 'localhost:8080', 21, '', ''),
                                                                                             (43, 14, '16.170.159.93', 26, 'https://yosoytuprofe.20minutos.es/wp-content/uploads/2023/08/manualidades-infantiles-con-papel.png', 'PEPE'),
                                                                                             (44, 14, '16.170.159.93', 26, 'https://upload.wikimedia.org/wikipedia/commons/4/47/Cyanocitta_cristata_blue_jay.jpg', 'descripcion impresionante'),
                                                                                             (45, 15, '16.170.159.93', 26, 'https://i.pinimg.com/236x/f2/8b/63/f28b636b13ee992e4bf9c31fec51db5a.jpg', 'jijijaja'),
                                                                                             (46, 15, '16.170.159.93', 26, 'https://img.remediosdigitales.com/3ea3c9/manualidades-vuelta-al-cole/450_1000.jpg', 'PACHA'),
                                                                                             (47, 23, '16.170.159.93', 26, 'https://www.materialescolar.es/blog/wp-content/uploads/2014/07/Manualidades-1.png', 'POCHA'),
                                                                                             (48, 23, '16.170.159.93', 26, 'https://upload.wikimedia.org/wikipedia/commons/4/47/Cyanocitta_cristata_blue_jay.jpg', 'PUCHA'),
                                                                                             (49, 23, '16.170.159.93', 26, 'https://upload.wikimedia.org/wikipedia/commons/4/47/Cyanocitta_cristata_blue_jay.jpg', 'zsdfcgbhjnmkñl'),
                                                                                             (50, 15, '16.170.159.93', NULL, '', ''),
                                                                                             (51, 16, '16.170.159.93', NULL, '', 'tengo miedo'),
                                                                                             (52, 17, '16.170.159.93', NULL, '', 'tengo miedo');

DELETE FROM `servers`;
INSERT INTO `servers` (`ip`, `name`, `isSeed`) VALUES
                                                   ('16.170.159.93', 'Touka', b'1'),
                                                   ('localhost:8080', 'Touka', b'1');

DELETE FROM `step`;
INSERT INTO `step` (`id`, `description`, `previousStep`, `image`) VALUES
                                                                      (21, 'hghfghgf', NULL, 'hgfh'),
                                                                      (22, 'hgfhgf', 21, 'hgfhg'),
                                                                      (26, 'fucking boss', NULL, 'https://upload.wikimedia.org/wikipedia/commons/4/47/Cyanocitta_cristata_blue_jay.jpg'),
                                                                      (27, 'buenos dias', 26, 'https://upload.wikimedia.org/wikipedia/commons/4/47/Cyanocitta_cristata_blue_jay.jpg'),
                                                                      (28, 'PASO SIGUIENTE', 27, 'https://upload.wikimedia.org/wikipedia/commons/4/47/Cyanocitta_cristata_blue_jay.jpg');

DELETE FROM `users`;
INSERT INTO `users` (`id`, `userName`, `password`, `email`, `image`, `description`) VALUES
                                                                                        (13, 'hector', 'pass', 'a@b.es', '', 'hola'),
                                                                                        (14, 'jrber', 'pollagorda', 'kalar@gmail.com', 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=', ''),
                                                                                        (15, 'rizna', 'yourPassword', 'rizna@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', 'adre2'),
                                                                                        (16, 'jrber23', '1234', 'jrber23@gmail.com', 'https://i.pinimg.com/474x/61/8b/bf/618bbf80468e9cdae2aef327df83bf09.jpg', 'Hola new copy link working: http://16.170.159.93/getUserByName?username=jrber23Hola new copy link working: http://16.170.159.93/getUserByName?username=jrber23'),
                                                                                        (17, 'Test display WN', 'password123', 'doesitworkWN@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (18, 'GreenIt enjoyer', 'password123', 'greenItIsGreat@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (19, 'f1test', 'jijijaja', 'f1@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (20, 'f1test2', 'pass', 'f2@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (21, 'f3test', 'pass', 'f3@gmail.com', '', 'F3desc'),
                                                                                        (22, 'f4test', 'pass', 'f4@gmail.com', '', 'F4desc'),
                                                                                        (23, 'f5test', 'pass', 'f5@gmail.com', '', 'F5desc');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
