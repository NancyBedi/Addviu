package com.app.addviu.model.channelVideosModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist {

@SerializedName("id")
@Expose
public int id;
@SerializedName("user_id")
@Expose
public int userId;
@SerializedName("channel_id")
@Expose
public int channelId;
@SerializedName("playlist_name")
@Expose
public String playlistName;
@SerializedName("description")
@Expose
public String description;
@SerializedName("playlist_icon")
@Expose
public String playlistIcon;
@SerializedName("banner")
@Expose
public String banner;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;

}