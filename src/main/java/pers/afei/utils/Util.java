package pers.afei.utils;

import pers.afei.profile.ProfileBean;

public class Util {

    /**
     * 传入一个字符串，返回对应的16进制字符串，支持中文转换
     * 
     * @param string
     * @return string对应的16进制串，每个字符用两位16进制数字表示
     */
    public static String stringToHex(String string) {
        final char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder hex = new StringBuilder("");
        byte[] bytes = string.getBytes();
        int bit;
        for (int i = 0; i < bytes.length; i++) {
            bit = (bytes[i] & 0x0f0) >> 4;
            hex.append(chars[bit]);
            bit = bytes[i] & 0x0f;
            hex.append(chars[bit]);
        }
        return hex.toString();
    }

    /**
     * 根据当前时间生成随机种子
     * 
     * @return long 随机种子
     */
    public static long getRandomizationSeed() {
        long t = System.currentTimeMillis() % 4294967296L + 1;
        return t;
    }

    private final static ProfileBean PROFILE_BEAN = new ProfileBean("", "");
    /**
     * 生成wlan配置文件并存入path
     * @param ssid
     * @param password
     * @param path
     */
    public static void generator(String ssid, String password, String path) {
        PROFILE_BEAN.setssid(ssid);
        PROFILE_BEAN.setPassword(password);
        PROFILE_BEAN.setProfile();
        FileOperation.overwriteFile(path, PROFILE_BEAN.getProfile());
    }

    /**
     * 生成wlan配置文件并存入固定目录下
     * @param ssid
     * @param password
     */
    public static void generator(String ssid, String password) {
        generator(ssid, password, "C:\\profiles\\WLAN-" + ssid + ".xml");
    }


    public static void main(String[] args) {
        generator("飝龘龘", "asdfghjkl");
    }
}