package com.clb.componet;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ArcgisConfig {

    @Value("${arcgisMapHost}")
    private String arcgisMapHost;

    @Value("${appMapServerName}")
    private String appMapServerName;


}
