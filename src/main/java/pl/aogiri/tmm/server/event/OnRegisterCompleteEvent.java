package pl.aogiri.tmm.server.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class OnRegisterCompleteEvent extends ApplicationEvent {
    private UserEntity userEntity;

    public OnRegisterCompleteEvent(UserEntity userEntity) {
        super(userEntity);
        this.userEntity = userEntity;
    }
}
