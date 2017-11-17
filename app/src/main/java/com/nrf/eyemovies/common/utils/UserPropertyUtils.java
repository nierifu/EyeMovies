package com.nrf.eyemovies.common.utils;

import android.content.Context;


/**
 * Created by Administrator on 2017/4/5.
 * 功能： 用于用户数据操作时使用，避免字符串错误
 */
// TODO: 2017/4/24 测试状态以后要改
public class UserPropertyUtils {
    public static String uid = "uid"; //当前用户id
    public static String city = "city";  //城市
    public static String account = "account"; //账号名称
    public static String tzaccount = "tzaccount";//内部账号
    public static String nikename = "userName"; //昵称
    public static String pwd = "pwd";           //密码
    public static String nameSpelling = "nameSpelling"; //拼音名字
    public static String imageUrl = "imageUrl";  //头像链接
    public static String mailbox = "mailbox";  //邮箱
    public static String phone = "phone";  //电话号
    public static String empiricalvalue = "empiricalvalue"; //积分
    public static String registerdate = "registerdate";  //注册时间
    public static String userauth = "userauth";   //V认证
    public static String authdate = "authdate";  //认证时间
    public static String identityid = "identityid"; //vipid
    public static String token = "token";  //im令牌 IMtoken
    public static String gid = "gid";  //等级关联id
    public static String jqkey = "jqkey"; //鉴权值
    public static String sex = "sex";  //性别
    public static String content = "content"; //个人简介
    public static String virtualcoins = "virtualcoins";  //虚拟币
    public static String identity = "identity";//我的身份
    public static String signature = "signature"; //个人签名
    public static String userbindid = "user_bind_id"; //用户绑定 id
    public static String birthday = "birthday"; //生日
    public static String userinfo = "userinfo"; //存取json串
    public static String userAuthenticationStatus = "user_authentication_status"; //直播状态
    public static String post_bar_id = "post_bar_id"; //贴吧ID
    public static String qq_uid = "qq_uid"; //QQID
    public static String wx_uid = "wx_uid"; //WXID
    public static String wb_uid = "wb_uid"; //WBID

    //定位相关
    public static String location_Latitude = "location_Latitude";//维度
    public static String location_Longitude = "location_Longitude";//经度
    public static String location_country = "location_country";//国家
    public static String location_province = "location_province";//省
    public static String location_city = "location_city";//城市
    public static String postBarList = "post_bar_list";//自己的贴吧
    public static String alias = "alias";//极光设备别名
    public static String ball_id = "ballId";//球类ID  分类
    public static String ball_url = "ballUrl";//球类图片地址

    /*
    *
    * im
    * */
    // public static String imToken = "";//极光设备别名

