package pl.aogiri.tmm.server.tests.dao;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.aogiri.tmm.server.utils.ReplaceCamelCase;

@DisplayNameGeneration(ReplaceCamelCase.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith({MockitoExtension.class})
public abstract class BaseDAOTest {
}
