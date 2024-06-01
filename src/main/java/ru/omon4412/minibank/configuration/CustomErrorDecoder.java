package ru.omon4412.minibank.configuration;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;
import ru.omon4412.minibank.exception.ConflictException;

import java.io.IOException;

@Configuration
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = "";
        try {
            if (response.body() != null) {
                errorMessage = Util.toString(response.body().asReader());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return switch (response.status()) {
            case 409 -> new ConflictException(errorMessage);
            default -> new RuntimeException(errorMessage);
        };
    }
}
