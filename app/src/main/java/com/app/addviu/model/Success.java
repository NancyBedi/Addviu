package com.app.addviu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Success {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("user_id")
@Expose
public Integer userId;
@SerializedName("channel_id")
@Expose
public Integer channelId;
@SerializedName("playlist_id")
@Expose
public Object playlistId;
@SerializedName("category_id")
@Expose
public Integer categoryId;

}