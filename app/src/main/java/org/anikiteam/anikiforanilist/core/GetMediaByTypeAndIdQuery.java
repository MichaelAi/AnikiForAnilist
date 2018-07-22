package org.anikiteam.anikiforanilist.core;

import android.content.Context;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class GetMediaByTypeAndIdQuery extends GraphQLQuery{

    public GetMediaByTypeAndIdQuery(Context context, String type, String id){
        parseFile(context,"QueryMediaByTypeFileAndId");
        setArgument("type",type);
        setArgument("id",id);

    }

}
