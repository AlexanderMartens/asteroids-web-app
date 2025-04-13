import React, { useEffect, useRef } from "react";

const Game = () => {
  const canvasRef = useRef(null);
  const hitboxCheckboxRef = useRef(null);
  const pauseButtonRef = useRef(null);

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
        requestAnimationFrame(animate);
        context.save();
        context.fillStyle = "black";
        context.font = "50px Arial";
        context.fillText("Paused", 350, 350);
        context.restore();
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/updateGame?dt=${encodeURIComponent(dt)}&` +
            `username=${encodeURIComponent(username)}&` +
            `profile_name=${encodeURIComponent(profile_name)}&` +
            `inputs=${encodeURIComponent(input)}`
        );
        const data = await response.json();
        if (timestamp % 1000 < 16) console.log(data);

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

        // Player
        context.save();
        context.fillStyle = player?.is_invincible ? "purple" : "red";
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

        // Enemies
        for (let enemy of enemies) {
          context?.save();
          context.fillStyle = "green";
          context.beginPath();
          let size = enemy.hitbox[0].radius;
          console.log("enemy size", size);
          context.arc(enemy.position.x, enemy.position.y, size, 0, 2 * Math.PI);
          context.fill();
          context.restore();
        }

        // Hitboxes
        if (hitboxes) {
          enemies.forEach((enemy) => {
            enemy.hitbox?.forEach((hb) => {
              context.save();
              context.strokeStyle = "rgba(255, 0, 0, 0.5)";
              context.beginPath();
              context.arc(hb.position.x, hb.position.y, hb.radius, 0, 2 * Math.PI);
              context.stroke();
              context.restore();
            });
          });

          bullets.forEach((bullet) => {
            bullet.hitbox?.forEach((hb) => {
              context.save();
              context.strokeStyle = "rgba(255, 0, 0, 0.5)";
              context.beginPath();
              context.arc(hb.position.x, hb.position.y, hb.radius, 0, 2 * Math.PI);
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
      } catch (err) {
        console.error("Error during game update:", err);
      }

      requestAnimationFrame(animate);
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
          `profile_name=${encodeURIComponent(profile_name)}`
      );
    });

    pauseButtonRef.current.addEventListener("click", () => {
      paused = !paused;
      pauseButtonRef.current.innerText = paused ? "Resume" : "Pause";
    });

    requestAnimationFrame(animate);

    return () => {
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
