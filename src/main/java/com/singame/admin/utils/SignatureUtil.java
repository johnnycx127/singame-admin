package com.singame.admin.utils;


import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureUtil {
  private static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
  
  private static String byteArrayToHexString(byte[] b) {
    StringBuilder hs = new StringBuilder();
    String stmp;
    for (int n = 0; b != null && n < b.length; n++) {
      stmp = Integer.toHexString(b[n] & 0XFF);
      if (stmp.length() == 1)
        hs.append('0');
      hs.append(stmp);
    }
    return hs.toString().toLowerCase();
  }

  public static String sha256(String message) {
    String hash = "";
    try {
      byte[] bt = message.getBytes();
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(bt);
      hash = byteArrayToHexString(md.digest());
    } catch (Exception e) {
      logger.error("Error HmacSHA256 " + e.getMessage());
    }
    return hash;
  }
}