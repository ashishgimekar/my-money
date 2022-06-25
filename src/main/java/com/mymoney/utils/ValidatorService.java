package com.mymoney.utils;

import com.mymoney.model.CommandEnum;

public class ValidatorService {

    public static boolean isValidInput(CommandEnum command, String[] inputs) {
        int expectedNumberOfInputs = command.getNumberOfInputs();
        if(expectedNumberOfInputs != (inputs.length - 1))
            return false;

        return true;
    }

}
