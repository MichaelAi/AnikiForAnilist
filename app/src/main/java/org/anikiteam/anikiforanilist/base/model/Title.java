package org.anikiteam.anikiforanilist.base.model;

import android.support.annotation.CallSuper;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Title extends BaseModel {

    String romaji;
    String english;
    @JsonAlias("native")
    String nativeTitle; //native is a reserved word

}
