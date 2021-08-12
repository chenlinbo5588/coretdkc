package com.clb.service;

import com.clb.entity.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProjectService {

    public Page<Project> getallProject();

    public Page<Project> getallProject(String value);

    public Page<Project> getallProject(int page);

    public Page<Project> getallProject(int page,String value);

    public Page<Project> getallProjectPx(int page,String paixu);

    public Page<Project> getallProjectPx(int page, String value,String paixu);

    public void saveProject(Project project);

    public List<Project> getOverTimeProject();

    public Page<Project> dealPage(Page<Project> projects);

    public void deleteProjectById(int id);

    public void deleteProjectByIdList(String idList);

    public Project getProjectById(int id);

    public List<InspectionRecord> getInspectionRecordsByGlxmId(int glxmId);

    public Long setLastXcdateByGlxmId(int glxmId);

    public void deleteXcjlById(int id);

    public void saveXcjl(InspectionRecord inspectionRecord);

    public void saveFileUpload(ProjectAttachment projectAttachment);

    public List<ProjectAttachment> getProjectAttachmentsByCategroyAndId(int id,String category);

    public void delteProjectAttachmentById(int id);

    public XSSFWorkbook outputExcel(String value);
}
