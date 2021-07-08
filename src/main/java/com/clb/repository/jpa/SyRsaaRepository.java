package com.clb.repository.jpa;

import com.clb.entity.River;
import com.clb.entity.SyAcaa;
import com.clb.entity.SyRsaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface SyRsaaRepository extends JpaRepository<SyRsaa,Integer>,JpaSpecificationExecutor<SyRsaa> {

    @Query(value = "SELECT count(*) AS number,SUM(area) AS area,sum(tcr) AS vol FROM sy_rsaa ",nativeQuery = true)
    public Map<String,String> aggregate();



    @Query(value = "SELECT count(*) AS number, type AS name,sum(tcr) AS value FROM sy_rsaa group by type",nativeQuery = true)
    public List<Map<String,String>> sumVolthgroupbyType();

    @Query(value = "SELECT count(*) AS number, type AS name,sum(area) AS value FROM sy_rsaa group by type",nativeQuery = true)
    public List<Map<String,String>> sumAreathgroupbyType();

}
