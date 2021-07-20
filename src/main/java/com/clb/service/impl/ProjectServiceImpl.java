package com.clb.service.impl;

import com.clb.constant.DateConstant;
import com.clb.entity.InspectionRecord;
import com.clb.entity.Project;
import com.clb.repository.jpa.InspectionRecordRepository;
import com.clb.repository.jpa.ProjectRepository;
import com.clb.service.ProjectService;
import io.github.perplexhub.rsql.RSQLJPASupport;
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
    RestTemplate restTemplate;

    public Page<Project> getallProject(){
        Pageable pageable = PageRequest.of(0, 10);
        Specification<Project> specification = RSQLJPASupport.<Project>toSort("id,asc");
        return dealPage(projectRepository.findAll(specification,pageable)) ;
    }

    public Page<Project> getallProject(String value){
        Pageable pageable = PageRequest.of(0, 10);
        String filer = "pname=like="+value +",pfwh=like="+ value;
        Specification<Project> specification = RSQLJPASupport.<Project>toSpecification(filer).and(toSort("id,asc"));
        return dealPage(projectRepository.findAll(specification,pageable));
    }
    public Page<Project> getallProject(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Specification<Project> specification = RSQLJPASupport.<Project>toSort("id,asc");
        return dealPage(projectRepository.findAll(specification,pageable)) ;
    }
    public Page<Project> getallProject(int page, String value){
        Pageable pageable = PageRequest.of(page, 10);
        String filer = "pname=like="+value +",pfwh=like="+ value;
        Specification<Project> specification = RSQLJPASupport.<Project>toSpecification(filer).and(toSort("id,asc"));
        return dealPage(projectRepository.findAll(toSpecification(filer),PageRequest.of(page, 10)));
    }
    public Page<Project> getallProjectPx(int page,String paixu){
        Pageable pageable = PageRequest.of(page, 10);
        Specification<Project> specification = RSQLJPASupport.<Project>toSort(paixu);
        return dealPage(projectRepository.findAll(specification,pageable)) ;
    }
    public Page<Project> getallProjectPx(int page, String value,String paixu){
        Pageable pageable = PageRequest.of(page, 10);
        String filer = "pname=like="+value +",pfwh=like="+ value;
        Specification<Project> specification = RSQLJPASupport.<Project>toSpecification(filer).and(toSort(paixu));
        return dealPage(projectRepository.findAll(toSpecification(filer),PageRequest.of(page, 10)));
    }

    public Page<Project> dealPage(Page<Project> projects){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (projects.getContent().size()>0){
            for (int i=0;i<projects.getContent().size();i++) {
                long data = System.currentTimeMillis() / 1000;
                if((data - projects.getContent().get(i).getXcdate())< DateConstant.ONE_MONTH_SECONDS * 3){
                    projects.getContent().get(i).setJsd("lv");
                }else if((data - projects.getContent().get(i).getXcdate())>DateConstant.ONE_MONTH_SECONDS  * 3 && (data - projects.getContent().get(i).getXcdate())<DateConstant.ONE_MONTH_SECONDS * 6){
                    projects.getContent().get(i).setJsd("huang");
                }else if((data - projects.getContent().get(i).getXcdate())>DateConstant.ONE_MONTH_SECONDS * 6){
                    projects.getContent().get(i).setJsd("hong");
                }
                long xcdata = projects.getContent().get(i).getXcdate();
                long ysdata = projects.getContent().get(i).getYsdate();
                projects.getContent().get(i).setZjxcsj(sdf.format(xcdata*1000));
                projects.getContent().get(i).setYssj(sdf.format(ysdata*1000));
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
        project.setXcdate((int) (project.getXcdateS().getTime() /1000));
        project.setYsdate((int) (project.getYsdateS().getTime() /1000));

        projectRepository.save(project);
    }
    public Project getProjectById(int id){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Project project = projectRepository.findAllById(Collections.singleton(id)).get(0);
        project.setXcdateS(Date.valueOf(sdf.format(project.getXcdate()*1000)));
        project.setYsdateS(Date.valueOf(sdf.format(project.getYsdate()*1000)));

        return project;
    }

    public List<InspectionRecord> getInspectionRecordsByGlxmId(int glxmId){
        String filer = "glxmId=="+glxmId ;
        return inspectionRecordRepository.findAll(toSpecification(filer));
    }
    public void deleteXcjlById(int id){
        inspectionRecordRepository.deleteById(Integer.valueOf(id));
    }

    public void saveXcjl(InspectionRecord inspectionRecord){

        inspectionRecordRepository.save(inspectionRecord);

    }

}
