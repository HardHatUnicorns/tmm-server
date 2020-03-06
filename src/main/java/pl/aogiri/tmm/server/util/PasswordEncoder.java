package pl.aogiri.tmm.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PasswordEncoder {

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String encode(CharSequence rawPassword){
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
