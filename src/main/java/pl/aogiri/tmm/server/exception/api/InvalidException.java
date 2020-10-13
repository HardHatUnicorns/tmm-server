package pl.aogiri.tmm.server.exception.api;

import pl.aogiri.tmm.server.util.enums.Message;

import static java.lang.String.format;

public class InvalidException extends ApiException {
    public InvalidException(String fieldName) {
        super(format(Message.IS_INVALID.getMessage(), fieldName));
    }
}
