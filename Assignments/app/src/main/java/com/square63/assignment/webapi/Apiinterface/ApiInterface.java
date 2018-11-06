package com.square63.assignment.webapi.Apiinterface;


import com.square63.assignment.webapi.responses.DataObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiInterface {

    @GET(Request.SEARCH_BY_DATE)
    Call<DataObject> getPosts(@Query("tags") String tags, @Query("page") String page);
    interface Request {
        String SEARCH_BY_DATE = "search_by_date";
    }
}
