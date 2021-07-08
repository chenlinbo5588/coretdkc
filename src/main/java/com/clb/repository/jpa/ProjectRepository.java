package com.clb.repository.jpa;

import com.clb.entity.Project;
import com.clb.entity.River;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project,Integer>,JpaSpecificationExecutor<Project> {

}
