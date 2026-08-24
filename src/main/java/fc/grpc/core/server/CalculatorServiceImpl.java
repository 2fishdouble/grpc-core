package fc.grpc.core.server;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import org.springframework.grpc.server.NettyGrpcServerFactory;
import org.springframework.grpc.server.exception.GrpcExceptionHandlerInterceptor;
import org.springframework.grpc.server.lifecycle.GrpcServerLifecycle;
import org.springframework.grpc.server.service.GrpcService;

/**
 * @see GrpcServerAutoConfiguration
 * @see GrpcServerLifecycle
 * @see GrpcExceptionHandlerInterceptor
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
