package com.clb.dao;

import com.clb.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public List<Role> findAllByRoleName(String roleName);
    public void deleteByRoleName(String roleName);
}

