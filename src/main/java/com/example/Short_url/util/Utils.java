package com.example.Short_url.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.IntStream;
import org.hashids.Hashids;

@Slf4j
@UtilityClass
public class Utils {


    // Decrypt and Encrypt
    public String encryptDecrypt(String text, String action) {
        StringBuffer stringBuffer = new StringBuffer();
        IntStream
                .range(0, text.length())
                .forEach(value -> {
                    if(value % 2 == 0)
                        if("encrypt".equals(action))
                            stringBuffer.append((char) (text.charAt(value) + 4));
                        else if("decrypt".equals(action))
                            stringBuffer.append((char) (text.charAt(value) - 4));
                    else
                    if("encrypt".equals(action))
                        stringBuffer.append((char) (text.charAt(value) + 3));
                    else if("decrypt".equals(action))
                        stringBuffer.append((char) (text.charAt(value) - 3));
                });
        return stringBuffer.toString();
    }

    //ShortKey Generation
    public  String hashUrl(String url, int minLength) {
        Hashids hashids = new Hashids("my-salt", minLength);
        int hash = Math.abs(url.hashCode());
        return hashids.encode(hash);
    }

}