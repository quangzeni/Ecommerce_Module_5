package ra.advice;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> invalidRequest(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        fieldErrorList.forEach(fieldError -> errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
        return errors;
    }
}
