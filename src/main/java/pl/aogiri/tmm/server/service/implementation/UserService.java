package pl.aogiri.tmm.server.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.exception.api.register.EmailExistException;
import pl.aogiri.tmm.server.exception.api.register.FieldRequiredException;
import pl.aogiri.tmm.server.exception.api.register.LoginExistException;
import pl.aogiri.tmm.server.exception.api.register.RegisterException;
import pl.aogiri.tmm.server.service.GenericService;
import pl.aogiri.tmm.server.util.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements GenericService {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder){
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDTO> findById(Long id){
        return userDAO.findById(id).map(UserEntity::toDTO);
    }

    public Collection<UserDTO> findAll(){
        return StreamSupport.stream(userDAO.findAll().spliterator(), true)
                        .map(UserEntity::toDTO)
                        .collect(Collectors.toSet());
    }

    public Optional<UserDTO> createUser(UserDTO userDTO) throws RegisterException {
        UserEntity userEntity = userDTO.toEntity();

        checkRequiredFields(userDTO);

        userEntity.setPassword(passwordEncoder.encode(userDTO.getPlainPassword()));

        if(!isLoginFree(userEntity.getLogin()))
            throw new LoginExistException();
        if(!isEmailFree(userEntity.getEmail()))
            throw new EmailExistException();

        userDAO.save(userEntity);
        return Optional.of(userEntity.toDTO());

    }

    private boolean isLoginFree(String login){
        return userDAO.countByLogin(login) == 0;
    }

    private boolean isEmailFree(String email){
        return userDAO.countByEmail(email) == 0;
    }

    private void checkRequiredFields(UserDTO dto) throws FieldRequiredException{
        if(dto.getLogin() == null)
            throw new FieldRequiredException("Login");

        if(dto.getEmail() == null)
            throw new FieldRequiredException("Email");

        if(dto.getPlainPassword() == null)
            throw new FieldRequiredException("Password");
    }

}
