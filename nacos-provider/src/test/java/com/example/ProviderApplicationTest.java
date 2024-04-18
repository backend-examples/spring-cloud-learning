package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
public class ProviderApplicationTest {


    @Test
    public void generateKey() throws NoSuchAlgorithmException {

        // 获取指定算法的密钥对生成器
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");

        // 初始化密钥对生成器（指定密钥长度, 使用默认的安全随机数源）
        gen.initialize(2048);

        // 随机生成一对密钥（包含公钥和私钥）
        KeyPair keyPair = gen.generateKeyPair();

        // 获取 公钥 和 私钥
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey priKey = keyPair.getPrivate();

        System.out.println("公钥： " + new BASE64Encoder().encode(pubKey.getEncoded()));
        System.out.println("私钥： " + new BASE64Encoder().encode(priKey.getEncoded()));
    }

    @Test
    public void  generateUUID() {
        UUID uuid = UUID.randomUUID();

        byte[] bytes = new byte[16];
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (mostSigBits >>> 8 * (7 - i));
            bytes[8 + i] = (byte) (leastSigBits >>> 8 * (7 - i));
        }

        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        String uuid16 = builder.substring(0, 16);

        System.out.println("生成的16位UUID字符串：" + uuid16);
    }
}
