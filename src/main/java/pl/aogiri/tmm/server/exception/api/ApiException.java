package pl.aogiri.tmm.server.exception.api;


public abstract class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
