package com.clb.utils;


import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CalculatorTest {
    private Calculator calculator;
    private Operation mockOperation;

    @Before
    private void setup() {
        mockOperation = Mockito.mock(Operation.class);
        calculator =  new Calculator(Collections.
                singletonList(mockOperation));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenNoSuitableOperationFound() {
        when(mockOperation.handles(anyChar())).thenReturn(false);
        calculator.calculate(2, 2, '*');
    }


    @Test
    public void shouldCallApplyMethodWhenSuitableOperationFound() {
        when(mockOperation.handles(anyChar())).thenReturn(true);
        when(mockOperation.apply(2, 2)).thenReturn(4);

        calculator.calculate(2, 2, '*');
        verify(mockOperation, times(1)).apply(2,2);
    }

}
