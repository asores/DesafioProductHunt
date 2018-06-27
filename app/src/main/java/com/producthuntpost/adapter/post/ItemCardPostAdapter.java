package com.producthuntpost.adapter.post;

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
import com.producthuntpost.adapter.post.IAdapterPost.AdapterCallback;
import com.producthuntpost.model.Post;
import com.producthuntpost.model.posts.PostsModel;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemCardPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private PostsModel postsModel;
    private Activity activity;
    private int viewPosition;
    private Picasso picasso;
    private AdapterCallback adapterCallbackItem;


    public ItemCardPostAdapter(PostsModel postsModel, Activity activity, AdapterCallback adapterCallback){
        this.postsModel = postsModel;
        this.activity = activity;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
        this.adapterCallbackItem = adapterCallback;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_item)
        CardView cardViewItemCollection;
        @BindView(R.id.text_name)
        TextView txtInfoTitle;
        @BindView(R.id.text_description)
        TextView txtInfoDescription;
        @BindView(R.id.text_name_user)
        TextView txtNameUser;
        @BindView(R.id.text_name_head_line)
        TextView txtNameHeadLine;
        @BindView(R.id.text_label_post_or_votes)
        TextView txtLabelPostOrVotes;
        @BindView(R.id.text_vote)
        TextView txtVote;
        @BindView(R.id.img_avatar)
        RoundedImageView avatarUser;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_adapter, parent, false);
        return new ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        setViewPosition(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Post post = postsModel.getCollection().getPosts().get(position);

        itemViewHolder.txtInfoTitle.setText(post.getName());
        itemViewHolder.txtLabelPostOrVotes.setText(activity.getString(R.string.label_vote));
        itemViewHolder.txtVote.setText(String.valueOf(post.getVotesCount()));
        if(post.getTagline() == null){
            itemViewHolder.txtInfoDescription.setVisibility(View.GONE);
        }else {
            itemViewHolder.txtInfoDescription.setText(post.getTagline());
        }

        //User
        picasso.load(post.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemViewHolder.avatarUser);

        itemViewHolder.txtNameUser.setText(post.getUser().getName());
        itemViewHolder.txtNameHeadLine.setText(post.getUser().getHeadline());

        itemViewHolder.cardViewItemCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallbackItem.onMethodCallback(post.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (postsModel != null) {
            return postsModel.getCollection().getPosts().size();
        } else {
            return 0;
        }
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }

   public void setCollectionsPostDTO(PostsModel postDTO) {
        this.postsModel = postDTO;
        notifyDataSetChanged();
    }


}



