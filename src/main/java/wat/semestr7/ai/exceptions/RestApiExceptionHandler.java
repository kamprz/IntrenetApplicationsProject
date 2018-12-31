package wat.semestr7.ai.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        RestApiError apiError = RestApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(error)
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(RestApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
