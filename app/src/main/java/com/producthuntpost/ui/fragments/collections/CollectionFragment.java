package com.producthuntpost.ui.fragments.collections;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.producthuntpost.R;
import com.producthuntpost.adapter.collections.IAdapterCollection.AdapterCollectionCallback;
import com.producthuntpost.adapter.collections.ItemCardCollectionAdapter;
import com.producthuntpost.adapter.collections.ItemListCollectionAdapter;
import com.producthuntpost.model.collections.CollectionsPostModel;
import com.producthuntpost.ui.activity.HomeActivity;
import com.producthuntpost.ui.fragments.BaseFragment;
import com.producthuntpost.ui.fragments.datepicker.DatePickerFragment;
import com.producthuntpost.ui.fragments.posts.PostFragment;
import com.producthuntpost.util.Constants;
import com.producthuntpost.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CollectionFragment extends BaseFragment implements ICollectionView, DatePickerFragment.OnDataSetListener, AdapterCollectionCallback {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.main_activity_swipe_to_refresh)
    SwipeRefreshLayout swipeLayout;


    private LinearLayoutManager mLayoutManager;
    private int savedPosition = 0;


    //Scroll
    private int COUNT_PAGE = 1;
    private boolean loadingScrollCollection = true;

    private ICollectionPresenter presenter;
    private boolean dual;
    private View view;
    private ItemCardCollectionAdapter adapterCard;
    private ItemListCollectionAdapter adapterList;


    public static CollectionFragment newInstance() {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity)getActivity()).setTitle(R.string.title_fragment_collection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_collections, container, false);
        ButterKnife.bind(this, view);

        presenter = new CollectionPresenterImpl();
        presenter.setView(this);
        if (savedInstanceState == null)
            presenter.getCollectionToday();

        dual = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        initSwipeRefresh();

        return view;
    }


    //List CardView
    private void initListCard() {
        if (COUNT_PAGE == 1) {
            mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setOnScrollListener(scrollListener);
            adapterCard = new ItemCardCollectionAdapter(presenter.getCollectionDTO(), getActivity(), this);
            recyclerView.setAdapter(adapterCard);
            adapterCard.notifyDataSetChanged();

            //Scroll Infinite
            recyclerView.addOnScrollListener(createInfiniteScrollListener());
            recyclerView.scrollToPosition(savedPosition);
        } else {
            adapterCard.setCollectionsPostModel(presenter.getCollectionDTO());

        }
    }

    //List View
    private void initList() {
        if (COUNT_PAGE == 1) {
            mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setOnScrollListener(scrollListener);

            adapterList = new ItemListCollectionAdapter(presenter.getCollectionDTO(), getActivity(), this);
            recyclerView.setAdapter(adapterList);
            adapterList.notifyDataSetChanged();

            //Scroll Infinite
            recyclerView.addOnScrollListener(createInfiniteScrollListener());
            recyclerView.scrollToPosition(savedPosition);
        } else {
            adapterList.setCollectionsPostModel(presenter.getCollectionDTO());
        }
    }

    private void initSwipeRefresh() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                presenter.getCollectionToday();
                savedPosition = 0;
            }
        });
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    @Override
    public void getCollectionData() {
        if (dual) {
            initList();
        } else {
            initListCard();
        }



        loadingScrollCollection = true;
        swipeLayout.setRefreshing(false);

        //Close  Diaolog
        hideLoading();
    }

    @Override
    public void getCollectionFilterDate() {
    }

    @Override
    public void getPostOrder() {
        if (dual) {
            adapterList.setCollectionsPostModel(presenter.getCollectionDTO());
        } else {
            adapterCard.setCollectionsPostModel(presenter.getCollectionDTO());
        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constants.STATE_SCROLL_POSITION, savedPosition);
        outState.putSerializable(Constants.POST_DATA, presenter.getCollectionDTO());
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setCollectionDTO((CollectionsPostModel) savedInstanceState.getSerializable(Constants.POST_DATA));
            savedPosition = savedInstanceState.getInt(Constants.STATE_SCROLL_POSITION);
            if (dual) {
                initList();
            } else {
                initListCard();
            }
        }

    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (dual) {
                savedPosition = adapterList.getViewPosition();
            } else {
                savedPosition = adapterCard.getViewPosition();
            }

        }
    };

    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(Constants.MAX_ITEM_PER_REQUEST, mLayoutManager) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                if(loadingScrollCollection){
                    COUNT_PAGE += COUNT_PAGE;
                    presenter.getCollectionAll(COUNT_PAGE);
                    loadingScrollCollection = false;
                }

            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_vote:
                presenter.sortNumberPost();
                return true;
            case R.id.menu_title:
                presenter.sortTitle();
                return true;
            case R.id.menu_creator:
                presenter.sortCreateUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void selectDate(int i, int i1, int i2) {
        System.out.println(Utils.formatDate(i, i1, i2));
    }

    @Override
    public void onMethodCallback(int idCollectionSelect) {
        callFragmentPos(idCollectionSelect);
    }

    private void callFragmentPos(int idCollectionSelect){
        Fragment postFragment = PostFragment.newInstance(idCollectionSelect);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.add(R.id.frame_fragment, postFragment);
        fragmentTransaction.addToBackStack(Constants.NAME_FRAGMENT_SAVE_STATE);
        fragmentTransaction.commit();
    }
}