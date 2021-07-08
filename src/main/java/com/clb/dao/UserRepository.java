package com.clb.dao;

import com.clb.entity.User;
import com.clb.entity.User1;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper
public interface UserRepository extends JpaRepository<User1, Integer> {
    public List<User1> findAllByUserName(String userName);
    public void deleteByUserName(String userName);

}


