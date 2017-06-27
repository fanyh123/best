package com.ran.test.encrypt;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

public class SignupUtil {
	
	@Value("${secret}")
	private String secret;
	
	public static final Charset CHARSET = Charset.forName("utf-8");
	
	/*
	 * 对请求参数排序后MD5签名
	 */
	public static String getSign(Map<String,String> param){
		String result = "";
		Set<String> paramKeys =  param.keySet();
		List<String> paramList = new ArrayList<String>();
		paramList.addAll(paramKeys);
		Collections.sort(paramList);
		Iterator<String> a = paramList.iterator();	
		while(a.hasNext()){
			String key = a.next();
			result += key+"=" + param.get(key)+"&";
		}
		//md5第一次
		result = result.substring(0, result.length()-1);
		String first_encrypt = MD5coding.MD5(result);
		return first_encrypt;
	}
	
	public static String strEncodBase64(String secretStr, String inputStr){
        String base64Str = Base64.encodeBase64String(encode(secretStr, inputStr));
        System.out.println("strEncodBase64 > base64 encrypt = {}"+base64Str);
        return base64Str;
    }
	
	public static byte[] encode(String secretStr, String inputStr) {
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);
        byte[] inputBytes = inputStr.getBytes(CHARSET);

        byte[] outputBytes = encryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }
	
	public static byte[] encryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
            int inputLength = input.length;
            int srcLength;

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] srcBytes;
            if (0 != inputLength % blockSize) {
                srcLength = inputLength + (blockSize - inputLength % blockSize);
                srcBytes = new byte[srcLength];
                System.arraycopy(input, 0, srcBytes, 0, inputLength);
            } else {
                srcBytes = input;
            }

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(srcBytes);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static String getDataFormat(String source){
		if(source == null) return null;
		return source.substring(0, 10);
	}
	
}
 