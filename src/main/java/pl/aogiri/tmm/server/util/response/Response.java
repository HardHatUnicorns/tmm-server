package pl.aogiri.tmm.server.util.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Response {
    private String message;
    private Date requested;
    private int status;

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                ", dateOfRequest=" + requested +
                ", status=" + status +
                '}';
    }


}
