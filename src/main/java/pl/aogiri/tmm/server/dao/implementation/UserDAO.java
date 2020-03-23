package pl.aogiri.tmm.server.dao.implementation;

import org.springframework.stereotype.Repository;
import pl.aogiri.tmm.server.dao.GenericDAO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;

import java.util.Optional;


@Repository
public interface UserDAO extends GenericDAO<UserEntity> {

    long countByLogin(String login);

    long countByEmail(String var);

    Optional<UserEntity> findByLogin(String login);

}
