package com.clb.controller;

import ch.qos.logback.core.db.dialect.SybaseSqlAnywhereDialect;
import com.clb.dao.AuthorityRepository;
import com.clb.dao.RoleRepository;
import com.clb.dao.UserRepository;
import com.clb.entity.Authority;
import com.clb.entity.Role;
import com.clb.entity.User1;
import com.clb.service.EntityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.text.AttributedString;
import java.util.*;

@Controller
@RequestMapping("/user1")
public class EntityController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private EntityService entityService;

    /*
        用户部分的增删查改
     */

    @RequestMapping("/finduser")
    public List<User1> findByName(@RequestParam(value = "userName") String userName) {
        return userRepository.findAllByUserName(userName);
    }
    @RequestMapping("/findalluser")
    public ModelAndView findAllUser(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/user1/findalluser");
//        model.addAttribute("aaa",userRepository.findAll());
        return modelAndView;
    }

    @RequestMapping("/adduser")
    public List<User1> addUser(@RequestParam(value = "userName") String userName,
                              @RequestParam(value = "roleName") String roleName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/user1/add");
        User1 user = new User1();
        Role role = roleRepository.findAllByRoleName(roleName).get(0);
        user.setUserName(userName);
        user.setRoles(new ArrayList<>());
        user.getRoles().add(role);//给用户设置权限
        userRepository.save(user);
        return userRepository.findAll();
    }

    /*
        给用户添加角色
     */
    @RequestMapping("/adduserrole")
    public List<User1> addUserRole(@RequestParam(value = "userName") String userName,
                                  @RequestParam(value = "roleName") String roleName) {
        User1 user = userRepository.findAllByUserName(userName).get(0);
        Role role = roleRepository.findAllByRoleName(roleName).get(0);
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        user.getRoles().add(role);//给用户设置权限
        userRepository.save(user);
        return userRepository.findAll();
    }

    @RequestMapping("/deleteuser")
    public List<User1> deleteUser(
            @RequestParam(value = "userName") String userName) {
        entityService.deleteUser(userName);
        return userRepository.findAll();
    }

    /*
        查询用户权限
     */
    @RequestMapping("/getauth")
    public Set<Authority> getAuthority(
            @RequestParam(value = "userName") String userName) {
        Set<Authority> authoritieSet = new HashSet<>();
        User1 user = userRepository.findAllByUserName(userName).get(0);
        for(Role role : user.getRoles()){
            for(Authority authority : role.getAuthorities()) {
                authoritieSet.add(authority);
            }
        }
        return authoritieSet;
    }

    /*
        角色部分的增删查改
     */
    @RequestMapping("/findallrole")
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @RequestMapping("/addrole")
    public List<Role> addRole(
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "authName") String authName) {
        Role role = new Role();
        Authority authority = authorityRepository.findAllByAuthorityName(authName).get(0);
        role.setRoleName(roleName);
        role.setAuthorities(new ArrayList<>());
        role.getAuthorities().add(authority);
        roleRepository.save(role);
        return roleRepository.findAll();
    }

    /*
        给角色添加权限
     */
    @RequestMapping("/addroleauth")
    public List<Role> addRoleAuth(
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "authName") String authName) {
        Role role = roleRepository.findAllByRoleName(roleName).get(0);
        Authority authority = authorityRepository.findAllByAuthorityName(authName).get(0);
        if (role.getAuthorities() == null) {
            role.setAuthorities(new ArrayList<>());
        }
        role.getAuthorities().add(authority);
        roleRepository.save(role);
        return roleRepository.findAll();
    }

    @RequestMapping("/deleterole")
    public List<Role> deleteRole(
            @RequestParam(value = "roleName") String roleName) {
        entityService.deleteRole(roleName);
        return roleRepository.findAll();
    }

    /*
        权限部分的增删查改
     */
    @RequestMapping("/findallauth")
    public String findAllAuthority(Model model) {
        System.out.print(authorityRepository.findAll());
        model.addAttribute("bbb",authorityRepository.findAll());
        return "html/user1/findallauth";
    }

    @RequestMapping("/toadd")
    public String addAuthority() {
        String authName;
        return "html/user1/addauth";
    }
    @RequestMapping("/addauth")
    public String toadd(Model model,HttpServletRequest request) {
        String authName = request.getParameter("authName");
        String groupData = request.getParameter("groupData");
        Integer userCnt = Integer.valueOf(request.getParameter("userCnt"));
        Authority authority = new Authority();
        authority.setAuthorityName(authName);
        authority.setGroupData(groupData);
        authority.setUserCnt(userCnt);
        authorityRepository.save(authority);

        model.addAttribute("bbb",authorityRepository.findAll());
        return "html/user1/findallauth";
    }

    @RequestMapping("/toedit")
    public String toedit(Model model,@RequestParam("id") Integer authid) {
        model.addAttribute("bbb",authorityRepository.findAllByAuthorityId(authid));
        return "html/user1/editauth";
    }
    @RequestMapping("/editauth")
    public String editAuthority(Model model,HttpServletRequest request) {
        String authName = request.getParameter("authName");
        authorityRepository.updateAuthorityNameByAuthorityId(request.getParameter("authName"), Integer.valueOf(request.getParameter("authId")));
        model.addAttribute("bbb",authorityRepository.findAll());
        return "html/user1/findallauth";
    }

    @RequestMapping("/deleteauth")
    public List<Authority> deletAuthority(
            @RequestParam(value = "authName") String authName) {
        entityService.deleteAuthority(authName);
        return authorityRepository.findAll();
    }
}

