package com.endpointapp.maker.sampleendpointapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.endpointapp.maker.sampleendpointapp.R;
import com.endpointapp.maker.sampleendpointapp.helper.MyModelNetworkHelper;
import com.example.mark.myapplication.backend.myModelApi.model.MyModel;

import java.util.List;
import java.util.Random;

/**
 * Created by Mark on 11/9/2015.
 */
public class MainActivity extends AppCompatActivity {

    private boolean networkRequestInProgress;
    private ProgressBar progressBar;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        infoText = (TextView) findViewById(R.id.text_content);

        findViewById(R.id.insert_model_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkRequestInProgress) return;

                new ModelCreator().execute();
            }
        });

        findViewById(R.id.show_models_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkRequestInProgress) return;

                new ModelListGetter().execute();
            }
        });
    }

    //AsyncTask is one of Android's ways of doing background work
    public class ModelCreator extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            networkRequestInProgress = true;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String randomNumber = "" + new Random().nextInt(2_000);
            MyModel model = new MyModel();
            model.setName(randomNumber);

            MyModelNetworkHelper.createModel(model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            networkRequestInProgress = false;
        }
    }

    public class ModelListGetter extends AsyncTask<Void, Void, List<MyModel>> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            networkRequestInProgress = true;
        }

        @Override
        protected List<MyModel> doInBackground(Void... params) {
            return MyModelNetworkHelper.getModels();
        }

        @Override
        protected void onPostExecute(List<MyModel> myModels) {
            if(myModels != null) {
                String output = myModels.toString();
                infoText.setText(output);
            }
            progressBar.setVisibility(View.INVISIBLE);
            networkRequestInProgress = false;
        }
    }
}
