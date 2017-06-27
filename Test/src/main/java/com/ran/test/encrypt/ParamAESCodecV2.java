package com.ran.test.encrypt;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 参数加密
 *
 * Created by an on 15/12/16.
 */
public class ParamAESCodecV2 {

    private static Logger logger = LoggerFactory.getLogger(ParamAESCodecV2.class);

    public static final Charset CHARSET = Charset.forName("utf-8");

    public static final String ALGORITHM = "AES";

    public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";



    /**
     * 为了平台的通用，选择 AES/CBC/NoPadding 的模式，然后手动 padding
     * 对应PHP 平台为 mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $key, $data, MCRYPT_MODE_CBC, $iv)
     *
     * AES/CBC/NoPadding encrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param secretKeyBytes
     * @param intVectorBytes
     * @param input
     * @return
     */
    public static byte[] encryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);
            int inputLength = input.length;
            int srcLength;

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
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


    /**
     * AES/CBC/NoPadding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param input
     * @return
     */
    public static byte[] decryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws CodecException
     */
    public static byte[] encode(String secretStr, String inputStr) {
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);
        byte[] inputBytes = inputStr.getBytes(CHARSET);

        byte[] outputBytes = encryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws CodecException
     */
    public static String strEncodBase64(String secretStr, String inputStr){
        String base64Str = Base64.encodeBase64String(encode(secretStr, inputStr));
        logger.debug("strEncodBase64 > base64 encrypt = {}", base64Str);
        return base64Str;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @return
     * @throws CodecException
     */
    public static byte[] decode(String secretStr, byte[] inputBytes){
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);

        byte[] outputBytes = decryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

  


    public static void main(String[] args) {
        

        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("appkey","f2b693c81234566e");
        params.put("method","jiedianqian.partner.synOrderInfo");
        params.put("signType","MD5");
        params.put("version","1.0");
        long time = System.currentTimeMillis();
        params.put("timestamp",""+time);
        params.put("channel_code","test");
        net.sf.json.JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel_code","100");
        //jsonObject.put("order_id","");
        jsonObject.put("partner_order_id","3");
        jsonObject.put("mobile_phone","15156039991");
        jsonObject.put("status","7");
        jsonObject.put("sign_loan_ periods",3);
        jsonObject.put("approval_amount","777");
        jsonObject.put("apply_time",149830100000L);
        jsonObject.put("check_finish_time",1498030100450L);
        jsonObject.put("grant_finish_time",1498030010000L);

        JSONArray listData = new JSONArray();
        JSONObject data1 = new JSONObject();
        data1.put("true_repayment_time","2017-07-14");
        data1.put("plan_repayment_time","2017-07-13");
        data1.put("amount","100");
        data1.put("period_fee",100);
        data1.put("period",1);
        data1.put("status","4");
        data1.put("overdue_fee","11");
        data1.put("overdue_day","1");
        listData.add(data1);
        JSONObject data2 = new JSONObject();
        data2.put("true_repayment_time","2017-07-11");
        data2.put("plan_repayment_time","2017-07-12");
        data2.put("amount","1");
        data2.put("period",2);
        data2.put("period_fee",3);
        data2.put("status","2");
        data2.put("overdue_fee","10");
        data2.put("overdue_day","1");
        listData.add(data2);
        jsonObject.put("repayment_plan",listData);
        String paramData = jsonObject.toString();
        String dataEncod = ParamAESCodecV2.strEncodBase64("ED3592B5D6DC67E3", paramData);
        try {
            String aaa = URLEncoder.encode(dataEncod,"UTF-8");
           params.put("data", aaa);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = ParamSignV2.signWithSalt("ED3592B5D6DC67E3", params);
        params.put("sign",sign);
        params.put("data",dataEncod);
//        HttpPost dataPost = HttpUtil.createHttpPostFormUrlecoded("http://wwwtest.jiedianqian.com:8084/gateway.do",null,params);
//        ResultOfAPI resultOfAPI = HttpUtil.doPost(dataPost, new ResponseHandler<ResultOfAPI>() {
//            @Override
//            public ResultOfAPI handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
//                String aa = EntityUtils.toString( response.getEntity());
//                System.out.println("-----------------------------------" +aa+"-----------------------------------");
//              return null;
//            }
//        });
    }

}
