package com.jwzhu.platform.plugs.web.token.impl;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.plugs.web.exception.token.TokenErrorException;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;
import com.jwzhu.platform.plugs.web.token.TokenConfig;
import com.jwzhu.platform.plugs.web.token.TokenSubject;
import com.jwzhu.platform.plugs.web.token.TokenUtil;

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
            throw new SystemException("初始化AES Token组件失败", e);
        }
    }

    private void checkConfig() {
        assert this.tokenConfig.getType() != null;
        assert this.tokenConfig.getSecretKey() != null;
        assert this.tokenConfig.getAesIv() != null;
    }

    @Override
    public String createToken(TokenSubject subject) {
        byte[] token;
        try {
            token = this.EncryptCipher.doFinal(JSON.toJSONString(subject).getBytes("UTF-8"));
        } catch (Exception e) {
            throw new SystemException("创建AES Token异常", e);
        }
        return Base64.encodeBase64URLSafeString(token);
    }

    @Override
    public TokenSubject checkToken(String token) {
        TokenSubject subject = analyzeToken(token);
        if (subject.getCreateMillis() == null) {
            throw new TokenErrorException("Token错误");
        }
        if (this.tokenConfig.getExpiredTime() > 0) {
            if (System.currentTimeMillis() - subject.getCreateMillis() > this.tokenConfig.getExpiredTime()) {
                throw new TokenTimeOutException();
            }
        }
        return subject;
    }

    @Override
    public TokenSubject analyzeToken(String token) {
        try {
            byte[] subject = this.DecryptCipher.doFinal(Base64.decodeBase64(token));
            String subjectStr = new String(subject, "UTF-8");
            return JSON.parseObject(subjectStr, TokenSubject.class);
        } catch (Exception e) {
            throw new SystemException("解析AES Token异常", e);
        }
    }
}
