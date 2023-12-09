package com.example.apptest;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public InterstitialLogic interstitialLogic;
    public BannerLogic bannerLogic;
    public RewardedVideoLogic rewardedVideoLogic;
    public NativeAdLogic nativeAdLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Appodeal.cache(this, Appodeal.NATIVE, 3);
        Appodeal.cache(this, Appodeal.BANNER);
        Appodeal.cache(this, Appodeal.REWARDED_VIDEO);
        Appodeal.cache(this, Appodeal.INTERSTITIAL);

        Appodeal.initialize(
                this,
                "e1ff323ebbe9b32bd2386a3f152a6dec8b40fb09c799afc4",
                Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.NATIVE,
                errors -> {

                }
        );

        Button btnShowBanner = findViewById(R.id.btnShowBanner);
        bannerLogic = new BannerLogic(btnShowBanner, new BannerLogic.BannerListener() {
            @Override
            public void onBannerLaunch() {
                Appodeal.show(MainActivity.this, Appodeal.BANNER_TOP);
            }

            @Override
            public void onBannerClose() {
                Appodeal.hide(MainActivity.this, Appodeal.BANNER);
            }

            @Override
            public void onBannerCountChanged(int newCount) {
                interstitialLogic.onBannerCountChanged(newCount);
            }
        });

        Button btnShowInterstitial = findViewById(R.id.btnShowInterstitial);
        interstitialLogic = new InterstitialLogic(btnShowInterstitial, 7, 60f, new InterstitialLogic.InterstitialListener() {
            @Override
            public void onInterstitialLaunch() {
                bannerLogic.callBannerCloser();
                Appodeal.show(MainActivity.this, Appodeal.INTERSTITIAL);
            }

            @Override
            public void onInterstitialClose() {
                bannerLogic.callBannerLauncher();
            }
        });

        Button btnShowRewardedVideo = findViewById(R.id.btnShowRewardedVideo);
        rewardedVideoLogic = new RewardedVideoLogic(btnShowRewardedVideo, new RewardedVideoLogic.RewardedVideoListener() {
            @Override
            public void onRewardedVideoLaunch() {
                bannerLogic.callBannerCloser();
                Appodeal.show(MainActivity.this, Appodeal.REWARDED_VIDEO);
            }

            @Override
            public void onRewardedVideoCountChanged(int newCount) {
                nativeAdLogic.onRewardedVideosCountChanged(newCount);
            }

            @Override
            public void onRewardedVideoClose() {
                bannerLogic.callBannerLauncher();
            }
        });

        Button btnShowNative = findViewById(R.id.btnShowNative);
        Button btnHideNative = findViewById(R.id.btnHideNative);
        nativeAdLogic = new NativeAdLogic(btnShowNative, btnHideNative, 3,  new NativeAdLogic.NativeAdListener() {
            ListView listView;
            @Override
            public void onNativeAdLaunch() {
                bannerLogic.callBannerCloser();
                if (listView != null) {
                    listView.setVisibility(View.VISIBLE);
                }
                else{
                try {
                    if (Appodeal.getAvailableNativeAdsCount() > 0) {
                        listView = findViewById(R.id.list_view_for_ads);
                        List<Object> items = new ArrayList<>();
                        for (int i = 0; i < 9; i++) {
                            if (i % 3 == 0) {
                                NativeAd nativeAd = Appodeal.getNativeAds(1).get(0);
                                items.add(nativeAd);
                            } else {
                                items.add("Item " + (i + 1));
                            }
                        }
                        NativeAdAdapter adapter = new NativeAdAdapter(MainActivity.this, items);
                        listView.setAdapter(adapter);
                    } else {
                        Log.i("NativeAdLogic", "No native ads available");
                    }
                } catch (Exception e) {
                    Log.e("NativeAdLogic", "Error showing native ad", e);
                }
            }
            }

            @Override
            public void onNativeAdClose() {
                bannerLogic.callBannerLauncher();
                if (listView.getVisibility() == View.VISIBLE) {
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }
}