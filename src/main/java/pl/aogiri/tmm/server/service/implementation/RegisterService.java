package pl.aogiri.tmm.server.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.TokenDAO;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.implementation.TokenEntity;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.event.OnRegisterCompleteEvent;
import pl.aogiri.tmm.server.exception.api.register.EmailExistException;
import pl.aogiri.tmm.server.exception.api.register.FieldRequiredException;
import pl.aogiri.tmm.server.exception.api.register.LoginExistException;
import pl.aogiri.tmm.server.exception.api.register.RegisterException;
import pl.aogiri.tmm.server.exception.api.token.ActivationFailedException;
import pl.aogiri.tmm.server.service.GenericService;
import pl.aogiri.tmm.server.util.PasswordEncoder;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService implements GenericService {

    private UserDAO userDAO;
    private TokenDAO tokenDAO;
    private PasswordEncoder passwordEncoder;
    private ApplicationEventPublisher eventPublisher;


    @Autowired
    public RegisterService(UserDAO userDAO, PasswordEncoder passwordEncoder, TokenDAO tokenDAO, ApplicationEventPublisher eventPublisher){
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.tokenDAO = tokenDAO;
        this.eventPublisher = eventPublisher;
    }


    public Optional<UserDTO> registerUser(UserDTO userDTO) throws RegisterException {
        UserEntity userEntity = userDTO.toEntity();

        checkRequiredFields(userDTO);

        userEntity.setPassword(passwordEncoder.encode(userDTO.getPlainPassword()));

        if(!isLoginFree(userEntity.getLogin()))
            throw new LoginExistException();
        if(!isEmailFree(userEntity.getEmail()))
            throw new EmailExistException();

        userDAO.save(userEntity);

        eventPublisher.publishEvent(new OnRegisterCompleteEvent(userEntity));

        return Optional.of(userEntity.toDTO());
    }

    private boolean isLoginFree(String login){
        return userDAO.countByLogin(login) == 0;
    }

    private boolean isEmailFree(String email){
        return userDAO.countByEmail(email) == 0;
    }

    private void checkRequiredFields(UserDTO dto) throws FieldRequiredException{
        if(dto.getLogin() == null || dto.getLogin().isBlank())
            throw new FieldRequiredException("Login");

        if(dto.getEmail() == null || dto.getEmail().isBlank())
            throw new FieldRequiredException("Email");

        if(dto.getPlainPassword() == null || dto.getPlainPassword().isBlank())
            throw new FieldRequiredException("Password");
    }

    public Optional<TokenEntity> createToken(UserEntity userEntity, String token){
        TokenEntity registerToken = TokenEntity.builder().token(token).user(userEntity).build();
        tokenDAO.save(registerToken);
        return Optional.of(registerToken);
    }

    public void activateAccountByTokenAndEmail(String token, String email) {
        Optional<TokenEntity> tokenEntity = tokenDAO.findByToken(token);
        if(tokenEntity.isPresent() && tokenEntity.get().getUser().getEmail().equals(email)){
            tokenEntity.get().getUser().setEnabled(true);
        }else{
            throw new ActivationFailedException();
        }
    }

}
