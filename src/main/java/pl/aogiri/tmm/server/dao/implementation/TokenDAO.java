package pl.aogiri.tmm.server.dao.implementation;

import org.springframework.stereotype.Repository;
import pl.aogiri.tmm.server.dao.GenericDAO;
import pl.aogiri.tmm.server.entity.implementation.TokenEntity;


@Repository
public interface TokenDAO extends GenericDAO<TokenEntity> {

    TokenEntity findByToken(String token);
}
