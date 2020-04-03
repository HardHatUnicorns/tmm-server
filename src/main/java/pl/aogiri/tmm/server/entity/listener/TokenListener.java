package pl.aogiri.tmm.server.entity.listener;

import lombok.NoArgsConstructor;
import pl.aogiri.tmm.server.entity.implementation.TokenEntity;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
public class TokenListener {
    private static final int VALIDITY_TIME_HOURS = 24;

    @PrePersist
    public void onPrePersist(TokenEntity entity){
        entity.setExpiryDate(LocalDateTime.now().plus(VALIDITY_TIME_HOURS, ChronoUnit.HOURS));
    }

}
