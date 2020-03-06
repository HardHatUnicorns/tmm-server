package pl.aogiri.tmm.server.dao.implementation;

import org.springframework.stereotype.Repository;
import pl.aogiri.tmm.server.dao.GenericDAO;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;


@Repository
public interface RoleDAO extends GenericDAO<RoleEntity> {

    RoleEntity findByName(String name);

}
