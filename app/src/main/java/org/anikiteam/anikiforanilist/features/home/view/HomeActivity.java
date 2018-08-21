package org.anikiteam.anikiforanilist.features.home.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.anikiforanilist.R;
import org.anikiteam.anikiforanilist.base.BaseActivity;
import org.anikiteam.anikiforanilist.features.home.HomeViewModel;
import org.anikiteam.anikiforanilist.features.home.model.HomeListGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    private HomeViewModel viewModel;

    @BindView(R.id.message) TextView messageView;
    @BindView(R.id.navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        messageView.setText(R.string.title_home);
                        return true;
                    case R.id.navigation_dashboard:
                        messageView.setText(R.string.title_dashboard);
                        return true;
                    case R.id.navigation_notifications:
                        messageView.setText(R.string.title_notifications);
                        return true;
                }
                return false;
            };

    @OnClick(R.id.testget)
    public void getTestAnime(){
        viewModel.fetchNowAiringAnime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewModel.getTestTitle().observe(this, newName -> messageView.setText(newName));
        viewModel.getNowAiringAnimeList().observe(this, this::updateRecyclerView);
    }

    private void updateRecyclerView(ArrayList<HomeListGroup> newValue) {
        if (recyclerView.getAdapter() == null) {
            RecyclerViewExpandableItemManager expMgr = new RecyclerViewExpandableItemManager(null);
            RecyclerView.Adapter adapter = new AdvancedItemAdapter(expMgr,newValue);
            adapter = expMgr.createWrappedAdapter(adapter);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            expMgr.attachRecyclerView(recyclerView);
        } else {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reload:
                getTestAnime();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
