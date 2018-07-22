package org.anikiteam.anikiforanilist.features.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.anikiforanilist.AnikiApp;
import org.anikiteam.anikiforanilist.R;
import org.anikiteam.anikiforanilist.features.home.network.HomeDataController;
import org.anikiteam.anikiforanilist.services.GraphQlService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject GraphQlService graphQlService;

    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    testQuery();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    testQuery();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    testQuery();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnikiApp.getAppComponent().inject(this);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    void testQuery() {
        ApolloCall.Callback<SearchMediaQuery.Data> callback = new ApolloCall.Callback<SearchMediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SearchMediaQuery.Data> response) {
                Collection<SearchMediaQuery.Medium> result = response.data().Page().media();
                for(SearchMediaQuery.Medium item : result){
                    Log.d("Apollo",item.title().english()
                            + " " +
                            item.title().native_()
                            + " "
                            + item.status());
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        };

        graphQlService.searchByMediaByTypeAndString("cowboy",1,callback);
    }

}
