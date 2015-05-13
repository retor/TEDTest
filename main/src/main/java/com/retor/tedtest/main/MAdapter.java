package com.retor.tedtest.main;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parserlib.beans.Item;
import com.retor.tedtest.main.ted.mediafragment.MFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by retor on 05.05.2015.
 */
public class MAdapter extends RecyclerView.Adapter<MViewHolder> {

    private List<Item> items = new ArrayList<Item>();
    private Context context;
    private FragmentActivity activity;

    public MAdapter(FragmentActivity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public MAdapter(ArrayList<Item> items) {
        this.items.addAll(items);
        Collections.reverse(this.items);
    }

    public void setItems(List<Item> items) {
        this.items.addAll(items);
        Collections.reverse(this.items);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, final int i) {
        final Item tmp = items.get(i);
        if (tmp != null && tmp.getTitle() != null) {
            holder.getHeaderText().setText(tmp.getTitle());
            Picasso.with(context).load(Uri.parse(tmp.getThumbnail().getUrl())).into(holder.getThumb());
            holder.getDescription().setText(Html.fromHtml(tmp.getDescription()));
            holder.getDuration().setText(tmp.getDurationiTunes());
            holder.getPubdate().setText(tmp.getPubDate().substring(0, tmp.getPubDate().length()-6));
            holder.getCard().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("OnCardClick", String.valueOf(i));
                    DialogFragment df = new MFragment();
                    Bundle arg = new Bundle();
                    arg.putSerializable("item", tmp);
                    df.setArguments(arg);
                    df.setRetainInstance(true);
                    df.setCancelable(true);
                    df.show(activity.getSupportFragmentManager().beginTransaction(), "Media");
                }
            });
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
