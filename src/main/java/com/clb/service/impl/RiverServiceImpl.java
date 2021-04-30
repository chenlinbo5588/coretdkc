package com.clb.service.impl;

import com.clb.dao.RiverDao;
import com.clb.entity.River;
import com.clb.service.RiverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiverServiceImpl implements RiverService {

    @Resource
    private RiverDao riverDao;

    @Override
    public List<River> getRiverList( ) {
        return riverDao.getRiverList();
    }
}
