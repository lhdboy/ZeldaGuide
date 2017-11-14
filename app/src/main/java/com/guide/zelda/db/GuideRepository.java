package com.guide.zelda.db;


import android.content.Context;

import com.guide.zelda.db.model.GuideModel;
import com.guide.zelda.db.model.GuideModel_Table;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

public class GuideRepository {

    @Inject
    public GuideRepository() {
    }

    public void copyDB2Storage(Context context) {
        File appDataBasePath = context.getDatabasePath(GuideDatabase.NAME + ".db");
        if (appDataBasePath.exists()) {
            return;
        }

        File f = new File(context.getApplicationInfo().dataDir + "/databases/");
        if (!f.exists()) {
            f.mkdir();
        }

        copyFileFromAsset(context, GuideDatabase.NAME + ".db", appDataBasePath.getPath());
    }

    private void copyFileFromAsset(Context app, String fileName, String targetPath) {
        try {
            InputStream srcStream = app.getAssets().open(fileName);
            OutputStream destStream = new FileOutputStream(targetPath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = srcStream.read(buffer)) > 0) {
                destStream.write(buffer, 0, length);
            }

            destStream.flush();
            destStream.close();
            srcStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GuideModel> queryAll() {
        return new Select().from(GuideModel.class).queryList();
    }

    public List<GuideModel> queryWholeFlow() {
        return new Select().from(GuideModel.class).where(GuideModel_Table.type.eq("whole_flow")).queryList();
    }

    public List<GuideModel> queryNewest() {
        return new Select().from(GuideModel.class).where(GuideModel_Table.type.notEq("whole_flow")).queryList();
    }

    public List<GuideModel> queryByPage(int page) {
        return new Select().from(GuideModel.class).limit(15).offset(page * 15).queryList();
    }

}
