package com.producthuntpost.adapter.details;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.adapter.details.IAdapterPost.DetailsUserVoteAdapterCallback;
import com.producthuntpost.model.posts.details.DetailsPostDTO;
import com.producthuntpost.model.posts.details.Vote;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

public class ItemCardUserVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DetailsPostDTO detailsPostDTO;
    private Activity activity;
    private DetailsUserVoteAdapterCallback adapterCallback;
    private Picasso picasso;

    public ItemCardUserVoteAdapter(DetailsPostDTO detailsPostDTO, Activity activity, DetailsUserVoteAdapterCallback adapterCallback){
        this.detailsPostDTO = detailsPostDTO;
        this.activity = activity;
        this.adapterCallback = adapterCallback;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViewItemUser;
        private TextView txtNameUser, txtNameHeadLine;
        private RoundedImageView avatarUser;
        public ItemViewHolder(View itemView) {
            super(itemView);
            cardViewItemUser = (CardView) itemView.findViewById(R.id.card_view_user_vote_item);
            txtNameUser = (TextView) itemView.findViewById(R.id.text_name_user);
            txtNameHeadLine = (TextView) itemView.findViewById(R.id.text_name_head_line);
            avatarUser = (RoundedImageView) itemView.findViewById(R.id.img_avatar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_user_vote_adapter, parent, false);
        return new ItemCardUserVoteAdapter.ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final Vote vote = detailsPostDTO.getPost().getVotes().get(position);

        itemHolder.txtNameUser.setText(vote.getUser().getName());
        itemHolder.txtNameUser.setPaintFlags(itemHolder.txtNameUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        itemHolder.txtNameHeadLine.setText(vote.getUser().getHeadline());
        itemHolder.txtNameHeadLine.setLines(2);
        itemHolder.txtNameHeadLine.setPaintFlags(itemHolder.txtNameHeadLine.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //User
        picasso.load(vote.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemHolder.avatarUser);

        itemHolder.cardViewItemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.onMethodCallback(vote.getUser().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (detailsPostDTO != null) {
            return detailsPostDTO.getPost().getVotes().size();
        } else {
            return 0;
        }
    }


}
