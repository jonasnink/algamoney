package br.mp.mpro.algamoney.service.custom;

import br.mp.mpro.algamoney.domain.User;
import br.mp.mpro.algamoney.repository.AuthorityRepository;
import br.mp.mpro.algamoney.repository.UserRepository;
import br.mp.mpro.algamoney.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class UserServiceCustom extends UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceCustom.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final CacheManager cacheManager;

    public UserServiceCustom(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        super(userRepository, passwordEncoder, authorityRepository, cacheManager);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public List<User> GetUsuarios(){
        return userRepository.findAll();
    }





    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }
}
