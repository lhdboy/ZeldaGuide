package com.guide.zelda.map;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.guide.zelda.R;
import com.guide.zelda.base.BasePresenter;
import com.guide.zelda.common.utils.CommonUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.IOException;

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
        String fileDir = context.getExternalFilesDir(null).getPath();
        String path = fileDir + "/MapResource.zip";
        initNotification();
        downloadId = FileDownloader.getImpl().create(context.getString(R.string.map_download_url))
                .setPath(path)
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
                        LogUtils.d(TAG, "completed");
                        getMvpView().updateProgress("238MB", 100);
                        new UnzipTask(fileDir).execute(task.getPath());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtils.d(TAG, "download paused");
                        getMvpView().downloadPause();
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtils.e(TAG, "download error" + e.getMessage());
                        getMvpView().downloadComplete(false);
                    }

                }).setAutoRetryTimes(3).start();
    }

    void pause() {
        FileDownloader.getImpl().pause(downloadId);
    }

    private String getMapPath() {
        return context.getExternalFilesDir(null) + File.separator + "MapResource/Index.html";
    }

    private class UnzipTask extends AsyncTask<String, Void, Boolean> {
        private String path;

        public UnzipTask(String path) {
            this.path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getMvpView().unzipFile();
        }

        @Override
        protected Boolean doInBackground(String... voids) {
            try {
                ZipUtils.unzipFile(voids[0], path + "/MapResource");
            } catch (IOException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            getMvpView().downloadComplete(success);
            if (success) {
                FileUtils.deleteFile(path + "/MapResource.zip");
            }
        }
    }

}