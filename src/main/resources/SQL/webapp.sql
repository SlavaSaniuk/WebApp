/*
  Create table 'user'.
  'user_id' - auto generated unique.
  'user_email' - filling by user. Must be unique.
  'user_pass' - filling by user.
 */
CREATE TABLE user (
  user_id INT(8) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_detail INT(8) NOT NULL,
  user_email VARCHAR(30) NOT NULL,
  user_pass VARCHAR(128) NOT NULL,
  pass_salt VARCHAR(128) NOT NUll
);


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
  city VARCHAR(20)
);

/*
  Add foreign key to user.user_detail which referenced on user_detail.detail_id.
 */
ALTER TABLE user ADD FOREIGN KEY (user_detail) REFERENCES user_detail(detail_id) ON DELETE CASCADE;

/*
  Create table "friends_relationships"
 */
CREATE TABLE friends_relationships (
  relationship_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  friend_1_id INT(8) NOT NULL,
  friend_2_id INT(8) NOT NULL,
  FOREIGN KEY (friend_1_id) REFERENCES user(user_id),
  FOREIGN KEY (friend_2_id) REFERENCES user(user_id)
);

/*
  Add "relationships_status" column to "friends_relationships"
 */
ALTER TABLE friends_relationships ADD COLUMN rel_status INT(1) NOT NULL;
