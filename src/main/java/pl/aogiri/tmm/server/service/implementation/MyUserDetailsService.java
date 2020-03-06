package pl.aogiri.tmm.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import pl.aogiri.tmm.server.dao.implementation.UserDAO;
import pl.aogiri.tmm.server.entity.implementation.PrivilegeEntity;
import pl.aogiri.tmm.server.entity.implementation.RoleEntity;
import pl.aogiri.tmm.server.entity.implementation.UserEntity;
import pl.aogiri.tmm.server.util.enums.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService() {
        return new UserService();
    }

    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException {
        UserEntity user = userDAO.findByLogin(var1).orElseThrow(() -> new UsernameNotFoundException(Message.BAD_CREDENTIALS.getMessage()));

        return new User(
                user.getLogin(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    public static List<GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {

        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();
        for (RoleEntity role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
}

