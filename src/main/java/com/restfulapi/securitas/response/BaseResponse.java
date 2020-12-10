package com.restfulapi.securitas.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    public String OurResponse;
    public int code;

    BaseResponse(String _OurResp, int _code){
        this.code = _code;
        this.OurResponse = _OurResp;
    }

}
