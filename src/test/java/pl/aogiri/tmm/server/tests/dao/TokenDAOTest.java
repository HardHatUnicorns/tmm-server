package pl.aogiri.tmm.server.tests.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.aogiri.tmm.server.dao.implementation.TokenDAO;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.TokenEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Token Database Access Object tests")
public class TokenDAOTest extends BaseDAOTest{

    static TokenDAO tokenDAO;

    @BeforeAll
    static void setUp(@Autowired TokenDAO tokenDAO, @Autowired UserDAO userDAO){
        TokenDAOTest.tokenDAO = tokenDAO;
        tokenDAO.save(TokenEntity.builder()
                .token("token-exist")
                .build());
    }


    @Test
    public void shouldFindByToken(){
        Optional<TokenEntity> actual = tokenDAO.findByToken("token-exist");
        assertTrue(actual.isPresent());
    }

    @Test
    public void shouldNotFindByToken(){
        Optional<TokenEntity> actual = tokenDAO.findByToken("token-not-exist");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldSetExpiryDateOnSave(){
        String tokenName = "token-expiry-date";
        tokenDAO.save(TokenEntity.builder().token(tokenName).build());

        Optional<TokenEntity> actual = tokenDAO.findByToken(tokenName);
        assertTrue(actual.isPresent());
        assertNotNull(actual.get().getExpiryDate());
        assertTrue(actual.get().getExpiryDate().isAfter(LocalDateTime.now()));
    }

}
