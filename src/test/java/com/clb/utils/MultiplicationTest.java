package com.clb.utils;

import com.clb.componet.Multiplication;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;


//基本单元测试
public class MultiplicationTest {

    private final Multiplication addition = new Multiplication();

    @Test
    public void sholdMatchOperation(){
        assertThat(addition.handles('*')).isTrue();
        assertThat(addition.handles('/')).isFalse();
    }


    @Test
    public void shouldCorrectlyApplyFormula(){
        assertThat(addition.apply(2,2)).isEqualTo(4);
        assertThat(addition.apply(12,10)).isEqualTo(120);
    }

}
