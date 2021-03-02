# 데이터베이스 생성
DROP DATABASE IF EXISTS springPractice;
CREATE DATABASE springPractice;
USE springPractice;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);

# 게시물, 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = "제목1 입니다.",
`body` = "내용1 입니다.",
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = "제목2 입니다.",
`body` = "내용2 입니다.",
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = "제목3 입니다.",
`body` = "내용3 입니다.",
memberId = 2,
boardId = 2;

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(100) NOT NULL,
    authKey CHAR(80) NOT NULL,
    UNIQUE INDEX (authKey),
    `name` CHAR(30) NOT NULL,
    nickname CHAR(30) NOT NULL,
    cellphoneNo CHAR(100) NOT NULL,
    email CHAR(20) NOT NULL,
    UNIQUE INDEX (loginId)
);

# 회원, 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = "user1",
loginPw = "user1",
authKey = 'authKey1__1',
`name` = "user1",
nickname = "user1",
cellphoneNo = "01012341234",
email = "qbj700@gmail.com";

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = "user2",
loginPw = "user2",
authKey = 'authKey1__2',
`name` = "user2",
nickname = "user2",
cellphoneNo = "01012341234",
email = "qbj700@gmail.com";

# 게시판 테이블 추가
CREATE TABLE board (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  `code` CHAR(20) UNIQUE NOT NULL,
  `name` CHAR(20) UNIQUE NOT NULL
);

# 공지사항 게시판 추가
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

# 자유 게시판 추가
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free',
`name` = '자유';

# 댓글 테이블 추가
CREATE TABLE reply (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  relId INT(10) UNSIGNED NOT NULL,
  relTypeCode CHAR(10) NOT NULL,
  memberId INT(10) UNSIGNED NOT NULL,
  `body` TEXT NOT NULL,
  INDEX (relTypeCode, relId)
);

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relId = 1,
relTypeCode = 'article',
memberId = 1,
`body` = "내용1 입니다.";

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relId = 1,
relTypeCode = 'article',
memberId = 2,
`body` = "내용2 입니다.";

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relId = 2,
relTypeCode = 'article',
memberId = 2,
`body` = "내용3 입니다."; 

# 파일 테이블 추가
CREATE TABLE genFile (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, # 번호
  regDate DATETIME DEFAULT NULL, # 작성날짜
  updateDate DATETIME DEFAULT NULL, # 갱신날짜
  delDate DATETIME DEFAULT NULL, # 삭제날짜
  delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, # 삭제상태(0:미삭제,1:삭제)
  relTypeCode CHAR(50) NOT NULL, # 관련 데이터 타입(article, member)
  relId INT(10) UNSIGNED NOT NULL, # 관련 데이터 번호
  originFileName VARCHAR(100) NOT NULL, # 업로드 당시의 파일이름
  fileExt CHAR(10) NOT NULL, # 확장자
  typeCode CHAR(20) NOT NULL, # 종류코드 (common)
  type2Code CHAR(20) NOT NULL, # 종류2코드 (attatchment)
  fileSize INT(10) UNSIGNED NOT NULL, # 파일의 사이즈
  fileExtTypeCode CHAR(10) NOT NULL, # 파일규격코드(img, video)
  fileExtType2Code CHAR(10) NOT NULL, # 파일규격2코드(jpg, mp4)
  fileNo SMALLINT(2) UNSIGNED NOT NULL, # 파일번호 (1)
  fileDir CHAR(20) NOT NULL, # 파일이 저장되는 폴더명
  PRIMARY KEY (id),
  KEY relId (relId,relTypeCode,typeCode,type2Code,fileNo)
); 