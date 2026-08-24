package fc.grpc.core;

import fc.grpc.proto.CalculatorServiceGrpc;
import io.grpc.internal.ManagedClientTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.grpc.client.GrpcClientFactory;
import org.springframework.grpc.client.ImportGrpcClients;

/**
 * @see GrpcClientFactory
 * @see ManagedClientTransport
 */
@SpringBootApplication
@ImportGrpcClients(target = "calculator",
        types = CalculatorServiceGrpc.CalculatorServiceBlockingStub.class)
public class GrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcApplication.class, args);
    }

}
