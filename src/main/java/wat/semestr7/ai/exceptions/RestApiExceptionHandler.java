package wat.semestr7.ai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wat.semestr7.ai.exceptions.customexceptions.ConcertAlreadyApprovedException;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.exceptions.customexceptions.PaymentTimeoutException;
import wat.semestr7.ai.exceptions.customexceptions.WrongEntityInRequestBodyException;

import java.text.ParseException;

@ControllerAdvice(basePackages = {"wat.semestr7.ai.controllers","wat.semestr7.ai.security"})
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(RestApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class, PaymentTimeoutException.class})
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

    @ExceptionHandler({IllegalArgumentException.class, ParseException.class,WrongEntityInRequestBodyException.class, ConcertAlreadyApprovedException.class})
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

    @ExceptionHandler(java.net.UnknownHostException.class)
    protected ResponseEntity<Object> handleUnknownHostException(Exception ex)
    {
        RestApiError error = RestApiError.builder()
                .message("Can't connect to paypal sandbox. Check your internet connection.")
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
