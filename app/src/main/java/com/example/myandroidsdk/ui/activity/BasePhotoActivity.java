package com.example.myandroidsdk.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.Image;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.utils.UriUtils;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by weiyang on 2018/1/25.
 * 封装图片处理的基类页面
 */
public abstract class BasePhotoActivity extends BaseSecondActivity {
    protected final int REQUEST_CODE_ALBUM = 0x101;
    protected final int REQUEST_CODE_CAMERA = 0x102;
    protected final int REQUEST_CODE_CROP = 0x103;

    /**
     * 默认压缩图片,如若不需要压缩,子类实现
     */
    private boolean isCompress = true;

    protected boolean isCompress() {
        return isCompress;
    }

    /**
     * 是否裁剪图片
     */
    private boolean isCrop;

    public Uri tempUri;

    protected void openAlbum() {
        checkPermission(Permission.Group.STORAGE, data -> {
            Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
            intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intentToPickPic, REQUEST_CODE_ALBUM);
        });
    }

    protected void openAlbumCrop() {
        isCrop = true;

        openAlbum();
    }

    protected void openCamera() {
        checkPermission(Permission.Group.CAMERA, data -> {
            //执行拍照前，应该先判断SD卡是否存在
            String SDState = Environment.getExternalStorageState();
            if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
                    /***
                     * 需要说明一下，以操作使下操作使用照相机拍照，拍照后的图片会存放在相册中的
                     * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
                     * 如果不实用ContentValues存放照片路径的话，拍照明一下，以操作使后获取的图片为缩略图不清晰
                     */
                    ContentValues values = new ContentValues();
                    tempUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } catch (Exception e) {
                    showToast("找不到系统相机");
                }
            } else {
                showToast("内存卡不存在!");
            }
        });
    }

    protected void openCameraCrop() {
        isCrop = true;

        openCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ALBUM) {
                Uri uri = data.getData();
                String path = UriUtils.getFilePathFromUri(this, uri);

                if (isCrop)
                    compressAndCrop(path);
                else if (isCompress())
                    compressImage(path);
                else
                    noCompressSuccess(path);
            } else if (requestCode == REQUEST_CODE_CAMERA) {
                String path = UriUtils.getFilePathFromUri(this, tempUri);
                if (TextUtils.isEmpty(path))
                    return;


                if (isCrop) {
                    compressAndCrop(path);
                } else if (isCompress()) {
                    compressImage(path);
                } else {
                    noCompressSuccess(path);
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
                if (data != null)
                    try {
                        Bitmap bp = BitmapFactory.decodeStream(getContentResolver().openInputStream(tempUri));
                        cropSuccess(bp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            onCancel();
        }
    }

    private void compressAndCrop(String imgPath) {
        Luban.with(this)
                .load(imgPath)
                .ignoreBy(200)
                .setTargetDir(getThumbDir())
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        String path = file.getAbsolutePath();

                        cropPictures(path);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用
                        //compressFailed();
                    }
                }).launch();
    }

    private void cropPictures(String path) {
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tempUri = FileProvider.getUriForFile(this, "com.yccj.client.fileprovider", file);
        } else {
            tempUri = Uri.fromFile(file);
        }
        if (tempUri == null) {
            Log.i("tag", "The uri is not exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(tempUri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // 设置x,y的比例，截图方框就按照这个比例来截 若设置为0,0，或者不设置 则自由比例截图
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    private int count;

    protected void compressImage(String path1) {
        Luban.with(this)
                .load(path1)
                .ignoreBy(200)
                .setTargetDir(getThumbDir())
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        String path = file.getAbsolutePath();

                        compressSuccess(path);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用
                        compressFailed();
                    }
                }).launch();
    }

    protected String getThumbDir() {
        File file = new File(getCacheDir(), "thumb");
        if (!file.exists())
            file.mkdirs();
        return file.getAbsolutePath();
    }

    protected void compressSuccess(String path) {

    }

    protected void compressFailed() {

    }

    protected void cropSuccess(Bitmap bitmap) {

    }

    protected void noCompressSuccess(String path) {

    }

    protected void onCancel() {

    }

    protected String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "data:image/png;base64," + result;
    }

    /**
     * 图片上传接口
     *
     * @param path     本地图片路径
     * @param listener 上传成功的回调
     */
    protected void uploadImage(String path, @NonNull OnUploadedListener listener) {
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .uploadImage(imageToBase64(path))
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .compose(RxUtils.bindEvent(this))
                .subscribe(   new BaseObserver<Image>() {
                    @Override
                    public void onSuccess(Image data) {
                        listener.uploaded(data);
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress(R.string.text_loading_normal);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                    }
                });
    }

    public interface OnUploadedListener {
        void uploaded(Image data);
    }
}
