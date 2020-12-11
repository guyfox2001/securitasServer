package com.restfulapi.securitas.response;

public class AuthResponse extends BaseResponse {

    String OurJwt;
    public AuthResponse(String _OurResp, int _code, String Jwt) {
        super(_OurResp, _code);
        this.OurJwt = Jwt;
    }
    public String getOurJwt() {
        return OurJwt;
    }
}
