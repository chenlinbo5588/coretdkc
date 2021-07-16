package com.clb.repository.jpa;

import com.clb.entity.ChangeWaterarea;
import com.clb.entity.InspectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ChangeWaterareaRepository extends JpaRepository<ChangeWaterarea,Integer>,JpaSpecificationExecutor<ChangeWaterarea> {

}
