package dictionarybuilding.web.model;

import dictionarybuilding.web.Response;

public class EmptyResponse implements Response {
    private String responseMsg = "Пустой ответ";

    public String getResponseMsg() {
        return responseMsg;
    }
}
