package com.clb.repository.querydsl;

import com.clb.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface ProjectRepository extends JpaRepository<Project,Integer>,JpaSpecificationExecutor<Project>, QuerydslPredicateExecutor<Project> {

}
