package com.clb;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class BaseBean implements Serializable {

    private static final long serialVersionUID = 6877955227522370690L;

    /**
     * 语言类型
     */
    @Pattern(regexp = "^\\w{2,10}$", message = "api.response.code.languageTypeError")
    private String language;

}

