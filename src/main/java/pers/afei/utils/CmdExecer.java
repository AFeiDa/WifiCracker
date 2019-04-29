package pers.afei.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * 用于执行命令的类，当然，也可以用于执行脚本啥的~~~
 */
public class CmdExecer {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    public Vector<String> exec(String cmd) {
        Vector<String> vStrings = new Vector<String>();
        try {
            Process p = RUNTIME.exec(cmd);  // 执行命令
            
            InputStream in = p.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, "GBK");
            BufferedReader bReader = new BufferedReader(reader);

            String line;
            while((line = bReader.readLine()) != null) {
                vStrings.add(line);
            }
        } catch (IOException e) {
            System.out.println("命令执行出现IO异常");
        }
        return vStrings;
    }

    public static void main(String[] args) {
        CmdExecer cmdExecer = new CmdExecer();
        Vector<String> vStrings = cmdExecer.exec("ipconfig");

        for(String i: vStrings) {
            System.err.println(i);
        }
    }
}