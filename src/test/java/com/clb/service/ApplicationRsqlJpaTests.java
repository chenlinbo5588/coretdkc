package com.clb.service;


import com.clb.dao.UserRepository;
import com.clb.entity.Project;
import com.clb.entity.Tubiao;
import com.clb.entity.User;
import com.clb.repository.jpa.ProjectRepository;

import com.clb.repository.jpa.SyRvaaRepository;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSort;
import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationRsqlJpaTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SyRvaaRepository syRvaaRepository;




    @Test
    public void testEqual(){

        String rsql = "id==2";
        List<Project> users = projectRepository.findAll(toSpecification(rsql));
        long count = users.size();

        assertThat(rsql, count, is(1l));



        rsql = "departments.code=='Tech'";
        List<Project> companies = projectRepository.findAll(toSpecification(rsql));

        count = companies.size();


    }


    @Test
    public void testPageData(){


        ArrayList<Tubiao> tubiaos =new ArrayList<Tubiao>();

        Tubiao tubiao = new Tubiao();
        tubiao.setName("河道");
        List<Map<String,String>> length =syRvaaRepository.sumLengthgroupbyHdfl();

        System.out.println(length);

    }


    @Test
    public void testGroupByCount(){

        //Specification spec = Specification.where();




    }

}
