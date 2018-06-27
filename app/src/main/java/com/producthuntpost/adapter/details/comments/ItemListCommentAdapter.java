package com.producthuntpost.adapter.details.comments;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.producthuntpost.R;
import com.producthuntpost.model.posts.details.ChildComment;
import com.producthuntpost.model.posts.details.Comment;
import com.producthuntpost.service.ServicePicasso;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Comment> comments;
    private Activity activity;
    private Picasso picasso;


    public ItemListCommentAdapter(List<Comment> comments, Activity activity){
        this.comments = comments;
        this.activity = activity;
        picasso = new Picasso.Builder(activity).downloader(ServicePicasso.getClientPicasso(activity)).build();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_child_comment)
        LinearLayout llChildComment;
        @BindView(R.id.text_name_user)
        TextView txtNameUser;
        @BindView(R.id.text_name_head_line)
        TextView txtNameHeadLine;
        @BindView(R.id.text_comment_user)
        TextView txtCommentUser;
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
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_comment_adapter, parent, false);
        return new ItemListCommentAdapter.ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        Comment comment = comments.get(position);

        itemHolder.txtNameUser.setText(comment.getUser().getName());
        itemHolder.txtNameHeadLine.setText(comment.getUser().getHeadline());
        itemHolder.txtCommentUser.setText(comment.getBody());

        //Avatar User
        picasso.load(comment.getUser().getImageUrl().get120px())
                .placeholder(R.drawable.ic_avatar)
                .into(itemHolder.avatarUser);


        //Child Comment
        if(comment.getChildComments().size() > 0){
            LayoutInflater inflater = LayoutInflater.from(activity);
            View view  = inflater.inflate(R.layout.item_child_commet, itemHolder.llChildComment, false);
            TextView txtNameChildUser = (TextView) view.findViewById(R.id.text_name_user);
            TextView txtNameChildHeadLine = (TextView) view.findViewById(R.id.text_name_head_line);
            TextView txtCommentChildUser = (TextView) view.findViewById(R.id.text_comment_user_child);
            RoundedImageView avatarUserChild = (RoundedImageView) view.findViewById(R.id.img_avatar);

            for (ChildComment childComment : comment.getChildComments()){
                txtNameChildUser.setText(childComment.getUser().getName());
                txtNameChildHeadLine.setText(childComment.getUser().getHeadline());
                txtCommentChildUser.setText(childComment.getBody());

                //Avatar User
                picasso.load(childComment.getUser().getImageUrl().get120px())
                        .placeholder(R.drawable.ic_avatar)
                        .into(avatarUserChild);
            }
            itemHolder.llChildComment.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        if (comments != null) {
            return comments.size();
        } else {
            return 0;
        }
    }

}
