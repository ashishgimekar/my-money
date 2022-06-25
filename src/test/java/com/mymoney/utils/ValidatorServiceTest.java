package com.mymoney.utils;

import com.mymoney.model.CommandEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorServiceTest {

    @Test
    public void testIsValidInput() {
        String[] inputs = {"ALLOCATE", "1000", "2000", "3000"};
        boolean actual = ValidatorService.isValidInput(CommandEnum.ALLOCATE, inputs);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testIsValidInputNegative() {
        String[] inputs = {"ALLOCATE", "1000", "2000"};
        boolean actual = ValidatorService.isValidInput(CommandEnum.ALLOCATE, inputs);
        boolean expected = false;
        assertEquals(expected, actual);
    }

}
