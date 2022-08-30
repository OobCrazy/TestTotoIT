package com.totoit.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.totoit.test.api.ApiManager;
import com.totoit.test.model.ApiData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadApiPage extends AppCompatActivity {
    private static final String TAG = "LoadApiPage";
    private static final String casino_lobby_url = "https%3A%2F%2Fgames.staging.irl.aws.tipicodev.de%2Fen%2F";
    private static final String fun_mode = "False";
    private static final String language = "en";
    private static final String launchApi = "true";
    private static final String sid = "R3fJpfl4GwmcsDzrNpvOyS1YEGM4agCkwxB.kjbzBzZZkbyfU0_GGg";

    private SwipeRefreshLayout swipeContainer;
    private LinearLayout dataView;
    private TextView errorMessage;
    private TextView errorType;
    private TextView requestId;
    private TextView timeStamp;
    private LinearLayout loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_api);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.api_swipe_container);
        dataView = (LinearLayout) findViewById(R.id.api_data_view);
        errorMessage = (TextView) findViewById(R.id.api_error_message);
        errorType = (TextView) findViewById(R.id.api_error_type);
        requestId = (TextView) findViewById(R.id.api_request_id);
        timeStamp = (TextView) findViewById(R.id.api_time_stamp);
        loadingView = (LinearLayout) findViewById(R.id.api_loading_view);
        Button refreshButton = (Button) findViewById(R.id.api_refresh);

        refreshButton.setOnClickListener((event) -> {
            this.loadApi();
        });

        swipeContainer.setOnRefreshListener(this::loadApi);

        loadApi();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadApi() {
        dataView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        swipeContainer.setRefreshing(true);
        try {
            Call<ApiData> call = ApiManager.getService(this).getApiData(
                    casino_lobby_url,
                    fun_mode,
                    language,
                    launchApi,
                    sid
            );

            call.enqueue(new Callback<ApiData>() {
                @Override
                public void onResponse(@NonNull Call<ApiData> call, @NonNull Response<ApiData> response) {
                    ApiData apiData = response.body();
                    if(apiData != null){
                        errorMessage.setText(apiData.ErrorMessage);
                        errorType.setText(apiData.ErrorType);
                        requestId.setText(apiData.RequestId);
                        timeStamp.setText(apiData.TimeStamp);
                        String message = "ErrorMessage: "+apiData.ErrorMessage+"\n\n"
                                +"ErrorType: "+apiData.ErrorType+"\n\n"
                                +"RequestId: "+apiData.RequestId+"\n\n"
                                +"TimeStamp: "+apiData.TimeStamp;
                        showAlertDialog(getString(R.string.api_alert_success_title), message);
                    } else {
                        errorMessage.setText(getString(R.string.api_error_text));
                        errorType.setText("");
                        requestId.setText("");
                        timeStamp.setText("");
                        showAlertDialog(getString(R.string.api_alert_fail_title), getString(R.string.api_error_text));
                    }
                    dataView.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<ApiData> call, @NonNull Throwable t) {
                    Log.e(TAG, "Fail: ", t);
                    errorMessage.setText(t.getMessage());
                    errorType.setText("");
                    requestId.setText("");
                    timeStamp.setText("");
                    showAlertDialog(getString(R.string.api_alert_fail_title), t.getMessage());
                    dataView.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Fail: ", e);
            errorMessage.setText(e.getMessage());
            errorType.setText("");
            requestId.setText("");
            timeStamp.setText("");
            showAlertDialog(getString(R.string.api_alert_fail_title), e.getMessage());
            dataView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            swipeContainer.setRefreshing(false);
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.api_alert_ok_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                })
                .setNegativeButton(getString(R.string.api_alert_close_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
