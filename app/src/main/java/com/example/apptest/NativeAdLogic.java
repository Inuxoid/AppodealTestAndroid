package com.example.apptest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;

public class NativeAdLogic {
    private Button nativeAdButton;
    private Button nativeAdButtonClose;
    private int minRewardedVideosShown;
    private NativeAdListener listener;

    public interface NativeAdListener {
        void onNativeAdLaunch();
        void onNativeAdClose();
    }

    public NativeAdLogic(Button nativeAdButton, Button nativeAdButtonClose, int minRewardedVideosShown, NativeAdListener listener) {
        this.nativeAdButton = nativeAdButton;
        this.nativeAdButtonClose = nativeAdButtonClose;
        this.minRewardedVideosShown = minRewardedVideosShown;
        this.listener = listener;

        this.nativeAdButtonClose.setVisibility(View.GONE);
        this.nativeAdButton.setVisibility(View.GONE);
        this.nativeAdButtonClose.setEnabled(false);
        this.nativeAdButton.setEnabled(false);

        this.nativeAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNativeAdLauncher();
            }
        });

        this.nativeAdButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNativeAdCloser();
            }
        });

        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {

            }
            @Override
            public void onNativeFailedToLoad() {
            }
            @Override
            public void onNativeShown(NativeAd nativeAd) {
            }
            @Override
            public void onNativeShowFailed(NativeAd nativeAd) {
                // Called when native ad show failed
            }
            @Override
            public void onNativeClicked(NativeAd nativeAd) {
                // Called when native ads is clicked
            }
            @Override
            public void onNativeExpired() {
                // Called when native ads is expired
            }
        });
    }

    public void onRewardedVideosCountChanged(int count) {
        boolean isEligibleToShow = count >= minRewardedVideosShown;
        if (isEligibleToShow){
            this.nativeAdButtonClose.setVisibility(View.VISIBLE);
            this.nativeAdButton.setVisibility(View.VISIBLE);
            this.nativeAdButtonClose.setEnabled(true);
            this.nativeAdButton.setEnabled(true);
        }
    }

    public void callNativeAdLauncher() {
        if(listener != null) listener.onNativeAdLaunch();
        nativeAdButton.setEnabled(false);
        nativeAdButtonClose.setEnabled(true);
    }

    public void callNativeAdCloser() {
        if(listener != null) listener.onNativeAdClose();
        nativeAdButton.setEnabled(true);
        nativeAdButtonClose.setEnabled(false);
    }
}
