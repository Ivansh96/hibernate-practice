package com.ivansh.listener;


import com.ivansh.entity.UserChat;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

public class UserChatListener {

    @PostPersist
    public void PostPersist(UserChat userChat) {
        var chat = userChat.getChat();
        chat.setCount(chat.getCount() + 1);
    }

    @PostRemove
    public void PostRemove(UserChat userChat) {
        var chat = userChat.getChat();
        chat.setCount(chat.getCount() - 1);
    }
}
