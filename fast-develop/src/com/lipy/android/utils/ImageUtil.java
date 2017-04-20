package com.lipy.android.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by dragon on 2014/7/29.
 */

@SuppressLint("SdCardPath")
public class ImageUtil {
    //获取图片
    public static Bitmap getBitmap(String string){
        if (string==null ||"".equals(string)) return null;
        if (string.startsWith("http:"))
            return getNetBitmap(string);
        else
            return readBitMap(string);

    }

    /**
     * 将InputStream对象转换为Byte[]
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(InputStream is) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        return baos.toByteArray();
    }

    private static Bitmap getNetBitmap(String url) {

        Bitmap bit=null;
        try {
            URL imageURL = new URL(url);
            HttpURLConnection conn=(HttpURLConnection)imageURL.openConnection();
            conn.setDoInput(true);

            conn.connect();
            InputStream inputStream=conn.getInputStream();
            bit=BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bit;
    }


    // 放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    // 将Drawable转化为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    public static String SaveBitmap(Bitmap bmp, String name) {
        File file = new File("mnt/sdcard/picture/");
        String path = null;
        if (!file.exists())
            file.mkdirs();
        try {
            path = file.getPath() + "/" + name;
            FileOutputStream fileOutputStream = new FileOutputStream(path);

            bmp.compress(CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("saveBmp is here");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    public static String saveToLocal(Bitmap bm) {
        String path = "/sdcard/test.jpg";
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bm.compress(CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return path;
    }

    // 获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    // 获得带倒影的图片方法
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 读取资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * @param context
     * @param RDrawableClz 这个地方应该传递 R.Drawable.class
     * @param imageName
     * @return
     */
    public static Bitmap readBitMap(Context context, Class RDrawableClz, String imageName) {
        Field field = null;
        try {
            field = RDrawableClz.getField(imageName);
            int imageId = field.getInt(field.getName());

            return readBitMap(context, imageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从sd卡读取图片
     *
     * @param path
     * @return
     */
    public static Bitmap readBitMap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        return bm;
    }

    public static Bitmap getBitmapFromFile(String filePath, int width, int height) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        File file = new File(filePath);
        return getBitmapFromFile(file, width, height);
    }

    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从资源图片里面获取指定文件名的图片，如果没有就返回空
     *
     * @param bitmapName 指定的要查找的文件名
     */
    public static int getDrawableIdFromDrawable(Class drawableClass, String bitmapName) {
        // 利用反射机制获取对象域名
        Object obj;
        String varName;
        Field[] fields = drawableClass.getDeclaredFields();
        for (Field field : fields) {
            // 对于每个属性，获取属性名
            varName = field.getName();
            if (varName.equals(bitmapName)) {
                try {
                    // 获取原来的访问控制权限
                    boolean accessFlag = field.isAccessible();
                    // 修改访问控制权限
                    field.setAccessible(true);
                    // 获取在对象f中属性fields[i]对应的对象中的变量
                    obj = field.get(varName);
                    // 恢复访问控制权限
                    field.setAccessible(accessFlag);
                    return Integer.parseInt(obj.toString());
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return -1;
    }

    private static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * @param normalColor
     * @param pressedColor
     * @param strokeColor
     * @param roundRadius
     * @param strokeWidth
     * @return
     */
    public static StateListDrawable addStateDrawable(String normalColor, String pressedColor, String strokeColor, int roundRadius, int strokeWidth) {
        Drawable normal = drawableRectangle(normalColor, strokeColor, roundRadius, strokeWidth);
        Drawable pressed = drawableRectangle(pressedColor, strokeColor, roundRadius, strokeWidth);
        return addStateDrawable(normal, pressed);
    }

    public static StateListDrawable addStateDrawable(Context context, Class drawableClass, String normalImage, String pressedImage) {
        Drawable normal = bitmapToDrawable(readBitMap(context, drawableClass, normalImage));
        Drawable pressed = bitmapToDrawable(readBitMap(context, drawableClass, pressedImage));
        return addStateDrawable(normal, pressed);
    }


    public static StateListDrawable addStateDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, pressed);
        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        sd.addState(new int[]{android.R.attr.state_focused}, pressed);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);
        return sd;
    }


    /**
     * 实现shape
     *
     * @param fillColor
     * @param strokeColor
     * @param roundRadius
     * @param strokeWidth
     * @return
     */
    public static GradientDrawable drawableRectangle(String fillColor, String strokeColor, int roundRadius, int strokeWidth) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(fillColor));
        gradientDrawable.setCornerRadius(roundRadius);
        gradientDrawable.setStroke(strokeWidth, Color.parseColor(strokeColor));
        return gradientDrawable;
    }


    public static GradientDrawable drawableRectangle(GradientDrawable.Orientation orientation, String[] fillColors, String strokeColor, int roundRadius, int strokeWidth) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            gradientDrawable.setColor(Color.parseColor(fillColors[0]));

        } else {
            gradientDrawable.setOrientation(orientation);
            int[] colors = new int[fillColors.length];
            for (int i = 0; i < fillColors.length; i++) {
                colors[i] = Color.parseColor(fillColors[i]);
            }
            gradientDrawable.setColors(colors);
        }
        gradientDrawable.setCornerRadius(roundRadius);
        gradientDrawable.setStroke(strokeWidth, Color.parseColor(strokeColor));
        return gradientDrawable;
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return bitmap;
    }

    /**
     * 将图像保存到SD卡中
     */
    public static void saveMyBitmap(String filePath, Bitmap mBitmap) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                mBitmap.compress(CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * bitmap中的透明色用白色替换
     * @param orginBitmap
     * @return
     */
    public static Bitmap drawBg4Bitmap(Bitmap orginBitmap) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Bitmap bitmap = Bitmap.createBitmap(orginBitmap.getWidth(),
                orginBitmap.getHeight(), orginBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, orginBitmap.getWidth(), orginBitmap.getHeight(), paint);
        canvas.drawBitmap(orginBitmap, 0, 0, paint);
        return bitmap;
    }
}