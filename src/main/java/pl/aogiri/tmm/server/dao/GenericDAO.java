package pl.aogiri.tmm.server.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.aogiri.tmm.server.entity.GenericEntity;


@NoRepositoryBean
public interface GenericDAO<T extends GenericEntity> extends CrudRepository<T, Long> {
}
