package com.ous.bio.ws.security;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX  = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL   = "/users";
    public static final String TOKEN_SECRET  = "dnè=:;osbxqatrepzncyvqloj)àç=1ààà&002231";
/*
*
* Token = Header + Payload + Signature
*
* */
}
