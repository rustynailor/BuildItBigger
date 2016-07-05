package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> implements JokeResponse {
    public ApplicationTest() {
        super(Application.class);
    }

    //we use this to store the string response from asynctask for evaluation
    private String mOutputResponse;

    @MediumTest
    public void testGetJoke() {
        FetchJokeTask fetchJokeTask =new FetchJokeTask();
        fetchJokeTask.delegate = this;
        //execute the async task
        fetchJokeTask.execute(getContext());

        // assume fetchJokeTask will be finished in 10 seconds.
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(mOutputResponse.isEmpty());

    }

    //called by FetchJokeTask in onPostExecute
    @Override
    public void processFinish(String output) {
        //assign response string to member variable for testing
        mOutputResponse = output;

    }
}