package com.retor.tedtest.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.parserlib.beans.Channel;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ted.loader.interfaces.IPresenter;
import ted.loader.interfaces.IScheduler;
import ted.loader.interfaces.IView;
import ted.loader.presenter.PresenterImpl;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements IView<Channel> {

    String mainUrl = "http://www.ted.com/themes/rss/id/25";//"http://www.ted.com/talks/rss";//"http://feeds.feedburner.com/tedtalks_video";http://www.ted.com/themes/rss/id/25
    ProgressDialog pd;
    private RecyclerView recyclerView;
    private MAdapter adapter = new MAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        final IPresenter presenter = new PresenterImpl(new IScheduler() {
            @Override
            public Scheduler getMain() {
                return AndroidSchedulers.mainThread();
            }

            @Override
            public Scheduler getBack() {
                return Schedulers.io();
            }
        }, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        pd.setMessage("Loading rss...");
        presenter.getData(mainUrl);
        pd.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(ArrayList<Channel> data) {
        Log.d("MainAcrtivity", data.toString());
        adapter.setItems(data);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        pd.dismiss();
    }

    @Override
    public void loadItem(Channel item) {
        //TODO new Fragment or load video and start play
    }

    @Override
    public void onError(Throwable t) {
        if (pd.isShowing())
            pd.dismiss();
        new AlertDialog.Builder(this).setMessage(t.getLocalizedMessage()).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        }).create().show();//TODO Error show
    }
}
