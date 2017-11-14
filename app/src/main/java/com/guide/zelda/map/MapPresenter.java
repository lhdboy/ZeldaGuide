package com.guide.zelda.map;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.guide.zelda.R;
import com.guide.zelda.base.BasePresenter;
import com.guide.zelda.common.LogUtil;
import com.guide.zelda.common.utils.CommonUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import javax.inject.Inject;


public class MapPresenter extends BasePresenter<MapView> {
    private static final int NOTIFICATION_ID = 1;

    private Context context;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

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
        getMvpView().showDownloadDialog(!file.exists());
    }

    public void downloadMap() {
        String path = context.getExternalFilesDir(null).getPath();
        initNotification();
        FileDownloader.getImpl().create("http://7xt7n1.com2.z0.glb.qiniucdn.com/zelda/MapResource.zip")
                .setPath(path, true)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.i("update", "update download process-->" + soFarBytes);
                        builder.setProgress(100, (int) (((float) soFarBytes / totalBytes) * 100), false)
                                .setContentText(context.getString(R.string.downloading)
                                        + "  " + CommonUtils.getPrintSize(task.getSpeed() * 1024) + "/s");
                        notificationManager.notify(NOTIFICATION_ID, builder.build());
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.i("update", "update download completed");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.i("update", "update download paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.i("update", "update download error" + e.getMessage());
                    }

                }).setAutoRetryTimes(3).start();
    }

    private String getMapPath() {
        return context.getExternalFilesDir(null) + File.separator + "MapResource/Index.html";
    }

}