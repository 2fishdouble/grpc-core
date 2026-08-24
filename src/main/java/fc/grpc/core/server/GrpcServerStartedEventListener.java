package fc.grpc.core.server;

import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.grpc.server.lifecycle.GrpcServerLifecycle;
import org.springframework.stereotype.Component;
import org.springframework.grpc.server.lifecycle.GrpcServerStartedEvent;

@Component
@Slf4j
public class GrpcServerStartedEventListener implements ApplicationListener<GrpcServerStartedEvent> {

    @Override
    public void onApplicationEvent(GrpcServerStartedEvent event) {
        String address = event.getAddress();
        int port = event.getPort();
        Server server = event.getServer();
        GrpcServerLifecycle source = event.getSource();
        log.info("gRPC server started at {}:{} with port {}", address, port, server.getPort());
    }
}