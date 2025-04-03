Research Report
Connecting Frontend to VM
Summary of Work
<!--One paragraph summary of the research being performed-->
I researched how to deploy and connect a frontend application to a backend server hosted on a virtual machine (VM) within the UW–Madison Computer Sciences Lab (CSL) infrastructure. My goal was to host the frontend on pages.cs.wisc.edu and have it communicate with a backend service running on the school VM via HTTP. I successfully deployed a Spring Boot backend using Docker and created a React frontend, but was ultimately unable to complete the connection due to firewall restrictions that block external access to the school VM. As a result, I plan to instead run both the frontend and backend entirely within the school VM.

Motivation
<!--Explain why you felt the need to perform this research-->
For our project, we needed a working deployment of a full-stack web application with a frontend and backend. I wanted to explore how to use the CSL environment to deploy our system. Initially, the idea was to separate the frontend (hosted publicly on pages.cs.wisc.edu) and the backend (on a school VM), but this was blocked by security policies. After discovering these restrictions, I decided to try deploying both parts inside the VM to allow communication internally without requiring public access.

Time Spent
<!--Explain how your time was spent-->
15 minutes reading the CSL web service and network documentation

20 minutes setting up Docker on the school VM

20 minutes deploying Spring Boot backend

20 minutes building and testing the React frontend

15 minutes debugging connection issues caused by the firewall

10 minutes planning revised deployment strategy

Results
<!--Explain what you learned/produced/etc.-->
I learned that school VMs are hosted on restricted subnets (e.g., 128.105.102.0/24) that block incoming public traffic as a security precaution. This means any service hosted on a VM is inaccessible from the public internet, including from pages.cs.wisc.edu. Even with the backend correctly running on port 8080 and the frontend sending HTTP requests via fetch, the frontend could not connect due to blocked inbound traffic.

To work around this, I will run both the backend and frontend inside the same school VM, serving the React build using either a simple static server (e.g., serve) or by integrating it into the Spring Boot backend.

For example, a working production plan is to:

Build the React frontend using npm run build

Copy the build files into src/main/resources/static in the Spring Boot project

Serve the static frontend and API from the same Spring Boot server

This way, everything runs locally within the school VM, avoiding network restrictions. While this limits outside users from accessing the app directly, it enables internal testing and satisfies the requirements for our project.

Sources
<!--list your sources and link them to a footnote with the source url-->
CSL Web Services Overview1

CSL Docker and Firewall Info2

Apache Server Docs3

React Production Build4

Spring Boot Static Hosting5

Let me know if you want to include the actual code/configs for serving the React app inside Spring Boot, or if you'd like this adapted for your team's documentation!

Footnotes
https://www.cs.wisc.edu/computing/facilities/web/ ↩

https://csl.cs.wisc.edu ↩

https://httpd.apache.org/docs/ ↩

https://create-react-app.dev/docs/deployment/ ↩

https://spring.io/guides/gs/serving-web-content/ ↩
