package org.anikiteam.anikiforanilist.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public abstract class BaseRecyclerAdapter<T1, T2 extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T2> {

    protected Context context;
    protected ArrayList<T1> data;
    protected View.OnClickListener onClickListener;

    public BaseRecyclerAdapter(Context context, List<T1> data, View.OnClickListener onClickListener) {
        this.context = context;
        this.data = (ArrayList<T1>) data;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
