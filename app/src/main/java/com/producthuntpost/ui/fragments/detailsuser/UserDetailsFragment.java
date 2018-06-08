package com.producthuntpost.ui.fragments.detailsuser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.adapter.users.IUserAdapterUser.UserAdapterCallback;
import com.producthuntpost.adapter.users.posts.ItemCardUserDetailsPostAdapter;
import com.producthuntpost.adapter.users.votes.ItemCardUserDetailsVotesAdapter;
import com.producthuntpost.ui.activity.HomeActivity;
import com.producthuntpost.ui.fragments.BaseFragment;
import com.producthuntpost.ui.fragments.detailspost.DetailsPostFragment;
import com.producthuntpost.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsFragment extends BaseFragment implements IUserDetailsView, UserAdapterCallback{
    @BindView(R.id.text_name_user)
    TextView txtNameUser;
    @BindView(R.id.text_name_head_line)
    TextView txtNameHeadLine;
    @BindView(R.id.img_avatar)
    RoundedImageView imgAvatarUser;
    @BindView(R.id.rc_view_posts_user)
    RecyclerView rcViewPostsUser;
    @BindView(R.id.rc_view_votes_user)
    RecyclerView rcViewVotesUser;
    @BindView(R.id.text_label_user_post)
    TextView txtLabelUserPost;
    @BindView(R.id.text_label_user_vote)
    TextView txtLabelUserVote;
    @BindView(R.id.text_label_user_instruction_post)
    TextView txtLabelUserInstructionPost;
    @BindView(R.id.text_label_user_instruction_vote)
    TextView txtLabelUserInstructionVote;


    private static final String ARG_PARAM1 = "param1";
    private IUserDetailsPresenter presenter;
    private int idUserSelect;
    private LinearLayoutManager mLayoutManager;
    private View view;

    //Adapter Post
    private ItemCardUserDetailsPostAdapter adapterCardUserDetailsPost;

    //Adapter Votes
    private ItemCardUserDetailsVotesAdapter adapterCardUserDetailsVotes;


    public static UserDetailsFragment newInstance(int idPostSelect) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, idPostSelect);
        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setTitle(R.string.title_fragment_user_details);
        setHasOptionsMenu(true);
        presenter = new UserDetailsPresenterImpl();
        if (getArguments() != null) {
            idUserSelect = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);
        presenter.requestUserDetails(idUserSelect);
        return view;
    }

    private void initViews() {
        imgAvatarUser.setVisibility(View.INVISIBLE);
        if (presenter.getUserDetails() != null) {
            txtNameUser.setText(presenter.getNameUser());
            txtNameHeadLine.setText(presenter.getHeadLineUser());
            presenter.setAvatarUser(imgAvatarUser);
        }

        initCardListPostsUser();
        initCardListVotesUser();
        hideLoading();
    }

    //Posts
    private void initCardListPostsUser(){
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcViewPostsUser.setHasFixedSize(true);
        rcViewPostsUser.setLayoutManager(mLayoutManager);
        adapterCardUserDetailsPost = new ItemCardUserDetailsPostAdapter(presenter.getListPostsUser(), getActivity(), this);
        rcViewPostsUser.setAdapter(adapterCardUserDetailsPost);
        adapterCardUserDetailsPost.notifyDataSetChanged();

        if(presenter.getListPostsUser().size() > 0){
            txtLabelUserPost.setText(getString(R.string.label_details_post_creator));
        }
        if(presenter.getListPostsUser().size() > 1){
            txtLabelUserInstructionPost.setVisibility(View.VISIBLE);
        }

    }


    //Votes
    private void initCardListVotesUser(){
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcViewVotesUser.setHasFixedSize(true);
        rcViewVotesUser.setLayoutManager(mLayoutManager);
        adapterCardUserDetailsVotes = new ItemCardUserDetailsVotesAdapter(presenter.getListVotesUser(), getActivity(), this);
        rcViewVotesUser.setAdapter(adapterCardUserDetailsVotes);
        adapterCardUserDetailsVotes.notifyDataSetChanged();

        if(presenter.getListVotesUser().size() > 0){
            txtLabelUserVote.setText(getString(R.string.label_details_vote_creator));
        }
        if(presenter.getListVotesUser().size() > 1){
            txtLabelUserInstructionVote.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void setDetailsUserData() {
        initViews();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeActivity) getActivity()).setTitle(R.string.title_fragment_details);
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
}
