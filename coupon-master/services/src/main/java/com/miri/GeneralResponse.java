package com.miri;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.dao.DataIntegrityViolationException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse {

    private Object error;
    private Object result;

    GeneralResponse(Object result) {
        this.result = result;
    }

    GeneralResponse(Exception e) {
        this.error = e.getMessage();

        if (e instanceof DataIntegrityViolationException) {
            String message = ((DataIntegrityViolationException) e).getMostSpecificCause().getMessage();
            if (message.contains("comp_name_UNIQUE")) {
                this.error = "Duplicate company name!";
            }
            else if (message.contains("Column 'comp_name' cannot be null")) {
                this.error = "Null value not allowed for company name!";
            }
            else if (message.contains("title_UNIQUE")) {
                this.error = "Duplicate coupon title!";
            }
            else {
                this.error = message;
            }
        }
    }

    public Object getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
