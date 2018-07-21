package org.anikiteam.anikiforanilist.base.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

@Data
@NoArgsConstructor
public class Title extends BaseModel {

    String romaji;
    String english;
    @JsonAlias("native")
    String nativeTitle; //native is a reserved word

}
