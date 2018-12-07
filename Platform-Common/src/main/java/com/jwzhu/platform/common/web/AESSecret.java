package com.jwzhu.platform.common.web;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESSecret {

    public static void main(String[] args){
        try {
            String src = "{\"userId\":\"1\",\"userType\":\"1\"}";
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            byte[] keyBytes = keyGenerator.generateKey().getEncoded();
            byte[] ivBytes = keyGenerator.generateKey().getEncoded();
            System.out.println("AES Key : " + Base64.encodeBase64String(keyBytes));
            System.out.println("AES Iv : " + Base64.encodeBase64String(ivBytes));

            // key的转换
            Key key = new SecretKeySpec(keyBytes, "AES");

            // 加密
            // AES/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk aes encrypt : " + Base64.encodeBase64String(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
