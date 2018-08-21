package org.anikiteam.anikiforanilist.features.home.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.anikiforanilist.R;
import org.anikiteam.anikiforanilist.features.home.model.HomeListGroup;
import org.anikiteam.anikiforanilist.features.home.model.HomeListItem;
import org.anikiteam.anikiforanilist.util.Valid;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

// test
public class AdvancedItemAdapter extends AbstractExpandableItemAdapter<AdvancedItemAdapter.MediaGroupViewHolder, AdvancedItemAdapter.MediaItemViewHolder>
implements View.OnClickListener{

    RecyclerViewExpandableItemManager expandableItemManager;
    List<HomeListGroup> groups;

    public AdvancedItemAdapter(RecyclerViewExpandableItemManager expandableItemManager,  List<HomeListGroup> data){
        this.expandableItemManager = expandableItemManager;
        groups = data;
        setHasStableIds(true); // required
    }

    @Override
    public void onClick(View v) {
        RecyclerView rv = RecyclerViewAdapterUtils.getParentRecyclerView(v);
        RecyclerView.ViewHolder vh = rv.findContainingViewHolder(v);

        int rootPosition = vh.getAdapterPosition();
        if (rootPosition == RecyclerView.NO_POSITION) {
            return;
        }

        // need to determine adapter local flat position like this:
        RecyclerView.Adapter rootAdapter = rv.getAdapter();
        int localFlatPosition = WrapperAdapterUtils.unwrapPosition(rootAdapter, this, rootPosition);

        long expandablePosition = expandableItemManager.getExpandablePosition(localFlatPosition);
        int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(expandablePosition);
        int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(expandablePosition);

        String message;
        if (childPosition == RecyclerView.NO_POSITION) {
            // Clicked item is a group!

            // toggle expand/collapse
            if (expandableItemManager.isGroupExpanded(groupPosition)) {
                expandableItemManager.collapseGroup(groupPosition);
                message = "COLLAPSE: Group " + groupPosition;
            } else {
                expandableItemManager.expandGroup(groupPosition);
                message = "EXPAND: Group " + groupPosition;
            }
        } else {
            // Clicked item is a child!

            message = "CLICKED: Child " + groupPosition + "-" + childPosition;
        }

//        mOnItemClickListener.onItemClicked(message);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return groups.get(groupPosition).getItems().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // This method need to return unique value within all group items.
        return groups.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // This method need to return unique value within the group.
        return groups.get(groupPosition).getItems().get(childPosition).getId();
    }

    @Override
    public MediaGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_home_card, parent, false);
        MediaGroupViewHolder vh = new MediaGroupViewHolder(v);
        vh.itemView.setOnClickListener(this);
        return vh;
    }

    @Override
    public MediaItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_card, parent, false);
        MediaItemViewHolder vh = new MediaItemViewHolder(v);
        vh.itemView.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindGroupViewHolder(MediaGroupViewHolder holder, int groupPosition, int viewType) {
        HomeListGroup group = groups.get(groupPosition);
        holder.textView.setText(group.getHeader());
    }

    @Override
    public void onBindChildViewHolder(MediaItemViewHolder holder, int groupPosition, int childPosition, int viewType) {
        HomeListItem child = groups.get(groupPosition).getItems().get(childPosition);

        if(holder.mediaTitleView == null || holder.mediaStatusView == null) return; // may be caused by grouping
        // todo: improve
        SearchMediaByTypeAndStatusQuery.Medium item = child.getMedium();
        if(Valid.isOk(item.title().english())) holder.mediaTitleView.setText(item.title().english());
        else if(Valid.isOk(item.title().native_())) holder.mediaTitleView.setText(item.title().native_());
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(MediaGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return false;
    }

    static class MediaGroupViewHolder extends AbstractSwipeableItemViewHolder {
        TextView textView;

        public MediaGroupViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        @Override
        public View getSwipeableContainerView() {
            return null;
        }
    }

    static class MediaItemViewHolder extends AbstractSwipeableItemViewHolder {

        TextView mediaTitleView;
        TextView mediaStatusView;

        public MediaItemViewHolder(View itemView) {
            super(itemView);
            mediaTitleView = itemView.findViewById(R.id.media_title);
            mediaStatusView = itemView.findViewById(R.id.media_status);
            // do not use butterknife here as it may cause problems when collapsing/expanding items

        }

        @Override
        public View getSwipeableContainerView() {
            return null;
        }
    }


}
