package com.example.androidnadaption;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidnadaption.crop.CropActivity;
import com.example.androidnadaption.widget.ActionSheetDialog;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class CameraAndAlbumActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{


    private Uri imageUri;
    private static final int TAKE_PHOTO = 111;
    private static final int CHOOSE_PHOTO = 112;
    private static final int CUT_PHOTO = 0x04;
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private File mFileImage;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_and_album);

        mImageView = (ImageView) findViewById(R.id.iv);

        findViewById(R.id.task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task();
            }
        });
    }

    /**
     * 图片选择对话框
     */
    public void task(){
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
//                .setTitle("更新头像")
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                cameraTask();
                            }
                        })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                album();
                            }
                        })
                .show();
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    showTakePhoto();
                    break;
                case CHOOSE_PHOTO:
                    showChoosePhoto(data);
                    break;
                case CUT_PHOTO:
                    showImage(data);
                    break;

                default:
                    break;
            }
        }

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //在应用程序中对权限进行设置后，返回到这里
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }

    }

    //==================================权限 start==============================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //处理该请求的结果。
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            takePhoto();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_CAMERA_PERM, perms);
        }
    }


    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void album() {//获取权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            openAlbum();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        //(可选)检查用户是否勾选“不再询问。”
        //这将显示一个对话框,指导他们在应用程序启用权限设置。
        //用户勾选了不在询问权限后，弹出自己的对话框告诉用户，可以在应用程序中对权限进行设置，点“确定”后，跳转到应用程序的设置页面
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("提示")
                    .setRationale("请手动打开 设置-更多应用-demo-权限管理 打开相关权限，否则可能影响app的正常使用")
                    .setPositiveButton("确定")
                    .setNegativeButton("取消")
                    .build().show();
//            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    //==================================权限 end==============================

    //=====================================图片处理 start=====================================

    /**
     * 拍照
     */
    public void takePhoto() {

        mFileImage = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        LogUtil.e("haha 图片 拍照 ", mFileImage.getAbsolutePath());

        if (!mFileImage.getParentFile().exists()) {
            mFileImage.getParentFile().mkdirs();
        }

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", mFileImage);
        } else {
            imageUri = Uri.fromFile(mFileImage);
        }

        if (imageUri != null) {
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(captureIntent, TAKE_PHOTO);
        }
    }

    /**
     * 系统手机相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);//android.intent.action.PICK
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


    /**
     * 拍照的回调
     */
    private void showTakePhoto() {
        try {

            String imageUriPath = imageUri.getPath();
            String imagePath = mFileImage.getAbsolutePath();
            CropActivity.show(this, imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 相册的回调
     */
    private void showChoosePhoto(Intent data) {

        if (Build.VERSION.SDK_INT >= 19) {
            // 4.4及以上系统使用这个方法处理图片
            handleImageOnKitKat(data);
        } else {
            // 4.4以下系统使用这个方法处理图片
            handleImageBeforeKitKat(data);
        }
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        if (data == null || data.getData() == null) {
            return;
        }
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        if (data == null || data.getData() == null) {
            return;
        }
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 获取到的系统图片--剪切
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            CropActivity.show(this, imagePath);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 最终显示的图片
     *
     * @param data
     */
    private void showImage(Intent data) {
        String imagePath = data.getStringExtra("crop_path");
        File file = new File(imagePath);

        if (file == null) {
            Toast.makeText(CameraAndAlbumActivity.this, "剪切图片失败", Toast.LENGTH_SHORT).show();
            return;
        }

//        updataImage(file);  //上传图片到服务器


        mImageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
    }


    //=====================================图片处理 end=====================================

}
