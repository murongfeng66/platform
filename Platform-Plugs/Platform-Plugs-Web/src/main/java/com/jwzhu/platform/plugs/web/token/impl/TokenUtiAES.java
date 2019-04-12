package com.jwzhu.platform.plugs.web.token.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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

    private Logger logger = LoggerFactory.getLogger(TokenUtiAES.class);
    private TokenConfig tokenConfig;
    private Cipher EncryptCipher;
    private Cipher DecryptCipher;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public TokenUtiAES(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        init();
    }

    private void init() {
        checkConfig();
        Key key = new SecretKeySpec(Base64.getDecoder().decode(this.tokenConfig.getSecretKey()), "AES");
        try {
            this.EncryptCipher = Cipher.getInstance(ALGORITHM);
            this.EncryptCipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(this.tokenConfig.getAesIv())));
            this.DecryptCipher = Cipher.getInstance(ALGORITHM);
            this.DecryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(this.tokenConfig.getAesIv())));
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
        return Base64.getUrlEncoder().encodeToString(token);
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
            byte[] subject = this.DecryptCipher.doFinal(Base64.getUrlDecoder().decode(token));
            String subjectStr = new String(subject, "UTF-8");
            logger.debug("Token明文：{}", subjectStr);
            return JSON.parseObject(subjectStr, TokenSubject.class);
        } catch (Exception e) {
            throw new SystemException("解析AES Token异常", e);
        }
    }
}
