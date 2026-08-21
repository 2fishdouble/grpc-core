package fc.grpc.core.external;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * 模拟「外部」CalculatorService
 * 独立启动在 9091 端口，与本项目的 Server (9090) 完全分离
 * 手动运行此类即可启动外部服务
 */
public class CalculatorServerMain {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(9091)
                .addService(new CalculatorImpl())
                .build()
                .start();

        System.out.println("=== 外部 CalculatorService 已启动，监听 9091 ===");
        server.awaitTermination();
    }

    static class CalculatorImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
        @Override
        public void add(CalcRequest request, StreamObserver<CalcReply> responseObserver) {
            int result = request.getA() + request.getB();
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
            CalcReply reply = CalcReply.newBuilder()
                    .setResult(result)
                    .setExpression(request.getA() + " * " + request.getB() + " = " + result)
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
