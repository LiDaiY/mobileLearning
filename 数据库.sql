/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 5.5.49 : Database - mlearnserver
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mlearnserver` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mlearnserver`;

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `courseID` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(100) DEFAULT NULL,
  `teacherName` varchar(100) DEFAULT NULL,
  `courseUrl` varchar(100) DEFAULT NULL,
  `courseAbstract` varchar(100) DEFAULT NULL,
  `courseType` int(11) DEFAULT NULL,
  `teacherID` int(11) DEFAULT NULL,
  `detailInfo` text,
  PRIMARY KEY (`courseID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`courseID`,`courseName`,`teacherName`,`courseUrl`,`courseAbstract`,`courseType`,`teacherID`,`detailInfo`) values 
(1,'恋恋有词','朱伟','res/course/1/cover.jpg','考研英语',1,1,'今天就想跟大家谈一谈，朱伟老师，《恋恋有词》的使用方法。《恋恋有词》目前来说，是考研界英语单词书中，最火的一本书。很多同学都在问我，恋恋有词要怎么学？ 总的来说，《恋恋有词》的资源还是蛮全的。有视频，音频，配套书籍等等。背恋恋有词还是非常非常耗时间的。就比如说恋恋有词的，视频讲解部分，将近有120个小时。就拿一个普通的读大三的考研学生来说。每天看能不能抽出六个小时来学习，六个小时中有多少小时用来背单词，如果每天不间断的学习的话，估计这个视频部分都要看两个多月。\r\n《恋恋有词》中朱伟老师还推荐了将近150多首的英文歌曲，还有十几部的英文电视剧电影等。如果在老师的旁征博引中你还想听听老师推荐的英文歌曲和看看老师的电影的话，我想一段时间又过去了。去年我还发现一个考研用恋恋有词背单词的小伙伴们，对书中的单词不敢兴趣，但是对朱伟老师推荐的歌曲特感兴趣。每天都去练嗓子去了。当然确实老师推荐的歌曲确实挺好听的！\r\n所以如果用恋恋有词背单词的话，提前一定要把时间安排好，我建议大家一定要在5月份之前把恋恋有词看完，当然，看一遍，最好是看一便视频，然后理解理解了，背诵一单元的单词，然后再进行下一篇。千万不要像看电视连续剧一样，看完了之后。接着看笑话一样。然后把三十节课都看完了之后。才开始复习。等你看完了所有的视频再打算背单词的时候就来不及了。而且根据我这两天我分享的那个艾宾浩森记忆曲线。就是说时间间隔和记忆的关系，你刚刚记忆完毕的时候，差不多能记住百分之百，等你百分二十分钟之后就只能记住60%左右一个小时就只能记住44%，然后等到一个月以后你就只能记住20%，那么如果你用两个月的话把朱伟的所有的都看完，但是没有去复习，按实际意义，重复记忆的话，那么你到两个月之后可能真的就没有记住什么，时间也就这么耗费过去了。\r\n最后希望本年单词跟着伟哥走的人，一定要做好计划，把总的工作量分到每天去完成，风雨无阻！'),
(2,'高数十八讲','张宇','res/course/2/cover.jpg','考研数学',1,2,'本书主要介绍考研数学中高等数学的全部知识，并将其分为18讲。每讲分为五部分：导语、考试大纲、知识体系、考试内容分析、典型例题分析. [1] \r\n（1）导语.对本讲内容的主要概括以及本讲在考试中的地位等的说明.\r\n（2）考试大纲.让同学们清楚地知道考研数学到底“考什么”，知道哪些内容只需了解，哪些内容则要重点掌握，这样在复习备考过程中才能真正做到有的放矢.\r\n（3）知识体系.通过逻辑框架将本讲所有知识点完美呈现，简洁明了.\r\n（4）考试内容分析.对考研数学的每个考点都做了全面细致地讲解，同时每个考点都紧跟着经典题目供同学们强\r\n\r\n第1讲 品味数学思想，走进考研数学\r\n\r\n第2讲 函数、极限与连续\r\n\r\n第3讲 一元函数微分学的概念与计算\r\n\r\n第4讲 一元函数积分学的概念与计算\r\n\r\n第5讲 一元函数微分学的应用\r\n\r\n第6讲 一元函数积分学的应用\r\n\r\n第7讲 中值定理\r\n\r\n第8讲 多元函数微分学的概念与计算\r\n\r\n第9讲 多元函数微分学的应用\r\n\r\n第10讲 二重积分\r\n\r\n第11讲 微分方程\r\n\r\n第12讲 无穷级数\r\n\r\n第13讲 多元函数微分学的应用二\r\n\r\n第14讲 三重积分\r\n\r\n第15讲 第一型曲线积分'),
(3,'考前必背20题','徐涛','res/course/3/cover.jpg','考研政治',1,4,'考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题14243234考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题14243234考前必背20题考前必背20题考前必背20题考前必背20题考前必背20题'),
(4,'数据结构','严蔚敏','res/course/4/cover.jpg','专业课',1,3,'数据结构数据结构数\r\n据结构数据结构数据结构\r\n数据结构数据结构64654564\r\n数据结构数据结构数据结构\r\n数据结数据结构数据结构数据结构数据结构数据结构数据结构数据结构64654564数据结\r\n构数据结构数据结构数\r\n据结构数据结构数据结构561565构数据结构数据结构561565'),
(7,'c++','张三','res/course/7/cover.jpg','编程学习',NULL,NULL,NULL),
(8,'java','李四','res/course/8/cover.jpg','编程学习',NULL,NULL,NULL),
(9,'数据库','王五','res/course/9/cover.jpg','编程学习',NULL,NULL,NULL),
(10,'线代辅导讲义','李永乐','res/course/10/cover.jpg','考研数学',NULL,NULL,NULL),
(12,'题源1000题','张宇','res/course/12/cover.jpg','考研数学',NULL,2,NULL),
(13,'命题人1000题','肖秀荣','res/course/13/cover.jpg',NULL,NULL,6,NULL),
(14,'零基础入门学习python','小甲鱼','res/course/14/cover.jpg',NULL,NULL,7,NULL),
(28,'第一行代码','郭霖','res/course/28/cover.jpg',NULL,NULL,NULL,NULL),
(34,'词组背多分','朱伟','res/course/34/cover.jpg','',1,1,''),
(35,'了不起的盖茨比','朱伟','res/course/35/cover.jpg','英语阅读',1,1,'了不起的盖茨比\r\n了不起的盖茨比\r\n了不起的盖茨比\r\n了不起的盖茨比\r\n了不起的盖茨比\r\n了不起的盖茨比\r\n了不起的盖茨比'),
(38,'写作宝典','朱伟','res/course/38/cover.jpg','',1,1,'');

/*Table structure for table `coursematerial` */

DROP TABLE IF EXISTS `coursematerial`;

CREATE TABLE `coursematerial` (
  `resID` int(11) NOT NULL AUTO_INCREMENT,
  `courseID` int(11) DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  `resTitle` varchar(100) DEFAULT NULL,
  `resUrl` varchar(100) DEFAULT NULL,
  `teacherID` int(11) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `coursematerial` */

/*Table structure for table `homework` */

DROP TABLE IF EXISTS `homework`;

CREATE TABLE `homework` (
  `hwID` int(11) NOT NULL AUTO_INCREMENT COMMENT '作业题目的id',
  `courseID` int(11) DEFAULT NULL,
  `teacherID` int(11) DEFAULT NULL,
  `hwContent` text COMMENT '作业题目的内容',
  `publishTime` datetime DEFAULT NULL COMMENT '布置的时间',
  `hwTitle` text COMMENT '标题',
  PRIMARY KEY (`hwID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `homework` */

insert  into `homework`(`hwID`,`courseID`,`teacherID`,`hwContent`,`publishTime`,`hwTitle`) values 
(1,1,1,'请翻译以下句子，以word形式提交\r\n    By the date of his birth Europe was witnessing the passing of the religious drama, and the creation of new forms under the incentive of classical tragedy and comedy. \r\n\r\n    no boy who went to a grammar school could be ignorant that the drama was a form of literature which gave glory to Greece and Rome and might yet bring honor to England.\r\n\r\n    But the professional companies prospered in their permanent theaters, and university men with literary ambitions were quick to turn to these theaters as offering a means of livelihood.','2018-04-27 14:57:36','翻译练习1');

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `noticeID` int(11) NOT NULL AUTO_INCREMENT,
  `noticeTitle` varchar(100) DEFAULT NULL,
  `noticeContent` varchar(100) DEFAULT NULL,
  `teacherID` int(11) DEFAULT NULL,
  `courseID` int(11) DEFAULT NULL,
  `noticeTime` date DEFAULT NULL,
  `courseName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`noticeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `notice` */

insert  into `notice`(`noticeID`,`noticeTitle`,`noticeContent`,`teacherID`,`courseID`,`noticeTime`,`courseName`) values 
(2,'下周一有公开课','详情关注我的微博',1,1,'2018-04-22','恋恋有词');

/*Table structure for table `replypost` */

DROP TABLE IF EXISTS `replypost`;

CREATE TABLE `replypost` (
  `replyID` int(11) NOT NULL AUTO_INCREMENT,
  `postID` int(11) DEFAULT NULL COMMENT '主题帖的id',
  `replyContent` text CHARACTER SET utf8 COMMENT '回帖内容',
  `replyTime` datetime DEFAULT NULL COMMENT '回帖时间',
  `userStatus` int(11) DEFAULT NULL COMMENT '用户身份，0代表学生，1代表老师',
  `starNum` int(11) DEFAULT NULL COMMENT '获得的赞的数量',
  `userID` int(11) DEFAULT NULL COMMENT '用户id，老师和学生可能一样，需要用身份辅助判断',
  `userName` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `userEmail` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`replyID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;

/*Data for the table `replypost` */

insert  into `replypost`(`replyID`,`postID`,`replyContent`,`replyTime`,`userStatus`,`starNum`,`userID`,`userName`,`userEmail`) values 
(1,4,'    本句的主干是The way…means that…。主语后的定语从句对The way进行了说明，从句前省略了关系代词that。该定语从句的主语是consumers，谓语是approach，宾语为the process，后面的of making purchase decisions是解释the process的后置定语。主干的谓语动词means之后是that引导的宾语从句，主语为marketing\'s impact，谓语为stems from，宾语为a broad range of factors， beyond conventional paid media是修饰factors的后置定语。\r\n    译文：如今消费者购买商品的决策过程说明，市场营销的影响力来自于传统付费媒介以外的诸多因素。','2018-04-19 10:10:30',1,0,1,'朱伟','zhuwei@qq.com'),
(2,3,'    每个人都有每个人的特点，也会有自己适合的方法，如果有同学有更好的方法，希望分享出来。但是我这个看似很笨的方法确实具有普遍适用性，我自己的学生，按照这个认真做的，都会有一个配得上自己努力的成绩。','2018-04-19 12:43:58',1,0,1,'朱伟','zhuwei@qq.com'),
(4,3,'要多背单词哦','2018-04-20 15:06:47',0,0,19,'彭于晏','pyy@qq.com'),
(6,3,'不能什么事都依赖老师','2018-04-20 15:11:02',0,0,1,'小埋','123@qq.com'),
(7,1,'有时间就刷两遍吧','2018-04-20 15:12:22',0,0,1,'小埋','123@qq.com'),
(11,4,'说得好','2018-04-20 17:21:07',0,0,19,'彭于晏','pyy@qq.com'),
(14,3,'(⊙﹏⊙)','2018-04-21 11:16:21',0,0,9,'马云','c@qq.com'),
(15,3,'are you OK？','2018-04-21 11:21:06',0,0,21,'雷军','leijun@qq.com'),
(16,3,'一起学习呀','2018-04-21 13:31:20',0,0,10,'芳心纵火犯','d@qq.com'),
(18,3,'谢谢大家的回答','2018-04-22 16:25:54',0,0,17,'我不是马化腾','mht@qq.com'),
(19,2,'伟哥的微博有回答哦','2018-04-22 16:28:04',0,0,17,'我不是马化腾','mht@qq.com'),
(22,2,'可以看看考研英语阅读同源外刊时文精析，文章数量不多不少，精选英语真题同源外刊的文章，可以买新版的。','2018-04-22 22:02:16',1,0,1,'朱伟','zhuwei@qq.com');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `signature` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`userID`,`username`,`password`,`sex`,`email`,`photo`,`signature`,`type`,`logintime`) values 
(1,'小埋','123','女','123@qq.com','res/user/123@qq.com/pic.jpg','真正的小埋',NULL,NULL),
(2,'路飞','123','男','456@qq.com','res/user/456@qq.com/pic.jpg',NULL,NULL,NULL),
(7,'王大锤','123','男','a@qq.com','res/user/a@qq.com/pic.jpg','我叫王大锤',0,NULL),
(9,'马云','123',NULL,'c@qq.com','res/user/c@qq.com/pic.jpg','我对钱不感兴趣',0,NULL),
(10,'芳心纵火犯','123',NULL,'d@qq.com','res/user/d@qq.com/pic.jpg','北大也还行',0,NULL),
(12,'阿拉蕾','123',NULL,'f@qq.com','res/user/f@qq.com/pic.jpg',NULL,0,NULL),
(13,'周杰伦','123',NULL,'g@qq.com','res/user/g@qq.com/pic.jpg','哎哟不错哟',0,NULL),
(16,'林俊杰','123',NULL,'p@qq.com','res/user/p@qq.com/pic.jpg',NULL,0,NULL),
(17,'我不是马化腾','123','男','mht@qq.com','res/user/mht@qq.com/pic.jpg','想一想，不充钱，你能变得更强吗？',0,NULL),
(19,'彭于晏','123','男','pyy@qq.com','res/user/pyy@qq.com/pic.jpg','清除心中的不可以，告诉自己我可以',0,NULL),
(20,'李宗豪','123','男','lzh@qq.com','res/user/lzh@qq.com/pic.jpg',NULL,0,NULL),
(21,'雷军','123','男','leijun@qq.com','res/user/leijun@qq.com/pic.jpg','Are you ok？',0,NULL);

/*Table structure for table `studentcourse` */

DROP TABLE IF EXISTS `studentcourse`;

CREATE TABLE `studentcourse` (
  `relationID` int(11) NOT NULL AUTO_INCREMENT,
  `courseID` int(11) DEFAULT NULL,
  `studentID` int(11) DEFAULT NULL,
  `studentGrade` int(11) DEFAULT NULL,
  `studentAnswer` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`relationID`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

/*Data for the table `studentcourse` */

insert  into `studentcourse`(`relationID`,`courseID`,`studentID`,`studentGrade`,`studentAnswer`) values 
(8,1,13,13,'A;未作答;未作答;未作答;未作答;未作答;未作答;未作答'),
(9,2,13,NULL,NULL),
(11,3,13,NULL,NULL),
(12,4,13,NULL,NULL),
(17,1,2,NULL,NULL),
(18,28,2,NULL,NULL),
(19,28,1,NULL,NULL),
(21,2,8,NULL,NULL),
(22,3,17,NULL,NULL),
(24,1,7,38,'B;B;B;C;A;D;B;B'),
(28,28,19,NULL,NULL),
(31,34,7,NULL,NULL),
(35,35,7,NULL,NULL),
(36,35,8,NULL,NULL),
(37,2,9,NULL,NULL),
(38,35,21,NULL,NULL),
(41,1,19,75,'A;A;A;A;A;A;A;A'),
(42,2,1,NULL,NULL),
(43,35,1,NULL,NULL),
(45,2,19,NULL,NULL),
(46,1,1,75,'A;A;A;A;A;A;A;A'),
(47,1,9,50,'A;B;B;C;A;A;B;A'),
(48,1,10,50,'C;C;A;C;B;A;A;B'),
(49,1,11,100,'A;B;A;A;A;A;A;B'),
(50,1,14,NULL,NULL),
(51,14,19,NULL,NULL),
(52,1,17,NULL,NULL),
(53,1,22,NULL,NULL),
(54,1,21,NULL,NULL);

/*Table structure for table `stuhomework` */

DROP TABLE IF EXISTS `stuhomework`;

CREATE TABLE `stuhomework` (
  `shwID` int(11) NOT NULL AUTO_INCREMENT,
  `hwID` int(11) DEFAULT NULL,
  `subTime` datetime DEFAULT NULL COMMENT '提交作业的时间',
  `hwUrl` varchar(100) DEFAULT NULL COMMENT '文件存放的位置，用于老师下载',
  `stuWorkTitle` varchar(100) DEFAULT NULL COMMENT '文件的标题，为老师下载所用',
  `studentID` int(11) DEFAULT NULL,
  PRIMARY KEY (`shwID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `stuhomework` */

insert  into `stuhomework`(`shwID`,`hwID`,`subTime`,`hwUrl`,`stuWorkTitle`,`studentID`) values 
(4,1,'2018-04-29 14:48:26','WEB-INF/res/homework/作业号1/学号19/口语.docx','口语.docx',19),
(5,1,'2018-04-29 14:49:04','WEB-INF/res/homework/作业号1/学号14/口语.docx','口语.docx',14);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `teacherID` int(11) NOT NULL AUTO_INCREMENT,
  `teacherName` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `signature` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`teacherID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`teacherID`,`teacherName`,`password`,`sex`,`email`,`photo`,`signature`,`type`,`logintime`) values 
(1,'朱伟','123','男','zhuwei@qq.com',NULL,NULL,NULL,'2018-05-10 11:09:59'),
(2,'张宇','123','男','zhangyu@qq.com',NULL,'',NULL,'2018-06-02 08:48:00'),
(3,'严蔚敏','123','女','ywm@qq.com',NULL,NULL,NULL,NULL),
(4,'徐涛','123','男','xutao@qq.com',NULL,NULL,NULL,NULL),
(6,'肖秀荣','123','男','xxr@qq.com',NULL,NULL,NULL,NULL),
(7,'小甲鱼','123','男','xjy@qq.com',NULL,NULL,NULL,NULL),
(8,'郭霖','123','男','guolin@qq.com',NULL,NULL,NULL,NULL);

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `testID` int(11) NOT NULL AUTO_INCREMENT,
  `courseID` int(11) DEFAULT NULL,
  `teacherID` int(11) DEFAULT NULL,
  `testContent` varchar(100) DEFAULT NULL,
  `testAnswer` varchar(100) DEFAULT NULL,
  `testOption` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`testID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `test` */

insert  into `test`(`testID`,`courseID`,`teacherID`,`testContent`,`testAnswer`,`testOption`) values 
(1,1,1,'There‘s no light on—they_____be at home.','A','can\'t;mustn\'t;needn\'t;shouldn\'t'),
(2,1,1,'I have_____old shirt and_____new sweater.','B','a,an;an,a;an,an;a,a'),
(3,1,1,'There‘s no light on—they_____be at home.','A','can\'t;mustn\'t;needn\'t;shouldn\'t'),
(4,1,1,'There‘s no light on—they_____be at home.','A','can\'t;mustn\'t;needn\'t;shouldn\'t'),
(5,1,1,'There‘s no light on—they_____be at home.','A','can\'t;mustn\'t;needn\'t;shouldn\'t'),
(6,1,1,'There‘s no light on—they_____be at home.','A','can\'t;mustn\'t;needn\'t;shouldn\'t'),
(7,1,1,'There‘s no light on—they_____be at home.','A','can\'t;mustn\'t;needn\'t;shouldn\'t'),
(8,1,1,'I have_____old shirt and_____new sweater. ','B','a,an;an,a;an,an;a,a');

/*Table structure for table `themepost` */

DROP TABLE IF EXISTS `themepost`;

CREATE TABLE `themepost` (
  `postID` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) DEFAULT NULL COMMENT '发帖人id',
  `courseID` int(11) DEFAULT NULL,
  `postTitle` varchar(100) DEFAULT NULL COMMENT '主题',
  `postContent` text COMMENT '内容',
  `postTime` datetime DEFAULT NULL COMMENT '发帖时间',
  `replyTime` datetime DEFAULT NULL COMMENT '最后回复时间',
  `state` int(11) DEFAULT NULL COMMENT '状态，0表示老师未回复，1表示老师已回复',
  PRIMARY KEY (`postID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `themepost` */

insert  into `themepost`(`postID`,`studentID`,`courseID`,`postTitle`,`postContent`,`postTime`,`replyTime`,`state`) values 
(1,19,1,'伟哥，恋练有词刷1遍够吗','如题','2018-04-18 08:58:15',NULL,0),
(2,1,1,'伟哥，题源报刊怎么利用啊？','如题','2018-04-18 09:16:48',NULL,1),
(3,17,1,'伟哥，能讲一讲英语怎么复习吗','谢谢','2018-04-18 16:12:34',NULL,0),
(4,10,1,'伟哥，这个句子怎么理解？','The way consumers now approach the process of making purchase decisions means that marketing\'s impact stems from a broad range of factors beyond conventional paid media.','2018-04-19 09:26:11',NULL,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
