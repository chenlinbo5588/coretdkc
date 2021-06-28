package com.clb.repository.querydsl;

import com.clb.entity.River;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface RiverRepository extends JpaRepository<River,Integer>, JpaSpecificationExecutor<River>, QuerydslPredicateExecutor<River> {


}
