package pl.aogiri.tmm.server.util.enums;

public enum Message {
    LOGIN_EXIST("Given login is already taken"),
    EMAIL_EXIST("Given email is already taken"),
    BAD_CREDENTIALS("Wrong credentials"),
    FIELD_REQUIRED("%s is required")
    ;
    private String message;

    Message(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
