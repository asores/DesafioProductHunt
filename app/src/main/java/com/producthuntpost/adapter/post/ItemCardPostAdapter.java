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
import com.producthuntpost.model.posts.PostsDTO;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

public class ItemCardPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private PostsDTO postsDTO;
    private Activity activity;
    private int viewPosition;
    private Picasso picasso;
    private AdapterCallback adapterCallbackItem;


    public ItemCardPostAdapter(PostsDTO postsDTO, Activity activity, AdapterCallback adapterCallback){
        this.postsDTO = postsDTO;
        this.activity = activity;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
        this.adapterCallbackItem = adapterCallback;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViewItemCollection;
        private TextView txtInfoTitle, txtInfoDescription, txtNameUser, txtNameHeadLine, txtLabelPostOrVotes, txtVote;
        private RoundedImageView avatarUser;

        public ItemViewHolder(View view) {
            super(view);
            cardViewItemCollection = (CardView) view.findViewById(R.id.card_view_item);
            txtInfoTitle = (TextView) view.findViewById(R.id.text_name);
            txtLabelPostOrVotes = (TextView) view.findViewById(R.id.text_label_post_or_votes);
            txtVote = (TextView) view.findViewById(R.id.text_vote);
            txtInfoDescription = (TextView) view.findViewById(R.id.text_description);
            txtNameUser = (TextView) view.findViewById(R.id.text_name_user);
            txtNameHeadLine = (TextView) view.findViewById(R.id.text_name_head_line);
            avatarUser = (RoundedImageView) view.findViewById(R.id.img_avatar);
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
        final Post post = postsDTO.getCollection().getPosts().get(position);

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
        if (postsDTO != null) {
            return postsDTO.getCollection().getPosts().size();
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

   public void setCollectionsPostDTO(PostsDTO postDTO) {
        this.postsDTO = postDTO;
        notifyDataSetChanged();
    }


}



