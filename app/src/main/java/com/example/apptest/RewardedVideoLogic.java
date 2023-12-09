package com.example.apptest;
import android.view.View;
import android.widget.Button;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.RewardedVideoCallbacks;

public class RewardedVideoLogic {
    private Button rewardedVideoButton;
    private int rewardedVideoShowCount;
    private final int MaxRewardedVideosPerSession = 3;

    private RewardedVideoListener listener;

    public interface RewardedVideoListener {
        void onRewardedVideoLaunch();
        void onRewardedVideoClose();
        void onRewardedVideoCountChanged(int newCount);
    }

    public RewardedVideoLogic(Button rewardedVideoButton, RewardedVideoListener listener) {
        this.rewardedVideoButton = rewardedVideoButton;
        this.listener = listener;
        this.rewardedVideoShowCount = 0;
        this.rewardedVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRewardedVideoLauncher();
            }
        });
        updateButtonInteractable();

        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
            @Override
            public void onRewardedVideoLoaded(boolean isPrecache) {
                updateButtonInteractable();
            }
            @Override
            public void onRewardedVideoFailedToLoad() {
                // Called when rewarded video failed to load
            }
            @Override
            public void onRewardedVideoShown() {
            }
            @Override
            public void onRewardedVideoShowFailed() {
                // Called when rewarded video show failed
            }
            @Override
            public void onRewardedVideoClicked() {
                // Called when rewarded video is clicked
            }
            @Override
            public void onRewardedVideoFinished(double amount, String name) {
                setRewardedVideoShowCount(getRewardedVideoShowCount() + 1);
            }
            @Override
            public void onRewardedVideoClosed(boolean finished) {
                callRewardedVideoCloser();
            }
            @Override
            public void onRewardedVideoExpired() {
                // Called when rewarded video is expired
            }
        });
    }

    public int getRewardedVideoShowCount() {
        return rewardedVideoShowCount;
    }

    public void setRewardedVideoShowCount(int rewardedVideoShowCount) {
        if (this.rewardedVideoShowCount != rewardedVideoShowCount) {
            this.rewardedVideoShowCount = rewardedVideoShowCount;
            if(listener != null) listener.onRewardedVideoCountChanged(this.rewardedVideoShowCount);
            updateButtonInteractable();
        }
    }

    private void updateButtonInteractable() {
        boolean isLoaded =  Appodeal.isLoaded(Appodeal.REWARDED_VIDEO);
        rewardedVideoButton.setEnabled(isLoaded && rewardedVideoShowCount < MaxRewardedVideosPerSession);
    }

    public void callRewardedVideoLauncher() {
        if (listener != null) {
            listener.onRewardedVideoLaunch();
        }
    }

    public void callRewardedVideoCloser() {
        if (listener != null) {
            listener.onRewardedVideoClose();
        }
    }
}
