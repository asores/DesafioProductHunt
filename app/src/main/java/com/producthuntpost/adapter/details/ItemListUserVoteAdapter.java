package com.producthuntpost.adapter.details;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.adapter.details.IAdapterPost.DetailsUserVoteAdapterCallback;
import com.producthuntpost.model.posts.details.DetailsPostModel;
import com.producthuntpost.model.posts.details.Vote;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListUserVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DetailsPostModel detailsPostModel;
    private Activity activity;
    private DetailsUserVoteAdapterCallback adapterCallback;
    private Picasso picasso;

    public ItemListUserVoteAdapter(DetailsPostModel detailsPostModel, Activity activity, DetailsUserVoteAdapterCallback adapterCallback){
        this.detailsPostModel = detailsPostModel;
        this.activity = activity;
        this.adapterCallback = adapterCallback;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_item_user)
        LinearLayout llItemUser;
        @BindView(R.id.text_name_user)
        TextView txtNameUser;
        @BindView(R.id.text_name_head_line)
        TextView txtNameHeadLine;
        @BindView(R.id.img_avatar)
        RoundedImageView avatarUser;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_vote_adapter, parent, false);
        return new ItemListUserVoteAdapter.ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final Vote vote = detailsPostModel.getPost().getVotes().get(position);

        itemHolder.txtNameUser.setText(vote.getUser().getName());
        itemHolder.txtNameHeadLine.setText(vote.getUser().getHeadline());
        itemHolder.txtNameHeadLine.setLines(2);

        //User
        picasso.load(vote.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemHolder.avatarUser);

        itemHolder.llItemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.onMethodCallback(vote.getUser().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (detailsPostModel != null) {
            return detailsPostModel.getPost().getVotes().size();
        } else {
            return 0;
        }
    }

}
