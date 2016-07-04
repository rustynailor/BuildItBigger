package com.udacity.gradle.builditbigger;

/**
 * Created by russellhicks on 04/07/16.
 *  interface to pass joke back to calling activity
 */
public interface JokeResponse {
    void processFinish(String output);
}