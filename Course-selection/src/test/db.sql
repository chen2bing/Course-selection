SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
                            `cid` int(3) UNSIGNED NOT NULL,
                            `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `leader` varchar(64) NOT NULL,
                            PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `course`.`college`(`cid`, `name`, `leader`) VALUES (1, '计算机与信息学院', '张院长');
INSERT INTO `course`.`college`(`cid`, `name`, `leader`) VALUES (2, '外国语学院', '刘院长');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
                            `mid` int(10) UNSIGNED NOT NULL,
                            `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            PRIMARY KEY (`mid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `course`.`major`(`mid`, `name`) VALUES (1, '信息安全');
INSERT INTO `course`.`major`(`mid`, `name`) VALUES (2, '通信工程');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
                            `tid` int(10) UNSIGNED NOT NULL,
                            `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `sex` int(1) NOT NULL,
                            `college` int(3) UNSIGNED NOT NULL,
                            `school` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `qualification` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `job` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                            `isadmin` int(1) NOT NULL,
                            PRIMARY KEY (`tid`) USING BTREE,
                            FOREIGN KEY (college) REFERENCES college(cid) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `course`.`teacher`(`tid`, `name`, `sex`, `college`, `school`, `qualification`, `job`, `title`, `tel`, `email`, `isadmin`)
    VALUES (10001, '张老师', 1, 1, '合肥工业大学', '博士', '教师', '教授', '15200010001', 'zhang@hfut.edu.cn', 0);
INSERT INTO `course`.`teacher`(`tid`, `name`, `sex`, `college`, `school`, `qualification`, `job`, `title`, `tel`, `email`, `isadmin`)
    VALUES (10002, '王老师', 0, 1, '合肥工业大学', '博士', '系主任', '教授', '15200010000', 'wang@hfut.edu.cn', 1);



-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
                         `sid` int(10) UNSIGNED NOT NULL,
                         `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `sex` int(1) NOT NULL,
                         `college` int(3) UNSIGNED NOT NULL,
                         `major` int(10) UNSIGNED NOT NULL,
                         `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         PRIMARY KEY (`sid`) USING BTREE,
                         FOREIGN KEY (college) REFERENCES college(cid) ON DELETE CASCADE,
                         FOREIGN KEY (major) REFERENCES major(mid) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `course`.`student`(`sid`, `name`, `sex`, `college`, `major`, `tel`) VALUES (00001, '张三', 1, 1, 1, '15200000001');
INSERT INTO `course`.`student`(`sid`, `name`, `sex`, `college`, `major`, `tel`) VALUES (00002, '李四', 1, 1, 2, '15200000002');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `uid` int(10) UNSIGNED NOT NULL,
                         `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `group` int(3) UNSIGNED NOT NULL DEFAULT 0,
                         PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `course`.`user`(`uid`, `password`, `group`) VALUES (00001, '264c8c381bf16c982a4e59b0dd4c6f7808c51a05f64c35db42cc78a2a72875bb', 1);#student
INSERT INTO `course`.`user`(`uid`, `password`, `group`) VALUES (10001, '1057a9604e04b274da5a4de0c8f4b4868d9b230989f8c8c6a28221143cc5a755', 2);#teacher
INSERT INTO `course`.`user`(`uid`, `password`, `group`) VALUES (10002, '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 3);#admin


-- ----------------------------
-- Table structure for courseinfo
-- ----------------------------
DROP TABLE IF EXISTS `courseInfo`;
CREATE TABLE `courseInfo`  (
                         `cid` int(10) UNSIGNED NOT NULL,
                         `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `tid` int(10) UNSIGNED NOT NULL,
                         `ctype` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `cdate` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `place` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         `howlong` int(3) NOT NULL,
                         `credit` double(2,1) NOT NULL,
                         `book` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                         PRIMARY KEY (`cid`) USING BTREE,
                         FOREIGN KEY (tid) REFERENCES teacher(tid) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of courseInfo
-- ----------------------------
INSERT INTO `course`.`courseInfo`(`cid`, `name`, `tid`, `ctype`, `cdate`, `place`, `howlong`, `credit`, `book`)
        VALUES (00001, '软件安全', 10001, '专业必修课', '1-8周，周二第三、四节', '七教302', 48, 3, '暂无');
INSERT INTO `course`.`courseInfo`(`cid`, `name`, `tid`, `ctype`, `cdate`, `place`, `howlong`, `credit`, `book`)
        VALUES (00002, '计算机网络', 10002, '专业必修课', '1-16周，周二第一、二节', '七教301', 56, 3.5, '计算机网络，谢希仁著');
INSERT INTO `course`.`courseInfo`(`cid`, `name`, `tid`, `ctype`, `cdate`, `place`, `howlong`, `credit`, `book`)
VALUES (00003, '西方电影鉴赏', 10001, '选修课', '3-10周，周二第九、十节', '二教101', 16, 2, '无');
INSERT INTO `course`.`courseInfo`(`cid`, `name`, `tid`, `ctype`, `cdate`, `place`, `howlong`, `credit`, `book`)
VALUES (00004, '名著导读', 10002, '选修课', '3-10周，周一第九、十节', '三教306', 16, 2, '《红楼梦》等');
INSERT INTO `course`.`courseInfo`(`cid`, `name`, `tid`, `ctype`, `cdate`, `place`, `howlong`, `credit`, `book`)
VALUES (00005, '操作系统', 10001, '专业必修课', '1-10周，周二第三、四节', '七教301', 48, 3, '《现代操作系统》');

-- ----------------------------
-- Table structure for courseChoice
-- ----------------------------
DROP TABLE IF EXISTS `courseChoice`;
CREATE TABLE `courseChoice`  (
                               `cid` int(10) UNSIGNED NOT NULL,
                               `sid` int(10) UNSIGNED NOT NULL,
                               `score` int(3) UNSIGNED,
                               PRIMARY KEY (`cid`,`sid`) USING BTREE,
                               FOREIGN KEY (cid) REFERENCES courseInfo(cid) ON DELETE CASCADE,
                               FOREIGN KEY (sid) REFERENCES student(sid) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of courseChoice
-- ----------------------------
INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (00001, 00001, 95);
INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (00001, 00002, 90);
INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (00002, 00001, 80);
INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (00003, 00001, 72);
INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (00004, 00002, 89);
INSERT INTO `course`.`courseChoice`(`cid`, `sid`, `score`) VALUES (00004, 00001, 85);

-- ----------------------------
-- View structure for courseQuery
-- ----------------------------
DROP VIEW IF EXISTS `courseQuery`;
CREATE VIEW courseQuery(`cid`, `name`, `teacher`, `ctype`, `cdate`, `place`, `howlong`, `credit`, `book`) AS
SELECT cid, courseInfo.name, teacher.name, ctype, cdate, place, howlong, credit, book
FROM courseInfo,teacher
WHERE courseInfo.tid = teacher.tid;

-- ----------------------------
-- View structure for scoreQuery
-- ----------------------------
DROP VIEW IF EXISTS `scoreQuery`;
CREATE VIEW scoreQuery(`cid`, `sid`, `courseName`, `studentName`, `teacher`, `score`, `credit`) AS
SELECT courseChoice.cid, courseChoice.sid, courseInfo.name, student.name, teacher.name, courseChoice.score, courseInfo.credit
FROM courseChoice, student, courseInfo,teacher
WHERE courseChoice.score is not null AND courseChoice.cid = courseInfo.cid AND courseChoice.sid = student.sid AND courseInfo.tid = teacher.tid;

-- ----------------------------
-- 测试
-- ----------------------------
 SELECT * FROM scoreQuery;
SELECT * FROM courseChoice;
DROP TABLE IF EXISTS `courseChoice`;
DROP TABLE IF EXISTS `courseInfo`;
DROP TABLE IF EXISTS `teacher`;
DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `major`;
DROP TABLE IF EXISTS `college`;