import React, { useRef, useEffect, useState } from "react";
import asteroid32png from './images/asteroid_32x32.png'; // adjust the path as needed

const Play = () => {
  const canvasRef = useRef(null);
  const hitboxRef = useRef(null);
  const startButtonRef = useRef(null);
  const pauseButtonRef = useRef(null);

  const [isPaused, setIsPaused] = useState(false);
  const [showHitboxes, setShowHitboxes] = useState(false);
  const activeInputs = useRef(new Set());
  const lastTime = useRef(0);
  const inputRef = useRef("");

  const username = "test";
  const profileName = "test";

  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    const handleKeyDown = (event) => {
      if (event.key === "w") activeInputs.current.add("UP");
      if (event.key === "s") activeInputs.current.add("SHOOT");
      if (event.key === "a") activeInputs.current.add("LEFT");
      if (event.key === "d") activeInputs.current.add("RIGHT");
      inputRef.current = Array.from(activeInputs.current).join(",");
    };

    const handleKeyUp = (event) => {
      if (event.key === "w") activeInputs.current.delete("UP");
      if (event.key === "s") activeInputs.current.delete("SHOOT");
      if (event.key === "a") activeInputs.current.delete("LEFT");
      if (event.key === "d") activeInputs.current.delete("RIGHT");
      inputRef.current = Array.from(activeInputs.current).join(",");
    };

    document.addEventListener("keydown", handleKeyDown);
    document.addEventListener("keyup", handleKeyUp);

    const animate = async (timestamp) => {
      let dt = (timestamp - lastTime.current) / 1000;
      lastTime.current = timestamp;
      if (isNaN(dt)) dt = 0;

      if (isPaused) {
        requestAnimationFrame(animate);
        context.save();
        context.fillStyle = "black";
        context.font = "50px Arial";
        context.fillText("Paused", 350, 350);
        context.restore();
        return;
      }

      const response = await fetch(
        `http://localhost:8080/api/updateGame?dt=${encodeURIComponent(dt)}&` +
          `username=${encodeURIComponent(username)}&` +
          `profile_name=${encodeURIComponent(profileName)}&` +
          `inputs=${encodeURIComponent(inputRef.current)}`
      );
      const data = await response.json();
      if (timestamp % 1000 < 16) console.log(data);

      let { player, bullets, asteroids, score, lives, level, time, is_running: isRunning } = data;

      context.clearRect(0, 0, canvas.width, canvas.height);

      // Player
      context.save();
      context.fillStyle = player.is_invincible ? "purple" : "red";
      context.translate(player.position.x, player.position.y);
      context.rotate(player.orientation);
      context.beginPath();
      context.moveTo(25, 0);
      context.lineTo(-25, 25);
      context.lineTo(-25, -25);
      context.closePath();
      context.fill();
      context.restore();

      // Bullets
      bullets.forEach((bullet) => {
        context.save();
        context.fillStyle = "blue";
        context.beginPath();
        context.arc(bullet.position.x, bullet.position.y, 5, 0, 2 * Math.PI);
        context.fill();
        context.restore();
      });

      // Asteroids
      const asteroid32Image = new Image();
      asteroid32Image.src = asteroid32png;

      asteroids.forEach((asteroid) => {
        context.save();

        
        const halfSize = asteroid.hitbox[0].radius * 2;

        context.drawImage(
            asteroid32Image,
            asteroid.position.x - halfSize / 2,
            asteroid.position.y - halfSize / 2,
            halfSize,
            halfSize
        );

        // context.fillStyle = "green";
        // context.beginPath();
        // context.arc(
        //   asteroid.position.x,
        //   asteroid.position.y,
        //   asteroid.hitbox[0].radius,
        //   0,
        //   2 * Math.PI
        // );
        // context.fill();

        context.restore();
      });

      // Hitboxes
      if (showHitboxes) {
        const drawHitbox = (hitbox) => {
          context.save();
          context.strokeStyle = "rgba(255, 0, 0, 0.5)";
          context.beginPath();
          context.arc(hitbox.position.x, hitbox.position.y, hitbox.radius, 0, 2 * Math.PI);
          context.stroke();
          context.restore();
        };

        asteroids.forEach((a) => a.hitbox.forEach(drawHitbox));
        bullets.forEach((b) => b.hitbox.forEach(drawHitbox));
        player.hitbox.forEach(drawHitbox);
      }

      // HUD Text
      context.save();
      context.fillStyle = "black";
      context.font = "20px Arial";
      context.fillText(`Lives: ${lives}`, 10, 20);
      context.fillText(`Score: ${score}`, 10, 40);
      context.fillText(`Level: ${level}`, 10, 60);
      context.fillText(`Time: ${time}`, 10, 80);
      context.restore();

      // Game Over
      if (!isRunning) {
        context.save();
        context.fillStyle = "black";
        context.font = "50px Arial";
        context.fillText("Game Over", 350, 450);
        context.restore();
      }

      requestAnimationFrame(animate);
    };

    requestAnimationFrame(animate);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
      document.removeEventListener("keyup", handleKeyUp);
    };
  }, [isPaused, showHitboxes]);

  const handleStartGame = async () => {
    await fetch(
      `http://localhost:8080/api/newGame?username=${encodeURIComponent(username)}&profile_name=${encodeURIComponent(profileName)}`
    );
  };

  const handlePauseToggle = () => {
    setIsPaused((prev) => !prev);
  };

  const handleHitboxToggle = (e) => {
    setShowHitboxes(e.target.checked);
  };

  return (
    <div>
      <div>
        <label htmlFor="hitboxes">Hitboxes: </label>
        <input
          type="checkbox"
          id="hitboxes"
          ref={hitboxRef}
          onChange={handleHitboxToggle}
        />
        <button ref={startButtonRef} onClick={handleStartGame}>
          Start New Game
        </button>
        <button ref={pauseButtonRef} onClick={handlePauseToggle}>
          {isPaused ? "Resume" : "Pause"}
        </button>
      </div>
      <canvas ref={canvasRef} height="1000" width="1000" />
    </div>
  );
};

export default Play;
