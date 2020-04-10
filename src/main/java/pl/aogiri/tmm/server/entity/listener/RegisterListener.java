package pl.aogiri.tmm.server.entity.listener;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.event.OnRegisterCompleteEvent;
import pl.aogiri.tmm.server.service.implementation.RegisterService;

import javax.persistence.PrePersist;
import java.util.UUID;

@Component
@NoArgsConstructor
public class RegisterListener implements ApplicationListener<OnRegisterCompleteEvent> {

    private static RegisterService registerService;

    private static JavaMailSender sender;

    @Autowired
    public RegisterListener(RegisterService registerService, JavaMailSender javaMailSender) {
        RegisterListener.registerService = registerService;
        RegisterListener.sender = javaMailSender;
    }

    @PrePersist
    public void onPrePersist(UserEntity entity){
            entity.setEnabled(false);
    }

    @Override
    public void onApplicationEvent(OnRegisterCompleteEvent onRegisterCompleteEvent) {
        this.onRegisterCreateToken(onRegisterCompleteEvent);
    }

    private void onRegisterCreateToken(OnRegisterCompleteEvent event){
        UserEntity entity = event.getUserEntity();

        String token = UUID.randomUUID().toString();
        registerService.createToken(entity, token);
        String recipientAddress = entity.getEmail();
        String subject = "Registration Confirmation";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("This is your activation token : " + UUID.randomUUID().toString());
        sender.send(email);
    }
}
