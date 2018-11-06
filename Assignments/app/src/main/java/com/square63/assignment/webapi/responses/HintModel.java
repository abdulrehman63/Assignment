package com.square63.assignment.webapi.responses;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.SwitchCompat;

import com.google.gson.annotations.SerializedName;
import com.square63.assignment.BR;
import com.square63.assignment.R;

import java.io.Serializable;

public class HintModel extends BaseObservable implements Serializable {
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("title")
    private String title;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    @Bindable
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        notifyPropertyChanged(BR.createdAt);
    }

    public String getTitle() {
        return title;
    }
    @Bindable
    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @BindingAdapter("handleSwitch")
    public static void setSwitch(SwitchCompat switchCompat,boolean isSelected){
        switchCompat.setChecked(isSelected);
    }
    @BindingAdapter("background")
    public static void setBackground(ConstraintLayout constraintLayout,boolean isSelected){
        if(isSelected){
            constraintLayout.setBackgroundResource(R.color.silver_twenty_opacity);
        }else {
            constraintLayout.setBackgroundResource(R.color.white_eighty_opacity);
        }
    }
}
