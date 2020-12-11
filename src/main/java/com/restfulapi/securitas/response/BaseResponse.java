package com.restfulapi.securitas.response;




public class BaseResponse {
    public String OurResponse;
    public int code;

    public BaseResponse(String _OurResp, int _code){
        this.code = _code;
        this.OurResponse = _OurResp;
    }

}
