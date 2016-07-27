package com.cqs.bishe.tool;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Chars;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cqs on 16-6-5.
 */
public class MD5Tool {

    private static final int end = 4;
    public static final String encodePwdByMd5(final String str) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(str, Charset.forName("UTF-8"));
        return hasher.hash().toString();
    }

    public static synchronized final String encodeMobileByOwn(final String mobile){
        StringBuffer stringBuffer = new StringBuffer(mobile);
        for (int i=0;i<mobile.length()-end;i++){
            stringBuffer.setCharAt(i,encodeChar(mobile.charAt(i)));
        }
        return stringBuffer.toString();
    }

    public static synchronized final String decodeMobileByOwn(final String mobile){
        StringBuffer stringBuffer = new StringBuffer(mobile);
        for (int i=0;i<mobile.length()-end;i++){
            stringBuffer.setCharAt(i,decodeChar(mobile.charAt(i)));
        }
        return stringBuffer.toString();
    }

    private static final char encodeChar(final char c){
        return Chars.checkedCast(c+10);
    }

    private static char decodeChar(final char c){
        return Chars.checkedCast(c-10);
    }
}
