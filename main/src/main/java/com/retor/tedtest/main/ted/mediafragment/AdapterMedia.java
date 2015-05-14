package com.retor.tedtest.main.ted.mediafragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import com.parser.beans.Media;
import com.retor.tedtest.main.R;

import java.util.ArrayList;

/**
 * Created by Admin on 13.05.15.
 */
public class AdapterMedia  extends BaseAdapter {
    private ArrayList<Media> media;
    private Choicer<Media> choicer;

    public AdapterMedia(ArrayList<Media> items, Choicer<Media> choice) {
        this.media = items;
        this.choicer = choice;
    }

    @Override
    public int getCount() {
        return media.size();
    }

    @Override
    public Media getItem(int position) {
        return media.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View out = convertView;
        if (out == null) {
            out = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.media, parent, false);
        }
        RadioButton rb = (RadioButton) out.findViewById(R.id.media_type);
        rb.setText(media.get(position).getBitRate() + " " + (media.get(position).getFileSize()/1024) + " kB");
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choicer.onChose(media.get(position));
            }
        });
        return out;
    }
}
