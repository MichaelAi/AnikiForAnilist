package org.anikiteam.anikiforanilist.features.home.model;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;

import java.util.List;

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

    private long id;
    private String title;
    private String status;
    private SearchMediaByTypeAndStatusQuery.Medium medium;

    public HomeListItem(SearchMediaByTypeAndStatusQuery.Medium medium){
        this.medium = medium;
    }
}
