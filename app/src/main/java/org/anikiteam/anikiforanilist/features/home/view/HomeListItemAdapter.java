package org.anikiteam.anikiforanilist.features.home.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.anikiforanilist.R;
import org.anikiteam.anikiforanilist.base.BaseRecyclerAdapter;
import org.anikiteam.anikiforanilist.features.home.model.HomeListItem;
import org.anikiteam.anikiforanilist.util.Valid;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class HomeListItemAdapter extends BaseRecyclerAdapter<SearchMediaByTypeAndStatusQuery.Medium, HomeListItemAdapter.HomeItemViewHolder>{


    public HomeListItemAdapter(Context context, List<SearchMediaByTypeAndStatusQuery.Medium> data, View.OnClickListener onClickListener) {
        super(context, data, onClickListener);
    }

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_card, parent, false );
        if(onClickListener != null) view.setOnClickListener(onClickListener);
        return new HomeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position) {
        SearchMediaByTypeAndStatusQuery.Medium item = data.get(position);
        if(Valid.isOk(item.title().english())) holder.mediaTitleView.setText(item.title().english());
        else if(Valid.isOk(item.title().native_())) holder.mediaTitleView.setText(item.title().native_());
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull HomeItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbinder.unbind();
    }

    class HomeItemViewHolder extends RecyclerView.ViewHolder
    {

        Unbinder unbinder;
        @BindView(R.id.media_title) TextView mediaTitleView;
        @BindView(R.id.media_status) TextView mediaStatusView;

        public HomeItemViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
