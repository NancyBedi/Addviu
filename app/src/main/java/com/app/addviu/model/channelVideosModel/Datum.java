package com.app.addviu.model.channelVideosModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("id")
@Expose
public int id;
@SerializedName("user_id")
@Expose
public int userId;
@SerializedName("channel_id")
@Expose
public int channelId;
@SerializedName("playlist_id")
@Expose
public int playlistId;
@SerializedName("category_id")
@Expose
public int categoryId;
@SerializedName("uid")
@Expose
public String uid;
@SerializedName("title")
@Expose
public String title;
@SerializedName("original_name")
@Expose
public String originalName;
@SerializedName("keywords")
@Expose
public String keywords;
@SerializedName("description")
@Expose
public String description;
@SerializedName("processed")
@Expose
public int processed;
@SerializedName("video_filename")
@Expose
public String videoFilename;
@SerializedName("duration")
@Expose
public String duration;
@SerializedName("visibility")
@Expose
public String visibility;
@SerializedName("allow_votes")
@Expose
public int allowVotes;
@SerializedName("allow_comments")
@Expose
public int allowComments;
@SerializedName("thumbnail_url")
@Expose
public String thumbnailUrl;
@SerializedName("watch_seconds")
@Expose
public int watchSeconds;
@SerializedName("quality_height")
@Expose
public int qualityHeight;
@SerializedName("quality_width")
@Expose
public int qualityWidth;
@SerializedName("views_count")
@Expose
public int viewsCount;
@SerializedName("likes")
@Expose
public int likes;
@SerializedName("deleted_at")
@Expose
public Object deletedAt;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;
@SerializedName("created_date")
@Expose
public String createdDate;
@SerializedName("channelName")
@Expose
public String channelName;
@SerializedName("playlistName")
@Expose
public String playlistName;
@SerializedName("categoryName")
@Expose
public String categoryName;
@SerializedName("channel")
@Expose
public Channel channel;
@SerializedName("playlist")
@Expose
public Playlist playlist;

}