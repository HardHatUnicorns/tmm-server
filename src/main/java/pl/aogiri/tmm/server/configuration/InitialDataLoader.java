package pl.aogiri.tmm.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.PrivilegeDAO;
import pl.aogiri.tmm.server.dao.implementation.RoleDAO;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.util.PasswordEncoder;

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
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;

        UserEntity user = new UserEntity();
        user.setLogin("root");
        user.setPassword(passwordEncoder.encode("root"));
        user.setEmail("root@root.pl");
        userDAO.save(user);
        user.setEnabled(true);
        userDAO.save(user);

        alreadySetup = true;
    }

}
