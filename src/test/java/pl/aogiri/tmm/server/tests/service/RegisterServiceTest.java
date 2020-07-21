package pl.aogiri.tmm.server.tests.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import pl.aogiri.tmm.server.dao.implementation.TokenDAO;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.implementation.TokenEntity;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.exception.api.register.EmailExistException;
import pl.aogiri.tmm.server.exception.api.register.FieldRequiredException;
import pl.aogiri.tmm.server.exception.api.register.LoginExistException;
import pl.aogiri.tmm.server.exception.api.token.ActivationFailedException;
import pl.aogiri.tmm.server.service.implementation.RegisterService;
import pl.aogiri.tmm.server.util.PasswordEncoder;
import pl.aogiri.tmm.server.utils.ReplaceCamelCase;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("User Service")
@DisplayNameGeneration(value = ReplaceCamelCase.class)
@ExtendWith({MockitoExtension.class})
public class RegisterServiceTest {

    @Mock
    private static UserDAO userDAO;

    @Mock
    private static TokenDAO tokenDAO;

    @Mock
    private static PasswordEncoder encoder;

    @Mock
    private static ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private static RegisterService registerService;

    @BeforeAll
    public static void setUp() {
        registerService = new RegisterService(userDAO, encoder, tokenDAO, eventPublisher);
    }

    @Test
    void shouldRegisterNewUser() {
        UserEntity userExpected = UserEntity.builder().id(2L).login("user").email("user@tmm.com").enabled(false).roles(Collections.emptySet()).build();
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword("456");
        when(userDAO.save(any(UserEntity.class))).then(ans -> {
            UserEntity tmp = ans.getArgument(0, UserEntity.class);
            tmp.setId(2L);
            tmp.setRoles(Collections.emptySet());
            tmp.setEnabled(false);
            return tmp;
        });

        Optional<UserDTO> userActual = registerService.registerUser(userFromUi);
        assertFalse(userActual.isEmpty());
        UserDTO userActualDTO = userActual.get();
        assertEquals(userExpected.toDTO(), userActualDTO);
    }

    @Test
    void shouldReturnResponseAboutLoginExist() {
        UserDTO userToAdd = UserEntity.builder().login("user").email("user@tmm.com").password("456").roles(Collections.emptySet()).build().toDTO();
        userToAdd.setPlainPassword("123");
        when(userDAO.countByLogin(anyString())).thenReturn(1L);
        when(encoder.encode(any())).thenReturn("Pa$$w0rd");

        assertThrows(LoginExistException.class, () -> registerService.registerUser(userToAdd));
    }

    @Test
    void shouldReturnResponseAboutEmailExist() {
        UserDTO userToAdd = UserEntity.builder().login("user").email("user@tmm.com").password("456").roles(Collections.emptySet()).build().toDTO();
        userToAdd.setPlainPassword("123");
        when(userDAO.countByEmail(anyString())).thenReturn(1L);
        when(encoder.encode(any())).thenReturn("Pa$$w0rd");

        assertThrows(EmailExistException.class, () -> registerService.registerUser(userToAdd));
    }

    @Test
    void shouldReturnResponseAboutPlainPasswordRequiredNull() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutLoginRequiredNull() {
        UserDTO userFromUi = UserEntity.builder().email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutEmailRequiredNull() {
        UserDTO userFromUi = UserEntity.builder().login("user").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutPlainPasswordRequiredBlank() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword(" ");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutLoginRequiredBlank() {
        UserDTO userFromUi = UserEntity.builder().email("user@tmm.com").login(" ").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutEmailRequiredBlank() {
        UserDTO userFromUi = UserEntity.builder().login("user").email(" ").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutPlainPasswordRequiredEmpty() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("user@tmm.com").build().toDTO();
        userFromUi.setPlainPassword("");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutLoginRequiredEmpty() {
        UserDTO userFromUi = UserEntity.builder().email("user@tmm.com").login("").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldReturnResponseAboutEmailRequiredEmpty() {
        UserDTO userFromUi = UserEntity.builder().login("user").email("").build().toDTO();
        userFromUi.setPlainPassword("123");

        assertThrows(FieldRequiredException.class, () -> registerService.registerUser(userFromUi));
    }

    @Test
    void shouldCreateNewToken() {
        when(tokenDAO.save(any(TokenEntity.class))).then(ans -> {
            TokenEntity tmp = ans.getArgument(0, TokenEntity.class);
            tmp.setId(1L);
            return tmp;
        });

        Optional<TokenEntity> tokenEntity = registerService.createToken(new UserEntity(), "token-exist");
        assertTrue(tokenEntity.isPresent());
        assertEquals(1L, tokenEntity.get().getId());
    }

    @Test
    void shouldActivateAccount() {
        UserEntity userEntity = UserEntity.builder()
                .email("activate@tmm.com")
                .build();
        when(tokenDAO.findByToken(anyString())).then(ans -> Optional.of(TokenEntity.builder()
                .token("token-exist")
                .user(userEntity)
                .build()));

        registerService.activateAccountByTokenAndEmail("token-exist", "activate@tmm.com");
        assertTrue(userEntity.getEnabled());
    }


    @Test
    void shouldThrowExceptionAboutTokenNotExist() {
        when(tokenDAO.findByToken(anyString())).then(ans -> Optional.empty());

        assertThrows(ActivationFailedException.class, () -> registerService.activateAccountByTokenAndEmail("token-exist", "activate@tmm.com"));
    }

    @Test
    void shouldThrowExceptionAboutIncorrectEmail() {
        UserEntity userEntity = UserEntity.builder()
                .email("activate@tmm.com")
                .build();
        when(tokenDAO.findByToken(anyString())).then(ans -> Optional.of(TokenEntity.builder()
                .token("token-exist")
                .user(userEntity)
                .build()));

        assertThrows(ActivationFailedException.class, () -> registerService.activateAccountByTokenAndEmail("token-exist", "invalid@tmm.com"));

    }


}
