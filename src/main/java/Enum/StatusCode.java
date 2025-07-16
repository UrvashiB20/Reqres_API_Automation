package Enum;

public enum StatusCode {

    SUCCESS(200,"OK"),
    CREATED(201,"The Resource is successfully created"),
    NO_CONTENT(204,"The Resource is successfully deleted"),
    NOT_FOUND(404,"Resource not found"),
    BAD_REQUEST(400,"Please check the missing field in request body");

    public final int code;
    public final String message;

    StatusCode(int code, String message) {
        this.code=code;
        this.message=message;
    }

}
