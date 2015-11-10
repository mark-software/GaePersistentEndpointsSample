package com.endpointapp.maker.sampleendpointapp.helper;

import android.util.Log;

import com.endpointapp.maker.sampleendpointapp.Constants;
import com.example.mark.myapplication.backend.myModelApi.MyModelApi;
import com.example.mark.myapplication.backend.myModelApi.model.MyModel;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mark on 11/9/2015.
 */
public class MyModelNetworkHelper {

    private static MyModelApi myApiService = null;

    static {
        if(myApiService == null) {
            MyModelApi.Builder builder = new MyModelApi
                    .Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(Constants.API_ROOT_URL);

            myApiService = builder.build();
        }
    }

    public static List<MyModel> getModels() {
        try {
            return myApiService.list().execute().getItems();
        } catch (IOException e) {
            Log.e(Constants.TAG, e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    public static void createModel(MyModel model) {
        try {
            myApiService.insert(model).execute();
        } catch (IOException e) {
            Log.e(Constants.TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
