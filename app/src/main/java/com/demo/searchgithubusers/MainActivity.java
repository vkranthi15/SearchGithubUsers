package com.demo.searchgithubusers;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.searchgithubusers.model.Items;
import com.demo.searchgithubusers.ui.Adapter;
import com.demo.searchgithubusers.ui.MainPresenter;
import com.demo.searchgithubusers.ui.MainView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView.InitView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MainPresenter mainPresenter;
    private RelativeLayout emptyView;
    private TextView errorTitle, errorMessage;
    private Adapter adapter;
    private List<Items> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mainPresenter = new MainPresenter(this);
    }

    private void init() {

        emptyView = findViewById(R.id.empty_view);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Github Users");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (users != null) {
                    users.clear();
                }
                errorView(View.VISIBLE, "", "Search Github Users");
                return true;
            }
        });

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainPresenter.getUserList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        errorView(View.INVISIBLE, "", "");
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void userList(@Nullable List<Items> items) {
        if (users != null) {
            users.clear();
        }
        users = items;
        adapter = new Adapter(users, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void userListFailure(String errorMessage, String keyword) {
        errorView(View.VISIBLE, errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message) {
        emptyView.setVisibility(visibility);
        errorTitle.setText(title);
        errorMessage.setText(message);
    }
}
