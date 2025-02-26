CREATE DATABASE IF NOT EXISTS Users;
USE Users;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Scores (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL,
    level_reached INT NOT NULL,
    time_played TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS GameSettings (
    setting_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    sound BOOLEAN DEFAULT TRUE,
    difficulty ENUM('Easy', 'Medium', 'Hard') DEFAULT 'Medium',
    controls VARCHAR(50) DEFAULT 'ArrowKeys',
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

INSERT INTO Users (username, password) VALUES
('player1', 'hashed_password1'),
('player2', 'hashed_password2');

INSERT INTO Scores (user_id, score, level_reached) VALUES
(1, 1000, 5),
(1, 2000, 8),
(2, 1500, 6);

INSERT INTO GameSettings (user_id, sound, difficulty, controls) VALUES
(1, TRUE, 'Hard', 'WASD'),
(2, FALSE, 'Medium', 'ArrowKeys');
