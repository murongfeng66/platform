package com.jwzhu.platform.common.web.token.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.web.token.TokenConfig;
import com.jwzhu.platform.common.web.token.TokenSubject;
import com.jwzhu.platform.common.web.token.TokenUtil;

public class TokenUtiAES implements TokenUtil {

    private TokenConfig tokenConfig;
    private Cipher EncryptCipher;
    private Cipher DecryptCipher;

    public TokenUtiAES(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        init();
    }

    private void init() {
        checkConfig();
        Key key = new SecretKeySpec(Base64.decodeBase64(this.tokenConfig.getSecretKey()), "AES");
        try {
            this.EncryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.EncryptCipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Base64.decodeBase64(this.tokenConfig.getAesIv())));
            this.DecryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.DecryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.decodeBase64(this.tokenConfig.getAesIv())));
        } catch (Exception e) {
            throw new SystemException("init AES exception", e);
        }
    }

    private void checkConfig() {
        assert this.tokenConfig.getType() == null;
        assert this.tokenConfig.getSecretKey() == null;
        assert this.tokenConfig.getAesIv() == null;
    }

    @Override
    public String createToken(TokenSubject subject) {
        byte[] token;
        try {
            token = this.EncryptCipher.doFinal(JSON.toJSONString(subject).getBytes());
        } catch (Exception e) {
            throw new SystemException("create AES Token exception", e);
        }
        return Base64.encodeBase64URLSafeString(token);
    }

    @Override
    public boolean checkToken(String token) {
        TokenSubject subject = analyzeToken(token);
        if(this.tokenConfig.getExpiredTime() != null && this.tokenConfig.getExpiredTime()>0){
            return System.currentTimeMillis() - subject.getCurrentMilli() > this.tokenConfig.getExpiredTime();
        }
        return true;
    }

    @Override
    public TokenSubject analyzeToken(String token) {
        byte[] subject;
        try {
            subject = this.DecryptCipher.doFinal(Base64.decodeBase64(token));
        } catch (Exception e) {
            throw new SystemException("create AES Token exception", e);
        }
        return JSON.parseObject(new String(subject), TokenSubject.class);
    }
}
