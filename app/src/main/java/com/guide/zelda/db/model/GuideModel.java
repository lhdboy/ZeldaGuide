package com.guide.zelda.db.model;


import com.guide.zelda.db.GuideDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(name = "guide", database = GuideDatabase.class)
public class GuideModel {

    @PrimaryKey
    @Column
    public int id;

    @Column
    public String category;

    @Column
    public String file_name;

    @Column
    public String image_name;

    @Column
    public String name;

    @Column
    public String type;


}