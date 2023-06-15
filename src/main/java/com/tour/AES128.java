package com.tour;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.Base64.*;



public class AES128 extends Exception{

    private String key;
    private SecretKeySpec keySpec;
    private Cipher cipher;

    /**
     * key set
     * */
    public AES128(String key) throws Exception{
        byte [] keyBytes = new byte[16];
        byte [] obj = key.getBytes("UTF-8");

        System.arraycopy(obj, 0, keyBytes,0, keyBytes.length);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.key = key.substring(0, 16);
        this.keySpec = keySpec;
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    /**
     * java 암호화
     * */
    public String javaEncrypt(String str) throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(key.getBytes()));
        Encoder encoder = Base64.getEncoder();
        String res =encoder.encodeToString(cipher.doFinal(str.getBytes("UTF-8")));

        return res;
    }

    /**
     * java 복호화
     * */
    public String javaDecrypt(String str) throws Exception{
        Decoder decoder = Base64.getDecoder();
        byte strBytes [] = decoder.decode(str);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(key.getBytes()));

        String res = new String(cipher.doFinal(strBytes), "UTF-8");

        return res;
    }

    /**
     * mysql 암호화
     * */
    public String mySqlEncrypt(String str, String mySqlKey) throws Exception{
        cipher = Cipher.getInstance("AES");

        return "개발중";
    }

    /**
     * mysql 복호화
     * */
    public String mySqlDecrypt(String str, String mySqlKey) throws Exception{
        cipher = Cipher.getInstance("AES");

        return "개발중";
    }



}
