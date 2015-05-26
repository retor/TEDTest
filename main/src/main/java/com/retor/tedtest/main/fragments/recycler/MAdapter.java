package com.retor.tedtest.main.fragments.recycler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parser.beans.Item;
import com.retor.tedtest.main.DialogsBuilder;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.fragments.dialog.MFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by retor on 05.05.2015.
 */
public class MAdapter extends RecyclerView.Adapter<MViewHolder> {

    private List<Item> items = new ArrayList<>();
    private Context context;
    private FragmentActivity activity;

    public MAdapter(FragmentActivity activity, ArrayList<Item> items) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
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
            holder.itemView.animate();
            holder.getHeaderText().setText(tmp.getTitle());
            Picasso.with(context).load(Uri.parse(tmp.getThumbnail().getUrl())).into(holder.getThumb());
            holder.getDescription().setText(Html.fromHtml(tmp.getDescription()));
            holder.getDuration().setText(tmp.getDurationiTunes());
            holder.getPubdate().setText(tmp.getPubDate().substring(0, tmp.getPubDate().length() - 6));
            holder.getCard().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("OnCardClick", String.valueOf(i));
                    createDialog(tmp, activity.getSupportFragmentManager());
                }
            });
        } else {
            DialogsBuilder.createAlert(context, "Null Array or item Exception").show();
        }
    }

    private void createDialog(Item tmp, FragmentManager fm) {
        DialogFragment df = new MFragment();
        Bundle arg = new Bundle();
        arg.putSerializable("item", tmp);
        df.setArguments(arg);
        df.show(fm.beginTransaction(), "Media");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
