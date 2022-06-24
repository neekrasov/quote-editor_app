package com.app.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordMD5 {

    public static String hash_password(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder hashPass = new StringBuilder();
        for (byte b : bytes) {
            hashPass.append(String.format("%02X", b));
        }
        return hashPass.toString();
    }
}
