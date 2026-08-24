package fc.grpc.core;

import fc.grpc.proto.CalculatorServiceGrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.grpc.client.ImportGrpcClients;

@SpringBootApplication
@ImportGrpcClients(target = "external-calculator",
        types = CalculatorServiceGrpc.CalculatorServiceBlockingStub.class)
public class GrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcApplication.class, args);
    }

}
