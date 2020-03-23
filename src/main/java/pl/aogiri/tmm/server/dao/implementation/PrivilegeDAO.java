package pl.aogiri.tmm.server.dao.implementation;

import org.springframework.stereotype.Repository;
import pl.aogiri.tmm.server.dao.GenericDAO;
import pl.aogiri.tmm.server.entity.implementation.PrivilegeEntity;


@Repository
public interface PrivilegeDAO extends GenericDAO<PrivilegeEntity> {

    PrivilegeEntity findByName(String name);

}
