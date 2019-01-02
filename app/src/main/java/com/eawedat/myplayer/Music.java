package com.eawedat.myplayer;

public class Music {

    String songName;
    //int songPath; //from raw (local path)
    String songURL;

    public Music(String songName, String songURL) {
        this.songName = songName;
        this.songURL = songURL;
    }
}
