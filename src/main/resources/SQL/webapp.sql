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