package com.clb.repository.jpa;

import com.clb.entity.SyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SyTokenRepository extends JpaRepository<SyToken,Integer>,JpaSpecificationExecutor<SyToken> {

    @Query(value = "select * FROM sy_token where name = ?1 and password = ?2 and referer = ?3",nativeQuery = true)
    public List<SyToken> getSytokenByinfo(String username,String password,String referer);

}
