package org.anikiteam.anikiforanilist.features.home.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Created by Mike Ai on 18-Jul-18.
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HomeListItem {

    private String title;
    private String status;

}
