package pl.aogiri.tmm.server.exception.api.register;

import pl.aogiri.tmm.server.util.enums.Message;

public class LoginExistException extends RegisterException {

    public LoginExistException() {
        super(Message.LOGIN_EXIST.getMessage(), "login");
    }
}
