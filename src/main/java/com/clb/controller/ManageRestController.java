package com.clb.controller;

import com.clb.service.ProjectService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/manage")
@RestController
public class ManageRestController {
    @Resource
    ProjectService projectService;

    @RequestMapping("/delete")
    public boolean delete(@RequestParam(value = "id", required = true) int id ) {

        projectService.deleteProjectById(id);
        return true;
    }
    @RequestMapping("/deleteByList")
    public boolean deleteByList(@RequestParam(value = "idList", required = true) String idList ) {

        projectService.deleteProjectByIdList(idList);
        return true;
    }


}
