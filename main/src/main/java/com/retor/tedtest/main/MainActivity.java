package com.retor.tedtest.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.parser.beans.Channel;
import com.retor.tedtest.main.ted.DialogsBuilder;
import ted.loader.interfaces.IView;
import ted.loader.presenter.IPresenter;
import ted.loader.presenter.PresenterImpl;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements IView<Channel> {

    String mainUrl = "http://www.ted.com/themes/rss/id/6";// "http://www.ted.com/themes/rss/id/25";//"http://www.ted.com/talks/rss";//"http://feeds.feedburner.com/tedtalks_video";http://www.ted.com/themes/rss/id/25
    ProgressDialog pd;
    private RecyclerView recyclerView;
    private MAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        IPresenter presenter = new PresenterImpl(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        presenter.getData(mainUrl);
        pd = DialogsBuilder.createProgress(this, "Loading rss...");
        pd.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(ArrayList<Channel> data) {
        Log.d("MainActivity", data.toString());
        adapter.setItems(data.get(0).getItems());
        recyclerView.setAdapter(adapter);
        pd.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadItem(Channel item) {
        //TODO new Fragment or load video and start play
    }

    @Override
    public void onError(Throwable t) {
        if (pd.isShowing())
            pd.dismiss();
        DialogsBuilder.createAlert(this, t.getLocalizedMessage()).show();
    }
}
