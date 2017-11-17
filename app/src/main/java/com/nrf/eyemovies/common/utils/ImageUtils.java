package com.nrf.eyemovies.common.utils;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2017/08/04
 *     desc   : 加载图片封装类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */

public class ImageUtils {


    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static final String DST_FOLDER_NAME = "Spvball_pic";
    private static long dataTake;
    private static String jpegName;
    private static File mCurrentPhotoFile;
    private static PopupWindow stakepopwindow;
    private static Activity mActivity;
    public static final int TAKE_OR_CHOOSE_PHOTO = 3024;
    public static final int CAMERA_PERMISSION = 1003;
    public static final int STORAGE_PERMISSION = 1004;
    /**
     * 加载一般图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadNormalImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
//                .placeholder(com.huayu.coorlib.R.mipmap.default_image_normal)
//                .error(R.drawable.icon_default_media)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .thumbnail(0.8f)//缩略图
                .centerCrop()
                .into(imageView);
    }


    /**
     * 加载一般图片 按照下载下来的图片宽高比显示
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadNormalImageWidthHeightScale(final Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
                .asBitmap()
//                .placeholder(R.drawable.icon_default_media)
//                .error(R.drawable.icon_default_media)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .thumbnail(0.6f)//缩略图
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.width = ScreenUtils.getScreenWidth(context);
                        layoutParams.height = (int) (layoutParams.width * (height * 1.0f / width));
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setImageBitmap(resource);
                    }
                });

    }

    /**
     * 按照给定的宽高加载图片
     *
     * @param context   上下文
     * @param url       地址
     * @param imageView 控件
     * @param width     宽
     * @param height    高
     */
    public static void loadNormalImageWithPostImageEntity(Context context, String url, ImageView
            imageView, double width, double height) {
        double scale = height / width;
        if (width > ScreenUtils.getScreenWidth(context)) {
            width = ScreenUtils.getScreenWidth(context);
            height = scale * width;
        }
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
                .centerCrop()
//                .placeholder(R.drawable.icon_default_media)
//                .error(R.drawable.icon_default_media)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override((int) width, (int) height)
                .into(imageView);
    }

    public static void loadNormalImageWithPostImageEntity(Context context, String url, ImageView
            imageView, double width, double height, int margin) {
        double scale = height / width;
        if (width > ScreenUtils.getScreenWidth(context)) {
            width = ScreenUtils.getScreenWidth(context) - 2 * DensityUtils.dp2px(context, margin);
            height = DensityUtils.dp2px(context, 220);
        }
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
                .centerCrop()
//                .placeholder(R.drawable.icon_default_media)
//                .error(R.drawable.icon_default_media)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override((int) width, (int) height)
                .into(imageView);
    }


    /**
     * 加载大图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadshowBigNormalImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
//                .placeholder(R.drawable.icon_default_media)
//                .error(R.drawable.icon_default_media)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadGifImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param context
     * @param url       图片地址
     * @param imageView 图片控件
     * @param normal    如果是头像传false 正常则传true
     */
    public static void loadCircleImage(final Context context, String url, final ImageView imageView, boolean normal) {
        int res = -1;
//        if (normal) {
//            res = R.drawable.icon_default_media;
//        } else {
//            res = R.drawable.icon_default_media;
//        }
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
                .asBitmap()
                .placeholder(res)
                .error(res)
                .centerCrop()
                .thumbnail(0.8f)//缩略图
                .transform(new GlideCircleTransform(context))
                .into(imageView);

    }

    public static void loadRoundRecImage(final Context context, String url, int roundpx, int type, final ImageView imageView) {
        int res = -1;
//        res = R.drawable.icon_default_media;
        Glide.with(context)
                .load(StringUtils.formatImageUrl(url))
                .asBitmap()
                .placeholder(res)
                .error(res)
                .centerCrop()
                .thumbnail(0.8f)//缩略图
                .transform(new GlideRonudRecTransform(context, roundpx, type))
                .into(imageView);

    }


