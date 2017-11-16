package com.guide.zelda.map;

import com.guide.zelda.base.MvpView;


public interface MapView extends MvpView {

    void showDownloadTip(boolean show);

    void updateProgress(String downloadSize, int progress);

    void downloadPause();

    void downloadComplete(boolean success);

    void unzipFile();
}
