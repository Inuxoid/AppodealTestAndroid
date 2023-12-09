package com.example.apptest;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;

public class BannerLogic {

    private Button bannerButton;

    private int bannerShowCount = 0;

    private BannerListener listener;

    public interface BannerListener {
        void onBannerLaunch();
        void onBannerClose();
        void onBannerCountChanged(int newCount);
    }

    public BannerLogic(Button bannerButton, BannerListener listener) {
        this.bannerButton = bannerButton;
        this.listener = listener;;
        this.bannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBannerLauncher();
            }
        });

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
            }
            @Override
            public void onBannerFailedToLoad() {
            }
            @Override
            public void onBannerShown() {
                setBannerShowCount(getBannerShowCount() + 1);
            }
            @Override
            public void onBannerShowFailed() {
                // Called when banner show failed
            }
            @Override
            public void onBannerClicked() {
                // Called when banner is clicked
            }
            @Override
            public void onBannerExpired() {
                // Called when banner is expired
            }
        });
    }

    public int getBannerShowCount() {
        return bannerShowCount;
    }

    public void setBannerShowCount(int bannerShowCount) {
        if (this.bannerShowCount != bannerShowCount) {
            this.bannerShowCount = bannerShowCount;
            if(listener != null) listener.onBannerCountChanged(this.bannerShowCount);
        }
    }

    public void callBannerLauncher() {
        bannerButton.setEnabled(false);
        if(listener != null) listener.onBannerLaunch();
    }

    public void callBannerCloser() {
        if(listener != null) listener.onBannerClose();
        bannerButton.setEnabled(true);
    }
}

