/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.touhou.game;

import com.huawei.agconnect.cloud.database.CloudDBZoneObject;
import com.huawei.agconnect.cloud.database.annotations.PrimaryKeys;

/**
 * Definition of ObjectType Score.
 *
 * @since 2021-11-24
 */
@PrimaryKeys({"ID"})
public final class Score extends CloudDBZoneObject {
    private String ID;

    private String Name;

    private Float Score;

    public Score() {
        super(Score.class);
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setScore(Float Score) {
        this.Score = Score;
    }

    public Float getScore() {
        return Score;
    }

}