package pl.aogiri.tmm.server.entity.listener;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.service.implementation.UserService;

import javax.persistence.PrePersist;
import java.util.UUID;

@Component
@NoArgsConstructor
public class UserListener {

    private static UserService userService;

    @Autowired
    public UserListener(UserService userService) {
        UserListener.userService = userService;
    }

    @PrePersist
    public void onPrePersist(UserEntity entity){
            entity.setEnabled(false);
            onRegisterCreateToken(entity);
    }

    private void onRegisterCreateToken(UserEntity entity){
        userService.createToken(entity, UUID.randomUUID().toString());
    }
}
