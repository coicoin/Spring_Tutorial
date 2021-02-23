package org.example.web.dto;

import javax.validation.constraints.NotNull;

public class BookValueToRemove {

    @NotNull
    private String valueToRemove;

    public String getValueToRemove() {
        return valueToRemove;
    }

    public void setValueToRemove(String valueToRemove) {
        this.valueToRemove = valueToRemove;
    }


}
