package com.jwzhu.platform.plugs.web.token.impl;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.web.TokenSubject;
import com.jwzhu.platform.plugs.web.exception.token.TokenErrorException;
import com.jwzhu.platform.plugs.web.exception.token.TokenTimeOutException;
import com.jwzhu.platform.plugs.web.token.TokenConfig;
import com.jwzhu.platform.plugs.web.token.TokenUtil;

public class TokenUtiAES implements TokenUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private Logger logger = LoggerFactory.getLogger(TokenUtiAES.class);
    private TokenConfig tokenConfig;

    public TokenUtiAES(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        checkConfig();
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
            SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(this.tokenConfig.getSecretKey()), "AES");
            Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
            encryptCipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(this.tokenConfig.getAesIv())));

            token = encryptCipher.doFinal(JSON.toJSONString(subject).getBytes("UTF-8"));
        } catch (Exception e) {
            throw new SystemException("创建AES Token异常", e);
        }
        return Base64.getUrlEncoder().encodeToString(token);
    }

    @Override
    public TokenSubject checkToken(String token) {
        TokenSubject subject = analyzeToken(token);
        if (subject.getTime() == null) {
            throw new TokenErrorException("Token错误");
        }
        if (this.tokenConfig.getExpiredTime().toMillis() > 0) {
            if (System.currentTimeMillis() - subject.getTime() > this.tokenConfig.getExpiredTime().toMillis()) {
                throw new TokenTimeOutException();
            }
        }
        return subject;
    }

    @Override
    public TokenSubject analyzeToken(String token) {
        try {
            SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(this.tokenConfig.getSecretKey()), "AES");
            Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
            decryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.getDecoder().decode(this.tokenConfig.getAesIv())));

            byte[] subject = decryptCipher.doFinal(Base64.getUrlDecoder().decode(token));
            String subjectStr = new String(subject, "UTF-8");
            logger.debug("Token明文：{}", subjectStr);
            return JSON.parseObject(subjectStr, TokenSubject.class);
        } catch (Exception e) {
            throw new SystemException("解析AES Token异常", e);
        }
    }
}
