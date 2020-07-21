package pl.aogiri.tmm.server.exception;

import java.util.logging.Logger;

public abstract class GenericException extends RuntimeException{
    private Logger logger = Logger.getLogger(GenericException.class.getCanonicalName());

    public GenericException(String message) {
        super(message);
        logger.severe(message);
    }
}
