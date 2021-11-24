package com.clb.repository.jpa;

import com.clb.entity.SyToken;
import com.clb.entity.SyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SyUserRepository extends JpaRepository<SyUser,Integer>,JpaSpecificationExecutor<SyUser> {



}
