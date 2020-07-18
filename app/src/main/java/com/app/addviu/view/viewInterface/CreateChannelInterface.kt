package com.app.addviu.view.viewInterface

import com.app.addviu.model.CreateChannelBean

interface CreateChannelInterface {
    fun addChannel(
        setupProfileData: CreateChannelBean,
        isSelected: String
    )
}