package com.example.restclienthw;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    @GET("users/")
    Call<List<User>> loadCommentByPostId(@Query("id") int id);


    @POST("users/")
    @FormUrlEncoded
    Call<User> addToUserPage(@Field("postId") int postId,
                                   @Field("name") String name,
                                   @Field("email") String email,
                                   @Field("body") String body);
}
