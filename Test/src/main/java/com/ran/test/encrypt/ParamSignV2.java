package com.ran.test.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class ParamSignV2 {

    public static final Logger logger = LoggerFactory.getLogger(ParamSignV2.class);

    public static final Charset CHARSET = Charset.forName("utf-8");

    public static final String ALGORITHMS_SHA1 = "SHA-1";

    public static final String ALGORITHMS_MD5 = "MD5";

    public static final String ALGORITHMS_HMACMD5 = "HmacMD5";

    /**
     * 消息摘要，使用参数 algorithms 指定的算法
     *
     * @param algorithms
     * @param inputStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] digest(String algorithms, String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithms);
        messageDigest.update(inputStr.getBytes(CHARSET));
        return messageDigest.digest();
    }

    /**
     * byte 数组转 十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byte2HexStr(byte[] byteArray) {

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }

    /**
     * 签名,并将签名结果转换成 十六进制字符串
     *
     * @param inputStr
     * @return
     */
    public static String digestToHexStr(String algorithms, String inputStr) {
        try {
            return byte2HexStr(digest(algorithms, inputStr));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 拼接参数用于签名
     *
     * @param paramMap
     * @return
     */
    public final static String paramTreeMapToString(TreeMap<String, String> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append(entrySet.getValue());
        }

        return paramStrBuilder.toString();
    }


    /**
     * 拼接参数字符串
     *
     * @param paramMap
     * @return
     */
    public static String paramTreeMapToStrSymbol(TreeMap<String, String> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append('=')
                    .append(entrySet.getValue()).append('&');
        }
        return paramStrBuilder.substring(0, paramStrBuilder.length() - 1);
    }

    public static String paramTreeMapToFormStr(TreeMap<String, Object> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, Object>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append('=')
                    .append(entrySet.getValue()).append('&');
        }
        return paramStrBuilder.substring(0, paramStrBuilder.length() - 1);
    }

    public static String paramTreeMapToFormEncode(TreeMap<String, Object> paramMap) {
        try {
            return paramTreeMapToFormEncode(paramMap, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("paramTreeMapToFormEncode > catch  UnsupportedEncodingException");
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    public static String paramTreeMapToFormEncode(TreeMap<String, Object> paramMap, String charsetName) throws UnsupportedEncodingException {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, Object>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            if (0 != paramStrBuilder.length()) {
                paramStrBuilder.append('&');
            }
            Map.Entry<String, Object> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append('=')
                    .append(URLEncoder.encode((String) entrySet.getValue(), charsetName));
        }
        return paramStrBuilder.toString();
    }

    public static String signWithSalt(String key, String sourceStr) {
        String md5Str1 = digestToHexStr(ParamSignV2.ALGORITHMS_MD5, sourceStr);
        String sourceStr2 = md5Str1 + key;
        return digestToHexStr(ParamSignV2.ALGORITHMS_MD5, sourceStr2);
    }

    public static String signWithSalt(String key, TreeMap<String, String> paramMap) {
        String sourceStr = paramTreeMapToStrSymbol(paramMap);
        return signWithSalt(key, sourceStr);
    }


    public static byte[] encodeHmacMd5(byte[] key, byte[] input) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHMS_HMACMD5);
            Mac mac = Mac.getInstance(ALGORITHMS_HMACMD5);
            mac.init(keySpec);
            mac.update(input);
            return mac.doFinal();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            throw new RuntimeException("siginHmacMd5 catch Exception "
                    + e.getClass() + " , message : " + e.getMessage(), e);
        }
    }

    public static String signHmacMd5(String key, String input) {
        return byte2HexStr(encodeHmacMd5(key.getBytes(), input.getBytes()));
    }

    public static String signHmacMd5(String key, TreeMap<String, String> paramMap) {
        return signHmacMd5(key, paramTreeMapToString(paramMap));
    }

}
