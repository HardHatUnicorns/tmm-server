package pl.aogiri.tmm.server.exception.api.register;

import pl.aogiri.tmm.server.exception.api.ApiException;

public abstract class RegisterException extends ApiException {
    public RegisterException(String message) {
        super(message);
    }
}
