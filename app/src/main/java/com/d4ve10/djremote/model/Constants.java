package com.d4ve10.djremote.model;

import java.util.UUID;

public interface Constants {
    int CONNECTION_ESTABLISHED = 1;
    int CONNECTION_CLOSED = 2;
    int MEDIA_UPDATED = 3;
    UUID BT_MODULE_UUID = UUID.fromString("db8b0706-16d0-40b6-852f-4e13bac19127");
    String TAG = "djremote";
}
