package com.restoreapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyajun on 16/8/2.
 * <p/>
 * APP KILL STATE UTIL
 */
public class StateUtil {

    private static final String TAG = "StateUtil";
    private static final String SAVED_ATY = "SAVED_ATY";//目标Activity
    private static final String SAVED_PAR = "SAVED_PAR";//目标Activity 需要的参数

    private Object mSavedAty;
    private String mTargetAtyParam;
    private Object param;
    private Class mTargetClass;
    private Intent mIntent;
    private Map<String, Object> objectMap;
    private Map<String, Object> objectMapTemp;

    private StateUtil() {
    }

    private static StateUtil single = null;

    public static StateUtil getInstance() {
        if (single == null) {
            single = new StateUtil();
        }
        return single;
    }

    /**
     * 主页 判断是否有目标Activity ，有判断目标Activity是不是自己，不是则跳转
     *
     * @param activity
     */
    public void jumpSavedActivity(final Activity activity) {
        mSavedAty = MyApp.asp.read(SAVED_ATY, "");
        mTargetAtyParam = MyApp.asp.read(SAVED_PAR, "");
        objectMap = JSON.parseObject(mTargetAtyParam);

        if (mSavedAty != null) {
            Log.i(TAG, "SAVED_ATY : " + mSavedAty.toString() + mTargetAtyParam);
            try {
                if (!MainActivity.class.getCanonicalName().equals(mSavedAty.toString())) {
                    mTargetClass = Class.forName(mSavedAty.toString());
                    mIntent = new Intent(activity, mTargetClass);
                    if (objectMap != null) {//目标Activity 参数不为空则设置参数
                        for (Object object : objectMap.entrySet()) {
                            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) object;
                            setIntent(mIntent, entry);
                        }
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.startActivity(mIntent);
                        }
                    }, 300);

                    MyApp.asp.write(SAVED_ATY, "");
                    MyApp.asp.write(SAVED_PAR, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 每个Activity onSaveInstanceState 时 保存目标 Activity
     *
     * @param targetAty
     */
    public void saveTargetData(String targetAty, String targetAtyParam) {
        Log.i(TAG, "SAVE_ATY : " + targetAty);
        MyApp.asp.write(SAVED_ATY, targetAty);
        MyApp.asp.write(SAVED_PAR, targetAtyParam);
    }

    /**
     * 每个Activity onCreate时 清除 目标 Activity 主界面不清除
     *
     * @param targetActivity
     */
    public void clearTargetData(String targetActivity) {
        Log.i(TAG, "SAVE_ATY : doTargetActivityEmpty");
        if (!MainActivity.class.getCanonicalName().equals(targetActivity)) {
            MyApp.asp.write(SAVED_ATY, "");
            MyApp.asp.write(SAVED_PAR, "");
        }
    }

    /**
     * 目标Activity 参数转换成JSON 方便存储
     *
     * @param key
     * @param value
     * @return
     */
    public String getTargetParam(String[] key, Object[] value) {
        mTargetAtyParam = "";
        if (objectMapTemp == null) {
            objectMapTemp = new HashMap<>();
        } else {
            objectMapTemp.clear();
        }
        if (key != null && value != null && key.length == value.length && key.length != 0) {
            for (int i = 0; i < key.length; i++) {
                objectMapTemp.put(key[i], value[i]);
            }
        }
        return JSONObject.toJSON(objectMapTemp).toString();
    }

    /**
     * 设置目标Activity 参数
     *
     * @param mIntent
     * @param entry
     */
    private void setIntent(Intent mIntent, Map.Entry<String, Object> entry) {
        param = entry.getValue();
        if (param instanceof Integer) {
            int value = ((Integer) param).intValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof String) {
            String value = (String) param;
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Double) {
            double value = ((Double) param).doubleValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Float) {
            float value = ((Float) param).floatValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Long) {
            long value = ((Long) param).longValue();
            mIntent.putExtra(entry.getKey(), value);
        } else if (param instanceof Boolean) {
            boolean value = ((Boolean) param).booleanValue();
            mIntent.putExtra(entry.getKey(), value);
        }
    }
}
