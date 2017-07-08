package com.westerdals.gard.hotel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class RecommendationFragment extends Fragment {
    private DBHandler dbHandler;
    private RecyclerView postsRecyclerView;
    private List<Post> posts;
    private PostsAdapter postsAdapter;

    private EditText submitText;
    private Button submitButton;
    private Button mostPopularButton;
    private Button newestButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        dbHandler = new DBHandler(getActivity());
        posts = dbHandler.getPostByScore();
        postsAdapter = new PostsAdapter(posts);

        initWidgets(view);
        initListeners();

        postsRecyclerView.setAdapter(postsAdapter);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    private void initWidgets(View view){
        postsRecyclerView = (RecyclerView)view.findViewById(R.id.postsRecyclerView);
        submitText = (EditText)view.findViewById(R.id.submitText);
        submitButton = (Button)view.findViewById(R.id.submitButton);
        mostPopularButton = (Button)view.findViewById(R.id.mostPopularButton);
        newestButton = (Button)view.findViewById(R.id.newestButton);
    }

    private void initListeners(){
        postsAdapter.setListener(new PostsAdapter.AdapterListener() {
            @Override
            public void onLongClick(View v, int position) {
                if (submitText.getText().toString().equals("secret password")){ //Staff exclusive(hopefully)
                    Post post = posts.get(position);
                    dbHandler.removePostById(post.getId());
                    posts.remove(post);
                    postsAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onThumbsUpClick(View v, int position) {
                Post post = posts.get(position);
                post.setScore(post.getScore() + 1);
                dbHandler.updateScore(String.valueOf(post.getId()), post.getScore());
                postsAdapter.notifyItemChanged(position);
            }

            @Override
            public void onThumbsDownClick(View v, int position) {
                Post post = posts.get(position);
                post.setScore(post.getScore() - 1);
                dbHandler.updateScore(String.valueOf(post.getId()), post.getScore());
                postsAdapter.notifyItemChanged(position);
            }
        });

        submitButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(submitText.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please write a valid recommendation", Toast.LENGTH_SHORT).show();
                } else {
                    dbHandler.createPost(submitText.getText().toString());
                    posts.add(dbHandler.getLatestPost());
                    postsAdapter.notifyItemInserted(posts.size());
                    submitText.setText("");
                }
            }
        });

        newestButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                posts.clear();
                posts.addAll(dbHandler.getPostByTime());
                postsAdapter.notifyDataSetChanged();
            }
        });

        mostPopularButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                posts.clear();
                posts.addAll(dbHandler.getPostByScore());
                postsAdapter.notifyDataSetChanged();
            }
        });
    }
}