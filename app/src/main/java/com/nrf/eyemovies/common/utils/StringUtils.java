package com.nrf.eyemovies.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nrf.eyemovies.common.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2017/08/04
 *     desc   : 字符串操作工具类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */

public class StringUtils {
    private static final String atRule = "@[^,，：:。@\b#\n\\s]+";
    private static final String pictureRule = "#图片#";
    private static final String pictureWordsRule = "查看图片#####";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static Boolean ISNOPIC = null;

    public static SpannableStringBuilder getAtHeightLightString(String text) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(atRule);
        Matcher matcher = pattern.matcher(text);
        for (int i = 0; i < text.length(); i++) {
            if (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                Spanned htmlText = Html.fromHtml(String.format("<font color='%s'>" + text.substring(start, end) + "</font>",
                        "#0000ff"));
                spannableStringBuilder.replace(start, end, htmlText);
            }
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getAtHeightLight(String text) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        Spanned htmlText = Html.fromHtml(String.format("<font color='%s'>" + text + "</font>", "#0000ff"));
        spannableStringBuilder.replace(0, text.length(), htmlText);
        return spannableStringBuilder;
    }

    public static long getLongMilionSeconds(String time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = f.parse(time);
        } catch (ParseException e) {
            return 0;
        }
        return parse.getTime();

    }

    /**
     * '@XXX
     *
     * @param text 文字
     * @return
     */
    public static SpannableStringBuilder setAtHeighLight(String text, Context context) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(atRule);
        Matcher matcher = pattern.matcher(text);
        int length = text.length();
        for (int i = 0; i < length; i++) {
            if (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String str = text.substring(start + 1, end);
                spannableString.setSpan(new MyClickSpan(str, context), start, end, Spanned.SPAN_MARK_POINT);
            }
        }
        return spannableString;
    }

    public static class MyClickSpan extends ClickableSpan {
        String name;
        Context context;

        public MyClickSpan(String name, Context context) {
            this.name = name;
            this.context = context;
        }

        @Override
        public void onClick(View arg0) {
//            ARouter.getInstance()
//                    .build(UserRouter.USER_PERSONALCENTER)
//                    .withString("userName", name)
//                    .greenChannel()
//                    .navigation();

        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    /**
     * 设置高亮 传入字符串
     *
     * @param text    要高亮的字符串
     * @param context 上下文
     * @return
     */
    public static SpannableStringBuilder setHighlight(String text, Context context) {
        text = text.replaceAll(pictureRule, pictureWordsRule);
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(atRule);
        Matcher matcher = pattern.matcher(text);
        int length = text.length();
        SpannableStringBuilder spannableStringBuilder =
                new SpannableStringBuilder(text);
        for (int i = 0; i < length; i++) {
            if (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String str = text.substring(start, end);
//                spannableString.setSpan(new MyClickSpan(str, context), start, end, Spanned.SPAN_MARK_POINT);
                Spanned htmlText = Html.fromHtml(String.format("<font color='%s'>" + str + "</font>", "#0000ff"));
                spannableStringBuilder.replace(matcher.start(), matcher.start() + str.length(), htmlText);
                int index = matcher.start() + htmlText.length();
                if (index < text.length()) {
                    if (" ".equals(text.subSequence(index - 1, index))) {
                        spannableStringBuilder.replace(index - 1, index, "\b");
                    } else {
                        spannableStringBuilder.insert(index, "\b");
                    }
                } else {
                    if (text.substring(index - 1).equals(" ")) {
                        spannableStringBuilder.replace(index - 1, index, "\b");
                    } else {
                        //如果是最后面的没有空格，补上\b
                        spannableStringBuilder.insert(index, "\b");
                    }
                }
            }
        }
        return spannableStringBuilder;
    }

    /**
     * @param text     文本
     * @param color    背景色   顶置为#f0c000   精华为#fc8f46
     * @param textSize
     * @return
     */
    public static SpannableStringBuilder getHeightBackground(String text, int color, int textSize) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        spannableStringBuilder.setSpan(new BackgroundColorSpan(color), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(BaseApplication.getInstance(), textSize), false)
                , 0, 4,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder getRecommendText(int textSize) {
        return getHeightBackground(" 精华 ", Color.parseColor("#fc8f46"), textSize);
    }

    public static SpannableStringBuilder getStickyText(int textSize) {
        return getHeightBackground(" 置顶 ", Color.parseColor("#f0c000"), textSize);
    }

    /**
     * @param text  文本
     * @param color 修改字体颜色   顶置为#d22330   精华为#f4bf08
     * @return
     */
    public static SpannableStringBuilder setTextColor(String text, int color) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 0, 3,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder setTextColor(String text, int color, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    private static Boolean getISNOPIC() {
        if (ISNOPIC == null) {
            ISNOPIC = (Boolean) SharedPreferencesUtils.get(BaseApplication.getInstance(), "isNoPic", false);
        }
        return ISNOPIC;
    }

    /**
     * 格式化图片地址
     *
     * @param url
     * @return
     */
    public static String formatImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        url = url.trim();
        if (url.contains("content:")) {
            return url;
        }
        if (url.contains("/storage/")) {
            return url;
        }
        if (!url.contains("http:") && !url.contains("https:")) {
//            url = String.format(ConstantUtils.BASE_MEDIA_FILE_URI, url);
        }
        return url;
    }

    /**
     * 得到中文路径的图片地址
     *
     * @param url
     * @return
     */
    public static String getChineseImageURL(String url) {
        String encode = null;
        try {
            encode = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String replace = encode.replace("%3A", ":").replace("%2F", "/").replace("%3D", "=").replace("%2C", ",").replace("%26",
                "&");
        return replace;
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
        }
        return DateLocal.get();
    }

    public static String getTodayOrYesterday(String str) {
        String strDes = "";
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = getDateFormat().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                strDes = "今天";
            } else if (diffDay == -1) {
                strDes = "昨天";
            } else if (diffDay == 1) {
                strDes = "明天";
            } else {
                strDes = getYearMothDay(str);//直接显示时间
            }
        } else {
            strDes = getYearMothDay(str);//直接显示时间
        }
        return strDes;
    }


    /**
     * 后台接口返回的时间，可以是yyyy-MM-dd HH:mm:ss 也可以是时间戳
     */
    public static String getUnificationTime(String str) {
        try {
            if (str.contains(":")) {
                return getTwoDaysDistance(str);
            } else {
                //时间戳
                return longStringToTwoDaysDistance(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static Date stringToDate(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 传入字符串事件 距离现在事件的间隔
     *
     * @param timeStr yyyy-MM-dd HH:mm:ss类型字符串
     * @return
     */
    public static String getTwoDaysDistance(String timeStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            Date date = dateFormat.parse(timeStr);
            Date dateToday = new Date();
            long timeToday = dateToday.getTime();
            long time = date.getTime();
            long timeLong = timeToday - time;
            if (timeLong < -10000) {
                return "未知时间";
            } else if (timeLong < 60 * 1000) {
                //一分钟之内
                return "刚刚";
            } else if (timeLong < 60 * 60 * 1000) {
                //一个小时之内
                timeLong = timeLong / 1000 / 60;
                return timeLong + "分钟前";
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Calendar calendarToday = Calendar.getInstance();
                calendarToday.setTime(dateToday);
                //如果是今天
                if (calendar.get(Calendar.DAY_OF_YEAR) == calendarToday.get(Calendar.DAY_OF_YEAR)) {
                    return getMonthHourMinuteChinese(time);
                } else {
                    if (calendar.get(Calendar.YEAR) == calendarToday.get(Calendar.YEAR)) {
                        //如果是今年
                        return getMonthHourMinuteTime(time);
                    } else {
                        //如果不是今年
                        return longStringToStringChinese(time + "");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * date转化为字符串 YYYY_MM_DD
     *
     * @param data date对象
     * @return YYYY-MM-DD字符串
     */
    public static String getTimeStringYYYY_MM_DD(Date data) {
        return new SimpleDateFormat(YYYY_MM_DD).format(data);
    }

    /**
     * @param longTime 毫秒值
     * @return 返回 HH:mm类型字符串
     */
    public static String getHourMinuteTime(long longTime) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        String result = time.format(new Date(longTime));
        return result;
    }

    /**
     * @param
     * @return 返回 HH:mm类型字符串
     */
    public static String getYearMothDay(String str) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        try {
            Date parse = time_mm.parse(str);
            result = time.format(parse);
        } catch (ParseException e) {
            return "";
        }

        return result;
    }

    /**
     * @param
     * @return 返回 HH:mm类型字符串
     */
    public static String getHHmm(String str) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        SimpleDateFormat time_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        try {
            Date parse = time_mm.parse(str);
            result = time.format(parse);
        } catch (ParseException e) {
            return str;
        }

        return result;
    }


    /**
     * @param longTime 毫秒值
     * @return 返回 HH:mm类型字符串
     */
    public static String getMonthHourMinuteTime(long longTime) {
        SimpleDateFormat time = new SimpleDateFormat("MM-dd HH:mm");
        String result = time.format(new Date(longTime));
        return result;
    }
    /**
     * @param longTime 毫秒值
     * @return 返回 HH:mm类型字符串
     */
    public static String getYearMonth(long longTime) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        String result = time.format(new Date(longTime));
        return result;
    }


    /**
     * @param longTime 毫秒值
     * @return 返回 HH:mm类型字符串
     */
    public static String getMonthHourMinuteChinese(long longTime) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        String result = time.format(new Date(longTime));
        return result;
    }

    /**
     * @param longTime 毫秒值
     * @return 返回 HH:mm类型字符串
     */
    public static String getMonthHourMinuteTimeChinese(long longTime) {
        SimpleDateFormat time = new SimpleDateFormat("MM月dd日 HH:mm");
        String result = time.format(new Date(longTime));
        return result;
    }

    /**
     * long 类型字符串 转换成事件 yyyy-MM-dd HH:mm
     *
     * @param longString
     * @return
     */
    public static String longStringToStringChinese(String longString) {
        try {
            Long aLong = Long.valueOf(longString);
            Date date = new Date(aLong);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return format.format(date);
        } catch (Exception e) {
            return longString;
        }
    }

    public static String longStringToTwoDaysDistance(String longString) {
        Long aLong = Long.valueOf(longString);
        Date date = new Date(aLong);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getTwoDaysDistance(format.format(date));
    }

    public static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.CHINA, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.CHINA, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

    /**
     * 将文件进行base64编码,
     *
     * @param fileStr
     * @return
     */
    public static String encodeBase64(String fileStr) {
        File file = new File(fileStr);
        String result = null;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                int len = -1;
                byte[] data = new byte[1024];
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                while ((len = inputStream.read(data)) != -1) {
                    os.write(data, 0, len);
                }
                byte[] resData = os.toByteArray();
                byte[] encode = Base64.encode(resData, Base64.DEFAULT);
                inputStream.close();
                result = new String(encode);
                //进行URL编码
                result = URLEncoder.encode(result, "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将bitmap转换成base64字符串
     *
     * @param bitmap
     * @return base64 字符串
     */
    public static String encodeBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);//将bitmap放入字节数组流中
                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();
                byte[] bitmapByte = bos.toByteArray();
                result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
                //进行URL编码
                result = URLEncoder.encode(result, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void setTextImage(final Context context, final TextView textView, final List<String> urls) {
        String text = textView.getText().toString();
        final SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        Pattern patternAt = Pattern.compile(atRule);
        Matcher matcherAt = patternAt.matcher(text);
        Pattern pattern = Pattern.compile(pictureRule);
        Matcher matcher = pattern.matcher(text);
        final int widthPixels = textView.getMeasuredWidth();
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (matcherAt.find()) {
                int start = matcherAt.start();
                int end = matcherAt.end();
                String name = text.substring(start + 1, end);
                stringBuilder.setSpan(new AtPeopleClickableSpan(context, name), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (matcher.find()) {
                final int start = matcher.start();
                final int end = matcher.end();
                final int currentPosition = count;
                Glide.with(context)
                        .load(StringUtils.formatImageUrl(urls.get(currentPosition)))
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                stringBuilder.setSpan(new ImageClickableSpan(currentPosition, urls), start, end, Spannable
                                        .SPAN_INCLUSIVE_EXCLUSIVE);
                                Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                                double v = widthPixels * 1.0 / resource.getWidth() * resource.getHeight();
                                drawable.setBounds(0, 20, widthPixels, (int) v);
                                ImageSpan imageSpan = new ImageSpan(drawable);
                                stringBuilder.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                textView.setText(stringBuilder);
                            }
                        });
                count++;
            }
        }
    }

    public static class ImageClickableSpan extends ClickableSpan {
        private int currentPosition;
        private List<String> urls;

        public ImageClickableSpan(int currentPosition, List<String> urls) {
            this.currentPosition = currentPosition;
            this.urls = urls;
        }

        @Override
        public void onClick(View widget) {
            Bundle bundle = new Bundle();
            bundle.putInt("currentPosition", currentPosition);
            bundle.putStringArrayList("urls", new ArrayList<>(urls));
        }
    }

    public static class AtPeopleClickableSpan extends ClickableSpan {
        private Context context;
        private String name;

        public AtPeopleClickableSpan(Context context, String name) {
            this.context = context;
            this.name = name;
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(context, "跳转到" + name + "个人中心", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
//            ds.setColor(context.getResources().getColor(R.color.at_people_color));
        }
    }

    /**
     * @param dot 输入0.0或者0.00标识保留几位
     * @return
     */
    public static String getDouble(String dot, Double dotNumber) {
        DecimalFormat df = new DecimalFormat(dot);
        String number = df.format(dotNumber);
        return number;
    }

    /**
     * 设备唯一标识码
     *
     * @param context
     * @return
     */
//    public static String getUniqueDeviceId(Context context) {
//        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//        String deviceId = TelephonyMgr.getDeviceId();
//        if (!TextUtils.isEmpty(deviceId) && !deviceId.equals("000000000000000")) {
//            return deviceId;
//        }
//        String deviceString = "35" + //we make this look like a valid IMEI
//                Build.BOARD.length() % 10 +
//                Build.BRAND.length() % 10 +
//                Build.SUPPORTED_ABIS.length % 10 +
//                Build.DEVICE.length() % 10 +
//                Build.DISPLAY.length() % 10 +
//                Build.HOST.length() % 10 +
//                Build.ID.length() % 10 +
//                Build.MANUFACTURER.length() % 10 +
//                Build.MODEL.length() % 10 +
//                Build.PRODUCT.length() % 10 +
//                Build.TAGS.length() % 10 +
//                Build.TYPE.length() % 10 +
//                Build.USER.length() % 10; //13 digits
//        return deviceString;
//    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getMobileType() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备系统版本
     *
     * @return
     */
    public static String getEquipSysVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备应用版本
     *
     * @return
     */
    public static String getAppVersion(Context context) {
        String versionCode = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

        }
        return versionName;
    }

    /**
     * 获取登陆设备IP
     *
     * @return
     */
//    public static String getIPAddress(Context context) {
//        NetworkInfo info = ((ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        if (info != null && info.isConnected()) {
//            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
//                try {
//                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
//                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                        NetworkInterface intf = en.nextElement();
//                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                            InetAddress inetAddress = enumIpAddr.nextElement();
//                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//                                return inetAddress.getHostAddress();
//                            }
//                        }
//                    }
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
//                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
//                return ipAddress;
//            }
//        } else {
//            //当前无网络连接,请在设置中打开网络
//        }
//        return null;
//    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 过滤emoji表情
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern
                    .UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("");
                return source;
            }
            return source;
        }
        return source;
    }

    /**
     * 判断是含有emoji表情
     *
     * @param source
     * @return
     */
    public static Boolean hasEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern
                    .UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return true;
            }
            return false;
        }
        return false;
    }

//    public static String initNum2TwoByte(int num) {
//        if (num <= 0) {
//            return num + "";
//        } else if (num < 10) {
//            return "0" + num;
//        } else {
//            return num + "";
//        }
//    }

    public static String intNumToString(String intNum) {
        switch (intNum) {
            case "1":
                return "一";
            case "2":
                return "二";
            case "3":
                return "三";
            case "4":
                return "四";
            case "5":
                return "五";
            case "6":
                return "六";
            case "7":
                return "七";
            case "8":
                return "八";
            case "9":
                return "九";
            case "0":
                return "零";
        }
        return "";
    }

    public static String intNumToString(int intNum) {
        switch (intNum) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            case 0:
                return "零";
        }
        return "";
    }

    public static String getTodayStringWeek() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        if (i - 1 == 0) {
            return "周日";
        } else {
            return "周" + intNumToString(i - 1);
        }
    }

    public static long stringTimeToLong(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = simpleDateFormat.parse(time);
            return parse.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
