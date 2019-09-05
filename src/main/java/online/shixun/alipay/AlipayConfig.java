package online.shixun.alipay;


import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101400686180";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPAhh"+
                                               "v1kE7aqduOIxQzAviADQ85BxgyeJswbuHrF1aXBtMYCbVs1ba9xs7eDwxQ2g7z2X0Ajg"+
                                               "WEgaPjw0MXye5OYu4ZBHnUeVc2gF92JhMBtaomBRJPb/Ykeb9AUopsnfpZcsP7cQ659mMpDU+XRtU"+
                                               "aecoGTIiWDcrGgClA1giQMrAetfibWvkXIGRKNsRhI1fkJQwOvp1g2+WTNszcCpsdDVUSfLL0wV4sxZ"+
                                               "XpLAqWdoYwLuOdIGR5l6Qy0h6WH4qlKimUurDiniSsq5C7b+oOG47fKrDT+U/SRdvVe6ZdvFer8XPrv/5W"+
                                               "9QIlIqKxTrwu5jHrQAGqCT6M8laekqdAgMBAAECggEAVPFKsIsTvgYQozBlb8xzNm8mmBBqJrdjjXPZPrIL1"+
                                               "RMmh8wI5SAb8/2YqE83eY9vRy9egHwc8kcMrJKsjm+quaNLsV+HHt4PHYLEJgrUEzxwnj2Otx49aJbpG9h7Q58lC"+
                                               "HYVEkYJK2/xUU9r7LoC9LXevnuQwsOhKTHNtjXxUI5CzAKyhunt+/dZvx13F39XR1RWSwEaEEpM+gWn3AdgR2SkcSybX"+
                                               "wftkB6VoVlT16QvhHGIIPvwkSOT64WGmDZLODs2WiekRtJZKn2eu6SaYO1+cMqtqGHJWfOfNtd4WoRONsGVuE0LW6FXvMJl"+
                                               "wC6H0pqQaJvyfSTG7IY5VjxoxQKBgQDkJFo+P+iC4TsaPhAAAAUFhEHEaRj3nAiUp+YJlN2C5El0elleGNbwAonKPff91ZXobVA"+
                                               "zIQC/tspOgy/UQ9LA0sshMzcUmryCPSWV3ljHRKifSYL6KItdQC5H+DGw8JKea52A/L6ZeBDktLf6KuiIwpoEzH3kk5XZygwZ4mo5Tw"+
                                               "KBgQCgeHyMSYs8xSVFvgxCsx+xsP9sdWlpKauXW0NukvvSh65NaQJhLpLAikBUWqgKDClgp9k5w4ELZ+3sFgq2gvKYnDZCeUwUClanLnm"+
                                               "7nHPxgamvI8p34EOpX57N8yRTN6Dq5agkY85KchVcapvGwEHeFVyiV8pr9RQskqswMwFqUwKBgQDZw8lP+fiaVRrxJaRoG5yvc1rDe/4U+5"+
                                               "9FJi5xnuMBeZ6Ty+VZ6CPh3MphfV0lsaXxwcX3x9zLIlNhNd+FoWeeHpc+DYDZ7glZ17v0ndPoASVjRev7lI/SZtzGXaYw2rFY/ifNY3MbUn"+
                                               "vtkoK6DBMB157kTb0tXaJ/zFhYrMHwCQKBgEsgXV7oR3GJ8qs2EFNhvOLZtJs4VMNRKO5RYb5wvciQkP/Yu653uWi+0OMtFiKuEukXa9FpQA7w"+
                                               "9yhiIV3U7HKaH6T7WKzBMlROo9s7oeRdCe3e2blJmyO+ioBs5oK3NnUCU7fFqhycUHfVyVxBQhUQ9y9h3niwYmODvvVvy6fVAoGABrNuYsn9u9"+
                                               "x6Q4B8ML6Xz8isrTXcOF2Xgoc7Xxdg+b3rZjnFwNfbIekSyWH8Yb/6km+c3Hre+FUaQnaL+CO9OKpW32pFn2o4xhOm5+9l0n3gcwzM6N7K0B9X5g"+
                                               "/62Zxts4QHPTBZSMhs2gdWmj0d5ST/0TCrant/BoUPqpGMBQw=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl4ClI2jFtBdzALhhXGKHUoZDBg"+
    										 "idHiqkSZ67lO9CY2RFiCyEcXzCii6Ef1+ZTVQVxuLeAXOZmJM6Jh1mEICO8UV+fiuqoOtgS"+
    										 "Eu57mrxRdDJoWYRyYsYJak5f03EnSS9NVTbpOUTjM9EtTIDtcIMlGWOdMsRpcV/hSM25r20"+
    										 "YPHwzQPTjPAH9BJRHdp94njKH97m9py5eH3aL2AB86IAay6fODG3aInGaRrPOYdTnUu1GKT5"+
    										 "YxI9Ifyjfo2Wp7LLyFWHhKvb2778bGOZIkZG4JDH+3QjL47cZKzvEuHs1aH4fIJQx5y9i9HDh"+
    										 "vL969lhdQOnHWHWmaxJaGl0pHJSUwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8081/eweb/front/order/list";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8081/eweb/front/order/list";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝日志
	public static String log_path = "F:\\test";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
