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
import com.retor.tedtest.dataloader.IPresenter;
import com.retor.tedtest.main.fragments.recycler.NewsList;
import com.retor.tedtest.main.interfaces.IView;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements IView<Channel>, IPresenter {

    private String mainUrl = "http://www.ted.com/themes/rss/id/6";
    private String[] mainUrls = {mainUrl, "http://www.ted.com/themes/rss/id/25", "http://www.ted.com/themes/rss/id/5", "http://www.ted.com/themes/rss/id/2"};
    private ProgressDialog pd;
    private Content content;
    private IView<Channel> view;
    private Fragment newsList;
    private Drawer.Result drawer;
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null && savedInstanceState.getSerializable("array") != null) {
            restoreBundle(savedInstanceState);
        } else {
            nullBundle();
        }
    }

    private void restoreBundle(Bundle savedInstanceState) {
        content = (Content) savedInstanceState.getSerializable("array");
        newsList = getSupportFragmentManager().findFragmentByTag("list");
        view = (IView<Channel>) newsList;
        if (content.hasChannels()) {
            lastPosition = savedInstanceState.getInt("position", 0);
            loadData(content.getChannels());
        } else {
            takeData(mainUrls);
        }
    }

    private void nullBundle() {
        newsList = new NewsList();
        view = (IView<Channel>) newsList;
        getSupportFragmentManager().beginTransaction().add(R.id.frame, newsList, "list").commit();
        takeData(mainUrls);
    }

    private boolean isNetworkConnected() {
        NetworkInfo ni = ((ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    private void showNetworkAlert() {
        android.support.v7.app.AlertDialog al = DialogsBuilder.createAlert(this, "No internet connection");
        al.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                MainActivity.this.finish();
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
            IPresenter presenter = new DataLoader(this, this);
            presenter.getData(url);
            createShowProgress();
        } else {
            showNetworkAlert();
        }
    }

    private void reLoad(Channel item) {
        view.loadItem(item);
        for (int i = 0; i < content.getChannels().size(); i++) {
            String tmpTitle = content.getChannel(i).getTitle();
            if (item.getTitle().equalsIgnoreCase(tmpTitle)) {
                content.getChannels().remove(i);
                content.getChannels().add(i, item);
            }
        }
    }

    private void firstLoad(Channel item) {
        content = new Content(new ArrayList<Channel>());
        content.addChannel(item);
        reLoad(item);
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
        return new Drawer()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.fragment_navigation_drawer)
                .addDrawerItems(items)
                .build();
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
    public void loadData(ArrayList<Channel> data) {//TODO Drawer
        content = new Content(data);
        drawer = initDrawer(data);
        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                lastPosition = i / 2;
                MainActivity.this.view.loadItem(content.getChannels().get(i / 2));
            }
        });
        drawer.openDrawer();
        view.loadItem(content.getChannel(lastPosition));
        clearProgressDialog();
    }

    @Override
    public void loadItem(Channel item) {
        if (content == null) {
            firstLoad(item);
        } else {
            reLoad(item);
        }
        clearProgressDialog();
    }

    @Override
    public void onError(Throwable t) {
        Log.d("Error", t.getLocalizedMessage());
        clearProgressDialog();
        DialogsBuilder.createAlert(this, t.getLocalizedMessage()).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (content != null) {
            outState.putSerializable("array", content);
        } else {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("list")).commit();
        }
        if (lastPosition > 0)
            outState.putInt("position", lastPosition);
        clearProgressDialog();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void getData(String... url) {
        takeData(url);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
            super.onBackPressed();
    }
}
