package fc.grpc.core.server;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

/**
 * CalculatorService 的 Spring Boot 托管实现
 * 与 GreeterService 一样由 spring-boot-starter-grpc-server 注册，监听 9090
 */
@Slf4j
@GrpcService
public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void add(CalcRequest request, StreamObserver<CalcReply> responseObserver) {
        int result = request.getA() + request.getB();
        log.info("Calculator.Add({}, {}) = {}", request.getA(), request.getB(), result);
        CalcReply reply = CalcReply.newBuilder()
                .setResult(result)
                .setExpression(request.getA() + " + " + request.getB() + " = " + result)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void multiply(CalcRequest request, StreamObserver<CalcReply> responseObserver) {
        int result = request.getA() * request.getB();
        log.info("Calculator.Multiply({}, {}) = {}", request.getA(), request.getB(), result);
        CalcReply reply = CalcReply.newBuilder()
                .setResult(result)
                .setExpression(request.getA() + " * " + request.getB() + " = " + result)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
