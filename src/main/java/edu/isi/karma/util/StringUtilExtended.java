package edu.isi.karma.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 4535992 on 30/11/2015.
 */
public class StringUtilExtended {

    public static String generateMD5Token(int lengthToken){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        StringBuilder hexString = new StringBuilder();
        byte[] data = md.digest(RandomStringUtils.randomAlphabetic(lengthToken).getBytes());
        for (byte aData : data) {
            hexString.append(Integer.toHexString((aData >> 4) & 0x0F));
            hexString.append(Integer.toHexString(aData & 0x0F));
        }
        return hexString.toString();
    }

}
