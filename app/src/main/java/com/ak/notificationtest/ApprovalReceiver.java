package com.ak.notificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ApprovalReceiver extends BroadcastReceiver {
    int something;
    @Override
    public void onReceive(Context context, Intent intent){
        something = 1;

    }
}
