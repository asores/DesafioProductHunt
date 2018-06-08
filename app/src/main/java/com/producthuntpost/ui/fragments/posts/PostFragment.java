package com.producthuntpost.ui.fragments.posts;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.producthuntpost.adapter.post.IAdapterPost.AdapterCallback;
import com.producthuntpost.adapter.post.ItemCardPostAdapter;
import com.producthuntpost.adapter.post.ItemListPostAdapter;
import com.producthuntpost.model.posts.PostsDTO;
import com.producthuntpost.ui.activity.HomeActivity;
import com.producthuntpost.ui.fragments.BaseFragment;
import com.producthuntpost.ui.fragments.datepicker.DatePickerFragment;
import com.producthuntpost.ui.fragments.detailspost.DetailsPostFragment;
import com.producthuntpost.util.Constants;
import com.producthuntpost.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostFragment extends BaseFragment implements IPostView, DatePickerFragment.OnDataSetListener, AdapterCallback {
    @BindView(R.id.recyclerViewPost)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private int savedPosition = 0;

    //Scroll
    private boolean isUpdateScrollInfinite = false;
    private int COUNT_PAGE = 1;

    private IPostPresenter presenter;
    private boolean dual;
    private View view;
    private ItemCardPostAdapter adapterCardPost;
    private ItemListPostAdapter adapterListPost;
    private int idPostSelect = 0;


    public static PostFragment newInstance(int idCollectionSelect) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, idCollectionSelect);
        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity)getActivity()).setTitle(R.string.title_fragment_post);
        setHasOptionsMenu(true);
        presenter = new PostPresenterImpl();
        if (getArguments() != null) {
            idPostSelect = getArguments().getInt(Constants.ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);
        if (savedInstanceState == null)
            presenter.getPost(idPostSelect);
        return view;
    }


    //Lista com CardView
    private void initListCard() {
        if (COUNT_PAGE == 1) {
            mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setOnScrollListener(scrollListener);
            adapterCardPost = new ItemCardPostAdapter(presenter.getPostDTO(), getActivity(), this);
            recyclerView.setAdapter(adapterCardPost);
            adapterCardPost.notifyDataSetChanged();

            //Scroll Infinite
            recyclerView.addOnScrollListener(createInfiniteScrollListener());
            recyclerView.scrollToPosition(savedPosition);
        } else {
            adapterCardPost.setCollectionsPostDTO(presenter.getPostDTO());
            if (!isUpdateScrollInfinite)
                isUpdateScrollInfinite = true;
        }
    }

    //Lista sem CardView
    private void initList() {
        if (COUNT_PAGE == 1) {
            mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setOnScrollListener(scrollListener);

            adapterListPost = new ItemListPostAdapter(presenter.getPostDTO(), getActivity(), this);
            recyclerView.setAdapter(adapterListPost);
            adapterListPost.notifyDataSetChanged();

            //Scroll Infinite
            recyclerView.addOnScrollListener(createInfiniteScrollListener());
            recyclerView.scrollToPosition(savedPosition);
        } else {
            adapterListPost.setCollectionsPostDTO(presenter.getPostDTO());

            if (!isUpdateScrollInfinite)
                isUpdateScrollInfinite = true;
        }

    }


    @Override
    public void getPostData() {
        if (Utils.getRotatioScreen(getContext())) {
            initList();
        } else {
            initListCard();
        }

        //Fecha o Diaolog de Carregamento
        hideLoading();
    }

    @Override
    public void getPostFilterDate() {

    }

    @Override
    public void getPostOrder() {
        if (Utils.getRotatioScreen(getContext())) {
            adapterListPost.setCollectionsPostDTO(presenter.getPostDTO());
        } else {
            adapterCardPost.setCollectionsPostDTO(presenter.getPostDTO());
        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constants.STATE_SCROLL_POSITION, savedPosition);
        outState.putSerializable(Constants.POST_DATA, presenter.getPostDTO());
        outState.putBoolean(Constants.STATE_SCROLL_INFINITE, isUpdateScrollInfinite);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            presenter.setCollectionDTO((PostsDTO) savedInstanceState.getSerializable(Constants.POST_DATA));
            savedPosition = savedInstanceState.getInt(Constants.STATE_SCROLL_POSITION);
            isUpdateScrollInfinite = savedInstanceState.getBoolean(Constants.STATE_SCROLL_INFINITE);
            if (Utils.getRotatioScreen(getContext())) {
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
            if (Utils.getRotatioScreen(getContext())) {
                savedPosition = adapterListPost.getViewPosition();
            } else {
                savedPosition = adapterCardPost.getViewPosition();
            }

        }
    };

    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(Constants.MAX_ITEM_PER_REQUEST, mLayoutManager) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                if (isUpdateScrollInfinite) {
                    COUNT_PAGE += COUNT_PAGE;
                    presenter.getPostAll(COUNT_PAGE);
                    isUpdateScrollInfinite = false;
                }
            }
        };
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_post, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_all:
                isUpdateScrollInfinite = true;
                presenter.getPostAll(COUNT_PAGE);
                return true;
            case R.id.menu_date:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getActivity().getFragmentManager(), "datePicker");
                datePickerFragment.setClickDateListener(this);
                return true;
            case R.id.menu_post:
                presenter.sortNumberVotes();
                return true;
            case R.id.menu_title_post:
                presenter.sortTitle();
                return true;
            case R.id.menu_creat_user:
                presenter.sortCreateUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void selectDate(int i, int i1, int i2) {
        presenter.getPostDay(Utils.formatDate(i, i1, i2));
    }

    @Override
    public void onMethodCallback(int idPostSelect) {
        Fragment detailsPostFragment = DetailsPostFragment.newInstance(idPostSelect);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.add(R.id.frame_fragment, detailsPostFragment);
        fragmentTransaction.addToBackStack(Constants.NAME_FRAGMENT_SAVE_STATE);
        fragmentTransaction.commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeActivity)getActivity()).setTitle(R.string.title_fragment_collection);
    }
}