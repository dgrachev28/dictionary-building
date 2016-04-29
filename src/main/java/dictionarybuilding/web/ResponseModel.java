package dictionarybuilding.web;

public class ResponseModel implements Response {

    private Response data;

    private Boolean success;
    private String errorMsg;

    public Response getData() {
        return data;
    }

    public void setData(Response data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
