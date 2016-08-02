package com.restoreapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wuyajun on 16/8/2.
 */
public class BaseActivity extends Activity {

    protected Intent intent;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateUtil.getInstance().saveTargetData(this.getClass().getCanonicalName(), mTargetParam);
    }

    //-------------------------------------param save start
    private String mTargetParam;

    protected void setTargetParam(String[] key, Object[] value) {
        mTargetParam = StateUtil.getInstance().getTargetParam(key, value);
    }
    //-------------------------------------param save end

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        StateUtil.getInstance().clearTargetData(this.getClass().getCanonicalName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        StateUtil.getInstance().clearTargetData(this.getClass().getCanonicalName());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("MemoryLog", "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("MemoryLog", "onTrimMemory " + level);
    }
}
