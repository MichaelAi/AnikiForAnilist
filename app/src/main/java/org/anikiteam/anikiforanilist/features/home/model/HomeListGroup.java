package org.anikiteam.anikiforanilist.features.home.model;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class HomeListGroup {

    @Setter @Getter private long id;
    @Setter @Getter private String header;
    private List<HomeListItem> items;

    public HomeListGroup(String header){
        this.header = header;
    }

    public List<HomeListItem> getItems() {
        if (items == null) return new ArrayList<>();
        else return items;
    }

    public void setMedia(List<SearchMediaByTypeAndStatusQuery.Medium> media){
        List<HomeListItem> items = new ArrayList<>();
        for(SearchMediaByTypeAndStatusQuery.Medium medium : media){
            items.add(new HomeListItem(medium));
        }
        this.items = items;
    }



}
