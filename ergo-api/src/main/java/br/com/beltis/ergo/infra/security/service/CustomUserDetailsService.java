package br.com.beltis.ergo.infra.security.service;

import br.com.beltis.ergo.domain.model.ApplicationUser;
import br.com.beltis.ergo.domain.model.Usuario;
import br.com.beltis.ergo.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser users = this.applicationUserRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(users.getLogin(), users.getPassword(), new ArrayList<>());
    }

}
