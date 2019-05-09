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
            bit = (bytes[i] & 0xf0) >> 4;
            hex.append(chars[bit]);
            bit = bytes[i] & 0x0f;
            hex.append(chars[bit]);
        }
        return hex.toString();
    }

    /**
     * 传入一个十六进制字符串，转换为普通字符串
     * @param hexStr
     * @return String
     */
    public static String hexToString(String hexStr) {

        String bits = "0123456789ABCDEF";

        byte[] string = new byte[hexStr.length() >> 1];
        byte[] bytes = hexStr.getBytes();

        int bit = 0;
        for(int i = 0; i < string.length; ++ i) {
            bit = (bits.indexOf(bytes[i<<1]) << 4) + bits.indexOf(bytes[i<<1|1]);
            string[i] = (byte) (bit & 0xff);
        }
        
        return new String(string);        
    }

    /**
     * 传入一个串，判断是不是十六进制串
     * @param hex
     * @return boolean
     */
    public static boolean isHex(String hex) {
        final String BIT = "0123456789ABCDEF"; 
        boolean ret = true;
        int l = hex.length();
        for(int i = 0; i < l; ++ i) {
            ret &= !(BIT.indexOf(hex.charAt(i)) == -1);
        }
        return ret;
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
        String a = "ABC", b = "飝龘龘";
        System.out.println(isHex(a));
        System.out.println(isHex(b));
    }
}