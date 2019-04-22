package br.com.b2w.starwarsapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomExceptionResponse {

    private static final String TIMESTAMP = "yyyy-MM-dd hh:mm:ss";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIMESTAMP)
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

}
