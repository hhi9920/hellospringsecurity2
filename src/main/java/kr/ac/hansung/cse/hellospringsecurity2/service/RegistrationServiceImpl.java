package kr.ac.hansung.cse.hellospringsecurity2.service;

import kr.ac.hansung.cse.hellospringsecurity2.entity.Role;
import kr.ac.hansung.cse.hellospringsecurity2.entity.User;
import kr.ac.hansung.cse.hellospringsecurity2.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringsecurity2.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user, List<Role> userRoles) {
        for (Role ur : userRoles) {
            roleRepository.save(ur);    //role 저장장
        }

        // generate new Bcrypt hash
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);    //hasedpw를 저장

        user.setRoles(userRoles);

        User newUser = userRepository.save(user);

        return newUser;
    }

    public boolean checkEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

    public Role findByRolename(String rolename) {
        Optional<Role> role = roleRepository.findByRolename(rolename);
        return role.orElseGet(() -> new Role("ROLE_USER"));
    }

}