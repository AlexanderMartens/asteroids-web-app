CREATE TABLE IF NOT EXISTS `Users`.`Login` (
    `User_id` INT NOT NULL auto_increment,
    `User_name` VARCHAR(16) NOT NULL UNIQUE,
    `User_password` VARCHAR(32) NOT NULL,
    PRIMARY KEY (`User_id`)
);
ALTER TABLE Login auto_increment = 10001;