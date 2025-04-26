package ru.solomka.product.user;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import ru.solomka.product.common.RestRequestServiceProvider;
import ru.solomka.product.common.WebRequestSender;
import ru.solomka.product.common.exception.ServiceRequestException;

import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserValidatorWebRequestAdapter implements WebRequestSender<Object, UUID> {

    @NonNull RestRequestServiceProvider restRequestServiceProvider;

    @Override
    public Object getResponse(Class<Object> responseType, UUID args) {
        ResponseEntity<?> userValidationResponse = restRequestServiceProvider.postWithQueryParam(
                "http://gateway:8080/identity/users/validate/%s".formatted(args),
                responseType, null
        );

        if(!userValidationResponse.getStatusCode().is2xxSuccessful())
            throw new ServiceRequestException("Cannot get response from identity-service: 'identity/users/validate'");

        return true;
    }
}