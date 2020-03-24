package pl.aogiri.tmm.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.PrivilegeDAO;
import pl.aogiri.tmm.server.dao.implementation.RoleDAO;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.PrivilegeEntity;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;
import pl.aogiri.tmm.server.util.PasswordEncoder;

import java.util.Collection;

@Component
@Transactional
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        if (alreadySetup)
//            return;
//        PrivilegeEntity readPrivilege
//                = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");
//
//        List<PrivilegeEntity> adminPrivileges = Collections.singletonList(
//                readPrivilege);
//        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//
//        RoleEntity adminRole = roleDAO.findByName("ROLE_ADMIN");
//        UserEntity user = new UserEntity();
//        user.setLogin("root");
//        user.setPassword(passwordEncoder.encode("root"));
//        user.setRoles(Collections.singletonList(adminRole));
//        user.setEmail("root@root.pl");
//        userDAO.save(user);
//
//        alreadySetup = true;
    }



    @Transactional
    public PrivilegeEntity createPrivilegeIfNotFound(String name) {

        PrivilegeEntity privilege = privilegeDAO.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity();
            privilege.setName(name);
            privilegeDAO.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public RoleEntity createRoleIfNotFound(
            String name, Collection<PrivilegeEntity> privileges) {

        RoleEntity role = roleDAO.findByName(name);
        if (role == null) {
            role = new RoleEntity();
            role.setName(name);
            role.setPrivileges(privileges);
            roleDAO.save(role);
        }
        return role;
    }
}
