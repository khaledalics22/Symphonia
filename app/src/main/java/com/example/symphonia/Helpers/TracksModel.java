package com.example.symphonia.Helpers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TracksModel {
    @SerializedName("tracks")
    public ArrayList<String> tracks;

    public TracksModel(ArrayList<String> tracks){
        this.tracks = tracks;
    }
}
