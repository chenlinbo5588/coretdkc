package com.clb.repository.querydsl;

import com.clb.entity.ChangeWaterarea;
import com.clb.entity.River;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface ChangeWaterareaRepository extends JpaRepository<ChangeWaterarea,Integer>, JpaSpecificationExecutor<ChangeWaterarea>, QuerydslPredicateExecutor<ChangeWaterarea>{

}
