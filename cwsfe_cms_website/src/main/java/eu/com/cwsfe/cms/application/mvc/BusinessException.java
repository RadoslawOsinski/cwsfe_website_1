package eu.com.cwsfe.cms.application.mvc;

class BusinessException extends Exception {

    private final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
