# Research Report
## gRPC Java Server, gRPC Web Javascript Client, Envoy Proxy, and Protocol Buffers

### Summary of Work
<!--One paragraph summary of the research being performed-->
I researched into how gRPC worked in Java and Javascript. From this sprouted additional topics to research into, including how to handle Java package dependencies, how to handle Javascript dependencies, the Envoy Proxy, and how to compile proto files into Java/Javascript classes using the protoc compiler. I read many github pages and documentation, followed youtube tutorials, and read documentation on different APIs. During this process I created a Java gRPC server running in a docker container that listens to incomming requests from another Docker container running the Envoy proxy, which in turn listens to Client requests on the Browser. The client is a web server written in Javascript.

### Motivation
<!--Explain why you felt the need to perform this research-->
My team needed a way for the various components (Front end, Back end, and Sever) to communicate between each other. I was familiar with gRPC (google remote procedure call) and protocol buffers, which is a way for a client computer to remotely call a function from a server computer. Loosely, the client sends function arguments in an efficient way and recieves the result back from the Server. I have written a few servers and clients in python, and figured I could learn how to do it in Java and Javascript. The goal was to learn and create a demo of this to share with my team and decide if it is something we want to incorporate into our tech stack. 

### Time Spent
<!--Explain how your time was spent-->
~ 30 minutes writing a demo.proto file, reading Protoc/Java documentation and installing protoc tools for Java
~ 30 minutes figuring out dependencies for Java grpc libraries, reading java grpc github pages, downloading required jar files from the Maven repository, and compiling the demo.proto file into separate java classes
~ 60 minutes writing the Java service implementation, Java server, a Dockerfile for the server, and following parts of tutorials
~ 30 minutes writing a client in Java for testing
~ 30 minutes installing protoc tools for Javascript, reading Web grpc documentation and compiling demo.proto into javascript classes
~ 30 minutes troubleshooting npm and npx webpack to deal with javascript dependencies
~ 60 minutes following github tutorial to create a javascript web client that sends grpc requests
~ 120 minutes reading into envoy proxy in a docker container, docker networks, configuring envoy, writing a docker compose to get the proxy container and server container on same network and communicating. 

### Results
<!--Explain what you learned/produced/etc. This section should explain the
important things you learned so that it can serve as an easy reference for yourself
and others who could benefit from reviewing this topic. Include your sources as
footnotes. Make sure you include the footnotes where appropriate e.g [^1]-->
I started by writing a demo.proto file with a simple function (known as a service):
```proto
syntax = "proto3";

option java_multiple_files = true;
option java_outer_classname = "demo";

message AddTwoRequest {
    int32 x = 1;
}

message AddTwoResponse {
    int32 y = 1;
}

service AddTwoService {
    rpc AddTwo(AddTwoRequest) returns (AddTwoResponse);
}
```

The syntax option lets the compiler know what proto language I'm using. A message is defined by using the message keyword followed by the name. In it you can include as many arguments as you want. Note that the equals sign is not an assignment statement. It numbers the arguments sequentially, in case different versions of your message have a different number of arguments. These are language independent; a correspondence between different types can be found here[^1].

The service keyword defines the service, which will take a AddTwoRequest to the server and return an AddTwoResponse to the client. By convention, clients send requests and servers sent reponses. The messages and services define an API between the front end and back end. Messages are stored in a wire format,seirialized/encoded efficiently in a binary format which makes it fast.

Next I looked at language specific options here[^2] and found tools to compile demo.proto into Java code here[^3]. Here I found a link to the Maven repository for Jar dependencies and downloaded them. They can be found here: 
I then installed the following for compiling:
- protobuf: the protoc gen compiler
- protoc-gen-grpc-java: a protoc java extension

Using a command similar to
```bash
protoc --java_out=. --grpc-java_out=. demo.proto
```
I obtained several Java classes:
- AddTwoRequest.java: A class for turning java types into requests
- AddTwoResponse.java: A class for turning responses into java types
- AddTwoServiceGrpc.java: A class containing the base code for a grpc service
- Two more java interfaces that aren't that important



### Sources
<!--list your sources and link them to a footnote with the source url-->
- Language Guide (proto3)[^1]
- Protocol Buffer Basics: Java[^2]
- gRPC-Java[^3]
- Placeholder4[^4]
- And so on...
[^1]: https://protobuf.dev/programming-guides/proto3/
[^2]: https://protobuf.dev/getting-started/javatutorial/
[^3]: https://github.com/grpc/grpc-java
[^4]: www.google.com
