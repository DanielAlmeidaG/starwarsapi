package br.com.b2w.starwarsapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<CustomExceptionResponse> customHandleInternalServerError(Exception ex) {
        return new ResponseEntity<>(getCustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomExceptionResponse> customHandleIllegalArgumentException(Exception ex) {
        return new ResponseEntity<>(getCustomExceptionResponse(HttpStatus.BAD_REQUEST, ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomExceptionResponse> customHandleBadRequestException(Exception ex) {
        return new ResponseEntity<>(getCustomExceptionResponse(HttpStatus.BAD_REQUEST, ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<CustomExceptionResponse> customHandleHttpServerErrorException(Exception ex) {
        return new ResponseEntity<>(getCustomExceptionResponse(HttpStatus.SERVICE_UNAVAILABLE, ex), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(PlanetNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> customHandlePlanetNotFound(Exception ex) {
        return new ResponseEntity<>(getCustomExceptionResponse(HttpStatus.NOT_FOUND, ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanetAlreadyInsertedException.class)
    public ResponseEntity<CustomExceptionResponse> customHandlePlanetAlreadyInserted(Exception ex) {
        return new ResponseEntity<>(getCustomExceptionResponse(HttpStatus.CONFLICT, ex), HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("message", status.name());
        body.put("errors", getErrors(ex));

        return new ResponseEntity<>(body, headers, status);

    }

    private List<String> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
    }

    private CustomExceptionResponse getCustomExceptionResponse(HttpStatus status, Exception ex) {
        return new CustomExceptionResponse(LocalDateTime.now(), status.value(), status.name(), ex.getMessage());
    }
}
