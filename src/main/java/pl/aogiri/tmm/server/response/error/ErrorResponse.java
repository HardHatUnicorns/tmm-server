package pl.aogiri.tmm.server.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import pl.aogiri.tmm.server.response.error.sub.SubError;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ErrorResponse {
    int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime timestamp;
    Collection<SubError> errors;

    private ErrorResponse(){
        timestamp = LocalDateTime.now();
    }

    public  ErrorResponse(HttpStatus status, Collection<SubError> errors) {
        this();
        this.status = status.value();
        this.errors = errors;
    }

}
