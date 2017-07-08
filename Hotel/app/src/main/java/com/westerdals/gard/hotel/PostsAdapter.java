package com.westerdals.gard.hotel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gard on 21.05.2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> posts;
    private AdapterListener listener;

    public PostsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder viewHolder, int position) {
        Post post = posts.get(position);

        TextView scoreText = viewHolder.scoreText;
        TextView postText = viewHolder.postText;

        scoreText.setText(String.valueOf(post.getScore()));
        postText.setText(post.getText());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected interface AdapterListener {
        void onLongClick(View v, int position);
        void onThumbsUpClick(View v, int position);
        void onThumbsDownClick(View v, int position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView thumbsUpButton;
        protected ImageView thumbsDownButton;
        protected TextView scoreText;
        protected TextView postText;

        public ViewHolder(final View itemView) {
            super(itemView);
            thumbsUpButton = (ImageView) itemView.findViewById(R.id.thumbsUpButton);
            thumbsDownButton = (ImageView) itemView.findViewById(R.id.thumbsDownButton);
            scoreText = (TextView) itemView.findViewById(R.id.scoreText);
            postText = (TextView) itemView.findViewById(R.id.postText);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(v, getAdapterPosition());
                    return false;
                }
            });

            thumbsUpButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onThumbsUpClick(v, getAdapterPosition());
                }
            });

            thumbsDownButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onThumbsDownClick(v, getAdapterPosition());
                }
            });
        }
    }
}