package com.clb.repository.jpa;

import com.clb.entity.River;
import com.clb.entity.SyOwaa;
import com.clb.entity.SyRvaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;


public interface SyOwaaRepository extends JpaRepository<SyOwaa,Integer>,JpaSpecificationExecutor<SyOwaa> {

    @Query(value = "SELECT count(*) AS number,SUM(area) AS area,sum(vol) AS vol FROM sy_owaa ",nativeQuery = true)
    public Map<String,String> aggregate();

}
