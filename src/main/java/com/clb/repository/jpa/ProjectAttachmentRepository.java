package com.clb.repository.jpa;

import com.clb.entity.Project;
import com.clb.entity.ProjectAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ProjectAttachmentRepository extends JpaRepository<ProjectAttachment,Integer>,JpaSpecificationExecutor<ProjectAttachment> {

}
