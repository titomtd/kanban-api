package fr.duchemin.sir.kanban.message;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private Map<Object, Object> status;
    private String message;
    private Map<String, String> details;

    public Response(HttpStatus httpStatus, String message) {
        this.status = new HashMap<>();
        this.status.put("title", httpStatus);
        this.status.put("value", httpStatus.value());
        this.message = message;
        this.details = new HashMap<>();
    }

    public Map<Object, Object> getStatus() {
        return status;
    }

    public void setStatus(Map<Object, Object> status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return this.details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public void addDetail(String title, String detail) {
        this.details.put(title, detail);
    }
}
