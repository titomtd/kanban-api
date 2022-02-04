package fr.duchemin.sir.kanban.message;

public enum ResponseMessageType {

    SUCCESS("Success"),
    SERVER_ERROR("Server Error"),
    ENTITY_NOT_FOUND("Entity Not Found"),
    ARGUMENT_NOT_VALID("Argument Not Valid"),
    METHOD_NOT_ALLOWED("Method Not Allowed"),
    MEDIA_TYPE_NOT_SUPPORTED("Media Type Not Supported"),
    BAD_REQUEST("Bad Request");

    private String title;

    ResponseMessageType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
