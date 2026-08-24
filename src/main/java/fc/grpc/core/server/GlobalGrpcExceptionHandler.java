package fc.grpc.core.server;

import io.grpc.Status;

import org.springframework.grpc.server.advice.GrpcAdvice;
import org.springframework.grpc.server.advice.GrpcExceptionHandler;


@GrpcAdvice
public class GlobalGrpcExceptionHandler {

    @GrpcExceptionHandler(IllegalArgumentException.class)
    public Status handleIllegalArgumentException(IllegalArgumentException ex) {
        return Status.INVALID_ARGUMENT
                .withDescription("请求参数不合法: " + ex.getMessage())
                .withCause(ex);
    }

    @GrpcExceptionHandler(Exception.class)
    public Status handleUnknownException(Exception ex) {
        return Status.INTERNAL
                .withDescription("系统内部错误，请联系管理员");
    }
}