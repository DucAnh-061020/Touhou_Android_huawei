/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.touhou.game.hwDb;

import android.content.Context;
import android.util.Log;

import com.huawei.agconnect.cloud.database.AGConnectCloudDB;
import com.huawei.agconnect.cloud.database.CloudDBZone;
import com.huawei.agconnect.cloud.database.CloudDBZoneConfig;
import com.huawei.agconnect.cloud.database.CloudDBZoneObject;
import com.huawei.agconnect.cloud.database.ObjectTypeInfo;
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Definition of ObjectType Helper.
 *
 * @since 2021-11-24
 */
public final class ObjectTypeInfoHelper {
    private static final int FORMAT_VERSION = 2;
    private static final int OBJECT_TYPE_VERSION = 1;
    private static AGConnectCloudDB mCloudDB;
    private static CloudDBZoneConfig mConfig;
    private CloudDBZone mCloudDBZone;

    public static ObjectTypeInfo getObjectTypeInfo() {
        ObjectTypeInfo objectTypeInfo = new ObjectTypeInfo();
        objectTypeInfo.setFormatVersion(FORMAT_VERSION);
        objectTypeInfo.setObjectTypeVersion(OBJECT_TYPE_VERSION);
        List<Class<? extends CloudDBZoneObject>> objectTypeList = new ArrayList<>();
        Collections.addAll(objectTypeList, Score.class);
        objectTypeInfo.setObjectTypes(objectTypeList);
        return objectTypeInfo;
    }

    public static void initAGConnectCloudDB(Context context) {
        AGConnectCloudDB.initialize(context);
    }

    public static void createAGConnectCloudDB() throws AGConnectCloudDBException {
        mCloudDB = AGConnectCloudDB.getInstance();
        mCloudDB.createObjectType(ObjectTypeInfoHelper.getObjectTypeInfo());
    }

    public void CloudDBZoneConfig(){
        mConfig = new CloudDBZoneConfig("TouhouMobile",
                CloudDBZoneConfig.CloudDBZoneSyncProperty.CLOUDDBZONE_CLOUD_CACHE,
                CloudDBZoneConfig.CloudDBZoneAccessProperty.CLOUDDBZONE_PUBLIC);
        mConfig.setPersistenceEnabled(true);
        Task<CloudDBZone> openDBZoneTask = mCloudDB.openCloudDBZone2(mConfig, true);
        openDBZoneTask.addOnSuccessListener(new OnSuccessListener<CloudDBZone>() {
            @Override
            public void onSuccess(CloudDBZone cloudDBZone) {
                Log.i(TAG, "open cloudDBZone success");
                mCloudDBZone = cloudDBZone;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.w(TAG, "open cloudDBZone failed for " + e.getMessage());
            }
        });
    }

    public void upsertBookInfos(Score scoreInfo) {
        if (mCloudDBZone == null) {
            Log.w(TAG, "CloudDBZone is null, try re-open it");
            return;
        }
        Task<Integer> upsertTask = mCloudDBZone.executeUpsert(scoreInfo);
    }
}
