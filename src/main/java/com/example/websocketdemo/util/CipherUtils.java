package com.example.websocketdemo.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtils {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

    /**
     * SecretKeySpec类是KeySpec接口的实现类,用于构建秘密密钥规范
     */
    private SecretKeySpec key;

    public CipherUtils(String hexKey) {
        key = new SecretKeySpec(hexKey.getBytes(), ALGORITHM);
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     */
    public String encryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR); // 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            return new BASE64Encoder().encode(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     *
     * @param data
     * @return
     */
    public String decryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(data)), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    public static void main(String[] args) throws Exception {
//        CipherUtils util = new CipherUtils("-unify~!@#qaz019"); // 密钥
//        System.out.println("cardNo:"+util.encryptData("admin")); // 加密
//        System.out.println("exp:"+util.decryptData("f77o4P/+MevETc77bewqwA==")); // 解密
//    }


}
