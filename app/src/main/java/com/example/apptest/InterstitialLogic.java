package com.example.apptest;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;

public class InterstitialLogic {
    private Button interstitialButton;
    private int minBannerShown;
    private float interstitialCooldown;

    private boolean isEnoughBannerShown = false;
    private boolean isInterstitialCooldown = false;

    private InterstitialListener listener;

    public interface InterstitialListener {
        void onInterstitialLaunch();
        void onInterstitialClose();
    }

    public InterstitialLogic(Button interstitialButton, int minBannerShown, float interstitialCooldown, InterstitialListener listener) {
        this.interstitialButton = interstitialButton;
        this.minBannerShown = minBannerShown;
        this.interstitialCooldown = interstitialCooldown * 1000;
        this.listener = listener;

        this.interstitialButton.setEnabled(false);
        this.interstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInterstitialLauncher();
            }
        });

        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                // Called when interstitial is loaded
            }
            @Override
            public void onInterstitialFailedToLoad() {

            }
            @Override
            public void onInterstitialShown() {
                // Called when interstitial is shown
            }
            @Override
            public void onInterstitialShowFailed() {
                // Called when interstitial show failed
            }
            @Override
            public void onInterstitialClicked() {
                // Called when interstitial is clicked
            }
            @Override
            public void onInterstitialClosed() {
                callInterstitialClose();
            }
            @Override
            public void onInterstitialExpired() {
                // Called when interstitial is expired
            }
        });
    }

    public void onBannerCountChanged(int count) {
        isEnoughBannerShown = count >= minBannerShown;
        updateInterstitialButtonInteractable();
    }

    public void callInterstitialLauncher() {
        if (listener != null) listener.onInterstitialLaunch();
        isInterstitialCooldown = true;
        interstitialButton.setEnabled(false);
        interstitialCooldown();
    }

    public void callInterstitialClose() {
        if (listener != null) listener.onInterstitialClose();
    }

    private void interstitialCooldown() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            isInterstitialCooldown = false;
            updateInterstitialButtonInteractable();
        }, (long) interstitialCooldown);
    }

    private void updateInterstitialButtonInteractable() {
        interstitialButton.setEnabled(isEnoughBannerShown && !isInterstitialCooldown);
    }
}
