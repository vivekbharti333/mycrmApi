<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Face Attendance System</title>

<style>
  body {
    font-family: "Poppins", sans-serif;
    background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
    color: #fff;
    text-align: center;
    margin: 0;
    padding: 20px;
    transition: background 0.6s ease;
  }

  h2 {
    margin-top: 10px;
    font-size: 24px;
  }

  .container {
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
    display: inline-block;
    padding: 25px;
    margin-top: 20px;
    width: 95%;
    max-width: 450px;
    transition: all 0.3s;
  }

  video {
    border-radius: 12px;
    width: 100%;
    height: auto;
    border: 3px solid #fff;
  }

  button {
    background: #00c851;
    border: none;
    color: white;
    padding: 12px 25px;
    margin-top: 15px;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    width: 100%;
    transition: 0.3s;
  }

  button:hover {
    background: #007e33;
  }

  #status {
    margin-top: 10px;
    font-size: 15px;
  }

  #responseBox {
    display: none;
    margin-top: 20px;
    text-align: left;
    background: #ffffff22;
    padding: 15px 20px;
    border-radius: 10px;
    color: #fff;
  }

  .resp-label {
    font-weight: bold;
    color: #ffe082;
  }

  .success {
    color: #00ff99;
  }

  .error {
    color: #ff5252;
  }

  /* ✅ Mobile friendly adjustments */
  @media (max-width: 600px) {
    h2 { font-size: 20px; }
    button { font-size: 15px; padding: 10px; }
    .container { width: 100%; padding: 20px; }
  }
</style>
</head>
<body>

  <h2>Face Attendance System</h2>

  <div class="container" id="cameraContainer">
    <video id="video" autoplay playsinline></video><br>
    <button id="captureBtn">📸 Capture & Mark Attendance</button>
    <p id="status">Initializing camera...</p>
    <canvas id="canvas" style="display:none;"></canvas>
  </div>

  <div class="container" id="responseBox">
    <h3>📋 Attendance Result</h3>
    <p><span class="resp-label">Status:</span> <span id="respStatus"></span></p>
    <p><span class="resp-label">Similarity:</span> <span id="respSimilarity"></span></p>
    <p><span class="resp-label">Message:</span> <span id="respMessage"></span></p>
  </div>

  <script>
    const video = document.getElementById('video');
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    const statusEl = document.getElementById('status');
    const responseBox = document.getElementById('responseBox');
    const cameraContainer = document.getElementById('cameraContainer');
    const respStatus = document.getElementById('respStatus');
    const respSimilarity = document.getElementById('respSimilarity');
    const respMessage = document.getElementById('respMessage');

    // ✅ Start camera (works on mobile + desktop)
    async function startCamera() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: { facingMode: "user" }
        });
        video.srcObject = stream;
        statusEl.textContent = "Camera started ✅";
      } catch (err) {
        console.error(err);
        statusEl.textContent = "❌ Camera access denied";
      }
    }

    // ✅ Capture and send to API
    document.getElementById('captureBtn').addEventListener('click', async () => {
      try {
        statusEl.textContent = "Capturing image...";
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

        let base64Image = canvas.toDataURL('image/jpeg').split(',')[1];
        statusEl.textContent = "Please wait... ⏳";

        const payload = {
          payload: {
            token: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNQUlOQURNSU4iLCJpc3MiOiJBRE1JTiIsImlhdCI6MTY5NTM2MzI1MywiZXhwIjoxNjk1NDA2NDUzfQ.Fil32gaA1364xCwpLB1zUdTnSEVc9TFPKcX9_SrOOGTeNi45zzFIF5VyAZMwGx1bEtQDdsH0KLgTW96xPA2eXA",
            createdBy: "1234567890",
            superadminId: "MAINADMIN",
            punchInLocation: "Current Location",
            requestFor: "PUNCHIN",
            clickImage: base64Image
          }
        };

        const res = await fetch("http://localhost/mycrm/markAttendance", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        });

        if (!res.ok) throw new Error("Server error " + res.status);

        const data = await res.json();
        console.log("Response:", data);

        if (data && data.payload) {
          // 🎨 Change background color to orange and show only response
          document.body.style.background = "linear-gradient(135deg, #ff9800, #ff5722)";
          cameraContainer.style.display = "none";
          responseBox.style.display = "block";

          respStatus.innerHTML = data.payload.status === "MATCH"
            ? `<span class='success'>${data.payload.status}</span>`
            : `<span class='error'>${data.payload.status || 'NO MATCH'}</span>`;

          respSimilarity.textContent = (data.payload.similarity || 0).toFixed(2) + " %";
          respMessage.textContent = data.payload.respMesg || data.responseMessage || "No message";

          statusEl.textContent = "✅ Attendance marked successfully";
        } else {
          throw new Error("Invalid response format");
        }

      } catch (err) {
        console.error(err);
        statusEl.textContent = "❌ Error: " + err.message;
      }
    });

    startCamera();
  </script>
</body>
</html>
