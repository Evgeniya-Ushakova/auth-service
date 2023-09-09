package com.evg.authservice.service.impl;

import com.evg.authservice.service.AESCryptService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Service
@Slf4j(topic = "AES_CRYPTO_SERVICE")
public class AESCryptServiceImpl implements AESCryptService {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    @Value("${service.encryptionKey}")
    private String encryptionKey;

    @Override
    public String encrypt(String value) {
        if(StringUtils.isBlank(value)) {
            return value;
        }

        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            Key key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.ISO_8859_1), "AES");
            byte[] iv = new byte[cipher.getBlockSize()];
            IvParameterSpec ivParameterSpec = new IvParameterSpec((iv));
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw  new RuntimeException(" AESCryptService error on encryp");
        }
    }

    @Override
    public String decrypt(String value) {

        if(StringUtils.isBlank(value)) {
            return value;
        }

        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            Key key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.ISO_8859_1), "AES");
            byte[] iv = new byte[cipher.getBlockSize()];
            IvParameterSpec ivParameterSpec = new IvParameterSpec((iv));
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decrypted, StandardCharsets.ISO_8859_1);
        } catch (Exception e) {
            throw  new RuntimeException(" AESCryptService error on decrypt");
        }
    }
}
