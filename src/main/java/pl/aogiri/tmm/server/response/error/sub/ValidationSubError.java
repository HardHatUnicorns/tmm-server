package pl.aogiri.tmm.server.response.error.sub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ValidationSubError extends SubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ValidationSubError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ValidationSubError(String message) {
        this.message = message;
    }
}