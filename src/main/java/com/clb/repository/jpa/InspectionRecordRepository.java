package com.clb.repository.jpa;

import com.clb.entity.InspectionRecord;
import com.clb.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface InspectionRecordRepository extends JpaRepository<InspectionRecord,Integer>,JpaSpecificationExecutor<InspectionRecord> {

}
