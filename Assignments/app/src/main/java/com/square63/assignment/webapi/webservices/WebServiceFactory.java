package com.square63.assignment.webapi.webservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.square63.assignment.R;
import com.square63.assignment.Extras.Constants;
import com.square63.assignment.listeners.ApiCallback;
import com.square63.assignment.webapi.ApiClient;
import com.square63.assignment.webapi.Apiinterface.ApiInterface;
import com.square63.assignment.webapi.responses.DataObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WebServiceFactory {

    public static WebServiceFactory instance_;
    private Context context_;

    public synchronized static WebServiceFactory getInstance() {
        if (instance_ == null) {
            instance_ = new WebServiceFactory();
        }
        return instance_;
    }

    public void init(Context context) {
        context_ = context;
    }

    public void apiGetPosts(int noOfPages ,final ApiCallback apiCallback) {
        checkNetworkState();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
       // final ProgressDialog loading;
       // loading = ProgressDialog.show(context_, context_.getResources().getString(R.string.loading), "", true, false);

        Call<DataObject> signUpResponseCall = apiInterface.getPosts(Constants.STORY,""+noOfPages);
        signUpResponseCall.enqueue(new Callback<DataObject>() {
            @Override
            public void onResponse(Call<DataObject> call, Response<DataObject> response) {
                apiCallback.onSuccess(response.body().getHintModelArrayList());
                //loading.dismiss();
            }

            @Override
            public void onFailure(Call<DataObject> call, Throwable t) {
                Log.d("fail", t.getMessage());
                //loading.dismiss();

            }
        });

    }


    private void checkNetworkState(){
        if(!isNetworkAvailable(context_)) {
           showLongToastInCenter(context_,context_.getResources().getString(R.string.connectivity_msg));
            return;
        }
    }
    public static void showLongToastInCenter(Context ctx, String message) {
        //message = Strings.nullToEmpty( message );
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
