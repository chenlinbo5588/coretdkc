package com.clb.api;


import com.clb.common.ApiResult;
import com.clb.entity.River;
import com.clb.service.RiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private RiverService riverService;


    @GetMapping("/list")
    public ApiResult list() {

        ArrayList<River> arrayList = new ArrayList<River>();

        arrayList.add(new River(1,"name1","contente1"));
        arrayList.add(new River(2,"name2","contente2"));
        arrayList.add(new River(3,"name3","contente3"));

        return ApiResult.success(arrayList);

        //return riverService.getRiverList();

        //return arrayList;
    }
}
