package pl.aogiri.tmm.server.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.dto.implementation.UserDTO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.service.GenericService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements GenericService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public Optional<UserDTO> findById(Long id){
        return userDAO.findById(id).map(UserEntity::toDTO);
    }

    public Collection<UserDTO> findAll(){
        return StreamSupport.stream(userDAO.findAll().spliterator(), true)
                        .map(UserEntity::toDTO)
                        .collect(Collectors.toSet());
    }

}
