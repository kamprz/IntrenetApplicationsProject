package wat.semestr7.ai.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.ParseException;

@ControllerAdvice(basePackages = {"wat.semestr7.ai.controllers","wat.semestr7.ai.security"})
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(RestApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex)
    {
        RestApiError apiError =  RestApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .debugMessage(ex.toString())
                .build()
                .setNow();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({IllegalArgumentException.class, ParseException.class})
    protected ResponseEntity<Object> handleIllegalArgument(Exception ex)
    {
        RestApiError error = RestApiError.builder()
                .message(ex.getMessage())
                .debugMessage(ex.toString())
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .build()
                .setNow();
        return buildResponseEntity(error);
    }

/*
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFound(Exception ex)
    {
        RestApiError error = RestApiError.builder()
                .message("Unauthorized access")
                .debugMessage(ex.toString())
                .status(HttpStatus.UNAUTHORIZED)
                .build()
                .setNow();
        return buildResponseEntity(error);
    }
*/

    /*
    pojawiające się wyjątki:
    - SocketTimeoutException : brak internetu/ultra wolny, za długo idzie czekać
    - IOException - gdy coś nawali z biletem
    - UnknownHostException - próba rozpoczęcia płatności gdy nie ma internetu
     */
}
