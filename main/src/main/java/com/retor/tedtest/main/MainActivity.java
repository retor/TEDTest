package com.retor.tedtest.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ted.loader.TedNews;
import ted.loader.interfaces.IPresenter;
import ted.loader.interfaces.IView;
import ted.loader.presenter.PresenterImpl;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements IView<TedNews> {

    String mainUrl = "http://www.ted.com/talks/rss";
    private RecyclerView recyclerView;
    private MAdapter adapter = new MAdapter();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        IPresenter presenter = new PresenterImpl(this);
        presenter.getData(mainUrl);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        pd.setMessage("Loading rss...");
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
    public void update(ArrayList<TedNews> data) {
        Log.d("MainAcrtivity", data.toString());
        adapter.setItems(data);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        pd.dismiss();
    }

    @Override
    public void loadItem(TedNews item) {
        //TODO new Fragment or load video and start play
    }

    @Override
    public void onError(Throwable t) {
        DialogFragment.instantiate(getApplicationContext(), "Error");//TODO Error show
    }
}
