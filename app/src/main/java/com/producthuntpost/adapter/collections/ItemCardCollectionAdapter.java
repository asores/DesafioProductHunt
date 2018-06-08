package com.producthuntpost.adapter.collections;

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
import com.producthuntpost.adapter.collections.IAdapterCollection.AdapterCollectionCallback;
import com.producthuntpost.model.Collection;
import com.producthuntpost.model.collections.CollectionsPostDTO;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

public class ItemCardCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CollectionsPostDTO collectionsPostDTO;
    private Activity activity;
    private int viewPosition;
    private Picasso picasso;
    private AdapterCollectionCallback adapterCollectionCallbackItem;


    public ItemCardCollectionAdapter(CollectionsPostDTO collectionsPostDTO, Activity activity, AdapterCollectionCallback adapterCollectionCallback){
        this.collectionsPostDTO = collectionsPostDTO;
        this.activity = activity;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
        this.adapterCollectionCallbackItem = adapterCollectionCallback;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViewItemCollection;
        private TextView txtInfoTitle, txtInfoDescription, txtNameUser, txtNameHeadLine, txtLabelPostOrVotes, txtVote ;
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
        final Collection collectionItem = collectionsPostDTO.getCollections().get(position);

        itemViewHolder.txtInfoTitle.setText(collectionItem.getName());
        itemViewHolder.txtLabelPostOrVotes.setText(activity.getString(R.string.label_post));
        itemViewHolder.txtVote.setText(String.valueOf(collectionItem.getPostsCount()));
        if(collectionItem.getTagline() == null){
            itemViewHolder.txtInfoDescription.setVisibility(View.GONE);
        }else {
            itemViewHolder.txtInfoDescription.setText(collectionItem.getTagline());
        }

        //User
        picasso.load(collectionItem.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemViewHolder.avatarUser);

        itemViewHolder.txtNameUser.setText(collectionItem.getUser().getName());
        itemViewHolder.txtNameHeadLine.setText(collectionItem.getUser().getHeadline());

        itemViewHolder.cardViewItemCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCollectionCallbackItem.onMethodCallback(collectionItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (collectionsPostDTO != null) {
            return collectionsPostDTO.getCollections().size();
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

    public void setCollectionsPostDTO(CollectionsPostDTO collectionsPostDTO) {
        this.collectionsPostDTO = collectionsPostDTO;
        notifyDataSetChanged();
    }




}



