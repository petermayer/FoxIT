package com.foxyourprivacy.f0x1t;

/**
 * Created by Ich on 29.09.2016.
 */

public class ProfilListObject {
    String inputType;
    String inputDescription;
    String input = "";

    public ProfilListObject(String inputType, String inputDescription) {
        this.inputType = inputType;
        this.inputDescription = inputDescription;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInputDescription() {
        return inputDescription;
    }

    public String getInputType() {
        return inputType;
    }
}