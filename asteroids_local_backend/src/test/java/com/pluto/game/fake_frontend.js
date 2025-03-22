// @ts-check

export {}; // null export tells vscode to treat this as a module

/**
 * This is a fake frontend that sends requests to the backend and renders the game state.
 * It is intended to be used for testing the backend.
 * To open the frontend, Use the live server extension in vscode in index.html.
 */
// use type information to make TypeScript happy
let canvas = /** @type {HTMLCanvasElement} */ (document.getElementById("box1canvas"));
let context = canvas.getContext("2d");

// Create test username and password
let username = "test";
let profile_name = "test";

let input = "";
let last_time = 0;
let hitboxes = false;

// Set hitboxes based on checkbox state
let hitboxCheckbox = /** @type {HTMLInputElement} */ (document.getElementById("hitboxes"));
hitboxCheckbox.addEventListener("change", function () {
    hitboxes = hitboxCheckbox.checked;
});

// Check player inputs
let activeInputs = new Set();

document.addEventListener("keydown", function (event) {
    if (event.key === "w") {
        activeInputs.add("UP");
    } else if (event.key === "s") {
        activeInputs.add("SHOOT");
    } else if (event.key === "a") {
        activeInputs.add("LEFT");
    } else if (event.key === "d") {
        activeInputs.add("RIGHT");
    }
    input = Array.from(activeInputs).join(",");
});

document.addEventListener("keyup", function (event) {
    if (event.key === "w") {
        activeInputs.delete("UP");
    } else if (event.key === "s") {
        activeInputs.delete("SHOOT");
    } else if (event.key === "a") {
        activeInputs.delete("LEFT");
    } else if (event.key === "d") {
        activeInputs.delete("RIGHT");
    }
    input = Array.from(activeInputs).join(",");
});

// Animation loop
async function animate(timestamp) {
    // Get dt
    let dt = (timestamp - last_time) / 1000;
    last_time = timestamp;
    // Check if dt is NaN
    if (isNaN(dt)) {
        dt = 0;
    }
    // Get game data
    const response = await fetch(
        `http://localhost:8080/api/updateGame?dt=${encodeURIComponent(dt)}&` +
        `username=${encodeURIComponent(username)}&` +
        `profile_name=${encodeURIComponent(profile_name)}&` +
        `inputs=${encodeURIComponent(input)}`
    );
    const data = await response.json();
    if (timestamp % 1000 < 16) {
        console.log(data);
    }
    
    // Extract data
    let player = data.player;

    let lives = data.player.lives;
    let score = data.score;
    let level = data.level;
    let time = data.time;
    let is_running = data.is_running;

    let bullets = data.bullets;
    let asteroids = data.asteroids;

    // clear the canvas
    context.clearRect(0, 0, canvas.width, canvas.height);
    

    // Draw the player as a triangle
    context.save();
    if (player.is_invincible) {
        context.fillStyle = "purple";
    } else {
        context.fillStyle = "red";
    }
    context.translate(player.position.x, player.position.y);
    context.rotate(player.orientation);
    context.beginPath();
    context.moveTo(25, 0);
    context.lineTo(-25, 25);
    context.lineTo(-25, -25);
    context.closePath();
    context.fill();
    context.restore();

    // Draw the bullets
    for (let bullet of bullets) {
        context?.save();
        context.fillStyle = "blue";
        context.beginPath();
        context.arc(bullet.position.x, bullet.position.y, 5, 0, 2 * Math.PI);
        context.fill();
        context.restore();
    }

    // Draw the asteroids
    for (let asteroid of asteroids) {
        context?.save();
        context.fillStyle = "green";
        context.beginPath();
        let size = asteroid.hitbox[0].radius;
        context.arc(asteroid.position.x, asteroid.position.y, size, 0, 2 * Math.PI);
        context.fill();
        context.restore();
    }

    // Draw the hitboxes
    if (hitboxes) {
        for (let asteroid of asteroids) {
            for (let asteroidHitbox of asteroid.hitbox) {
                context?.save();
                context.strokeStyle = "rgba(255, 0, 0, 0.5)";
                context.beginPath();
                context.arc(asteroidHitbox.position.x, asteroidHitbox.position.y, asteroidHitbox.radius, 0, 2 * Math.PI);
                context.stroke();
                context.restore();
            }
        }
        for (let bullet of bullets) {
            for (let bulletHitbox of bullet.hitbox) {
                context?.save();
                context.strokeStyle = "rgba(255, 0, 0, 0.5)";
                context.beginPath();
                context.arc(bulletHitbox.position.x, bulletHitbox.position.y, bulletHitbox.radius, 0, 2 * Math.PI);
                context.stroke();
                context.restore();
            }
        }
        for (let playerHitbox of player.hitbox) {
            context?.save();
            context.strokeStyle = "rgba(255, 0, 0, 0.5)";
            context.beginPath();
            context.arc(playerHitbox.position.x, playerHitbox.position.y, playerHitbox.radius, 0, 2 * Math.PI);
            context.stroke();
            context.restore();
        }
    }

    // Draw text of player data
    context?.save();
    context.fillStyle = "black";
    context.font = "20px Arial";
    context.fillText(`Lives: ${lives}`, 10, 20);
    context.fillText(`Score: ${score}`, 10, 40);
    context?.fillText(`Level: ${level}`, 10, 60);
    context.fillText(`Time: ${time}`, 10, 80);
    context.restore();
    
    requestAnimationFrame(animate);
}
animate()
