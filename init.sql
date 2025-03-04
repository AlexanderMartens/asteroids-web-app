DROP TABLE IF EXISTS `Scores`;
DROP TABLE IF EXISTS `GameSettings`;
DROP TABLE IF EXISTS `UserProfiles`;
DROP TABLE IF EXISTS `Users`;

CREATE TABLE IF NOT EXISTS `Users` (
    `User_id` INT NOT NULL AUTO_INCREMENT,
    `User_name` VARCHAR(50) NOT NULL UNIQUE,
    `User_password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`User_id`)
);
ALTER TABLE `Users` AUTO_INCREMENT = 10001;

CREATE TABLE IF NOT EXISTS `UserProfiles` (
    `Profile_id` INT NOT NULL AUTO_INCREMENT,
    `User_id` INT NOT NULL,
    `Profile_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`Profile_id`),
    FOREIGN KEY (`User_id`) REFERENCES `Users`(`User_id`) ON DELETE CASCADE,
    CONSTRAINT `Unique_Profile_Per_User` UNIQUE (`User_id`, `Profile_name`)
);
ALTER TABLE `UserProfiles` AUTO_INCREMENT = 15001;

CREATE TABLE IF NOT EXISTS `Scores` (
    `Score_id` INT NOT NULL AUTO_INCREMENT,
    `Profile_id` INT NOT NULL,
    `Score` INT NOT NULL,
    `Level_reached` INT NOT NULL,
    `Duration_seconds` INT NOT NULL,
    `Time_played` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`Score_id`),
    FOREIGN KEY (`Profile_id`) REFERENCES `UserProfiles`(`Profile_id`) ON DELETE CASCADE
);
ALTER TABLE `Scores` AUTO_INCREMENT = 20001;

CREATE TABLE IF NOT EXISTS `GameSettings` (
    `Setting_id` INT NOT NULL AUTO_INCREMENT,
    `Profile_id` INT NOT NULL,
    `Sound` BOOLEAN DEFAULT TRUE,
    `Graphics` ENUM('low', 'medium', 'high') DEFAULT 'medium',
    PRIMARY KEY (`Setting_id`),
    FOREIGN KEY (`Profile_id`) REFERENCES `UserProfiles`(`Profile_id`) ON DELETE CASCADE
);
ALTER TABLE `GameSettings` AUTO_INCREMENT = 30001;

CREATE VIEW `Leaderboard` AS 
SELECT 
    `Users`.`User_name`, 
    `UserProfiles`.`Profile_name`,
    `Scores`.`Score`, 
    `Scores`.`Level_reached`, 
    `Scores`.`Duration_seconds`, 
    `Scores`.`Accuracy`, 
    `Scores`.`Game_mode`, 
    `Scores`.`Time_played`
FROM `Scores`
JOIN `UserProfiles` ON `Scores`.`Profile_id` = `UserProfiles`.`Profile_id`
JOIN `Users` ON `UserProfiles`.`User_id` = `Users`.`User_id`
ORDER BY `Scores`.`Score` DESC 
LIMIT 100;

