package kr.ac.hansung.cse.hellospringsecurity2.service;

import kr.ac.hansung.cse.hellospringsecurity2.entity.Role;
import kr.ac.hansung.cse.hellospringsecurity2.entity.User;

import java.util.List;

public interface RegistrationService {
    User createUser(User user, List<Role> userRoles); //db에 user 넣기

    boolean checkEmailExists(String email);

    Role findByRolename(String rolename);
}