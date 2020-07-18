package com.app.addviu.model.channelVideosModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Channel {

@SerializedName("id")
@Expose
public int id;
@SerializedName("user_id")
@Expose
public int userId;
@SerializedName("channel_name")
@Expose
public String channelName;
@SerializedName("slug")
@Expose
public String slug;
@SerializedName("description")
@Expose
public String description;
@SerializedName("banner")
@Expose
public String banner;
@SerializedName("cover_image")
@Expose
public String coverImage;
@SerializedName("category_id")
@Expose
public int categoryId;
@SerializedName("default_channel")
@Expose
public int defaultChannel;
@SerializedName("subscribers")
@Expose
public int subscribers;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;

}