    /**
     * 目前保存数据 当前用户id(uid)，账号名称(account)，昵称(nikename)，头像url(imageurl)，电话号(phone)，
     * 鉴权值(jqkey)，性别(sex)，个人简介(content)，我的身份(identity)，个人签名(signature),经验值（empiricalvalue）
     *
     * @param
     */
//    public static void saveData(Context context, final UserInfoEntity mLogin) {
//
//        try {
//            if (mLogin.getUid() != 0) {
//                Log.e("tag", "" + mLogin.getUid());
//                SharedPreferencesUtils.put(context,uid, mLogin.getUid());
//    //            SharedPreferencesUtils.put(context,uid, 1209);
//            }
//            if (mLogin.getAccount() != null) {
//                SharedPreferencesUtils.put(context, account, mLogin.getAccount());
//            }
//            if (mLogin.getCity() != null) {
//                SharedPreferencesUtils.put(context, city, mLogin.getCity());
//            } else {
//                SharedPreferencesUtils.put(context, city, "");
//            }
//            if (mLogin.getMailbox() != null) {
//                SharedPreferencesUtils.put(context, mailbox, mLogin.getMailbox());
//            } else {
//                SharedPreferencesUtils.put(context, mailbox, "");
//            }
//            if (mLogin.getNikename() != null) {
//                SharedPreferencesUtils.put(context, nikename, mLogin.getNikename());
//            }
//            if (mLogin.getImageUrl() != null) {
//                SharedPreferencesUtils.put(context, imageUrl, mLogin.getImageUrl());
//            }
//            if (mLogin.getPhone() != null) {
//                SharedPreferencesUtils.put(context, phone, mLogin.getPhone());
//            }
//            if (mLogin.getJqkey() != null) {
//                Log.e("tag", "" + mLogin.getJqkey());
//                SharedPreferencesUtils.put(context, jqkey, mLogin.getJqkey());
//            }
//            if (mLogin.getSex() != null) {
//                if (mLogin.getSex().equals("0")) {
//                    SharedPreferencesUtils.put(context, UserPropertyUtils.sex, "女");
//                } else if (mLogin.getSex().equals("1")) {
//                    SharedPreferencesUtils.put(context, UserPropertyUtils.sex, "男");
//                }
//            }
//            if (mLogin.getContent() != null) {
//                SharedPreferencesUtils.put(context, content, mLogin.getContent());
//            }
//            if (mLogin.getIdentity() != null) {
//                SharedPreferencesUtils.put(context, identity, mLogin.getIdentity());
//            }
//            if (mLogin.getSignature() != null) {
//                SharedPreferencesUtils.put(context, signature, mLogin.getSignature());
//            }
//            if (mLogin.getEmpiricalvalue() != 0) {
//                SharedPreferencesUtils.put(context, empiricalvalue, mLogin
//                        .getEmpiricalvalue());
//            }
//            if (mLogin.getUser_bind_id() != null) {
//                SharedPreferencesUtils.put(context, userbindid, mLogin.getUser_bind_id
//                        ());
//            }
//            if (mLogin.getBirthday() != null) {
//                SharedPreferencesUtils.put(context, birthday, mLogin.getBirthday());
//            }
//
//            if (mLogin != null) {
//
//                String userinfojson = JSON.toJSONString(mLogin);
//                SharedPreferencesUtils.put(context, userinfo, userinfojson);
//            }
//            SharedPreferencesUtils.put(context, userAuthenticationStatus, "0");
//            if (mLogin != null && mLogin.getAlias() != null) {
//                SharedPreferencesUtils.put(context, alias, mLogin.getAlias());
//            }
//            if (mLogin != null && mLogin.getPost_bar_id() != null) {
//                SharedPreferencesUtils.put(context, post_bar_id, mLogin.getPost_bar_id
//                        ());
//            }
//
//            if (mLogin.getQqUid() != null) {
//                SharedPreferencesUtils.put(context, qq_uid, mLogin.getQqUid());
//            }else {
//                SharedPreferencesUtils.put(context, qq_uid, "");
//            }
//
//            if (mLogin.getWxUid() != null) {
//                SharedPreferencesUtils.put(context, wx_uid, mLogin.getWxUid());
//            }else {
//                SharedPreferencesUtils.put(context, wx_uid, "");
//            }
//
//            if (mLogin.getWbUid() != null) {
//                SharedPreferencesUtils.put(context, wb_uid, mLogin.getWbUid());
//            }else {
//                SharedPreferencesUtils.put(context, wb_uid, "");
//            }
//            if (mLogin.getGid() != -1) {
//                SharedPreferencesUtils.put(context, gid, mLogin.getGid());
//            }
//            if (mLogin.getTzaccount() != null) {
//                SharedPreferencesUtils.put(context, tzaccount, mLogin.getTzaccount());
//            }
//            if (mLogin.getToken() != null) {
//                SharedPreferencesUtils.put(context, token, mLogin.getToken());
//            }
//
//            if (mLogin.getPassword() != null) {
//                SharedPreferencesUtils.put(context, pwd, mLogin.getPassword());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void saveBallId(String ballId, String ballUrl) {
//        SharedPreferencesUtils.put(BaseApplication.getInstance(), ball_id, ballId);
//        SharedPreferencesUtils.put(BaseApplication.getInstance(), ball_url, ballUrl == null ? "" : ballUrl);
//    }
//
//    public static String getBallId() {
//        return (String) SharedPreferencesUtils.get(BaseApplication.getInstance(), ball_id, "");
//    }
//
//    public static String getBallUrl() {
//        return (String) SharedPreferencesUtils.get(BaseApplication.getInstance(), ball_url, "");
//    }
//
//    public static String getPost_bar_list() {
//        return (String) SharedPreferencesUtils.get(BaseApplication.getInstance(), postBarList, "[]");
//    }
//
//    public static String getPost_bar_id() {
//        return (String) SharedPreferencesUtils.get(BaseApplication.getInstance(), post_bar_id, "");
//    }
//
//    public static String getAlias() {
////        return (String) SharedPreferencesUtils.get(BaseApplication.getInstance(), alias, "");
//        return StringUtils.getUniqueDeviceId(BaseApplication.getInstance());
//    }
//
//
//    public static String getUserAuthenticationStatus() {
//        return (String) SharedPreferencesUtils.get(BaseApplication.getInstance(), userAuthenticationStatus, "0");
//    }

