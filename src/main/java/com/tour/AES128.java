package com.tour;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.*;



public class AES128 extends Exception{

    private String key;
    private String cryptKey;
    private SecretKeySpec keySpec;
    private Cipher cipher;

    /**
     * key set
     * */
    public AES128(String key, String cryptKey) throws Exception{
        byte [] keyBytes = new byte[16];
        byte [] obj = cryptKey.getBytes("UTF-8");

        System.arraycopy(obj, 0, keyBytes,0, keyBytes.length);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.key = key.substring(0,16);
        this.cryptKey = cryptKey.substring(0,16);
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
    public String javaDecrypt(String str) throws Exception {
        Decoder decoder = Base64.getDecoder();
        byte[] strBytes = decoder.decode(str);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(key.getBytes()));
        String res = new String(cipher.doFinal(strBytes), "UTF-8");

        return res;
    }

    /**
     * mysql 복호화
     * */
    public String mySqlDecrypt(String str) throws Exception{
        byte[] encryptedBytes = DatatypeConverter.parseHexBinary(str);
        byte[] keyBytes = cryptKey.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

        return decryptedText;
    }

    /**s
     * mysql 암호화
     * */
    public String mySqlEncrypt(String str) throws Exception{
        byte[] keyBytes = cryptKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        String encryptedHex = DatatypeConverter.printHexBinary(encryptedBytes);

        return encryptedHex;
    }





}
