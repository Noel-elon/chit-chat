package com.example.chitchat.utils

import android.app.Application
import org.matomo.sdk.Matomo
import org.matomo.sdk.Tracker

import org.matomo.sdk.TrackerBuilder




class MyApplication: Application() {
    private var mMatomoTracker: Tracker? = null

    @Synchronized
    fun getTracker(): Tracker? {
        if (mMatomoTracker != null) return mMatomoTracker
        mMatomoTracker = TrackerBuilder.createDefault("http://your-matomo-domain.tld/matomo.php", 1)
            .build(Matomo.getInstance(this))
        return mMatomoTracker
    }
}