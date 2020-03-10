package pl.aogiri.tmm.server.exception.api.register;

import pl.aogiri.tmm.server.util.enums.Message;

import static java.lang.String.format;

public class FieldRequiredException extends RegisterException {
    public FieldRequiredException(String fieldName) {
        super(format(Message.FIELD_REQUIRED.getMessage(), fieldName), fieldName);
    }
}
