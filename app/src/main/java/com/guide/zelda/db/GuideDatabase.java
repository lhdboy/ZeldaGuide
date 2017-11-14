package com.guide.zelda.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = GuideDatabase.NAME, version = GuideDatabase.VERSION)
public final class GuideDatabase {
    public static final String NAME = "setup";

    public static final int VERSION = 1;

    private GuideDatabase() {

    }

}