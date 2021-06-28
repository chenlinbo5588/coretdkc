package com.clb.dao;

import com.clb.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    public List<Authority> findAllByAuthorityName(String authorityName);
    public List<Authority> findAllByAuthorityId(Integer authorityId);
    public void deleteByAuthorityName(String authorityName);

    @Transactional
    @Modifying
    @Query(value ="update auth_table  set auth_name=:authorityName where auth_id=:authorityId",nativeQuery = true)
    public void updateAuthorityNameByAuthorityId(@Param("authorityName")String authorityName, @Param("authorityId")Integer authorityId);
}


