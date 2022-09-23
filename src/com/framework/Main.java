package com.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    private static short[] library = new short[0];

    private static String decrypt(int s, int e, int key) {
        char[] result = new char[e - s];
        for (int i = 0; i < e - s; i++) {
            result[i] = (char) (library[s + i] ^ key);
        }
        return new String(result);
    }

    private static short[] encrypt(String txt, int key) {
        short[] result = new short[txt.length()];
        for(int i = 0;i < txt.length();i++) {
            int a = txt.charAt(i) ^ key;
            result[i] = (short)a;
        }
        return result;
    }

    private static short[] mergeShortArray(short[] a, short[] b) {
        short[] result = new short[a.length + b.length];
        for(int i = 0;i < a.length + b.length;i++) {
            if(i >= a.length) {
                result[i] = b[i - a.length];
            } else {
                result[i] = a[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> poss = new ArrayList<>(); // 位置列表
        List<Integer> keys = new ArrayList<>(); // key列表
        List<String> codes = new ArrayList<>(); // 调用代码列表

        Random ran = new Random();

        String mn = "$"; // 方法名

        poss.add(0);
        String[] a = new String[]{"狗哥单身一辈子", "我服了你个老六", "这就是老鼠药", "你干嘛～哎哟！", "控他啊这个张飞", "这种段位我直接乱杀好吧"};

        for (String in : a) {
            // 生成随机key
            int k = -9999 + ran.nextInt(19998);
            keys.add(k);
            // 加密
            short[] en = encrypt(in, k);
            int pos = library.length + en.length;
            // 生成调用代码
            codes.add(mn + "(" + library.length + ", " + pos + ", " + k + ")");
            library = mergeShortArray(library, en);
            poss.add(pos);
        }

        // 输出已加密代码
        System.out.println("private static short[] $ = " + Arrays.toString(library).replace("[", "{").replace("]", "}") + ";");

        // 输出解密代码
        System.out.println("private static String " +mn+ "(int i, int i2, int i3) {\n" +
                "	char[] cArr = new char[i2 - i];\n" +
                "	for (int i4 = 0; i4 < i2 - i; i4++) {\n" +
                "		cArr[i4] = (char) ($[i + i4] ^ i3);\n" +
                "	}\n" +
                "	return new String(cArr);\n" +
                "}");

        // 解密
        for(int i = 0;i < keys.size();i++) {
            System.out.println(codes.get(i) + " = " + decrypt(poss.get(i), poss.get(i+1), keys.get(i)));
        }
    }

}
