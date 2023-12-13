/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DELETE FROM `comments`;
INSERT INTO `comments` (`id`, `creator`, `replyto`, `text`, `postid`, `fecha`) VALUES
                                                                                   (4, 13, NULL, 'asdf', 10, '2023-12-07 10:36:00'),
                                                                                   (6, 13, NULL, 'probando comentarios', 10, '2023-12-07 10:36:00'),
                                                                                   (8, 13, NULL, 'proband respuestas', 10, '2023-12-07 10:36:00'),
                                                                                   (9, 13, NULL, 'no entiendo', 10, '2023-12-07 10:36:00'),
                                                                                   (10, 13, NULL, 'ya funciona', 10, '2023-12-07 10:36:00'),
                                                                                   (13, 13, 4, 'Testing', 10, '2023-12-07 10:36:00'),
                                                                                   (14, 13, NULL, 'testing comments by JR', 10, '2023-12-07 10:36:00'),
                                                                                   (15, 13, 4, 'hmm?', 10, '2023-12-07 10:36:00'),
                                                                                   (16, 13, NULL, 'test by WN', 10, '2023-12-07 10:36:00'),
                                                                                   (17, 13, 4, 'reply by WN', 10, '2023-12-07 10:36:00'),
                                                                                   (18, 13, 4, 'respuesta', 10, '2023-12-07 10:36:00'),
                                                                                   (19, 15, 6, 'yo que se tio :[', 10, '2023-12-07 10:36:00'),
                                                                                   (20, 15, 6, 'yo que se tio', 10, '2023-12-07 10:36:00'),
                                                                                   (21, 13, NULL, 'asdf', 11, '2023-12-07 10:36:00'),
                                                                                   (22, 13, NULL, 'probando si se actualiza', 11, '2023-12-07 10:36:00'),
                                                                                   (23, 13, NULL, 'lo hacia?', 11, '2023-12-07 10:36:00'),
                                                                                   (24, 13, NULL, 'catorceavo comment porque mal numero', 10, '2023-12-07 10:36:00'),
                                                                                   (25, 13, 19, 'response', 10, '2023-12-07 10:36:00'),
                                                                                   (26, 13, 24, 'agarramela que me crece', 10, '2023-12-07 10:00:05'),
                                                                                   (27, 13, NULL, 'es una prueba man', 11, '2023-12-10 08:33:31'),
                                                                                   (28, 13, 27, 'otra', 11, '2023-12-10 08:33:40');

DELETE FROM `Follows`;
INSERT INTO `Follows` (`User`, `Follows`) VALUES
                                              (6, 9),
                                              (6, 12),
                                              (6, 13),
                                              (6, 14),
                                              (6, 15),
                                              (6, 16),
                                              (6, 17),
                                              (9, 6),
                                              (9, 12),
                                              (9, 13),
                                              (13, 9);

DELETE FROM `likes`;
INSERT INTO `likes` (`user`, `post`) VALUES
                                         (13, 12),
                                         (14, 45),
                                         (14, 50),
                                         (14, 51),
                                         (14, 52);

DELETE FROM `posts`;
INSERT INTO `posts` (`id`, `creator`, `serverName`, `firstStep`, `image`, `description`, `title`) VALUES
                                                                                                      (10, 9, '16.170.159.93', 15, 'https://yosoytuprofe.20minutos.es/wp-content/uploads/2023/08/manualidades-infantiles-con-papel.png', 'Nice paper animals!!', 'Animals papyroflexia'),
                                                                                                      (11, 9, '16.170.159.93', 15, 'https://www.handmadecharlotte.com/wp-content/uploads/2019/05/eggcartonlantern.done1_.690.jpg', 'Two of our very favorite things are being combined for this colorful craft today: crafting with recyclable materials and making lanterns!!', 'Upcycled Summer Lanterns'),
                                                                                                      (12, 9, '16.170.159.93', 35, 'https://mindfulofthehome.com/wp-content/uploads/2019/09/diy-zero-waste-toiletry-travel-wrap-713x1056.jpeg', 'Inevitably, the night before a trip as I\'m packing up our bags I realize that I have yet again forgotten to procure a better solution for transporting toothbrushes than the trusty ziploc bag.', 'DIY Toothbrush Travel Wrap\r\n'),
                                                                                                      (13, 6, '16.170.159.93', 60, 'https://c02.purpledshub.com/uploads/sites/51/2020/12/DIY-cork-board-871e4f6.jpg', 'Save all those wine corks and turn them into a DIY cork board. Gathered have created this sustainable craft tutorial and uploaded it for free so you can make your own.', 'Upcycle wine corks'),
                                                                                                      (14, 6, '16.170.159.93', 75, 'https://content.instructables.com/FWH/IAM6/JGWJ7IVI/FWHIAM6JGWJ7IVI.jpg?auto=webp&frame=1&width=525&height=1024&fit=bounds&md=de014efc6851ad6df812bf17cc51a353', 'In this DIY, I will show you how to create your own self sustaining ecosystem.', 'Do It Your-self Sustaining Ecosystem- Printables');

DELETE FROM `servers`;
INSERT INTO `servers` (`ip`, `name`, `isSeed`) VALUES
                                                   ('16.170.159.93', 'Touka', b'1'),
                                                   ('localhost:8080', 'Touka', b'1');

