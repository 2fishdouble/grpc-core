package fc.grpc.core.server;

import fc.grpc.proto.GreeterServiceGrpc;
import fc.grpc.proto.HelloReply;
import fc.grpc.proto.HelloRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

import java.time.Instant;

@Slf4j
@GrpcService
public class GreeterServiceImpl extends GreeterServiceGrpc.GreeterServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.info("Server Unary SayHello: name={}, message={}", request.getName(), request.getMessage());
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello " + request.getName() + "! Your message: " + request.getMessage())
                .setTimestamp(Instant.now().toEpochMilli())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void sayHelloServerStream(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.info("Server ServerStream SayHello: name={}, message={}", request.getName(), request.getMessage());
        for (int i = 0; i < 5; i++) {
            HelloReply reply = HelloReply.newBuilder()
                    .setMessage("Hello " + request.getName() + "! [stream #" + (i + 1) + "] " + request.getMessage())
                    .setTimestamp(Instant.now().toEpochMilli())
                    .build();
            responseObserver.onNext(reply);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloClientStream(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<>() {
            private final StringBuilder allMessages = new StringBuilder();
            private String clientName = "";

            @Override
            public void onNext(HelloRequest request) {
                log.info("Server ClientStream onNext: name={}, message={}", request.getName(), request.getMessage());
                clientName = request.getName();
                allMessages.append(request.getMessage()).append("; ");
            }

            @Override
            public void onError(Throwable t) {
                log.error("ClientStream error", t);
            }

            @Override
            public void onCompleted() {
                log.info("Server ClientStream completed, collected {} messages", allMessages.length());
                HelloReply reply = HelloReply.newBuilder()
                        .setMessage("Hello " + clientName + "! Collected messages: " + allMessages)
                        .setTimestamp(Instant.now().toEpochMilli())
                        .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloBidiStream(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloRequest request) {
                log.info("Server BidiStream onNext: name={}, message={}", request.getName(), request.getMessage());
                HelloReply reply = HelloReply.newBuilder()
                        .setMessage("Echo to " + request.getName() + ": " + request.getMessage())
                        .setTimestamp(Instant.now().toEpochMilli())
                        .build();
                responseObserver.onNext(reply);
            }

            @Override
            public void onError(Throwable t) {
                log.error("BidiStream error", t);
            }

            @Override
            public void onCompleted() {
                log.info("Server BidiStream completed");
                responseObserver.onCompleted();
            }
        };
    }
}
