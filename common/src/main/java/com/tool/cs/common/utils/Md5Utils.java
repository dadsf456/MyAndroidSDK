package com.tool.cs.common.utils;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by *** on 2019/8/6.
 * 非对称加密md加密
 */
public class Md5Utils {
    /**
     * 字符串MD5加密工具类
     */
    public static String md5Encode(@NonNull String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xff) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 登录密码MD5加密
     * @param password
     * @return
     */
    public static String encryptPassword(@NonNull String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = password.getBytes("UTF-16LE");
            md.update(data);
            data = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                String d = Integer.toHexString(data[i] & 0xFF);
                if (i > 0) {
                    sb.append("-");
                }

                if (d.length() == 1) {
                    sb.append("0");
                }
                sb.append(d);
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
