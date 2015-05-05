package com.retor.tedtest.main;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.retor.tedtest.main.ted.MViewHolder;
import ted.loader.TedNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by retor on 05.05.2015.
 */
public class MAdapter extends RecyclerView.Adapter<MViewHolder> {

    private List<TedNews> items;

    public MAdapter() {
    }

    public MAdapter(ArrayList<TedNews> items) {
        this.items = items;
    }

    public void setItems(List<TedNews> items) {
        this.items = items;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int i) {
        TedNews tmp = items.get(i);
        holder.getHeaderText().setText(tmp.getHeader());
        holder.getMainVideo().setVideoURI(Uri.parse(tmp.getVideoURL()));
        holder.getDescription().setText(tmp.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
