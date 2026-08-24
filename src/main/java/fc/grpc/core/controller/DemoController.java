package fc.grpc.core.controller;

import fc.grpc.core.client.ExternalCalculatorClient;
import fc.grpc.proto.CalcReply;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST 控制器，演示同一进程同时扮演 Client 与 Server 两种角色
 */
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final ExternalCalculatorClient calculatorClient;

    @GetMapping("/client/add")
    public Map<String, Object> add(@RequestParam(defaultValue = "10") int a,
                                   @RequestParam(defaultValue = "20") int b) {
        CalcReply reply = calculatorClient.add(a, b);
        return Map.of("role", "CLIENT -> CalculatorService",
                      "operation", "add",
                      "a", a, "b", b,
                      "result", reply.getResult(),
                      "expression", reply.getExpression());
    }

    @GetMapping("/client/multiply")
    public Map<String, Object> multiply(@RequestParam(defaultValue = "6") int a,
                                        @RequestParam(defaultValue = "7") int b) {
        CalcReply reply = calculatorClient.multiply(a, b);
        return Map.of("role", "CLIENT -> CalculatorService",
                      "operation", "multiply",
                      "a", a, "b", b,
                      "result", reply.getResult(),
                      "expression", reply.getExpression());
    }

    @GetMapping("/server/info")
    public Map<String, Object> serverInfo() {
        return Map.of("role", "SERVER",
                      "services", new String[]{"GreeterService", "CalculatorService"},
                      "port", 9090,
                      "note", "本进程同时作为 Server 与 Client：对外提供 Greeter/Calculator 服务，/client/* 接口则以 Client 角色经 gRPC 调用 CalculatorService");
    }
}
