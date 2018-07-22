package org.anikiteam.anikiforanilist.features.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.anikiteam.aniki.AnimeQuery;
import org.anikiteam.anikiforanilist.R;
import org.anikiteam.anikiforanilist.core.ApolloApiBuilder;
import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    testQuery(1);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    testQuery(2);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    testQuery(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private final String BASE_URL = "https://graphql.anilist.co";

    void testQuery(int coolId) {
        ApolloApiBuilder builder = new ApolloApiBuilder();
        ApolloClient apolloClient = builder.setBaseUrl(BASE_URL)
                .setLanguage("en-us")
                .build();

        AnimeQuery animeQuery = AnimeQuery.builder().id(coolId).build();
        ApolloCall.Callback<AnimeQuery.Data> callback = new ApolloCall.Callback<AnimeQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<AnimeQuery.Data> response) {
                Log.d("query",response.data().Media().title().english());
                Log.d("query",response.data().Media().status().rawValue());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e("query",e.getMessage());

            }
        };

        apolloClient.query(animeQuery).enqueue(callback);
    }

}