    public static int getUid(Context context) {
        // TODO: 2017/6/12 应该改为-1 現在为了测试方便给8
        return (int) SharedPreferencesUtils.get(context, uid, -1);
    }

    public static String getCity(Context context) {
        return (String) SharedPreferencesUtils.get(context, city, "");
    }

    public static String getAccount(Context context) {
        return (String) SharedPreferencesUtils.get(context, account, "");
    }

    public static String getTzaccount(Context context) {
        return (String) SharedPreferencesUtils.get(context, tzaccount, "");
    }

    public static String getNikename(Context context) {
        return (String) SharedPreferencesUtils.get(context, nikename, "");
    }

    public static String getNameSpelling(Context context) {
        return (String) SharedPreferencesUtils.get(context, nameSpelling, "");
    }

    public static String getImageUrl(Context context) {
        return (String) SharedPreferencesUtils.get(context, imageUrl, "");
    }

    public static String getMailbox(Context context) {
        return (String) SharedPreferencesUtils.get(context, mailbox, "");
    }

    public static String getPhone(Context context) {
        return (String) SharedPreferencesUtils.get(context, phone, "");
    }

    public static int getEmpiricalvalue(Context context) {
        return (int) SharedPreferencesUtils.get(context, empiricalvalue, 0);
    }

    public static String getRegisterdate(Context context) {
        return (String) SharedPreferencesUtils.get(context, registerdate, "");
    }

    public static String getUserauth(Context context) {
        return (String) SharedPreferencesUtils.get(context, userauth, "");
    }

    public static String getAuthdate(Context context) {
        return (String) SharedPreferencesUtils.get(context, authdate, "");
    }

    public static int getIdentityid(Context context) {
        return (int) SharedPreferencesUtils.get(context, identityid, 0);
    }

    public static String getToken(Context context) {
        return (String) SharedPreferencesUtils.get(context, token, "");
    }

//    public static void saveGid(String gid) {
//        SharedPreferencesUtils.put(BaseApplication.getInstance(), gid, gid);
//    }

    public static String getGid(Context context) {
        return (Integer) SharedPreferencesUtils.get(context, gid, 1) + "";
    }

    public static String getJqkey(Context context) {
        return (String) SharedPreferencesUtils.get(context, jqkey, "");
    }

    public static String getSex(Context context) {
        return (String) SharedPreferencesUtils.get(context, sex, "");
    }

    public static String getContent(Context context) {
        return (String) SharedPreferencesUtils.get(context, content, "");
    }

    public static String getVirtualcoins(Context context) {
        return (String) SharedPreferencesUtils.get(context, virtualcoins, "");
    }

//    public static void saveIdentity(String identityString) {
//        SharedPreferencesUtils.put(BaseApplication.getInstance(), identity, identityString);
//    }

    public static String getIdentity(Context context) {
        return (String) SharedPreferencesUtils.get(context, identity, "");
    }

    public static String getSignature(Context context) {
        return (String) SharedPreferencesUtils.get(context, signature, "");
    }

    public static String getBaindUid(Context context) {
        return (String) SharedPreferencesUtils.get(context, userbindid, "");
    }

    public static String getBirthday(Context context) {
        return (String) SharedPreferencesUtils.get(context, birthday, "");
    }

    public static String getJson(Context context) {
        return (String) SharedPreferencesUtils.get(context, userinfo, "");
    }

    public static String getLocation_Latitude(Context context) {
        return (String) SharedPreferencesUtils.get(context, location_Latitude, "0.0");
    }

    public static String getLocation_Longitude(Context context) {
        return (String) SharedPreferencesUtils.get(context, location_Longitude, "0.0");
    }

    public static String getLocation_country(Context context) {
        return (String) SharedPreferencesUtils.get(context, location_country, "");
    }

    public static String getLocation_province(Context context) {
        return (String) SharedPreferencesUtils.get(context, location_province, "");
    }

    public static String getLocation_city(Context context) {
        return (String) SharedPreferencesUtils.get(context, location_city, "");
    }

    public static String getQqUid(Context context) {
        return (String) SharedPreferencesUtils.get(context, qq_uid, "");
    }

    public static String getWxUid(Context context) {
        return (String) SharedPreferencesUtils.get(context, wx_uid, "");
    }

    public static String getUiWbUid(Context context) {
        return (String) SharedPreferencesUtils.get(context, wb_uid, "");
    }

    public static String getPwd(Context context) {
        return (String) SharedPreferencesUtils.get(context, pwd, "");
    }
}
