/**
 * Authore by Gary on 2020/07/26.
 **/

package com.gary.microblog.util;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MD5Utils {
    /**
     * MD5加密
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String code(String str){
        try{
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest =md.digest();
            int i;
            StringBuffer buf=new StringBuffer("");
            for(int offset=0;offset<byteDigest.length;++offset){
                i=byteDigest[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32bit
            return buf.toString();
            //16bit
            //return buf.toString().substring(8,24);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //32bit的
    public static void main(String[] args) {
        System.out.println(code(""));
        System.out.println(new Date());
    }
}
