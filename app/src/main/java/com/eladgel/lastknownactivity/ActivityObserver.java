package com.eladgel.lastknownactivity;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by eladgelman on 6/21/15.
 */
public class ActivityObserver implements IActivityLifecycleObserver {
    private boolean mIsActivityVisible;

    public boolean isActivityVisible() {
        return mIsActivityVisible;
    }

    private ComponentName mRunningComponent;

    private ComponentName lastRunningComponent() {
        return mRunningComponent;
    }

//    public void clearLastRunningComponent() {
//        mRunningComponent = null;
//    }

    public void applicationOnCreate(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mIsActivityVisible = true;
                ComponentName componentName = activity.getComponentName();
                mRunningComponent = componentName;


            }

            @Override
            public void onActivityPaused(Activity activity) {
                mIsActivityVisible = false;
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void launchLastRunningActivity(Context context, Bundle bundle) {
        ComponentName cn = lastRunningComponent();
        if (cn != null) {
            try {
                Class<?> clazz = Class.forName(cn.getClassName());
                Intent launchAppIntent = new Intent(context, clazz);
                context.startActivity(launchAppIntent, bundle);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @return the current running activity instance.
     * Returns null if activity is not in the foreground
     */
    @Override
    public Class<?> getRunningActivity() {
        if (!isActivityVisible()) {
            return null;
        }
        return getLastRunningActivity();
    }

    @Override
    public Class<?> getLastRunningActivity() {
        Class<?> retVal = null;
        ComponentName cn = lastRunningComponent();
        if (cn != null) {
            try {
                retVal = Class.forName(cn.getClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }
}