package com.producthuntpost.ui.fragments.detailspost;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.producthuntpost.R;
import com.producthuntpost.adapter.details.IAdapterPost.DetailsUserVoteAdapterCallback;
import com.producthuntpost.adapter.details.ItemCardUserVoteAdapter;
import com.producthuntpost.adapter.details.ItemListUserVoteAdapter;
import com.producthuntpost.adapter.details.comments.ItemListCommentAdapter;
import com.producthuntpost.ui.activity.HomeActivity;
import com.producthuntpost.ui.fragments.BaseFragment;
import com.producthuntpost.ui.fragments.detailsuser.UserDetailsFragment;
import com.producthuntpost.util.Constants;
import com.producthuntpost.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsPostFragment extends BaseFragment implements IDetailsView, View.OnClickListener, DetailsUserVoteAdapterCallback, View.OnKeyListener {
    @BindView(R.id.text_titulo_details)
    TextView txtTituloDetails;
    @BindView(R.id.text_description_details)
    TextView txtDescriptionDetails;
    @BindView(R.id.text_label_user_vote)
    TextView txtLabelUserVotes;
    @BindView(R.id.text_send_details_creator)
    TextView txtSendDetailsCreator;
    @BindView(R.id.recycler_view_user_post)
    RecyclerView recyclerViewUserPost;
    @BindView(R.id.bottom_sheet)
    View bottomSheetComment;
    @BindView(R.id.text_view_commet)
    TextView txtViewComment;
    @BindView(R.id.recycle_view_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.text_label_user_instruction)
    TextView txtLabelUserInstruction;

    private static final String ARG_PARAM1 = "param1";
    private BottomSheetBehavior mBottomSheetBehavior;
    private View view;
    private IDetailsPresenter presenter;
    private int idPostSelect;
    private ItemCardUserVoteAdapter adapterCardUserVote;
    private ItemListUserVoteAdapter adapterListUserVote;
    private ItemListCommentAdapter adapterListComments;
    private LinearLayoutManager mLayoutManager;


    public static DetailsPostFragment newInstance(int idPostSelect) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, idPostSelect);
        DetailsPostFragment fragment = new DetailsPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setTitle(R.string.title_fragment_details);
        setHasOptionsMenu(true);
        presenter = new DetailsPresenterImpl();
        if (getArguments() != null) {
            idPostSelect = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details_post, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);
        presenter.getDetailsPost(idPostSelect);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);
        return view;
    }

    @Override
    public void setDetailsPost() {
        initViews();
    }

    private void initViews() {
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetComment);
        if (presenter.getDetailsPostModel() != null) {
            txtTituloDetails.setText(presenter.getNameUser());
            txtDescriptionDetails.setText(presenter.getDescriptionUser());
            txtLabelUserVotes.setText(getString(R.string.label_voters));
            txtSendDetailsCreator.setText(getString(R.string.label_details_creator));

            txtSendDetailsCreator.setOnClickListener(this);
            txtViewComment.setOnClickListener(this);

            if (Utils.getRotatioScreen(getContext())) {
                initListUserVote();
            } else {
                initCardUserVote();
            }
        }
    }

    private void initCardUserVote() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewUserPost.setHasFixedSize(true);
        recyclerViewUserPost.setLayoutManager(mLayoutManager);
        adapterCardUserVote = new ItemCardUserVoteAdapter(presenter.getDetailsPostModel(), getActivity(), this);
        recyclerViewUserPost.setAdapter(adapterCardUserVote);
        adapterCardUserVote.notifyDataSetChanged();

        initListComment();
        hideLoading();
    }

    private void initListUserVote() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewUserPost.setHasFixedSize(true);
        recyclerViewUserPost.setLayoutManager(mLayoutManager);
        adapterListUserVote = new ItemListUserVoteAdapter(presenter.getDetailsPostModel(), getActivity(), this);
        recyclerViewUserPost.setAdapter(adapterListUserVote);
        adapterListUserVote.notifyDataSetChanged();
        initListComment();
        hideLoading();
    }

    private void initListComment() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewComment.setHasFixedSize(true);
        recyclerViewComment.setLayoutManager(mLayoutManager);
        adapterListComments = new ItemListCommentAdapter(presenter.getComments(), getActivity());
        recyclerViewComment.setAdapter(adapterListComments);
        adapterListComments.notifyDataSetChanged();
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeActivity) getActivity()).setTitle(R.string.title_fragment_post);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_commet:
                sheetBehaviorChanged();
                break;
            case R.id.text_send_details_creator:
                onMethodCallback(presenter.getIdUserCreatorPost());
        }
    }

    private void sheetBehaviorChanged() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onMethodCallback(int idUserSelect) {
        Fragment userDetailsFragment = UserDetailsFragment.newInstance(idUserSelect);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.add(R.id.frame_fragment, userDetailsFragment);
        fragmentTransaction.addToBackStack(Constants.NAME_FRAGMENT_SAVE_STATE);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
