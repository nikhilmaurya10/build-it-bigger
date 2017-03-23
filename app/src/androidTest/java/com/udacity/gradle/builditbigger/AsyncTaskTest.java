package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

/**
 * Created by nikhil on 23/3/17.
 */


@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest  {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    @Test
    public void testAsyncTask() {

        final EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        String result=null;

        endpointsAsyncTask.execute(mActivityRule.getActivity());

        try {
            result = endpointsAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(result.length() > 0 && !result.contains("failed"));
    }
}
