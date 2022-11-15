package sit.int221.mydb.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
    //ver.1 ดูดีแต่ไม่ครบ
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders header, HttpStatus status, WebRequest request){
//        Map<String, List> errors = new HashMap<>();
////        List<Object> errors = new ArrayList<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            List<String> errorMessage2 = new ArrayList<>();
//            errorMessage2.add(errorMessage);
//            errors.put(fieldName, errorMessage2);
////            errors.add(fieldName);
////            errors.add(errorMessage);
//        });
//        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//    }

    //ver.4
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders header, HttpStatus status, WebRequest request){
        ErrorDetails errorDetail = new ErrorDetails(ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorDetail,HttpStatus.BAD_REQUEST);
    }
}
