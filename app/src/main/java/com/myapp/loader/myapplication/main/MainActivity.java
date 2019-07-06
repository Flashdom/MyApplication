package com.myapp.loader.myapplication.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myapp.loader.myapplication.ArticlesAPI;
import com.myapp.loader.myapplication.OnItemClick;
import com.myapp.loader.myapplication.PaginationScrollListener;
import com.myapp.loader.myapplication.R;
import com.myapp.loader.myapplication.RecyclerViewAdapter;
import com.myapp.loader.myapplication.main.di.DaggerMainComponent;
import com.myapp.loader.myapplication.main.di.MainComponent;
import com.myapp.loader.myapplication.main.di.MainModule;
import com.myapp.loader.myapplication.networkModels.Article;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends MvpAppCompatActivity implements IMainView, OnItemClick {
    @Inject
    ArticlesAPI articlesAPI;
    @InjectPresenter
    MainPresenter presenter;
    private boolean isLoading;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    MainComponent mainComponent;
    Button refresh;
    LinearLayoutManager layoutManager;
    private static int page = 1;
    ProgressBar progressBar;

    @ProvidePresenter
    public MainPresenter providePresenter() {
        return mainComponent.providePresenter();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependency();
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = findViewById(R.id.button);
        layoutManager = new LinearLayoutManager(this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    refresh.setVisibility(View.GONE);
                    presenter.loadArticles(page, isNetworkConnected());
                } else {
                    Toast.makeText(getApplicationContext(), "Check Internet", Toast.LENGTH_LONG).show();
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter();
        adapter.setOnItemClick(this);
        recyclerView.setAdapter(adapter);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading() && !isLastPage()) {
                    if (isNetworkConnected())
                        page++;
                    presenter.loadArticles(page, isNetworkConnected());
                }
            }

            @Override
            public boolean isLastPage() {
                return (page == 5);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        if (savedInstanceState == null)
            presenter.loadArticles(page, isNetworkConnected());
    }

    @Override
    public void showLoadIndicator() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadIndiactor() {
        isLoading = false;
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        refresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void showArticles(List<Article> articles) {

        adapter.addArticles(articles);
        adapter.notifyDataSetChanged();


    }


    @Override
    public void deleteArticles() {
        adapter.deleteArticles();
        adapter.notifyDataSetChanged();
    }

    public void injectDependency() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        mainComponent.inject(this);


    }

    @Override
    public void onClick(String URL) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class).putExtra("URL", URL);
        startActivity(intent);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeDB();
    }
}
