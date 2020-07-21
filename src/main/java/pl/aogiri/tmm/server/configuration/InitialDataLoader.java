package pl.aogiri.tmm.server.configuration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.PrivilegeDAO;
import pl.aogiri.tmm.server.dao.implementation.RoleDAO;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.PrivilegeEntity;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.exception.PrivilegeCreationException;
import pl.aogiri.tmm.server.util.PasswordEncoder;
import pl.aogiri.tmm.server.util.enums.Privileges;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Component
@Transactional
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    static boolean alreadySetup = false;

    private PrivilegeDAO privilegeDAO;
    private RoleDAO roleDAO;
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataLoader(PrivilegeDAO privilegeDAO, RoleDAO roleDAO, UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.privilegeDAO = privilegeDAO;
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Profile("dev")
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;
        if (alreadySetup)
            return;

        RoleEntity adminRole = createAdminRole(createAllPrivileges());

        UserEntity user = new UserEntity();
        user.setLogin("root");
        user.setPassword(passwordEncoder.encode("root"));
        user.setRoles(Collections.singletonList(adminRole));
        user.setEmail("root@root.pl");
        userDAO.save(user);

        alreadySetup = true;
    }

    @Transactional
    public RoleEntity createAdminRole(Collection<PrivilegeEntity> privilegeEntities) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_ADMIN");
        roleEntity.setPrivileges(privilegeEntities);
        roleDAO.save(roleEntity);
        return roleEntity;
    }

    @Transactional
    public Collection<PrivilegeEntity> createAllPrivileges() throws PrivilegeCreationException {
        Collection<PrivilegeEntity> privilegeEntities = new HashSet<>();
        for (Privileges privilege : Privileges.values()) {
            PrivilegeEntity privilegeEntity = new PrivilegeEntity();
            privilegeEntity.setName(privilege.name());
            privilegeDAO.save(privilegeEntity);
            if(privilegeEntity.getId() == null){
                throw new PrivilegeCreationException(privilege.name());
            }
            privilegeEntities.add(privilegeEntity);
        }
        return privilegeEntities;
    }

}
