package pl.aogiri.tmm.server.exception.api.register;

import pl.aogiri.tmm.server.util.enums.Message;

public class EmailExistException extends RegisterException {
    public EmailExistException() {
        super(Message.EMAIL_EXIST.getMessage(), "email");
    }
}
