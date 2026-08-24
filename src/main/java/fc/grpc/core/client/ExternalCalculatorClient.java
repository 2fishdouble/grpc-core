package fc.grpc.core.client;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Client 角色：调用外部远程 gRPC 服务（非本进程）
 * 连接地址见 application.yaml 中 spring.grpc.client.channel.external-calculator
 * stub 由 GrpcApplication 上的 @ImportGrpcClients 注册为 Bean
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCalculatorClient {

    private final CalculatorServiceGrpc.CalculatorServiceBlockingStub stub;

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
