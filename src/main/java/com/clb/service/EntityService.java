package com.clb.service;

import com.clb.dao.AuthorityRepository;
import com.clb.dao.RoleRepository;
import com.clb.dao.UserRepository;
import com.clb.entity.Authority;
import com.clb.entity.Role;
import com.clb.entity.User1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntityService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional
    public void deleteUser(String userName) {
        List<User1> users = userRepository.findAllByUserName(userName);
        //如果删除维护端数据，只是把维护端的List清空
        for(User1 user : users) {
            user.getRoles().clear();
            userRepository.save(user); //执行save()之后才会保存到数据库中
        }
        userRepository.deleteByUserName(userName);
    }

    @Transactional
    public void deleteRole(String roleName) {
        List<Role> roles = roleRepository.findAllByRoleName(roleName);
        List<User1> users = userRepository.findAll();
        for (User1 user : users) {
            List<Role> userRole = user.getRoles();
            for (Role role : roles) {
                if (userRole.contains(role)) {
                    userRole.remove(role);
                }
                role.getAuthorities().clear();
                roleRepository.save(role);
            }
            userRepository.save(user);
        }
        roleRepository.deleteByRoleName(roleName);
    }

    @Transactional
    public void deleteAuthority(String authName) {
        List<Authority> authorities = authorityRepository.findAllByAuthorityName(authName);
        List<Role> roles = roleRepository.findAll();
        //如果删除被维护端的数据，则把用户（维护端）的List中要移除的角色（被维护端）都remove掉
        for (Role role : roles) {
            List<Authority> roleAuthoritis = role.getAuthorities();
            for (Authority authority : authorities) {
                if (roleAuthoritis.contains(authority)) {
                    roleAuthoritis.remove(authority);
                }
            }
            roleRepository.save(role);
        }
        authorityRepository.deleteByAuthorityName(authName);
    }

}

