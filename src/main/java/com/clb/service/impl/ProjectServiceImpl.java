package com.clb.service.impl;

import com.clb.constant.DateConstant;
import com.clb.entity.FileUpload;
import com.clb.entity.InspectionRecord;
import com.clb.entity.Project;
import com.clb.entity.ProjectAttachment;
import com.clb.repository.jpa.FileUploadRepository;
import com.clb.repository.jpa.InspectionRecordRepository;
import com.clb.repository.jpa.ProjectAttachmentRepository;
import com.clb.repository.jpa.ProjectRepository;
import com.clb.service.ProjectService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSort;
import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    InspectionRecordRepository inspectionRecordRepository;


    @Autowired
    ProjectAttachmentRepository projectAttachmentRepository;

    @Autowired
    RestTemplate restTemplate;

    private int projectNumber = 12;

    public Page<Project> getallProject(){
        Pageable pageable = PageRequest.of(0, projectNumber);
        Specification<Project> specification = RSQLJPASupport.<Project>toSort("id,asc");
        return dealPage(projectRepository.findAll(specification,pageable)) ;
    }

    public Page<Project> getallProject(String value){
        Pageable pageable = PageRequest.of(0, projectNumber);
        String filer = "pname=like="+value +",pfwh=like="+ value;
        Specification<Project> specification = RSQLJPASupport.<Project>toSpecification(filer);
        return projectRepository.findAll(specification,pageable);
    }
    public Page<Project> getallProject(int page){
        Pageable pageable = PageRequest.of(page, projectNumber);
        Specification<Project> specification = RSQLJPASupport.<Project>toSort("id,asc");
        return dealPage(projectRepository.findAll(specification,pageable)) ;
    }
    public Page<Project> getallProject(int page, String value){
        Pageable pageable = PageRequest.of(page, projectNumber);
        String filer = "pname=like="+value +",pfwh=like="+ value;
        Specification<Project> specification = RSQLJPASupport.<Project>toSpecification(filer).and(toSort("id,asc"));
        return dealPage(projectRepository.findAll(specification,pageable));
    }
    public Page<Project> getallProjectPx(int page,String paixu){
        Pageable pageable = PageRequest.of(page, projectNumber);
        Specification<Project> specification = RSQLJPASupport.<Project>toSort(paixu);
        return dealPage(projectRepository.findAll(specification,pageable)) ;
    }
    public Page<Project> getallProjectPx(int page, String value,String paixu){
        Pageable pageable = PageRequest.of(page, projectNumber);
        String filer = "pname=like="+value +",pfwh=like="+ value;
        Specification<Project> specification = RSQLJPASupport.<Project>toSpecification(filer).and(toSort(paixu));
        return dealPage(projectRepository.findAll(specification,pageable));
    }

    public Page<Project> dealPage(Page<Project> projects){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        if (projects.getContent().size()>0){
            for (int i=0;i<projects.getContent().size();i++) {
                long data = System.currentTimeMillis() / 1000;
                if(projects.getContent().get(i).getXcdate() !=null){
                    if((data - projects.getContent().get(i).getXcdate())< DateConstant.ONE_MONTH_SECONDS * 3){
                        projects.getContent().get(i).setJsd("lv");
                    }else if((data - projects.getContent().get(i).getXcdate())>DateConstant.ONE_MONTH_SECONDS  * 3 && (data - projects.getContent().get(i).getXcdate())<DateConstant.ONE_MONTH_SECONDS * 6){
                        projects.getContent().get(i).setJsd("huang");
                    }else if((data - projects.getContent().get(i).getXcdate())>DateConstant.ONE_MONTH_SECONDS * 6){
                        projects.getContent().get(i).setJsd("hong");
                    }
                    long xcdata = projects.getContent().get(i).getXcdate();
                    projects.getContent().get(i).setZjxcsj(sdf.format(xcdata*1000));
                }else{
                    projects.getContent().get(i).setJsd("hong");
                }

                if(projects.getContent().get(i).getYsdate() !=null){
                    long ysdata = projects.getContent().get(i).getYsdate();
                    projects.getContent().get(i).setYssj(sdf.format(ysdata*1000));
                }else{
                    projects.getContent().get(i).setYssj(sdf.format(0));
                }

            }
        }
        return projects;
    }

    public void deleteProjectById(int id){
       projectRepository.deleteById(id);
    }
    public void deleteProjectByIdList(String idList){
        String[] split = idList.split(",");
        for(int i=0;i<split.length;i++){
            if(split[i].length()>0){
                projectRepository.deleteById(Integer.valueOf(split[i]));
            }
        }
    }

    public void saveProject(Project project){

        if(project.getYsdateS() != null ){
            project.setYsdate(project.getYsdateS().getTime()/1000);
        }
        if(project.getXcdateS() !=null){
            project.setXcdate(project.getXcdateS().getTime()/1000);
        }

        projectRepository.save(project);
    }
    public Project getProjectById(int id){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Project project = projectRepository.findAllById(Collections.singleton(id)).get(0);
        if(project.getXcdate() !=null ){
            project.setXcdateS(Date.valueOf(sdf.format(project.getXcdate()*1000)));
        }
        if(project.getYsdate() !=null){
            project.setYsdateS(Date.valueOf(sdf.format(project.getYsdate()*1000)));
        }
        return project;
    }

    public List<InspectionRecord> getInspectionRecordsByGlxmId(int glxmId){
        String filer = "glxmId=="+glxmId ;
        return inspectionRecordRepository.findAll(toSpecification(filer));
    }

    public Long setLastXcdateByGlxmId(int glxmId){
        String filer = "glxmId=="+glxmId ;

        Specification<InspectionRecord> specification = RSQLJPASupport.<InspectionRecord>toSpecification(filer).and(toSort("xcdate,desc"));
        List<InspectionRecord> inspectionRecords =  inspectionRecordRepository.findAll(specification);
        Project project = projectRepository.findAllById(Collections.singleton(glxmId)).get(0);
        if(inspectionRecords.size()>0){
            project.setXcdate(inspectionRecords.get(0).getXcdate());
            projectRepository.save(project);
            return inspectionRecords.get(0).getXcdate();
        }else{
            project.setXcdate((long) 0);
            projectRepository.save(project);
            return Long.valueOf(0);
        }


    }
    public void deleteXcjlById(int id){
        inspectionRecordRepository.deleteById(Integer.valueOf(id));
    }

    public void saveXcjl(InspectionRecord inspectionRecord){

        inspectionRecord.setXcdate((int) (inspectionRecord.getXcdateS().getTime() /1000));

        inspectionRecordRepository.save(inspectionRecord);

//        Project project = projectRepository.findAllById(Collections.singleton(inspectionRecord.getGlxmId())).get(0);
//        project.setXcdate((int) (inspectionRecord.getXcdateS().getTime() /1000));
//        projectRepository.save(project);

    }
    public void saveFileUpload(ProjectAttachment projectAttachment){
        projectAttachment.setCategroy("project");
        projectAttachmentRepository.save(projectAttachment);
    }

    public List<ProjectAttachment> getProjectAttachmentsByCategroyAndId(int id,String category){
        String filer = "refId=="+id +";categroy=="+ category;
        return projectAttachmentRepository.findAll(toSpecification(filer));
    }

    public void delteProjectAttachmentById(int id){
        projectAttachmentRepository.deleteById(id);
    }
    public List<Project> getOverTimeProject(){

        long data = System.currentTimeMillis() / 1000;
        long overTime = data -  DateConstant.ONE_MONTH_SECONDS * 6;
        String filer = "xcdate<" + overTime;

        return projectRepository.findAll(toSpecification(filer));
    }

    public XSSFWorkbook outputExcel(String value){
        List<Project> projects ;
        if(!value.equals("")){
            String filer = "pname=like="+value +",pfwh=like="+ value;
            projects =  projectRepository.findAll(toSpecification(filer));
        }else{
            projects = projectRepository.findAll();
        }
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("项目信息");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<DateConstant.PROJECT_EXCEL.length;i++){
            titleRow.createCell(i).setCellValue(DateConstant.PROJECT_EXCEL[i]);//第一列
        }
        int cell = 1;
        for (Project project : projects) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(cell);
            row.createCell(1).setCellValue(project.getPname());//将数据库的数据遍历出来
            row.createCell(2).setCellValue(project.getFzrname());
            row.createCell(3).setCellValue(project.getMj());
            row.createCell(4).setCellValue(project.getPfwh());
            row.createCell(5).setCellValue(sdf.format(project.getXcdate()*1000));
            row.createCell(6).setCellValue(sdf.format(project.getYsdate()*1000));
            cell++;
        }
        return wb;
    }


}
