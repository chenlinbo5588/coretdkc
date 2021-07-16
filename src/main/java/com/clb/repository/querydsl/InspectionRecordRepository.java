package com.clb.repository.querydsl;

import com.clb.entity.ChangeWaterarea;
import com.clb.entity.InspectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface InspectionRecordRepository extends JpaRepository<InspectionRecord,Integer>, JpaSpecificationExecutor<InspectionRecord>, QuerydslPredicateExecutor<InspectionRecord> {

}
