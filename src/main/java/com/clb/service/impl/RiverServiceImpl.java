package com.clb.service.impl;

import com.clb.entity.River;
import com.clb.repository.jpa.RiverRepository;
import com.clb.service.RiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;


@Service
public class RiverServiceImpl implements RiverService {

    @Autowired
    RiverRepository riverRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<River> getByLikeName(String name){

        String filer = "name=like="+name;

        return riverRepository.findAll(toSpecification(filer));
    }

    public River getByName(String name){
        return riverRepository.findByName(name);
    }

    public List<River> getByLikeNameAndTypes(String name,String[] types){
        String filer = "name=like="+name +";type=in=(" ;

        for(int i=0;i<types.length;i++){
            filer = filer + types[i] ;
            if(i ==(types.length-1)){
                filer = filer + ")" ;
            }else{
                filer = filer + "," ;
            }
        }
        return riverRepository.findAll(toSpecification(filer));
    }

    public List<River> getByLikeNameOrBm(String value){
        String filer = "name=like="+value +",bm=like="+ value ;

        return riverRepository.findAll(toSpecification(filer));
    }


}
