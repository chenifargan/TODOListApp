package com.example.todolistapp;

import android.app.Application;
import android.content.ClipData;

import com.example.todolistapp.income.InApp_ID;
import com.example.todolistapp.income.MyInApp;
import com.google.android.gms.ads.MobileAds;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this);
        Helper.initHelper();
/*
        String LICENSE_KEY = "sdsdfdsfdsfffds";
        MyInApp.Item[] items = new MyInApp.Item[] {
                new MyInApp.Item(MyInApp.TYPE.Subscription, InApp_ID.PRO_ID),
                new MyInApp.Item(MyInApp.TYPE.OneTimeInApp, InApp_ID.NO_ADS_ID),
                new MyInApp.Item(MyInApp.TYPE.RepurchaseInApp, InApp_ID.COINS_2_ID),
                new MyInApp.Item(MyInApp.TYPE.RepurchaseInApp, InApp_ID.COINS_1_ID),
        };
        MyInApp.initHelper(this, LICENSE_KEY, items);

*/


    }
}
