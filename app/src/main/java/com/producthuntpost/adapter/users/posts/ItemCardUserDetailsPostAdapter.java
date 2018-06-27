package com.producthuntpost.adapter.users.posts;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.adapter.users.IUserAdapterUser.UserAdapterCallback;
import com.producthuntpost.model.Post;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemCardUserDetailsPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> listPosts;
    private Activity activity;
    private int viewPosition;
    private Picasso picasso;
    private UserAdapterCallback userAdapterCallback;


    public ItemCardUserDetailsPostAdapter(List<Post> listPosts, Activity activity, UserAdapterCallback userAdapterCallback){
        this.listPosts = listPosts;
        this.activity = activity;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
        this.userAdapterCallback = userAdapterCallback;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_item)
        CardView cardViewItemCollection;
        @BindView(R.id.text_name)
        TextView txtInfoTitle;
        @BindView(R.id.text_label_votes)
        TextView txtLabelPostOrVotes;
        @BindView(R.id.text_vote)
        TextView txtVote;
        @BindView(R.id.text_description)
        TextView txtInfoDescription;
        @BindView(R.id.text_name_user)
        TextView txtNameUser;
        @BindView(R.id.text_name_head_line)
        TextView txtNameHeadLine;
        @BindView(R.id.img_avatar)
        RoundedImageView avatarUser;


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_user_details_adapter, parent, false);
        return new ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        setViewPosition(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Post post = listPosts.get(position);

        //Post
        itemViewHolder.txtInfoTitle.setText(post.getName());
        itemViewHolder.txtLabelPostOrVotes.setText(activity.getString(R.string.label_vote));
        itemViewHolder.txtVote.setText(String.valueOf(post.getVotesCount()));
        if(post.getTagline() == null){
            itemViewHolder.txtInfoDescription.setVisibility(View.GONE);
        }else {
            itemViewHolder.txtInfoDescription.setText(post.getTagline());
            itemViewHolder.txtInfoDescription.setLines(2);
        }

        //User
        picasso.load(post.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemViewHolder.avatarUser);

        itemViewHolder.txtNameUser.setText(post.getUser().getName());
        itemViewHolder.txtNameHeadLine.setText(post.getUser().getHeadline());
        itemViewHolder.txtNameHeadLine.setLines(2);

        itemViewHolder.cardViewItemCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAdapterCallback.onMethodCallback(post.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listPosts != null) {
            return listPosts.size();
        } else {
            return 0;
        }
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }



}



