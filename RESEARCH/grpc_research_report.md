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

Referencing here[^4] and a youtube tutorial[^5], I implemented the AddTwoServiceImpl.java and DemoServer.java for my specific services.

```Java
import io.grpc.ServerBuilder;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class AddTwoServiceImpl extends AddTwoServiceGrpc.AddTwoServiceImplBase {

    @Override
    public void addTwo(AddTwoRequest request, StreamObserver<AddTwoResponse> responseObserver) {
        int x = request.getX();
        AddTwoResponse response = AddTwoResponse.newBuilder().setY(x + 2).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```
This first file is the implementation of my service I defined in the demo.proto file. I extend the ImplBase class and implement the addTwo service function. It takes a request and gets the x value I defined in the demo.proto file. Then it creates a response object, setting the y value to y = x + 2. The StreamObserver class can be found here[^7] for the remaining method calls. The response value is then sent back to the client.

```Java
import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class DemoServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(5000).addService(new AddTwoServiceImpl()).build();
        server.start();
        System.out.println("Server started on port 5000");
        server.awaitTermination();
    }    
}
```
Next I implemented the server that handles requests on port 5000 and add the new service I implemented above. I also used[^7] as a reference for creating a Java client, which can be found in the repository. Once the server is coded, many services and messages may be added in the same manner.

Next I wrote a docker file to host my server in Ubuntu with portforwarding on port 5000. I also made a hacky start script since I can't run java code in the container that I compiled locally (newer open-jdk). 

I next learned about grpc web[^8] for Javascript web clients. I installed 
- protoc-gen-js: protoc javascript extension
- protoc-gen-grpc-web: protoc plugin
and using a command
```bash
protoc -I=. demo.proto --js_out=import_style=commonjs:. --grpc-web_out=import_style=commonjs,mode=grpcwebtext:.
```
I compiled the demo.proto (without the java related options) to obtain
- demo_pb.js: class for working with AddTwoRequest and AddTwoResponse objects
- demo_grpc_web_pb.js: class for making service calls




### Sources
<!--list your sources and link them to a footnote with the source url-->
- Language Guide (proto3)[^1]
- Protocol Buffer Basics: Java[^2]
- gRPC-Java[^3]
- gRPC in Java[^4]
- GRPC Service in Java[^5]
- io.grpc.stub Docs[^6]
- GRPC Client in Java[^7]
- gRPC Web
[^1]: https://protobuf.dev/programming-guides/proto3/
[^2]: https://protobuf.dev/getting-started/javatutorial/
[^3]: https://github.com/grpc/grpc-java
[^4]: https://grpc.io/docs/languages/java/basics/
[^5]: https://www.youtube.com/watch?v=2hjIn3kKXuo
[^6]: https://grpc.github.io/grpc-java/javadoc/io/grpc/stub/StreamObserver.html
[^7]: https://www.youtube.com/watch?v=eUu29SrGYTA
[^8]: https://github.com/grpc/grpc-web
