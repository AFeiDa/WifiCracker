package pers.afei.wificracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pers.afei.utils.FileOperation;

/**
 * DicGenerator，生成字典
 */
public class DicGenerator {

    private char[] charSet;
    private int length;
    private String dicName;
    private char[] content;

    /**
     * 
     * @param charSet 字符集
     * @param length  密码长度
     * @param dicName 字典名
     */
    public DicGenerator(String charSet, int length, String dicName) {
        this.charSet = charSet.toCharArray();
        this.length = length;
        this.dicName = dicName;
        this.content = new char[length + 2];
        content[length] = '\r';
        content[length + 1] = '\n';
    }

    public void generator() {
        String path = new File("").getAbsolutePath() + "//dictionary//" + dicName + ".txt";
        FileOperation.createFile(path);

        char[] pwd = new char[length];

        for (int i = 0; i < length; ++i) {
            pwd[i] = charSet[0];
        }

        FileWriter fileWriter = null;
        BufferedWriter bWriter = null;

        try {
            fileWriter = new FileWriter(path, true);
            bWriter = new BufferedWriter(fileWriter);

            dfs(0, charSet.length, bWriter);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bWriter != null) {
                    bWriter.close();
                }

                if (fileWriter != null) {
                    fileWriter.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void dfs(int i, int t, BufferedWriter bWriter) throws IOException {
        if(i == length) {
            String str = new String(content);
            bWriter.append(str);
        } else for(int k = 0; k < t; ++ k) {
            content[i] = charSet[k];
            dfs(i+1, t, bWriter);
        }
    }

    public static void main(String[] args) {
        DicGenerator generator = new DicGenerator("abcd", 2, "hh.txt");
        generator.generator();
    }
}
