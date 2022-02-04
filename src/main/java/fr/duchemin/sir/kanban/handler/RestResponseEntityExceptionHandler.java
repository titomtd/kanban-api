package fr.duchemin.sir.kanban.handler;

import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.message.Response;
import fr.duchemin.sir.kanban.message.ResponseMessageType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleAllExceptions(Exception ex, WebRequest request) {
        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessageType.SERVER_ERROR.toString());
        response.addDetail("error", ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Response> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        Response response = new Response(HttpStatus.NOT_FOUND, ResponseMessageType.ENTITY_NOT_FOUND.toString());
        response.addDetail("error", ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    public final ResponseEntity<Response> handleInternalServerException(InternalServerException ex, WebRequest request) {
        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessageType.SERVER_ERROR.toString());
        response.addDetail("error", ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response(status, ResponseMessageType.ARGUMENT_NOT_VALID.toString());
        BindingResult results = ex.getBindingResult();

        for (FieldError e : results.getFieldErrors()) {
            response.addDetail(e.getField(), e.getDefaultMessage());
        }

        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response(status, ResponseMessageType.METHOD_NOT_ALLOWED.toString());
        response.addDetail("error", ex.getLocalizedMessage());

        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response(status, ResponseMessageType.MEDIA_TYPE_NOT_SUPPORTED.toString());
        response.addDetail("error", ex.getLocalizedMessage());

        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response(status, ResponseMessageType.BAD_REQUEST.toString());
        response.addDetail("error", ex.getLocalizedMessage());

        return new ResponseEntity<>(response, status);
    }
}
