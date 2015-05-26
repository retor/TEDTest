package com.retor.tedtest.main.fragments.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parser.beans.Channel;
import com.parser.beans.Item;
import com.retor.tedtest.dataloader.IPresenter;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.interfaces.IView;

import java.util.ArrayList;

/**
 * Created by retor on 18.05.2015.
 */
public class NewsList extends Fragment implements IView<Channel> {
    private RecyclerView recyclerView;
    private MAdapter adapter;
    private IPresenter presenter;
    private SwipeRefreshLayout swiper;
    private Channel channel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        presenter = (IPresenter) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null && (channel = (Channel) savedInstanceState.getSerializable("channel")) != null) {
            adapter = new MAdapter(getActivity(), channel.getItems());
        } else {
            adapter = new MAdapter(getActivity(), new ArrayList<Item>());
        }
        View out = inflater.inflate(R.layout.news_recycler, container, false);
        swiper = (SwipeRefreshLayout) out.findViewById(R.id.swipe);
        recyclerView = (RecyclerView) out.findViewById(R.id.recycle);
        initSwipeRefresh(swiper);
        initRecycler(recyclerView);
        return out;
    }

    private void initRecycler(RecyclerView input) {
        input.setHasFixedSize(true);
        input.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemAnimator im = new DefaultItemAnimator();
        im.setAddDuration(1000);
        input.setItemAnimator(im);
        input.setAdapter(adapter);
        /*This listener adding scroll RecyclerView to Up if it not set RefreshLayout take first scrolling to up and do refresh*/
        input.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ?
                                0 : recyclerView.getChildAt(0).getTop();
                swiper.setEnabled(topRowVerticalPosition >= 0);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initSwipeRefresh(final SwipeRefreshLayout input) {
        input.setVerticalScrollBarEnabled(true);

        input.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                input.setRefreshing(false);
                if (channel!=null){
                    presenter.getData(channel.getRssUrl());
                }else{
                    presenter.getData(null);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("channel", channel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void loadData(ArrayList<Channel> data) {
        /*NOP*/
        /*This method work in activity*/
    }

    @Override
    public void loadItem(Channel item) {
        if (item!=null){
            channel = item;
            adapter.setItems(channel.getItems());
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void onError(Throwable t) {
        /*NOP*/
        /*This method processed in MainActivity*/
    }
}
