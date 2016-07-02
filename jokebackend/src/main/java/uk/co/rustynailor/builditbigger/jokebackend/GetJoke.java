package uk.co.rustynailor.builditbigger.jokebackend;

/**
 *  Created by russellhicks on 02/07/16.
 *  Simple object to return a single joke String
 *  in response to an API request to getjoke
 */
public class GetJoke {

    private String joke;

    public String getJoke() {
        return joke;
    }

    public void setJoke(String data) {
        joke = data;
    }
}
