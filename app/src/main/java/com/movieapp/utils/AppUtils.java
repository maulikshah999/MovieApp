package com.movieapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.movieapp.R;

/**
 * Created by mauli on 3/21/2018.
 */

public class AppUtils {

    // check network connectivity
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static void showNoConnectionSnackBar(View view, Context context, final ConnectionRefreshable connectionRefreshable) {
        Snackbar snackbar = null;
        snackbar = Snackbar.make(view, context.getString(R.string.txt_no_connection), Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(R.string.txt_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectionRefreshable.onRefreshPage();
                    }
                });
        snackbar.show();

    }

    private static ProgressDialog progressDialog = null;

    public static void showProgressDialog(Activity activity) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Fetching Movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
