package com.wujia.witstore.data.model;

import java.util.Map;

/**
 * Created by ZOUBIN on 2015/9/18.
 * 10:24
 */
public class SerchModel {

    private Map responseHeader;
    private Map response;

    public Map getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(Map responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Map getResponse() {
        return response;
    }

    public void setResponse(Map response) {
        this.response = response;
    }
}
