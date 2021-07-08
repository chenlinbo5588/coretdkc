package com.clb.repository.jpa;

import com.clb.entity.River;
import com.clb.entity.SyLkaa;
import com.clb.entity.SyOwaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Map;


public interface SyLkaaRepository extends JpaRepository<SyLkaa,Integer>,JpaSpecificationExecutor<SyLkaa> {

    @Query(value = "SELECT count(*) AS number,SUM(area) AS area,sum(vol) AS vol FROM sy_lkaa",nativeQuery = true)
    public Map<String,String> aggregate();

}
