package com.square63.assignment.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.square63.assignment.R;
import com.square63.assignment.listeners.ApiCallback;
import com.square63.assignment.listeners.RecyclerItemClickListener;
import com.square63.assignment.ui.adapters.PostsAdapter;
import com.square63.assignment.webapi.responses.HintModel;
import com.square63.assignment.webapi.webservices.WebServiceFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private PostsAdapter postsAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;
    private int pageIndex = 1;
    private RelativeLayout rltv_progressBar;
    private ArrayList<HintModel> hintModelArrayList;
    private TextView txtCounter;
    private int selectedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViews);
        rltv_progressBar = (RelativeLayout) findViewById(R.id.rltv_progressBar);
        txtCounter = (TextView) findViewById(R.id.txtCounter);
        hintModelArrayList = new ArrayList<>();
        setPaginationScroll();
        setSwipeView();
        getPosts(pageIndex);
    }

    public void getPosts(int pageIndex) {
        WebServiceFactory.getInstance().init(this);
        WebServiceFactory.getInstance().apiGetPosts(pageIndex, new ApiCallback() {
            @Override
            public void onSuccess(ArrayList<HintModel> hintModelArrayList) {
                setRecyclerView();
                MainActivity.this.hintModelArrayList.addAll(hintModelArrayList);
                mSwipeRefreshLayout.setRefreshing(false);
                rltv_progressBar.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }

    private void setRecyclerView() {
        if (pageIndex == 1) {
            postsAdapter = new PostsAdapter(hintModelArrayList, this);
            mManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mManager);
            recyclerView.setAdapter(postsAdapter);
        } else {
            postsAdapter.updateData(hintModelArrayList);
        }

    }

    private void setPaginationScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = mManager.getChildCount();
                int totalItemCount = mManager.getItemCount();
                int firstVisibleItemPosition = mManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        isLoading = true;
                        pageIndex++;
                        rltv_progressBar.setVisibility(View.VISIBLE);
                        getPosts(pageIndex);
                    }
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (hintModelArrayList.get(position).isSelected()) {
                    hintModelArrayList.get(position).setSelected(false);
                    selectedCount--;
                } else {
                    hintModelArrayList.get(position).setSelected(true);
                    selectedCount++;
                }
                if (selectedCount == 0)
                    txtCounter.setText("");
                else
                    txtCounter.setText("" + selectedCount);

                postsAdapter.updateItem(position, hintModelArrayList.get(position));
            }
        }));

    }

    @Override
    public void onRefresh() {
        resetList();
    }

    private void resetList() {
        pageIndex = 1;
        hintModelArrayList = new ArrayList<>();
        selectedCount = 0;
        txtCounter.setText("");
        getPosts(pageIndex);
    }

    private void setSwipeView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

}
