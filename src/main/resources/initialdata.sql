CREATE TABLE IF NOT EXISTS users (user_id INT AUTO_INCREMENT, username VARCHAR(50) NOT NULL, bungieID VARCHAR(250) UNIQUE, PRIMARY KEY (user_id));
CREATE TABLE IF NOT EXISTS power (power_id INT AUTO_INCREMENT, f_user_id INT NOT NULL, light varchar(200) NOT NULL, classType varchar(200) NOT NULL, characterId varchar(200) NOT NULL, PRIMARY KEY(power_id), FOREIGN KEY (f_user_id) REFERENCES users(user_id));

INSERT INTO users(username, bungieID)
VALUES ("greg", "4611686018483187700");
INSERT INTO users(username, bungieID)
VALUES ("aatha", "4611686018483647447");
 INSERT INTO users(username, bungieID)
 VALUES ("korunde", "4611686018496457639");
INSERT INTO users(username, bungieID)
VALUES ("nbl", "4611686018496533524");
INSERT INTO users(username, bungieID)
VALUES ("vi24", "4611686018496642168");
INSERT INTO users(username, bungieID)
VALUES ("nigglz", "4611686018527421496");
INSERT INTO users(username, bungieID)
VALUES ("sani", "4611686018496487028");
INSERT INTO users(username, bungieID)
VALUES ("ren", "4611686018528099816");
INSERT INTO users(username, bungieID)
VALUES ("pebbles", "4611686018537799339");
INSERT INTO users(username, bungieID)
VALUES ("hoshi", "4611686018537819678");
INSERT INTO users(username, bungieID)
VALUES ("axolotl", "4611686018537959772");
INSERT INTO users(username, bungieID)
VALUES ("jupiter", "4611686018516201285");
