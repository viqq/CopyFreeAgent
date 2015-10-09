CREATE TABLE USER (
  USER_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  EMAIL VARCHAR(50) NOT NULL,
  PASSWORD VARCHAR(60) NOT NULL,
  CITY VARCHAR(175) NULL DEFAULT NULL,
  ROLE VARCHAR(15) NOT NULL DEFAULT 'ROLE_NOT_ACTIVATED',
  GENDER VARCHAR(10) NOT NULL DEFAULT 'MALE',
  DESCRIPTION VARCHAR(500) NULL DEFAULT NULL,
  FIRST_NAME VARCHAR(40) NULL DEFAULT NULL,
  LAST_NAME VARCHAR(40) NULL DEFAULT NULL,
  PHONE VARCHAR(25) NULL DEFAULT NULL,
  DATE_OF_BIRTH DATE NULL DEFAULT NULL,
  IMAGE VARCHAR(100) NULL DEFAULT NULL,
  DATE_OF_REGISTRATION DATE NOT NULL,
  HASH VARCHAR(20) NOT NULL,
  UNIQUE (EMAIL),
  PRIMARY KEY (USER_ID)
);


CREATE TABLE SPORT (
  SPORT_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(40) NOT NULL,
  IMAGE VARCHAR(100) NULL DEFAULT NULL,
  UNIQUE (NAME),
  PRIMARY KEY (SPORT_ID)
);


CREATE TABLE MESSAGE (
  MESSAGE_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  AUTHOR_ID BIGINT(20) NULL DEFAULT NULL,
  AUTHOR_EMAIL VARCHAR(50) NULL DEFAULT NULL,
  TITLE VARCHAR(50) NULL DEFAULT NULL,
  TEXT VARCHAR(800) NULL DEFAULT NULL,
  TIME_OF_CREATE DATE NOT NULL,
  TIME_OF_READ DATE NULL DEFAULT NULL,
  USER_ID BIGINT(20) NOT NULL,
  PRIMARY KEY (MESSAGE_ID),
  CONSTRAINT FK_DEPT FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID)
);


CREATE TABLE USER_SPORT (
  USER_ID BIGINT(20) NOT NULL,
  SPORT_ID BIGINT(20) NOT NULL,
  PRIMARY KEY (USER_ID, SPORT_ID),
  INDEX FK_SPORT (SPORT_ID),
  CONSTRAINT FK_USER FOREIGN KEY (USER_ID) REFERENCES USER (USER_ID),
  CONSTRAINT FK_SPORT FOREIGN KEY (SPORT_ID) REFERENCES SPORT (SPORT_ID)
);


CREATE INDEX IN_USER ON USER (USER_ID, EMAIL);
CREATE INDEX IN_SPORT ON SPORT (SPORT_ID, NAME);
CREATE INDEX IN_MESSAGE ON MESSAGE (MESSAGE_ID);

INSERT INTO USER (USER_ID, EMAIL, PASSWORD, ROLE)
VALUES (1, 'Viqq@i.ua', 'qwerty123', 'ROLE_ADMIN');


ALTER TABLE USER DROP INDEX IN_USER;
ALTER TABLE SPORT DROP INDEX IN_SPORT;
ALTER TABLE MESSAGE DROP INDEX IN_MESSAGE;

DROP TABLE IF EXISTS USER_SPORT;
DROP TABLE IF EXISTS MESSAGE;
DROP TABLE IF EXISTS SPORT;
DROP TABLE IF EXISTS USER;
