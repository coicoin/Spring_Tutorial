package org.example.web.dto;

import javax.validation.constraints.NotEmpty;

public class BookValueToRemove {

    @NotEmpty
    private String valueToRemove;

    public String getValueToRemove() {
        return valueToRemove;
    }

    public void setValueToRemove(String valueToRemove) {
        this.valueToRemove = valueToRemove;
    }


}
