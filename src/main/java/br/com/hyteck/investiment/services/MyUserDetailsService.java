package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.repository.UserRepository;
import br.com.hyteck.investiment.users.MyUserDetails;
import br.com.hyteck.investiment.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService  extends AbstractService<User, String> implements UserDetailsService{
    public MyUserDetailsService(JpaRepository<User, String> repository) {
        super(repository);
    }

    @Override
    public UserRepository getRepository() {
        return (UserRepository) super.getRepository();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new MyUserDetails(getRepository().findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User Not Found with username: " + username)));
    }

    @Override
    public User validateSave(User user) {
        return user;
    }
}
