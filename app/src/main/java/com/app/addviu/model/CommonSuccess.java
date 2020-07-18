package com.app.addviu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonSuccess {

@SerializedName("status")
@Expose
public int status;
@SerializedName("message")
@Expose
public String message;

}