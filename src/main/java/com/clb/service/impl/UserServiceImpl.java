
package com.clb.service.impl;

import com.clb.entity.Project;
import com.clb.entity.SyUser;
import com.clb.repository.jpa.ProjectRepository;
import com.clb.repository.jpa.SyUserRepository;
import com.clb.service.UserService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    SyUserRepository syUserRepository;

    public Boolean isUser(String name){

        return false;
    }

    public void saveUser(SyUser syUser){

        syUserRepository.save(syUser);
    }

    public SyUser getUser(String userCode,String mobile,String trueName){

        String filer = "userCode=="+userCode +";mobile=="+ mobile+";trueName=="+ trueName;
        Specification<SyUser> specification = RSQLJPASupport.<SyUser>toSpecification(filer);
        List<SyUser> syUserList = syUserRepository.findAll(specification);
        if(syUserList.size() >0){
            return syUserList.get(0);
        }else{
            return null;
        }
    }
}

