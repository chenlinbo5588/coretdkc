package com.clb.repository.jpa;

import com.clb.entity.ChangeWaterarea;
import com.clb.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FileUploadRepository extends JpaRepository<FileUpload,Integer>,JpaSpecificationExecutor<FileUpload> {

}
