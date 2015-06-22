package com.eladgel.lastknownactivity;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by eladgelman on 6/21/15.
 */
public interface IActivityLifecycleObserver {

    void launchLastRunningActivity(Context context, Bundle bundle);

    Class<?> getRunningActivity();

    Class<?> getLastRunningActivity();
}
