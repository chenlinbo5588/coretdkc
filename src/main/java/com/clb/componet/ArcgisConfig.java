package com.clb.componet;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ArcgisConfig {

    @Value("${arcgis.prourl}")
    private String prourl;

    @Value("${arcgis.host}")
    private String ip;

    @Value("${project.host}")
    private String appHost;

}
