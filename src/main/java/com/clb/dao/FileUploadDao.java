package com.clb.dao;

import com.clb.entity.Authority;
import com.clb.entity.FileUpload;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper
public interface FileUploadDao extends JpaRepository<Authority, Integer> {
}


