package com.retor.tedtest.main.fragments.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.parser.beans.Item;
import com.parser.beans.Media;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.fragments.video.VideoPlay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by retor on 13.05.2015.
 */
public class MFragment extends DialogFragment {

    private Spinner spinner;
    private ImageView image;
    private Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        setCancelable(true);
        super.onCreate(savedInstanceState);
        this.item = (Item) getArguments().getSerializable("item");
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.choise_videoview, container, false);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        image = (ImageView) v.findViewById(R.id.imageView);
        Picasso.with(getActivity().getApplicationContext()).load(item.getImageTunes()).into(image);
        ((TextView) v.findViewById(R.id.media_description)).setText(Html.fromHtml(item.getDescription()));
        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        spinnerInit();
        return v;
    }

    private void spinnerInit() {
        ArrayList<String> spinArray = new ArrayList<>(item.getContent().size());
        spinArray.add(0, "Bit Rate");
        for (Media m : item.getContent()) {
            spinArray.add(m.getBitRate() + "bps" + " (" + (m.getFileSize() / 1024) + " kB)");
        }
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinArray));
        spinner.setEnabled(true);
        spinner.setPrompt("Bit Rate");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    startActivity(new Intent(getActivity(), VideoPlay.class).putExtra("url", item.getContent().get(position - 1).getUrl()));
                    dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }
}
