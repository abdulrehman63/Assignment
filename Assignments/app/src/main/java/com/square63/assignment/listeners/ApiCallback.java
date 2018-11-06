package com.square63.assignment.listeners;

import com.square63.assignment.webapi.responses.HintModel;

import java.util.ArrayList;

public interface ApiCallback {
    void onSuccess(ArrayList<HintModel> hintModelArrayList);
}
