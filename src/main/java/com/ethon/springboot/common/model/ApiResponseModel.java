package com.ethon.springboot.common.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiResponseModel {

    private Header header = new Header();
    private Object data = null;
    private Object errorData = null;

    public ApiResponseModel() {
        header.setResultCode(0);
        header.setResultMessage("SUCCESS");
    }

    public ApiResponseModel(Integer resultCode, String resultMessage) {
        header.setResultCode(resultCode);
        header.setResultMessage(resultMessage);
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    public Header getHeader() {
        return header;
    }

    public Object getData() {
        return data;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setResultCode(Integer resultCode) {
        header.resultCode = resultCode;
    }

    public void setResultMessage(String resultMessage) {
        header.resultMessage = resultMessage;
    }

    public void put(Object data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public void put(String key, Object value) {
        if(data == null || !(data instanceof Map)){
            data = new LinkedHashMap<String, Object>();
        }
        Map<String, Object> map = (Map<String, Object>)data;
        map.put(key, value);
    }

    public void putError(Object errorData) {
        this.errorData = errorData;
    }

    @SuppressWarnings("unchecked")
    public void putError(String key, Object value) {
        put(key, value);
    }

    public class Header {

        private Integer resultCode;
        private String resultMessage;

        @Override
        public String toString() {
            return "Header{" +
                    "resultCode=" + resultCode +
                    ", resultMessage='" + resultMessage + '\'' +
                    '}';
        }

        public Integer getResultCode() {
            return resultCode;
        }

        public void setResultCode(Integer resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }
    }

}