DELETE FROM `step`;
INSERT INTO `step` (`id`, `description`, `previousStep`, `image`) VALUES
                                                                      (15, 'Cutting the flat lid segment off of the egg carton', NULL, 'https://www.handmadecharlotte.com/wp-content/uploads/2019/05/eggcartonlantern.step1_.690.jpg'),
                                                                      (20, 'Lay out the segments side by side on your work surface', 15, 'https://www.handmadecharlotte.com/wp-content/uploads/2019/05/eggcartonlantern.step2_.690.jpg'),
                                                                      (25, 'Cut two pieces of string of equal length, about 10 inches long', 20, 'https://www.handmadecharlotte.com/wp-content/uploads/2019/05/eggcartonlantern.step3_.690.jpg'),
                                                                      (30, 'With your hot glue gun on the low-heat setting, apply a line of glue along the bottom edge of the lantern', 25, 'https://www.handmadecharlotte.com/wp-content/uploads/2019/05/eggcartonlantern.step4_.690.jpg'),
                                                                      (35, 'First I trimmed the fat quarter so it was totally squared up', NULL, 'https://theyellowbirdhouse.com/wp-content/uploads/2018/04/Marking-the-Pattern.jpeg'),
                                                                      (40, 'Trim away the fabric along these lines and cut out the terry cloth in the same way', 35, 'https://theyellowbirdhouse.com/wp-content/uploads/2018/04/Topstich-Top-and-Bottom.jpeg'),
                                                                      (45, 'Fold up the wider bottom edge to meet (approximately) the halfway point and topstitch down each side to create a pouch', 40, 'https://theyellowbirdhouse.com/wp-content/uploads/2018/04/Sewing-up-the-Sides.jpeg'),
                                                                      (50, 'Now you want to create the slots for your toiletries by stitching down the pouch at intervals', 45, 'https://theyellowbirdhouse.com/wp-content/uploads/2018/04/Creating-Slots.jpeg'),
                                                                      (55, 'Add some ribbon to one side of the wrap and you’re done!', 50, 'https://theyellowbirdhouse.com/wp-content/uploads/2018/04/Wrap-it-up.jpeg'),
                                                                      (60, 'Cut your corks in half, and glue them to the back of your frame', NULL, 'https://c02.purpledshub.com/uploads/sites/51/2020/12/DIY-cork-board-step-1-1edae1a.jpg'),
                                                                      (65, 'Use a floral die to cut three flower layers. Back a slice of cork with double-sided tape and stick looped twine underneath.', 60, 'https://c02.purpledshub.com/uploads/sites/51/2020/12/DIY-cork-board-step-2-1086677.jpg'),
                                                                      (70, 'Smooth two cork slices with sandpaper. Draw four triangles around the edge to make a flower shape and cut out using a craft knife to create a mini flower for the branch. Glue a button in the centre and use to embellish your die-cut foliage', 65, 'https://c02.purpledshub.com/uploads/sites/51/2020/12/DIY-cork-board-step-3-8fcb7de.jpg'),
                                                                      (75, 'Fill the Bottom With Pebbles', NULL, 'https://content.instructables.com/FAB/OS5R/JGWJ7IWH/FABOS5RJGWJ7IWH.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=614c5668c8e7b2c7272f7fb6f55bf912'),
                                                                      (80, 'Cover the Pebbles With a Layer of Charcoal', 75, 'https://content.instructables.com/FFX/9P7Y/JGWJ7IVC/FFX9P7YJGWJ7IVC.jpg?auto=webp&frame=1&width=600&height=1024&fit=bounds&md=c60e2a4aef29ebe2521922cc2ac1a4f6'),
                                                                      (85, 'Layer Soil on Top', 80, 'https://content.instructables.com/FP2/J8CE/JGWJ7IWC/FP2J8CEJGWJ7IWC.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=4d00ff01edb177c173580eba69d9cf99');

DELETE FROM `users`;
INSERT INTO `users` (`id`, `userName`, `password`, `email`, `image`, `description`) VALUES
                                                                                        (6, 'rizna', 'yourPassword', 'rizna@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', 'adre2'),
                                                                                        (9, 'jrber23', '1234', 'jrber23@gmail.com', 'https://i.pinimg.com/474x/61/8b/bf/618bbf80468e9cdae2aef327df83bf09.jpg', 'Hola copy link: http://16.170.159.93/getUserByName?username=jrber23Hola new copy link working: http://16.170.159.93/getUserByName?username=jrber23'),
                                                                                        (11, 'Test display WN', 'password123', 'doesitworkWN@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (12, 'GreenIt enjoyer', 'password123', 'greenItIsGreat@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (13, 'f1test', 'jijijaja', 'f1@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (14, 'f1test2', 'pass', 'f2@gmail.com', 'https://fcb-abj-pre.s3.amazonaws.com/img/jugadors/LEWANDOWSKI-min.jpg', ''),
                                                                                        (15, 'f3test', 'pass', 'f3@gmail.com', '', 'F3desc'),
                                                                                        (16, 'f4test', 'pass', 'f4@gmail.com', '', 'F4desc'),
                                                                                        (17, 'f5test', 'pass', 'f5@gmail.com', '', 'F5desc'),
                                                                                        (18, 'jrber', 'pollagorda', 'kalar@gmail.com', 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=', '');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
