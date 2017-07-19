package com.app.service.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Victor on 17-6-28.
 */
public class CharHandle {
    private static Pattern unicodeOutliers = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
    public static String filterSpecChar(String content) {
        String utf8tweet = "";
        try {
            byte[] utf8Bytes = content.getBytes("UTF-8");
            utf8tweet = new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
        utf8tweet = unicodeOutlierMatcher.replaceAll(" ");
        return utf8tweet;
    }
}
