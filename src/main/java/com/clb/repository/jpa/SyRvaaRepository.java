package com.clb.repository.jpa;

import com.clb.entity.SyRvaa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface SyRvaaRepository extends JpaRepository<SyRvaa,Integer>,JpaSpecificationExecutor<SyRvaa> {

    public SyRvaa findByName(String name);

    public List<SyRvaa> findByNameLike(String name);

    @Query(value = "SELECT count(*) AS number,sum(length) AS length,sum(area) AS area,sum(vol) AS vol FROM sy_rvaa ",nativeQuery = true)
    public Map<String,String> aggregate();

    @Query(value = "SELECT count(*) AS number, hdfl AS name,sum(length) AS value FROM sy_rvaa group by hdfl",nativeQuery = true)
    public List<Map<String,String>> sumLengthgroupbyHdfl();

    @Query(value = "SELECT count(*) AS number, hdfl AS name, sum(area) AS value FROM sy_rvaa group by hdfl",nativeQuery = true)
    public List<Map<String,String>> sumAreagroupbyHdfl();

    @Query(value = "SELECT count(*) AS number, hdfl AS name, sum(vol) AS value FROM sy_rvaa group by hdfl",nativeQuery = true)
    public List<Map<String,String>> sumVolgroupbyHdfl();

}
