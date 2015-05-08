package com.retor.tedtest.main;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parserlib.beans.Channel;
import com.retor.tedtest.main.ted.MViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by retor on 05.05.2015.
 */
public class MAdapter extends RecyclerView.Adapter<MViewHolder> {

    private List<Channel> items = new ArrayList<Channel>();
    private Context context;

    public MAdapter(Context context) {
        this.context = context;
    }

    public MAdapter(ArrayList<Channel> items) {
        this.items.addAll(items);
    }

    public void setItems(List<Channel> items) {
        this.items.addAll(items);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int i) {
        Channel tmp = items.get(i);
        if (tmp != null && tmp.getTitle() != null) {
            holder.getHeaderText().setText(tmp.getTitle());
            holder.getMainVideo().setVideoURI(Uri.parse(tmp.getLink()));
            holder.getDescription().setText(tmp.getDescription());
        } else {
            new AlertDialog.Builder(context).setMessage("Null Array or item Exception").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
