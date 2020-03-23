package pl.aogiri.tmm.server.exception.api.register;

import pl.aogiri.tmm.server.exception.api.ApiException;

public abstract class RegisterException extends ApiException {
    private String field;
    public RegisterException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
