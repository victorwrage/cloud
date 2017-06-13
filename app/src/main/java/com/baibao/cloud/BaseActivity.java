package com.baibao.cloud;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.device.DeviceManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baibao.cloud.present.QueryPresent;
import com.baibao.cloud.utils.Utils;
import com.jakewharton.rxbinding2.view.RxView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseActivity extends Activity {
    protected Context context;
    ProgressDialog progressDialog;
    DeviceManager deviceManager;
    QueryPresent present;
    Utils util;
    boolean stop = false;//网络请求标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        deviceManager = new DeviceManager();
        util = Utils.getInstance();
        present = QueryPresent.getInstance(BaseActivity.this);
    }

    protected void showWaitDialog(String tip) {
        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setMessage(tip);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setOnDismissListener((dia) -> onProgressDissmiss());
        progressDialog.show();

    }

    /**
     *
     */
    protected void onProgressDissmiss() {
        stop = true;
    }

    protected void hideWaitDialog() {
        progressDialog.dismiss();
    }


    protected void showDialog(int type, String title, String tip, String posbtn, String negbtn) {
        AlertDialog dialog = null;
        if (negbtn == null) {
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .create();
        } else {
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .setNegativeButton(negbtn, (dia, which) -> cancel(type, dia)).create();
        }
        dialog.setCancelable(false);
        dialog.show();

    }

    protected void showEditDialog(String count) {
        final EditText inputServer = new EditText(BaseActivity.this);
        inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputServer.setText(count);
        inputServer.selectAll();
        inputServer.setFocusable(true);
        inputServer.setFocusableInTouchMode(true);
        inputServer.requestFocus();

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("请输入数量").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, type) -> edit(inputServer, type, dialog));
        builder.setNegativeButton("取消", (dialog, type) -> cancelEdit(inputServer, type, dialog));
        builder.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) inputServer.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(inputServer, 0);
                           }
                       },
                500);

    }

    public String currentDate(long time,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(time!=-1) {
            return sdf.format(new Date(time));
        }
        return sdf.format(new Date());
    }

    protected void confirm(int type, DialogInterface dialog) {
        dialog.dismiss();
    }

    protected void cancel(int type, DialogInterface dialog) {
        dialog.dismiss();
    }


    protected void edit(EditText inputServer, int type, DialogInterface dialog) {
        dialog.dismiss();
    }
    protected void cancelEdit(EditText inputServer, int type, DialogInterface dialog) {
        dialog.dismiss();
    }


}
