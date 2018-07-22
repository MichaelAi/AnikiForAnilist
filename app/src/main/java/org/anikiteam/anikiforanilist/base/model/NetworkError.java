package org.anikiteam.anikiforanilist.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

@Data
@NoArgsConstructor
public class NetworkError {

    private int code;
    private String message;

    public NetworkError(int code, String message){
        this.code = code;
        this.message = message;
    }

    public String GetMessage(){
        if(message == null) return "";
        return message;
    }

    public NetworkErrorType whichError(){
        return NetworkErrorType.lookUp(code);
    }
}
