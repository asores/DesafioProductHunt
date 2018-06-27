package com.producthuntpost.adapter.collections;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.adapter.collections.IAdapterCollection.AdapterCollectionCallback;
import com.producthuntpost.model.Collection;
import com.producthuntpost.model.collections.CollectionsPostModel;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CollectionsPostModel collectionsPostModel;
    private int viewPosition;
    private Picasso picasso;
    private Activity activity;
    private AdapterCollectionCallback adapterCollectionCallbackItem;


    public ItemListCollectionAdapter(CollectionsPostModel collectionsPostModel, Activity activity, AdapterCollectionCallback adapterCollectionCallback) {
        this.collectionsPostModel = collectionsPostModel;
        this.activity = activity;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
        this.adapterCollectionCallbackItem = adapterCollectionCallback;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_item_collection)
        RelativeLayout rlItemCollection;
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
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_adapter, parent, false);
        return new ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        setViewPosition(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Collection collectionItem = collectionsPostModel.getCollections().get(position);

        itemViewHolder.txtInfoTitle.setText(collectionItem.getName());
        itemViewHolder.txtLabelPostOrVotes.setText(activity.getString(R.string.label_post));
        itemViewHolder.txtVote.setText(String.valueOf(collectionItem.getPostsCount()));
        if (collectionItem.getTagline() == null) {
            itemViewHolder.txtInfoDescription.setVisibility(View.GONE);
        } else {
            itemViewHolder.txtInfoDescription.setText(collectionItem.getTagline());
        }

        //User
        picasso.load(collectionItem.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemViewHolder.avatarUser);

        itemViewHolder.txtNameUser.setText(collectionItem.getUser().getName());
        itemViewHolder.txtNameHeadLine.setText(collectionItem.getUser().getHeadline());

        itemViewHolder.rlItemCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCollectionCallbackItem.onMethodCallback(collectionItem.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return collectionsPostModel.getCollections().size();
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }

    public void setCollectionsPostModel(CollectionsPostModel collectionsPostModel) {
        this.collectionsPostModel = collectionsPostModel;
        notifyDataSetChanged();
    }
}



