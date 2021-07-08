package com.clb.repository.jpa;

import com.clb.entity.River;
import com.clb.entity.SyAcaa;
import com.clb.entity.SyLkaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface SyAcaaRepository extends JpaRepository<SyAcaa,Integer>,JpaSpecificationExecutor<SyAcaa> {

    @Query(value = "SELECT count(*) AS number,SUM(length) AS length,SUM(area) AS area,sum(vol) AS vol FROM sy_acaa ",nativeQuery = true)
    public Map<String,String> aggregate();


    @Query(value = "SELECT count(*) AS number, type AS name,sum(length) AS value FROM sy_acaa group by type",nativeQuery = true)
    public List<Map<String,String>> sumLengthgroupbyType();

    @Query(value = "SELECT count(*) AS number, type AS name, sum(area) AS value FROM sy_acaa group by type",nativeQuery = true)
    public List<Map<String,String>> sumAreagroupbyType();

    @Query(value = "SELECT count(*) AS number, type AS name, sum(vol) AS value FROM sy_acaa group by type",nativeQuery = true)
    public List<Map<String,String>> sumVolgroupbyType();


}
