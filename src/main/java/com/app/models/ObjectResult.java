package com.app.models;

import com.app.enums.MessageKeys;
import com.fasterxml.jackson.annotation.JsonInclude;
 import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ObjectResult<T> implements Serializable {
    private static final long serialVersionUID = 3837972375357063975L;
    private boolean successful;
    private int resultCode;
    private String resultMessage;
    private T object = null;

    public ObjectResult(boolean successful) {
        this.successful = successful;
    }

    public ObjectResult(boolean successful, String resultMessage){
        this.successful = successful;
        this.resultMessage = resultMessage;
    }

    public ObjectResult(boolean successful, T object) {
        this.successful = successful;
        this.object = object;
    }

    public ObjectResult(boolean successful, String resultMessage, T object) {
        this.successful = successful;
        this.resultMessage = resultMessage;
        this.object = object;
    }


    public void setSuccessResponse(T object) {
        this.successful = Boolean.TRUE;
        this.resultCode = 0;
        this.resultMessage = "SUCCESS";
        this.object = object;
    }

    public void setSuccessResponse(MessageKeys messageKey, T object) {
        this.successful = Boolean.TRUE;
        this.resultCode = messageKey.getCode();
        this.resultMessage = messageKey.getKey();
        this.object = object;
    }




}
