package com.clb.service;

import com.clb.entity.Project;
import com.clb.entity.River;
import com.clb.entity.Tubiao;
import com.clb.entity.WaterTj;
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


    public Page<Project> dealPage(Page<Project> projects);

    public void deleteProjectById(int id);

    public void deleteProjectByIdList(String idList);

    public Project getProjectById(int id);


}
