package fc.grpc.core.client;

import fc.grpc.proto.CalcReply;
import fc.grpc.proto.CalcRequest;
import fc.grpc.proto.CalculatorServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Client 角色：通过 gRPC 调用 CalculatorService
 * 演示时指向本进程自建的 Server，连接地址见 application.yaml 中 spring.grpc.client.channel.calculator
 * stub 由 GrpcApplication 上的 @ImportGrpcClients 注册为 Bean
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCalculatorClient {

    private final CalculatorServiceGrpc.CalculatorServiceBlockingStub stub;

    public CalcReply add(int a, int b) {
        CalcRequest request = CalcRequest.newBuilder().setA(a).setB(b).build();
        return stub.add(request);
    }

    public CalcReply multiply(int a, int b) {
        CalcRequest request = CalcRequest.newBuilder().setA(a).setB(b).build();
        return stub.multiply(request);
    }
}
