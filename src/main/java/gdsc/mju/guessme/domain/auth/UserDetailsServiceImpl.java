package gdsc.mju.guessme.domain.auth;

import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.response.BaseException;
import gdsc.mju.guessme.global.response.UserNotFoundException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UserNotFoundException {
        System.out.println("ID in loadUserByUsername = " + userId);
        User user = userRepository.findByUserId(userId)
            .orElseThrow(UserNotFoundException::new);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org
            .springframework
            .security
            .core
            .userdetails
            .User(user.getUserId(), user.getPassword(), grantedAuthorities);

    }
}
