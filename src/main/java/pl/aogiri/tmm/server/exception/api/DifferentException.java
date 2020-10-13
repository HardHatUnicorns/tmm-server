package pl.aogiri.tmm.server.exception.api;

import pl.aogiri.tmm.server.util.enums.Message;

import static java.lang.String.format;

public class DifferentException extends ApiException {
    public DifferentException(String fieldName) {
        super(format(Message.ARE_DIFFERENT.getMessage(), fieldName));
    }
}
