package com.evg.authservice.service;

public interface AESCryptService {

    String encrypt(String value);

    String decrypt(String value);

}
