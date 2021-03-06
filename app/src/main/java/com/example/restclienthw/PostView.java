package com.example.restclienthw;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostView extends Activity implements Callback<List<Comment>>{

    ArrayList<Comment> myCommentsList;
    CommentAdapter myCommentsAdapter;
    ListView lvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        TextView tvPostUsername = (TextView)findViewById(R.id.tvPostUsername);
        TextView tvPostTitle = (TextView)findViewById(R.id.tvPostTitle);
        TextView tvPostBody = (TextView)findViewById(R.id.tvPostBody);
        //tvPostUsername.setText(this.getIntent().getStringExtra("postUserId"));
        tvPostUsername.setText("Username placeholder");
        tvPostBody.setText(this.getIntent().getStringExtra("postBody"));
        tvPostTitle.setText(this.getIntent().getStringExtra("postTitle"));
        findViewById(R.id.btnMakeComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewComment();
            }
        });
        findViewById(R.id.tvPostUsername).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("POSTVIEW: ", "Clicked here to go to user_layout ");
                usernameClicked();
            }
        });
        startQuery();

    }

    void usernameClicked() {
        setContentView(R.layout.user_layout);
        TextView userName = findViewById(R.id.tvUPName);
        TextView userUsername = findViewById(R.id.tvUPUsername);
        TextView userEmail = findViewById(R.id.tvUPEmail);
        TextView userPhone = findViewById(R.id.tvUPPhone);
        TextView userWebsite = findViewById(R.id.tvUPWebsite);
        MapView mapView = findViewById(R.id.mvUP);
        ListView upListView = findViewById(R.id.lvUPPosts);
        User user = null;
        userName.setText("user's name changed");
        userUsername.setText("user's username changed");
        userEmail.setText("user's email changed");
        userPhone.setText("user's phone changed");
        userWebsite.setText("user's website changed");
    }

    static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public void startQuery() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        lvComments = (ListView)findViewById(R.id.lvComments);
        CommentAPI commentAPI = retrofit.create(CommentAPI.class);
        Call<List<Comment>> call = commentAPI.loadCommentByPostId(getIntent().getIntExtra("postId",0));
        call.enqueue(this);
    }

    public void makeNewComment(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CommentAPI commentAPI = retrofit.create(CommentAPI.class);
        Call<Comment> call = commentAPI.addCommentToPost(1,"Kalia","kdh031@uark.edu","Lorem Ipsum");
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment myComment = response.body();
                Log.d("PostView","Post Created Successfully at id: " + myComment.getId());
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.d("PostView","Post Not Created");
            }
        });
    }

    @Override
    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
        if(response.isSuccessful()) {
            myCommentsList = new ArrayList<Comment>(response.body());
            myCommentsAdapter = new PostView.CommentAdapter(this,myCommentsList);
            lvComments.setAdapter(myCommentsAdapter);
            for (Comment comment:myCommentsList) {
                Log.d("MainActivity","ID: " + comment.getId());
            }
        } else {
            System.out.println(response.errorBody());
        }
    }


    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        t.printStackTrace();
    }

    protected class CommentAdapter extends ArrayAdapter<Comment> {
        public CommentAdapter(Context context, ArrayList<Comment> posts) {
            super(context, 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Comment comment = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_layout, parent, false);
            }
            // Lookup view for data population
            TextView tvCommentId = (TextView) convertView.findViewById(R.id.tvCommentId);
            TextView tvCommentUsername = (TextView) convertView.findViewById(R.id.tvCommentUsername);
            TextView tvCommentBody = (TextView) convertView.findViewById(R.id.tvCommentBody);
            // Populate the data into the template view using the data object
            tvCommentId.setText(Integer.toString(comment.getId()));
            tvCommentUsername.setText("Title placeholder");
            tvCommentBody.setText(comment.getBody());
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
