package com.retor.tedtest.main.fragments.newslist_cardslib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import com.parser.beans.Channel;
import com.parser.beans.Item;
import com.parser.beans.Media;
import com.retor.tedtest.dataloader.IModel;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.fragments.video.VideoPlay;
import com.retor.tedtest.main.interfaces.IView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

import java.util.ArrayList;

/**
 * Created by retor on 27.05.2015.
 */
public class News extends Fragment implements IView<Channel> {
    private CardRecyclerView cardRecyclerView;
    private IModel presenter;
    private SwipeRefreshLayout swiper;
    private Channel channel;
    private CardArrayRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        presenter = (IModel) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null && (channel = (Channel) savedInstanceState.getSerializable("channel")) != null) {
            adapter = new CardArrayRecyclerViewAdapter(getActivity(), fillCardList(channel));
        } else {
            adapter = new CardArrayRecyclerViewAdapter(getActivity(), new ArrayList<Card>());
        }
        View out = inflater.inflate(R.layout.news_cards, container, false);
        swiper = (SwipeRefreshLayout) out.findViewById(R.id.swipe);
        cardRecyclerView = (CardRecyclerView) out.findViewById(R.id.cardList);
        cardRecyclerView.setHasFixedSize(false);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initSwipeRefresh(swiper);
        cardRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ?
                                0 : recyclerView.getChildAt(0).getTop();
                swiper.setEnabled(topRowVerticalPosition >= 0);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        cardRecyclerView.setAdapter(adapter);
        return out;
    }

    private ArrayList<Card> fillCardList(Channel channel) {
        ArrayList<Card> out = new ArrayList<>();
        for (final Item item : channel.getItems()) {
            final Card card = new Card(getActivity());
            ThumbCard thumb = new ThumbCard(getActivity());
            thumb.setExternalUsage(true);
            thumb.setTitle(item.getDurationiTunes());
            thumb.setUrlResource(item.getImageTunes());
            CardHeader header = new CardHeader(getActivity());
            header.setPopupMenu(R.menu.menu_main,
                    new CardHeader.OnClickCardHeaderPopupMenuListener() {
                        @Override
                        public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case 0:
                                    startVideo(item, menuItem.getItemId() * 2);
                                    break;
                                case 1:
                                    startVideo(item, menuItem.getItemId() * 2);
                                    break;
                                case 2:
                                    startVideo(item, menuItem.getItemId() * 2);
                                    break;
                                case 3:
                                    startVideo(item, menuItem.getItemId() * 2);
                                    break;
                            }
                        }
                    }, new CardHeader.OnPrepareCardHeaderPopupMenuListener() {
                        @Override
                        public boolean onPreparePopupMenu(BaseCard baseCard, PopupMenu popupMenu) {
                            Media tmp;
                            for (int i = 0; i < item.getContent().size(); i++) {
                                tmp = item.getContent().get(i);
                                popupMenu.getMenu().add(tmp.getBitRate() + "bps" + " (" + (tmp.getFileSize() / 1024) + " kB)");
                                i++;
                            }
                            return true;
                        }
                    });
            header.setTitle(item.getTitle());
            card.addCardHeader(header);
            card.setTitle(Html.fromHtml(item.getDescription()).toString());
            card.addCardThumbnail(thumb);
            card.setShadow(true);
            out.add(card);
        }
        return out;
    }

    private void startVideo(Item item, int id) { /*Starts the Video player activity and load video with selected bitrate*/
        startActivity(new Intent(getActivity(), VideoPlay.class).putExtra("url", item.getContent().get(id).getUrl()));
    }


    private void initSwipeRefresh(final SwipeRefreshLayout input) {
        input.setVerticalScrollBarEnabled(true);
        input.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                input.setRefreshing(false);
                if (channel!=null){
                    presenter.getData(channel.getRssUrl());
                }else{
                    presenter.getData("");
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("channel", channel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void loadItem(Channel item) {
        if (item!=null){
            channel = item;
            adapter.clear();
            adapter.addAll(fillCardList(item));
            adapter.notifyDataSetChanged();
//            cardRecyclerView.smoothScrollToPosition(0);
            cardRecyclerView.scrollToPosition(0);
        }
    }
}
