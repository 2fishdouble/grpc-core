package fc.grpc.core.server;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import org.springframework.grpc.server.NettyGrpcServerFactory;
import org.springframework.grpc.server.advice.GrpcAdviceExceptionHandler;
import org.springframework.grpc.server.exception.GrpcExceptionHandlerInterceptor;
import org.springframework.grpc.server.lifecycle.GrpcServerLifecycle;
import org.springframework.grpc.server.service.GrpcService;

/**
 * @see GrpcServerAutoConfiguration
 * @see GrpcServerLifecycle
 * @see GrpcExceptionHandlerInterceptor
 * @see GrpcAdviceExceptionHandler
 */
@Slf4j
@GrpcService
public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void add(CalcRequest request, StreamObserver<CalcReply> responseObserver) {
        if (request.getA() < 0) {
            throw new IllegalArgumentException("Negative numbers are not allowed");
        }
        if (request.getB() < 0) {
            throw new UnsupportedOperationException("Negative numbers are not allowed");
        }
        int result = request.getA() + request.getB();
        log.info("Server Calculator.Add({}, {}) = {}", request.getA(), request.getB(), result);
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
        log.info("Server Calculator.Multiply({}, {}) = {}", request.getA(), request.getB(), result);
        CalcReply reply = CalcReply.newBuilder()
                .setResult(result)
                .setExpression(request.getA() + " * " + request.getB() + " = " + result)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
