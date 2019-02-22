/*
  Create table 'user'.
  'user_id' - auto generated unique.
  'user_email' - filling by user. Must be unique.
  'user_pass' - filling by user.
 */
CREATE TABLE user (
  user_id INT(8) NOT NULL AUTO_INCREMENT,
  user_email VARCHAR(30) NOT NULL,
  user_pass VARCHAR(30) NOT NULL,
  PRIMARY KEY (user_id)
);

/*
  Add column 'pass_salt' - NOT NULL.
 */
ALTER TABLE user
  ADD COLUMN pass_salt VARCHAR(128) NOT NULL AFTER user_pass;

/*
  Modify type of column 'user_pass'.
  Set it to VARCHAR(128);
 */
ALTER TABLE user
  MODIFY user_pass varchar(128) NOT NULL;

/*
  Create table 'user_detail'.
 */
CREATE TABLE user_detail (
  detail_id INT(8) PRIMARY KEY AUTO_INCREMENT NOT NULL,
  fname VARCHAR(20) NOT NULL,
  lname VARCHAR(20) NOT NULL,
  birth_date DATE NOT NULL,
  age INT(3) NOT NULL,
  sex CHAR(1),
  country VARCHAR(20),
  city VARCHAR(20),
  detail_owner INT(8) NOT NULL,
  FOREIGN KEY (detail_owner) REFERENCES user(user_id)
);