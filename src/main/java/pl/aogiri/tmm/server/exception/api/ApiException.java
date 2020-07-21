package pl.aogiri.tmm.server.exception.api;


import pl.aogiri.tmm.server.exception.GenericException;

public abstract class ApiException extends GenericException {

    public ApiException(String message) {
        super(message);
    }
}
