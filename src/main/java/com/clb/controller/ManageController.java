package com.clb.controller;

import com.clb.entity.*;
import com.clb.service.ProjectService;
import com.clb.service.RiverService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/manage")
@Controller
public class ManageController extends BaseController {

    @Resource
    ProjectService projectService;

    @RequestMapping("/index")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "value", required = false) String value,
                        @RequestParam(value = "paixu", required = false) String paixu,
                        HttpServletRequest request, ModelMap map) {
        Page<Project> projectPage = null;
        if(paixu !=null && value !=null){
            projectPage = projectService.getallProjectPx(page-1,value,paixu);
        }else if(paixu !=null && value == null){
            projectPage = projectService.getallProjectPx(page-1,paixu);
        }else if(paixu ==null && value !=null){
            projectPage = projectService.getallProject(page-1,value);
        }else if(paixu ==null && value ==null){
            projectPage = projectService.getallProject(page-1);
        }

        int maxPage = projectPage.getTotalPages();
        int selectMaxPage = 1;
        int selectMinPage = 1;
        int minPage = 1;
        if(maxPage ==0){
            selectMaxPage =1;
        }else if((maxPage - page)> 4 ){
            selectMaxPage = page+4;
        }else{
            selectMaxPage = maxPage;
        }
        if( page >5){
            selectMinPage = page-4;
        }
        map.put("paixuValue","");
        if(paixu !=null){
            if( paixu.contains("asc")){
                map.put("paixuValue","paixushang");
            }else{
                map.put("paixuValue","paixuxia");
            }
            String[] split = paixu.split(",");
            map.put("paixu",split[0]);
        }

        map.put("selectMaxPage",selectMaxPage);
        map.put("selectMinPage",selectMinPage);
        map.put("maxPage",maxPage);
        map.put("minPage",minPage);
        map.put("page",page);
        map.put("value",value);
        map.put("paixuTj",paixu);
        map.put("projectPage",projectPage);

        return "html/manage/index";
    }
    @RequestMapping("/add")
    public String add(HttpServletRequest request, ModelMap map,
                      @RequestParam(value = "page", required = false) int page,
                      @ModelAttribute(value="project") Project project) {

        if(project.getType() !=null){
            projectService.saveProject(project);
            map.put("id",project.getId());
        }
        map.put("data",project);
        map.put("page",page);
        return "html/manage/add";
    }
    @RequestMapping("/edit")
    public String edit(@ModelAttribute(value="project") Project project,
                       @RequestParam(value = "page", required = false) int page,
                       ModelMap map) {

        if(project.getType() ==null){
            project = projectService.getProjectById(project.getId());
        }else{
            projectService.saveProject(project);
        }

        map.put("data",project);
        map.put("page",page);

        return "html/manage/add";
    }
    @RequestMapping("/xcList")
    public String xcList(@RequestParam(value = "glxmId", required = false) int glxmId, ModelMap map) {
        List<InspectionRecord> inspectionRecords = new ArrayList<InspectionRecord>();
        if(glxmId !=0){
            inspectionRecords  = projectService.getInspectionRecordsByGlxmId(glxmId);
        }
       map.put("data",inspectionRecords);
       map.put("glxmId",glxmId);
       return "html/manage/xcList";
    }
    @RequestMapping("/xcDelete")
    public String xcDelete(@RequestParam(value = "id", required = false) int id,
                           @RequestParam(value = "glxmId", required = false) int glxmId ,
                           ModelMap map) {
        List<InspectionRecord> inspectionRecords = new ArrayList<InspectionRecord>();
        if(id !=0){
           projectService.deleteXcjlById(id);
            if(glxmId !=0){
                inspectionRecords = projectService.getInspectionRecordsByGlxmId(glxmId);
            }
        }
        map.put("data",inspectionRecords);
        map.put("glxmId",glxmId);
        return "html/manage/xcList";
    }

    @RequestMapping("/xc/add")
    public String add(@RequestParam(value = "glxmId", required = false) int glxmId ,@ModelAttribute(value="inspectionRecord") InspectionRecord inspectionRecord,ModelMap map) {

        List<InspectionRecord> inspectionRecords = new ArrayList<InspectionRecord>();
        if(glxmId !=0){
            projectService.saveXcjl(inspectionRecord);
            inspectionRecords = projectService.getInspectionRecordsByGlxmId(glxmId);
        }
        map.put("data",inspectionRecords);
        map.put("glxmId",glxmId);

        return "html/manage/xcList";
    }
}
