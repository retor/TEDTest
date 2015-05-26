package com.retor.tedtest.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.retor.tedtest.dataloader.IPresenter;
import com.retor.tedtest.main.fragments.recycler.NewsList;
import com.retor.tedtest.main.interfaces.IView;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements Observer<List<Channel>>, IPresenter {

    private String mainUrl = "http://www.ted.com/themes/rss/id/6";
    private String[] mainUrls = {mainUrl, "http://www.ted.com/themes/rss/id/25", "http://www.ted.com/themes/rss/id/5", "http://www.ted.com/themes/rss/id/2"};
    private ProgressDialog pd;
    private Content content;
    private IView<Channel> view;
    private Fragment newsList;
    private Drawer.Result drawer;
    private int lastPosition = 0;
    private boolean firstLoad = true;
    private IPresenter presenter = new DataLoader(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag("list") == null) {
            newsList = new NewsList();
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
            if (presenter==null)
                presenter = new DataLoader(this, this);
            if (url == null) {
                presenter.getData(mainUrls);
            } else {
                presenter.getData(url);
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
        drawer = initDrawer(content.getChannels());
        if (firstLoad) {
            drawer.openDrawer();
            if (drawer.isDrawerOpen())
                firstLoad = false;
            view.loadItem(content.getChannel(lastPosition));
        }
        clearProgressDialog();
    }

    private Drawer.Result initDrawer(ArrayList<Channel> data) {
        IDrawerItem[] items = new IDrawerItem[data.size() * 2];
        int i = 0;
        for (Channel c : data) {
            items[i] = new PrimaryDrawerItem().withName(c.getTitle()).withIcon(FontAwesome.Icon.faw_eye).withBadge(String.valueOf(c.getItems().size())).withIdentifier(i);
            i++;
            items[i] = new DividerDrawerItem();
            i++;
        }
        Drawer.Result out = new Drawer()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.fragment_navigation_drawer)
                .addDrawerItems(items)
                .addDrawerItems(new PrimaryDrawerItem().withName("Refresh All").withIcon(FontAwesome.Icon.faw_refresh).withIdentifier(i))
                .build();
        out.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                if (content.getChannels().size() > i / 2) {
                    lastPosition = i / 2;
                    MainActivity.this.view.loadItem(content.getChannels().get(i / 2));
                }
                if (content.getChannels().size() == i / 2) {
                    getData(mainUrls);
                }
            }
        });
        return out;
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
    public void onCompleted() {
        if (content != null)
            firstLoad();
    }

    @Override
    public void onError(Throwable e) {
//        Log.d("Error", e.getLocalizedMessage());
        e.printStackTrace();
        clearProgressDialog();
        DialogsBuilder.createAlert(this, "Opss... " + e.getCause().toString()).show();
        view.onError(e);
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
