package pl.aogiri.tmm.server.exception.api.token;

import pl.aogiri.tmm.server.exception.api.ApiException;
import pl.aogiri.tmm.server.util.enums.Message;

public class ActivationFailedException extends ApiException {
    public ActivationFailedException() {
        super(Message.ACTIVATION_FAILED.getMessage());
    }
}
