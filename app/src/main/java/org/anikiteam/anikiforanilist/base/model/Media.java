package org.anikiteam.anikiforanilist.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Media extends BaseModel{

    Title title;

}