    public static class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;

        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public static class GlideRonudRecTransform extends BitmapTransformation {
        public static final int ALL = 347120;
        public static final int TOP = 547120;
        public static final int LEFT = 647120;
        public static final int RIGHT = 747120;
        public static final int BOTTOM = 847120;
        public static final int LEFT_DIAGONAL = 0x123;//左对角线
        public static final int RIGHT_DIAGONAL = 0x223;//右对角线
        int type;
        int roundPx;

        public GlideRonudRecTransform(Context context, int type, int roundpx) {
            super(context);
            this.type = type;
            this.roundPx = roundpx;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return drawRondRec(pool, toTransform);
        }

        private Bitmap drawRondRec(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }
            int height = source.getHeight();
            int width = source.getWidth();
            Bitmap paintingBoard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(paintingBoard);
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            if (TOP == type) {
                clipTop(canvas, paint, roundPx, width, height);
            } else if (LEFT == type) {
                clipLeft(canvas, paint, roundPx, width, height);
            } else if (RIGHT == type) {
                clipRight(canvas, paint, roundPx, width, height);
            } else if (BOTTOM == type) {
                clipBottom(canvas, paint, roundPx, width, height);
            } else if (LEFT_DIAGONAL == type) {
                clipLeftDiagonal(canvas, paint, roundPx, width, height);
            } else if (RIGHT_DIAGONAL == type) {
                clipRightDiagonal(canvas, paint, roundPx, width, height);
            } else {
                clipAll(canvas, paint, roundPx, width, height);
            }
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, width, height);
            final Rect dst = src;
            canvas.drawBitmap(source, src, dst, paint);
            return paintingBoard;

        }

        private static void clipLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
            final Rect block = new Rect(offset, 0, width, height);
            canvas.drawRect(block, paint);
            final RectF rectF = new RectF(0, 0, offset * 2, height);
            canvas.drawRoundRect(rectF, offset, offset, paint);
        }

        private static void clipRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
            final Rect block = new Rect(0, 0, width - offset, height);
            canvas.drawRect(block, paint);
            final RectF rectF = new RectF(width - offset * 2, 0, width, height);
            canvas.drawRoundRect(rectF, offset, offset, paint);
        }

        private static void
        clipTop(final Canvas canvas, final Paint paint, int offset, int width, int height) {
            final Rect block = new Rect(0, offset, width, height);
            canvas.drawRect(block, paint);
            final RectF rectF = new RectF(0, 0, width, offset * 2);
            canvas.drawRoundRect(rectF, offset, offset, paint);
        }

        private static void clipBottom(final Canvas canvas, final Paint paint, int offset, int width, int height) {
            final Rect block = new Rect(0, 0, width, height - offset);
            canvas.drawRect(block, paint);
            final RectF rectF = new RectF(0, height - offset * 2, width, height);
            canvas.drawRoundRect(rectF, offset, offset, paint);
        }

        private static void clipAll(final Canvas canvas, final Paint paint, int offset, int width, int height) {
            final RectF rectF = new RectF(0, 0, width, height);
            canvas.drawRoundRect(rectF, offset, offset, paint);
        }

        private static void clipLeftDiagonal(Canvas canvas, Paint paint, int offset, int width, int height) {
            Rect rect1 = new Rect(offset, 0, width, height - offset);
            canvas.drawRect(rect1, paint);
            Rect rect2 = new Rect(0, offset, width - offset, height);
            canvas.drawRect(rect2, paint);
            canvas.drawCircle(offset, offset, offset, paint);
            canvas.drawCircle(width - offset, height - offset, offset, paint);
        }

        private static void clipRightDiagonal(Canvas canvas, Paint paint, int offset, int width, int height) {
            Rect rect1 = new Rect(0, 0, width - offset, height - offset);
            canvas.drawRect(rect1, paint);
            Rect rect2 = new Rect(offset, offset, width, height);
            canvas.drawRect(rect2, paint);
            canvas.drawCircle(width - offset, offset, offset, paint);
            canvas.drawCircle(offset, height - offset, offset, paint);
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * Save image to the SD card
     * 保存图片到SD卡
     *
     * @param photoBitmap
     * @param photoName
     * @param photoName
     */
    public static String savePhotoToSDCard(Bitmap photoBitmap, String photoName) {

        String path = initPath();
        dataTake = System.currentTimeMillis();
        jpegName = new String();
        jpegName = path + "/" + dataTake + ".jpg";
        if (checkSDCardAvailable()) {

            FileOutputStream fileOutputStream = null;
            try {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fileOutputStream = new FileOutputStream(new File(jpegName));
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
//						fileOutputStream.close();
                    }
                }
                return jpegName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static String initPath() {
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
        return storagePath;
    }

    /**
     * Check the SD card
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.US);
        return dateFormat.format(date) + ".jpg";
    }

    //得到保存的文件名字
    public static String filename() {
        return "/" + dataTake + ".jpg";
    }

    public static final File PHOTO_DIR = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    // public static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory()
//            + "/DCIM/Camera");
    public static File getOutPutFile() {
        return new File(PHOTO_DIR, getPhotoFileName());
    }

    public static void dissmissPopupWindow() {
        if (stakepopwindow != null && stakepopwindow.isShowing()) {
            stakepopwindow.dismiss();
        }
    }


    public static void choosePic(Activity context, int requestCode) {
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(galleryIntent, requestCode);
        stakepopwindow.dismiss();
    }



    public static void openCamera(Uri outputFileUri, Activity context, int requestCodeUser) {
        final Intent captureIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent2.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        context.startActivityForResult(captureIntent2, requestCodeUser);
        stakepopwindow.dismiss();
    }

    public static void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getPhotoFromResult(Context context, Intent data) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            isCamera = MediaStore.ACTION_IMAGE_CAPTURE.equals(data.getAction());
        }
        File f = null;
        if (isCamera) {
            f = mCurrentPhotoFile;
//            f = new File(getRealPathFromURI(context, data.getData()));
        } else {
            if (data != null) {
                try {
                    String path = getRealPathFromURI(context, data.getData());
                    f = new File(path);
                    if ((f == null) || !f.exists()) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ((f == null) || !f.exists()) {
                }
            }
        }
        return f;
    }


    /**
     * 根据获取到的Uri得到文件路径
     *
     * @param context    上下文
     * @param contentURI 获得的Uri
     * @return 返回文件路径
     */
    private String getRealPathFromURIAll(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * 根据获取到的Uri得到文件路径
     *
     * @param context 上下文
     * @param uri     获得的Uri
     * @return 返回文件路径
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (uri == null) {
            //FIXME: workaround for null uri, will try to get the last picture!
            Cursor myCursor = null;
            try {
                String[] largeFileProjection = {
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATA};

                String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
                myCursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        largeFileProjection, null, null, largeFileSort);
                String largeImagePath = "";

                if (myCursor.moveToFirst()) {
                    largeImagePath = myCursor.getString(1);
                    Uri imageCaptureUri = Uri.fromFile(new File(largeImagePath));
                    return imageCaptureUri.getPath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (myCursor != null) {
                    myCursor.close();
                }
            }

        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if ((cursor != null) && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static Uri getMediaFileUri() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Camera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return Uri.fromFile(mediaFile);
    }

    public static File getMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Camera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }


}
