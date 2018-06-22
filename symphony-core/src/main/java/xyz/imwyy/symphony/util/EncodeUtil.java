package xyz.imwyy.symphony.util;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码和解码的工具类
 * create by stephen on 2018/5/20
 */
public class EncodeUtil {

    public static String encodeURL(String url) {
        String result;
        try {
            result = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String decodeURL(String url) {
        String result;
        try {
            result = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
