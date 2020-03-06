package pl.aogiri.tmm.server.entity.listener;

import pl.aogiri.tmm.server.entity.implementation.UserEntity;

import javax.persistence.PrePersist;

public class UserListener {

    @PrePersist
    public void prePersist(UserEntity entity){
            entity.setEnabled(false);
    }
}
