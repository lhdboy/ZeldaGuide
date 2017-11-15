package com.guide.zelda.map;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.guide.zelda.R;
import com.guide.zelda.base.BasePresenter;
import com.guide.zelda.common.utils.CommonUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import javax.inject.Inject;


public class MapPresenter extends BasePresenter<MapView> {
    private static final String TAG = MapPresenter.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1;

    private Context context;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int downloadId;

    @Inject
    public MapPresenter(Context context) {
        this.context = context;
    }

    private void initNotification() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.map_resource))
                .setContentText(context.getString(R.string.downloading))
                .setTicker(context.getString(R.string.downloading))
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOngoing(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(100, 0, false);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void onResume() {
        File file = new File(getMapPath());
        getMvpView().showDownloadTip(!file.exists());
    }

    void downloadMap() {
        String path = context.getExternalFilesDir(null).getPath();
        initNotification();
        downloadId = FileDownloader.getImpl().create("http://7xt7n1.com2.z0.glb.qiniucdn.com/zelda/MapResource.zip")
                .setPath(path + "/MapResource.zip")
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        builder.setProgress(100, (int) (((float) soFarBytes / totalBytes) * 100), false)
                                .setContentText(context.getString(R.string.downloading)
                                        + "  " + CommonUtils.getPrintSize(task.getSpeed() * 1024) + "/s");
                        notificationManager.notify(NOTIFICATION_ID, builder.build());
                        getMvpView().updateProgress(CommonUtils.getPrintSize(soFarBytes), (int) ((soFarBytes * 1.0f / totalBytes) * 100));
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        getMvpView().downloadFinish(true);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtils.d(TAG, "download paused");
                        getMvpView().downloadPause();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtils.e(TAG, "download error" + e.getMessage());
                        getMvpView().downloadFinish(false);
                    }

                }).setAutoRetryTimes(3).start();
    }

    void pause() {
        FileDownloader.getImpl().pause(downloadId);
    }

    private String getMapPath() {
        return context.getExternalFilesDir(null) + File.separator + "MapResource/Index.html";
    }

}