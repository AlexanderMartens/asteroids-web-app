import React, { useEffect, useRef } from "react";
import asteroid_64 from "./images/asteroid_64x64.png";
import asteroid_32 from "./images/asteroid_32x32.png";
import ship from "./images/asteroid-logo-bgless.png";
import invShip from "./images/invincible-ship.png"

const Game = () => {
  const canvasRef = useRef(null);
  const hitboxCheckboxRef = useRef(null);
  const pauseButtonRef = useRef(null);
  const requestRef = useRef(null);
  const asteroidImg32 = new Image();
  const asteroidImg64 = new Image();
  const shipImg = new Image();
  const invincibleShip = new Image();
  asteroidImg32.src = asteroid_32;
  asteroidImg64.src = asteroid_64;
  shipImg.src = ship;
  invincibleShip.src = invShip;

  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    let username = "test";
    let profile_name = "test";
    let input = "";
    let last_time = 0;
    let hitboxes = false;
    let paused = false;

    const activeInputs = new Set();

    const handleHitboxChange = () => {
      hitboxes = hitboxCheckboxRef.current.checked;
    };

    const handleKeyDown = (event) => {
      if (event.key === "w") activeInputs.add("UP");
      if (event.key === "s") activeInputs.add("SHOOT");
      if (event.key === "a") activeInputs.add("LEFT");
      if (event.key === "d") activeInputs.add("RIGHT");
      input = Array.from(activeInputs).join(",");
    };

    const handleKeyUp = (event) => {
      if (event.key === "w") activeInputs.delete("UP");
      if (event.key === "s") activeInputs.delete("SHOOT");
      if (event.key === "a") activeInputs.delete("LEFT");
      if (event.key === "d") activeInputs.delete("RIGHT");
      input = Array.from(activeInputs).join(",");
    };

    const animate = async (timestamp) => {
      let dt = (timestamp - last_time) / 1000;
      last_time = timestamp;
      if (isNaN(dt)) dt = 0;

      if (paused) {
        requestRef.current = requestAnimationFrame(animate);
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
          `profile_name=${encodeURIComponent(profile_name)}&` +
          `inputs=${encodeURIComponent(input)}`,
      );

      const data = await response.json();
      if (timestamp % 1000 < 16) console.log(data);

      console.log(timestamp);
      console.log("Enemies:", data.enemies);

      const player = data.player;
      const lives = player?.lives ?? 0;
      const score = data.score ?? 0;
      const level = data.level ?? 0;
      const time = data.time ?? 0;
      const is_running = data.is_running;
      const bullets = data.bullets ?? [];
      const enemies = data.enemies ?? [];

      context.clearRect(0, 0, canvas.width, canvas.height);

      if (player) {
        context.save();
        context.translate(player.position.x, player.position.y);
        context.rotate(player.orientation + Math.PI / 2); //corrects for image rotation
      
        const shipSize = 50.0;
        if (player.is_invincible) {
          context.drawImage(
          invincibleShip,
          -shipSize / 2,
          -shipSize / 2,
          shipSize,
          shipSize
          );
        } else{
          context.drawImage(
          shipImg,
          -shipSize / 2,
          -shipSize / 2,
          shipSize,
          shipSize
          );
        }
      
        context.restore();
      }

      // Bullets
      bullets.forEach((bullet) => {
        context.save();
        context.fillStyle = "blue";
        context.beginPath();
        context.arc(bullet.position.x, bullet.position.y, 5, 0, 2 * Math.PI);
        context.fill();
        context.restore();
      });

      // Enemies
      for (let enemy of enemies) {
        console.log("Enemy type:", enemy.type);

        // Asteroid sprite depends on the size
        if (enemy.type === "ASTEROID") {
          const { x, y } = enemy.position;
          const orientation = enemy.orientation;
          let image;
          let imageSize;

          if (enemy.size === "SMALL" || enemy.size === "MEDIUM") {
            image = asteroidImg32;
            imageSize = enemy.hitbox[0].radius * 2; // use hitbox radius for small/medium asteroids
          } else if (enemy.size === "LARGE") {
            image = asteroidImg64;
            imageSize = enemy.hitbox[0].radius * 2; // use hitbox radius for large asteroids
          }

          if (image) {
            context?.save();
            context.translate(x, y);
            context.rotate(orientation);
            context.drawImage(
              image,
              0 - imageSize / 2, // center the image
              0 - imageSize / 2,
              imageSize,
              imageSize
            );
            context.restore();
          }
        } else {
          // fallback: draw green circle for non-asteroid enemies
          context?.save();
          context.fillStyle = "green";
          context.beginPath();
          let size = enemy.hitbox[0].radius;
          context.arc(enemy.position.x, enemy.position.y, size, 0, 2 * Math.PI);
          context.fill();
          context.restore();
        }
      }

      // Hitboxes
      if (hitboxes) {
        enemies.forEach((enemy) => {
          enemy.hitbox?.forEach((hb) => {
            context.save();
            context.strokeStyle = "rgba(255, 0, 0, 0.5)";
            context.beginPath();
            context.arc(
              hb.position.x,
              hb.position.y,
              hb.radius,
              0,
              2 * Math.PI,
            );
            console.log(typeof hb.position.x, typeof hb.position.y);
            context.stroke();
            context.restore();
          });
          console.log(
            "enemy pos",
            enemy.position,
            "hitbox pos",
            enemy.hitbox[0].position,
          );
        });

        bullets.forEach((bullet) => {
          bullet.hitbox?.forEach((hb) => {
            context.save();
            context.strokeStyle = "rgba(255, 0, 0, 0.5)";
            context.beginPath();
            context.arc(
              hb.position.x,
              hb.position.y,
              hb.radius,
              0,
              2 * Math.PI,
            );
            context.stroke();
            context.restore();
          });
        });

        player?.hitbox?.forEach((hb) => {
          context.save();
          context.strokeStyle = "rgba(255, 0, 0, 0.5)";
          context.beginPath();
          context.arc(hb.position.x, hb.position.y, hb.radius, 0, 2 * Math.PI);
          context.stroke();
          context.restore();
        });
      }

      // Info Text
      context.save();
      context.fillStyle = "black";
      context.font = "20px Arial";
      context.fillText(`Lives: ${lives}`, 10, 20);
      context.fillText(`Score: ${score}`, 10, 40);
      context.fillText(`Level: ${level}`, 10, 60);
      context.fillText(`Time: ${time}`, 10, 80);
      context.restore();

      if (!is_running) {
        context.save();
        context.fillStyle = "black";
        context.font = "50px Arial";
        context.fillText("Game Over", 350, 450);
        context.restore();
      }

      requestRef.current = requestAnimationFrame(animate);
    };

    document.addEventListener("keydown", handleKeyDown);
    document.addEventListener("keyup", handleKeyUp);
    hitboxCheckboxRef.current.addEventListener("change", handleHitboxChange);

    // Start Button (outside of canvas)
    const startBtn = document.getElementById("startGameButton");
    startBtn?.addEventListener("click", async () => {
      await fetch(
        `http://localhost:8080/api/newGame?` +
          `username=${encodeURIComponent(username)}&` +
          `profile_name=${encodeURIComponent(profile_name)}`,
      );
    });

    pauseButtonRef.current.addEventListener("click", () => {
      paused = !paused;
      pauseButtonRef.current.innerText = paused ? "Resume" : "Pause";
    });

    requestRef.current = requestAnimationFrame(animate);

    return () => {
      cancelAnimationFrame(requestRef.current);
      document.removeEventListener("keydown", handleKeyDown);
      document.removeEventListener("keyup", handleKeyUp);
    };
  }, []);

  return (
    <div>
      <div>
        <label>
          Show Hitboxes{" "}
          <input ref={hitboxCheckboxRef} type="checkbox" id="hitboxes" />
        </label>
      </div>
      <button id="startGameButton">Start Game</button>
      <button ref={pauseButtonRef} id="pauseGameButton">
        Pause
      </button>
      <canvas
        ref={canvasRef}
        id="box1canvas"
        width={1000}
        height={1000}
        style={{ border: "1px solid black", marginTop: "1em" }}
      ></canvas>
    </div>
  );
};

export default Game;
