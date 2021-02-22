package org.example.web.dto;

import javax.validation.constraints.NotNull;

public class BookIdToRemove {

    //validation-api и объект выполняющий валидацию hibernate-validator предоставляют аннотации
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
