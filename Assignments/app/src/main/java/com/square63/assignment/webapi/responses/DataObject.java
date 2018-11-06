package com.square63.assignment.webapi.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Abdul Rehman on 3/8/2018.
 */

public class DataObject implements Serializable {
    public ArrayList<HintModel> getHintModelArrayList() {
        return hintModelArrayList;
    }

    public void setHintModelArrayList(ArrayList<HintModel> hintModelArrayList) {
        this.hintModelArrayList = hintModelArrayList;
    }

    @SerializedName("hits")
    @Expose
    private ArrayList<HintModel> hintModelArrayList = new ArrayList<>();
}
