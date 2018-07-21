package org.anikiteam.anikiforanilist.base.model;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public enum NetworkErrorType {

    UNDEFINED(-3),
    NETWORK_DISCONNECTED(-2),
    NETWORK_UNRESOLVED(-1),
    CLIENT_BAD_REQUEST(400),
    CLIENT_UNAUTHORIZED(401),
    CLIENT_FORBIDDEN(403),
    CLIENT_NOT_FOUND(404),
    CLIENT_UPGRADE_REQUIRED(426),
    SERVER_INTERNAL_ERROR(500),
    SERVER_NOT_IMPLEMENTED(501),
    SERVER_BAD_GATEWAY(502),
    SERVER_SERVICE_UNAVAILABLE(503);

    private int value;

    NetworkErrorType(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static NetworkErrorType lookUp(int code){
        for(NetworkErrorType e : NetworkErrorType.values()){
            if(e.value == code)
                return e;
        }
        return UNDEFINED;
    }

    public boolean isNetwork(){
        return value < 0;
    }

    public boolean isClient(){
        return value >= 400 && value < 500;
    }

    public boolean isServer(){
        return value >= 500 && value < 600;
    }
}