package com.app.addviu.model.channelVideosModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelVidBean {

@SerializedName("status")
@Expose
public int status;
@SerializedName("channelSubscribers")
@Expose
public int channelSubscribers;
@SerializedName("data")
@Expose
public List<Datum> data = null;

}