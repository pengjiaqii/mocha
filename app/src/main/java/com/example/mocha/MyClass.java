package com.example.mocha;

import java.io.UnsupportedEncodingException;

public class MyClass {



    public static void main(String[] args) {
        System.out.println((SN));
        String encrypt_str = encryptStr(SN);
        System.out.println(encrypt_str);
        String decrypt_str = decryptStr(encrypt_str);
        System.out.println(decrypt_str);


        System.out.println("H7118231204300023");
        encrypt_str = encryptStr("H7118231204300023");
        System.out.println(encrypt_str);
        decrypt_str = decryptStr(encrypt_str);
        System.out.println(decrypt_str);
        //
//        System.out.println((PACKAGE));
//        encrypt_str = encryptStr(PACKAGE);
//        System.out.println(encrypt_str);
//        decrypt_str = decryptStr(encrypt_str);
//        System.out.println(decrypt_str);
        //
        //            encrypt_str = encryptStr(PACKAGE2);
        //            System.out.println(encrypt_str);
        //            decrypt_str = decryptStr(encrypt_str);
        //            System.out.println(decrypt_str);
        //
        //            encrypt_str = encryptStr(PACKAGE3);
        //            System.out.println(encrypt_str);
        //            decrypt_str = decryptStr(encrypt_str);
        //            System.out.println(decrypt_str);

//        System.out.println((PACKAGE4));
//        encrypt_str = encryptStr(PACKAGE4);
//        System.out.println(encrypt_str);
//        decrypt_str = decryptStr(encrypt_str);
//        System.out.println(decrypt_str);


//        System.out.println((PACKAGE5));
//        encrypt_str = encryptStr(PACKAGE5);
//        System.out.println(encrypt_str);
//        decrypt_str = decryptStr(encrypt_str);
//        System.out.println(decrypt_str);


//        System.out.println((PACKAGE6));
//        encrypt_str = encryptStr(PACKAGE6);
//        System.out.println(encrypt_str);
//        decrypt_str = decryptStr(encrypt_str);
//        System.out.println(decrypt_str);

        System.out.println((PACKAGE6));
        encrypt_str = encryptStr(PACKAGE6);
        System.out.println(encrypt_str);
        decrypt_str = decryptStr(encrypt_str);
        System.out.println(decrypt_str);

        System.out.println("{");
        for (int i = 0; i < SN_AR.length; i++) {
            System.out.println("\"" + encryptStr(SN_AR[i]) + "\",");
        }
        System.out.println("/");
        System.out.println("}");


    }

    private static String SN = "M68X7033205S00246";
    private static String[] SN_AR = {
            "M68X7033205S00246",
            "M68X7033205S00246",
            "M68X7033205S00246"
    };


    private static String PACKAGE = "com.jmgo.hotel";
    private static String PACKAGE1 = "com.jmgo.launcher.x";
    private static String PACKAGE2 = "com.ts.launcher";
    private static String PACKAGE3 = "com.jmgo.launcher";

    private static String PACKAGE4 = "com.hlauncher.jmgo";
    private static String PACKAGE5 = "com.iptv.laucher";
    private static String PACKAGE6 = "com.hassmedia.cloudlauncher";


    private static String getstrTo16(String str) {
        byte[] byte_str = str.getBytes();
        String stra = "";
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            String s4 = Integer.toHexString(ch);
            stra = stra + s4;
        }
        return stra;
    }

    private static String get16Tostr(String str) throws UnsupportedEncodingException {
        byte[] baKeyword = new byte[str.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String stra = new String(baKeyword, "UTF-8");
        return stra;
    }

    private static String encryptStr(String string) {
        String str = "";
        string = getstrTo16(string);
        byte[] bytes = exchangeByte(string.getBytes());
        //System.out.println(bytes.length);
        for (int i = 0; i < string.length(); i++) {
            bytes[i] = (byte) (bytes[i] + 0x05);
            //System.out.print("." + (bytes[i]));
        }
        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            str = "";
        } finally {
            return str;
        }
    }

    private static String decryptStr(String string) {
        String str = "";
        byte[] bytes = exchangeByte(string.getBytes());
        //System.out.println(bytes.length);
        for (int i = 0; i < string.length(); i++) {
            bytes[i] = (byte) (bytes[i] - 0x05);
            //System.out.print("." + (bytes[i]));
        }
        try {
            str = get16Tostr(new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            str = "";
        } finally {
            return str;
        }
    }

    private static byte[] exchangeByte(byte[] bytes) {
        byte[] bytes1 = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes1[i] = bytes[bytes.length - i - 1];
        }
        return bytes;
    }
}
