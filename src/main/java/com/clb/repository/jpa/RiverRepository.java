package com.clb.repository.jpa;

import com.clb.entity.River;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface RiverRepository extends JpaRepository<River,Integer>,JpaSpecificationExecutor<River> {

    public List<River> findByNameLike(String name);
    public River findByName(String name);


}
