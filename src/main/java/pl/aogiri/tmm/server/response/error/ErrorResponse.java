package pl.aogiri.tmm.server.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ErrorResponse {
    int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime timestamp;
    String message;

    private ErrorResponse(){
        timestamp = LocalDateTime.now();
    }

    public  ErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status.value();
        this.message = message;
    }

}
