// @ts-check

export {}; // null export tells vscode to treat this as a module

/**
 * Example 1 (Review the Basics) - just a simple square
 */
// use type information to make TypeScript happy
let canvas = /** @type {HTMLCanvasElement} */ (document.getElementById("box1canvas"));
let context = canvas.getContext("2d");

let username = "test";
let profile_name = "test";
let input = "";
let last_time = 0;

// Check player inputs
let activeInputs = new Set();

document.addEventListener("keydown", function (event) {
    if (event.key === "ArrowUp") {
        activeInputs.add("UP");
    } else if (event.key === "ArrowDown") {
        activeInputs.add("SHOOT");
    } else if (event.key === "ArrowLeft") {
        activeInputs.add("LEFT");
    } else if (event.key === "ArrowRight") {
        activeInputs.add("RIGHT");
    }
    input = Array.from(activeInputs).join(",");
});

document.addEventListener("keyup", function (event) {
    if (event.key === "ArrowUp") {
        activeInputs.delete("UP");
    } else if (event.key === "ArrowDown") {
        activeInputs.delete("SHOOT");
    } else if (event.key === "ArrowLeft") {
        activeInputs.delete("LEFT");
    } else if (event.key === "ArrowRight") {
        activeInputs.delete("RIGHT");
    }
    input = Array.from(activeInputs).join(",");
});

async function animate(timestamp) {
    // Get dt
    let dt = (timestamp - last_time) / 1000;
    last_time = timestamp;
    // Check if dt is NaN
    if (isNaN(dt)) {
        dt = 0;
    }
    // Get game data
    const response = await fetch(`http://localhost:8080/api/updateGame?dt=${encodeURIComponent(dt)}&username=${encodeURIComponent(username)}&profile_name=${encodeURIComponent(profile_name)}&inputs=${encodeURIComponent(input)}`);
    const data = await response.json();
    console.log(input, dt);
    console.log(data);
    // clear the canvas
    context.clearRect(0, 0, canvas.width, canvas.height);
    // draw the player
    context.fillStyle = "red";
    let player_x = data.player.position.x
    let player_y = data.player.position.y
    let player_orientation = data.player.orientation
    let lives = data.player.lives
    let score = data.score
    let time = data.time
    let is_running = data.is_running

    // Draw the player as a triangle
    context.save();
    context.translate(player_x, player_y);
    context.rotate(player_orientation);
    context.beginPath();
    context.moveTo(25, 0);
    context.lineTo(-25, 25);
    context.lineTo(-25, -25);
    context.closePath();
    context.fill();
    context.restore();

    // Draw the bullets
    for (let bullet of data.bullets) {
        context?.save();
        context.fillStyle = "blue";
        context.beginPath();
        context.arc(bullet.position.x, bullet.position.y, 5, 0, 2 * Math.PI);
        context.fill();
        context.restore();
    }

    // Draw the asteroids
    for (let asteroid of data.asteroids) {
        context?.save();
        context.fillStyle = "green";
        context.beginPath();
        let size;
        if (asteroid.size = "SMALL") {
            size = 25;
        } else if (asteroid.size = "MEDIUM") {
            size = 50;
        } else {
            size = 100;
        }
        context.arc(asteroid.position.x, asteroid.position.y, size, 0, 2 * Math.PI);
        context.fill();
        context.restore();
    }

    // Draw text of player data
    context?.save();
    context.fillStyle = "black";
    context.font = "20px Arial";
    context.fillText(`Lives: ${lives}`, 10, 20);
    context.fillText(`Score: ${score}`, 10, 40);
    context.fillText(`Time: ${time}`, 10, 60);
    context.fillText(`Running: ${is_running}`, 10, 80);
    context?.fillText('Player x position: ' + player_x, 10, 100);
    context?.fillText('Player y position: ' + player_y, 10, 120);
    context?.fillText('Player orientation: ' + player_orientation, 10, 140);
    context.restore();
    
    requestAnimationFrame(animate);
}
animate()
