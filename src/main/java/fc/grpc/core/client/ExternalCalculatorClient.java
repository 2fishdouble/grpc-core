package fc.grpc.core.client;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * Client 角色：调用外部远程 gRPC 服务（非本进程）
 * 连接地址见 application.yaml 中 grpc.client.external-calculator
 */
@Slf4j
@Service
public class ExternalCalculatorClient {

    @GrpcClient("external-calculator")
    private CalculatorServiceGrpc.CalculatorServiceBlockingStub stub;

    public CalcReply add(int a, int b) {
        CalcRequest request = CalcRequest.newBuilder().setA(a).setB(b).build();
        CalcReply reply = stub.add(request);
        log.info("远程 Calculator.Add({}, {}) = {}", a, b, reply.getResult());
        return reply;
    }

    public CalcReply multiply(int a, int b) {
        CalcRequest request = CalcRequest.newBuilder().setA(a).setB(b).build();
        CalcReply reply = stub.multiply(request);
        log.info("远程 Calculator.Multiply({}, {}) = {}", a, b, reply.getResult());
        return reply;
    }
}
