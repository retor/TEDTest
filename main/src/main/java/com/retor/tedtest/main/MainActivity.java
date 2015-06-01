package com.retor.tedtest.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.parser.beans.Channel;
import com.retor.tedtest.dataloader.DataLoader;
import com.retor.tedtest.dataloader.IModel;
import com.retor.tedtest.dataloader.SimpleRxModel;
import com.retor.tedtest.main.fragments.newslist_cardslib.News;
import com.retor.tedtest.main.help.Content;
import com.retor.tedtest.main.help.DialogsBuilder;
import com.retor.tedtest.main.interfaces.IView;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements Observer<List<Channel>>, IModel {

    private String mainUrl = "http://www.ted.com/themes/rss/id/6";
    private String[] mainUrls = {mainUrl, "http://www.ted.com/themes/rss/id/4", "http://www.ted.com/themes/rss/id/16", "http://www.ted.com/themes/rss/id/3", "http://www.ted.com/themes/rss/id/17", "http://www.ted.com/themes/rss/id/10", "http://www.ted.com/themes/rss/id/7", "http://www.ted.com/themes/rss/id/12",  "http://www.ted.com/themes/rss/id/25", "http://www.ted.com/themes/rss/id/5", "http://www.ted.com/themes/rss/id/2"};
    private ProgressDialog pd;
    private Content content;
    private IView<Channel> view;
    private Fragment newsList;
    private Drawer.Result drawer;
    private int lastPosition = 0;
    private boolean firstLoad = true;
//    private IModel model = new DataLoader(this, this);
    private IModel model = new SimpleRxModel(this, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag("list") == null) {
            newsList = new News();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, newsList, "list").commit();
        }else{
            newsList = getSupportFragmentManager().findFragmentByTag("list");
        }
        if (savedInstanceState != null && savedInstanceState.getSerializable("array") != null) {
            restoreBundle(savedInstanceState);
        } else {
            nullBundle();
        }
    }

    private void restoreBundle(Bundle savedInstanceState) {
        content = (Content) savedInstanceState.getSerializable("array");
        firstLoad = savedInstanceState.getBoolean("state", false);
        view = (IView<Channel>) newsList;
        if (content.hasChannels()) {
            lastPosition = savedInstanceState.getInt("position", 0);
            firstLoad();
        } else {
            takeData(mainUrls);
        }
    }

    private void nullBundle() {
        view = (IView<Channel>) newsList;
        takeData(mainUrls);
    }

    private boolean isNetworkConnected() {
        NetworkInfo ni = ((ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    private void showNetworkAlert() {
        android.app.AlertDialog al = DialogsBuilder.createAlert(this, "No internet connection");
        al.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        al.show();
    }

    private void createShowProgress() {
        pd = DialogsBuilder.createProgress(this, "Loading rss...");
        pd.show();
    }

    private void clearProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

    private void takeData(String... url) {
        if (isNetworkConnected()) {
            if (model ==null)
                model = new DataLoader(this, this);
            if (url == null) {
                model.getData(mainUrls);
            } else {
                model.getData(url);
            }
            createShowProgress();
        } else {
            showNetworkAlert();
        }
    }

    private void reLoad(Channel item) {
        view.loadItem(item);
        content.addChannelWithCheck(item);
        clearProgressDialog();
    }

    private void firstLoad() {
        drawer = initDrawer(content);
        if (firstLoad) {
            drawer.openDrawer();
            if (drawer.isDrawerOpen())
                firstLoad = false;
            view.loadItem(content.getChannel(lastPosition));
        }
        clearProgressDialog();
    }

    private Drawer.Result initDrawer(final Content data) {
        Drawer.Result out = new Drawer()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.fragment_navigation_drawer)
                .addDrawerItems(fillDrawerMenu(data.getChannels()))
                .addDrawerItems(new PrimaryDrawerItem().withName("Change View").withIcon(FontAwesome.Icon.faw_bomb).setEnabled(false))
                .addDrawerItems(new DividerDrawerItem())
                .addDrawerItems(new PrimaryDrawerItem().withName("Refresh All").withIcon(FontAwesome.Icon.faw_refresh))
                .build();
        out.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                Log.d("Checked channel", " "+i);
                if (data.getChannels().size() > i / 2) {
                    lastPosition = i / 2;
                    Log.d("Checked channel", data.getChannels().get(lastPosition).getTitle() + " " + lastPosition);
                    MainActivity.this.view.loadItem(data.getChannels().get(lastPosition));
                }
                if (data.getChannels().size()+1 == i/2) {
                    getData(mainUrls);
                }
            }
        });
        return out;
    }

    private IDrawerItem[] fillDrawerMenu(ArrayList<Channel> data){
        IDrawerItem[] items = new IDrawerItem[data.size() * 2];
        int i = 0;
        for (Channel c : data) {
            items[i] = new PrimaryDrawerItem().withName(c.getTitle()).withIcon(FontAwesome.Icon.faw_eye).withBadge(String.valueOf(c.getItems().size())).withIdentifier(i);
            i++;
            items[i] = new DividerDrawerItem();
            i++;
        }
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompleted() {
        if (content != null)
            firstLoad();
    }

    @Override
    public void onError(Throwable e) {
//        Log.d("Error", e.getLocalizedMessage());
        e.printStackTrace();
        clearProgressDialog();
        if (e.getCause()!=null)
            DialogsBuilder.createAlert(this, "Opss... " + e.getCause().toString()).show();
        else
            DialogsBuilder.createAlert(this, "Opss... " + e.toString()).show();
    }

    @Override
    public void onNext(List<Channel> channels) {
        if (channels.size() == 1) {
            reLoad(channels.get(0));
        } else if (channels.size() > 1) {
            content = new Content((ArrayList<Channel>) channels);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        clearProgressDialog();
        if (content != null) {
            outState.putSerializable("array", content);
            outState.putInt("position", lastPosition);
            outState.putBoolean("state", firstLoad);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void getData(String... url) {
        takeData(url);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
            super.onBackPressed();
    }
}
