package pers.afei.profile;

import pers.afei.utils.Util;

public class ProfileBean {

    private String ssid;
    private String password;
    private String profile;

    public ProfileBean(String ssid, String pwd) {

        setssid(ssid);
        setPassword(pwd);
        setProfile();
    }


    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile 根据ssid
     */
    public void setProfile() {

        if (password == null || password.equals("")) {
            profile = "<?xml version=\"1.0\"?>\r\n"
                + "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\r\n" 
                + "    <name>" + ssid + "</name>\r\n" 
                + "    <SSIDConfig>\r\n" 
                + "        <SSID>\r\n" 
                + "            <hex>" + Util.stringToHex(ssid) + "</hex>\r\n" 
                + "            <name>" + ssid + "</name>\r\n"
                + "        </SSID>\r\n" 
                + "    </SSIDConfig>\r\n" 
                + "    <connectionType>ESS</connectionType>\r\n"
                + "    <connectionMode>auto</connectionMode>\r\n" 
                + "    <MSM>\r\n" 
                + "        <security>\r\n"
                + "            <authEncryption>\r\n" 
                + "                <authentication>open</authentication>\r\n"
                + "                <encryption>none</encryption>\r\n"
                + "                <useOneX>false</useOneX>\r\n" 
                + "            </authEncryption>\r\n"
                + "        </security>\r\n" 
                + "    </MSM>\r\n"
                + "    <MacRandomization xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v3\">\r\n"
                + "        <enableRandomization>false</enableRandomization>\r\n" 
                + "        <randomizationSeed>" + Util.getRandomizationSeed() + "</randomizationSeed>\r\n" 
                + "    </MacRandomization>\r\n"
                + "</WLANProfile>";
        } else {
            profile = "<?xml version=\"1.0\"?>\r\n"
                + "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\r\n" 
                + "    <name>" + ssid + "</name>\r\n" 
                + "    <SSIDConfig>\r\n" 
                + "        <SSID>\r\n" 
                + "            <hex>" + Util.stringToHex(ssid) + "</hex>\r\n" 
                + "            <name>" + ssid + "</name>\r\n"
                + "        </SSID>\r\n" 
                + "    </SSIDConfig>\r\n" 
                + "    <connectionType>ESS</connectionType>\r\n"
                + "    <connectionMode>auto</connectionMode>\r\n" 
                + "    <MSM>\r\n" 
                + "        <security>\r\n"
                + "            <authEncryption>\r\n" 
                + "                <authentication>WPA2PSK</authentication>\r\n"
                + "                <encryption>AES</encryption>\r\n"
                + "                <useOneX>false</useOneX>\r\n" 
                + "            </authEncryption>\r\n"
                + "            <sharedKey>\r\n"
				+ "                <keyType>passPhrase</keyType>\r\n"
				+ "                <protected>false</protected>\r\n"
				+ "                <keyMaterial>" + password + "</keyMaterial>\r\n"
			    + "            </sharedKey>\r\n"
                + "        </security>\r\n" 
                + "    </MSM>\r\n"
                + "    <MacRandomization xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v3\">\r\n"
                + "        <enableRandomization>false</enableRandomization>\r\n" 
                + "        <randomizationSeed>" + Util.getRandomizationSeed() + "</randomizationSeed>\r\n" 
                + "    </MacRandomization>\r\n"
                + "</WLANProfile>";
        }
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the ssid
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * @param ssid the ssid to set
     */
    public void setssid(String ssid) {
        this.ssid = ssid;
    }

}