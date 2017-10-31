package com.example.dialogdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void uploadAvatar() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                cameraTask();
                            }
                        })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
//                                album();
                            }
                        })
                .show();
    }

    private void logout() {
        new MyAlertDialog(this).builder()
                .setCancelable(false)
                .setTitle("退出")
                .setUpdateMsg("是否退出当前账号？")
                .setNegativeButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        return;
                    }
                })
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }


    /**
     * 弹窗输入登录密码,在提交数据
     */
    private void showInputDialog() {

        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.layout_edittext, null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.edit_text);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("请输入密码").setView(dialogView);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mEditText = (EditText) dialogView.findViewById(R.id.edit_text);
                        String pwd = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(pwd)) {
                            Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        AlertDialog alertDialog = inputDialog.create();
        //弹窗软键盘
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        alertDialog.show();

    }
}
