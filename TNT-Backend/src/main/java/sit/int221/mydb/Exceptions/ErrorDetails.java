package sit.int221.mydb.Exceptions;

import lombok.Data;


public class ErrorDetails {
    private String details;

    public ErrorDetails(String details) {
        this.details = details;
    }
}
