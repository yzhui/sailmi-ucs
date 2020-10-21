package com.sailmi.system.user.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

//如果JDK版本是1.8,可使用原生Base64类
@Component
public  class PhoneCodeUtil {
	 @Value("${sms.url}")
	 private  String smsUrl;

	 @Value("${sms.appKey}")
	 private  String smsAppKey;

	 @Value("${sms.appSecret}")
	 private  String smsAppSecrect;

	 @Value("${sms.sender}")
	 private  String smsSender;

	 @Value("${sms.templateId}")
	 private  String smsTemplateId;

	 @Value("${sms.signature}")
	 private  String smsSignature;

	 @Value("${sms.receiver}")
	 private  String smsReceiver;

	 @Value("${sms.statusCallBack}")
	 private  String smsStatusCallBack;
		//无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
	 private  final static String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
	    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
	 private  final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

	  @Autowired
	  StringRedisTemplate stringRedisTemplate;

	public  String sendCodeMsg(String phone) throws Exception {

		String url = smsUrl;
		String appKey = smsAppKey;
		String appSecret = smsAppSecrect;
		String sender = smsSender;
		String templateId = smsTemplateId;
		String signature = smsSignature;
		String receiver =smsReceiver+phone;
		String statusCallBack =smsStatusCallBack;
	System.out.println(url+"--"+appKey+"--"+appSecret+"--"+sender+"--"+templateId+"--"+signature);
		String code=createCode();
	    //验证码Code生成6位数
	    String templateParas ="[\""+code+"\"]"; //单变量模板变量
    //将code与手机号对应关系存储在redis中
	    String key = "phoneCode:register"+phone;
        stringRedisTemplate.boundValueOps(key).set(code,600, TimeUnit.SECONDS);
	    //安全凭证用
        String keys = "phoneCode:check"+phone;
        stringRedisTemplate.boundValueOps(keys).set(code,300, TimeUnit.SECONDS);

        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            String msg="body is null";
            return msg;
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            String msg="wsse header is null";
            return msg;
        }

        DataOutputStream out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        HttpsURLConnection connection = null;
        InputStream is = null;

        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        HostnameVerifier hv = new HostnameVerifier() {

           // @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        trustAllHttpsCertificates();

        try {
            URL realUrl = new URL(url);
            connection = (HttpsURLConnection) realUrl.openConnection();

            connection.setHostnameVerifier(hv);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(true);
            //请求方法
            connection.setRequestMethod("POST");
            //请求Headers参数
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
            connection.setRequestProperty("X-WSSE", wsseHeader);

            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(body); //发送请求Body参数
            out.flush();
            out.close();

            int status = connection.getResponseCode();
            if (200 == status) { //200
                is = connection.getInputStream();
            } else { //400/401
                is = connection.getErrorStream();
            }
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString()); //打印响应消息实体
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != is) {
                    is.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return result.toString();

}



 private void removeAttrbute(HttpSession session, String string) {
	// TODO Auto-generated method stub

}



/**
 * 构造请求Body体
 * @param sender
 * @param receiver
 * @param templateId
 * @param templateParas
 * @param statusCallBack
 * @param signature | 签名名称,使用国内短信通用模板时填写
 * @return
 */
static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
        String statusCallBack, String signature) {
    if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
            || templateId.isEmpty()) {
        System.out.println("buildRequestBody(): sender, receiver or templateId is null.");
        return null;
    }
    Map<String, String> map = new HashMap<String, String>();

    map.put("from", sender);
    map.put("to", receiver);
    map.put("templateId", templateId);
    if (null != templateParas && !templateParas.isEmpty()) {
        map.put("templateParas", templateParas);
    }
    if (null != statusCallBack && !statusCallBack.isEmpty()) {
        map.put("statusCallback", statusCallBack);
    }
    if (null != signature && !signature.isEmpty()) {
        map.put("signature", signature);
    }

    StringBuilder sb = new StringBuilder();
    String temp = "";

    for (String s : map.keySet()) {
        try {
            temp = URLEncoder.encode(map.get(s), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append(s).append("=").append(temp).append("&");
    }

    return sb.deleteCharAt(sb.length()-1).toString();
}

/**
 * 构造X-WSSE参数值
 * @param appKey
 * @param appSecret
 * @return
 */
static String buildWsseHeader(String appKey, String appSecret) {
    if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
        System.out.println("buildWsseHeader(): appKey or appSecret is null.");
        return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    String time = sdf.format(new Date()); //Created
    String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

    MessageDigest md;
    byte[] passwordDigest = null;

    try {
        md = MessageDigest.getInstance("SHA-256");
        md.update((nonce + time + appSecret).getBytes());
        passwordDigest = md.digest();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
    String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest); //PasswordDigest
    //如果JDK版本低于1.8,请加载三方库提供Base64类,并使用如下代码
    //String passwordDigestBase64Str = Base64.encodeBase64String(passwordDigest); //PasswordDigest
    //若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
    //passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");
    return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
}

	/**
	 * 忽略证书信任问题
	 * @throws Exception
	 */
	static void trustAllHttpsCertificates() throws Exception {
	    TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	                    return;
	                }
	                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	                    return;
	                }
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	            }
	    };
	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, null);
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}






	private static String createCode() {
		//验证码生成6位
	    String code = "";
	    for(int i=0;i<6;i++){
	        int random = (int)(Math.random()*10);
	        code += String.valueOf(random);

	    }
		return code;
	}

}
