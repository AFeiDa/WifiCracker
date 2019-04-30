package pers.afei.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperation {

    /**
     * 创建文件
     * @return <code>true:</code> 文件不存在并且创建成功 <code>false:</code> 文件已存在或者出现异常
     * @param path 文件路径
     */
    public static boolean createFile(String path) {
        
        File file = new File(path);
        boolean flag = false;

        try {
            File filePaFile = file.getParentFile();
            
            if(filePaFile != null && !filePaFile.exists()) {
                filePaFile.mkdirs();
            }

            flag = file.createNewFile();
                
        } catch (IOException e) {                
            flag = false;
        }

        return flag;
    }

    /**
     * 删除文件
     * @return <code>true:</code> 文件不存在或者删除成功 <code>false:</code> 文件存在并且删除失败
     * @param path 文件路径
     */
    public static boolean deleteFile(String path) {
        
        File file = new File(path);
        boolean flag = false;

        if(file.exists()) {
            flag = file.delete();

        } else {
            flag = true;
        }

        return flag;
    }

    /**
     * 清空文件内容
     * 
     * @return <code>true:</code>清空文件成功 <code>false:</code>文件不存在或者出现异常
     * @param path 文件路径
     */
    public static boolean clearFile(String path) {
        
        File file = new File(path);
        return file.exists() && deleteFile(path) && createFile(path);
    }

    /**
     * 文件末尾追加内容，文件不存在则新建文件
     * @param path 文件路径
     * @param content 追加内容
     */
    public static boolean appendToFile(String path, String... content) {

        FileWriter fileWriter = null;
        BufferedWriter bWriter = null;
        boolean flag = false;

        try {
            File file = new File(path);
            
            if(!file.exists()) {
                createFile(path);
            }

            fileWriter = new FileWriter(path, true);
            bWriter = new BufferedWriter(fileWriter);

            for(String con: content) {
                bWriter.append(con + "\r\n");
            }

            flag = true;

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if(bWriter != null) {
                    bWriter.close();
                }

                if(fileWriter != null) {
                    fileWriter.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    /**
     * 覆盖文件，文件不存在则新建文件
     * @param path 文件路径
     * @param content 新文件内容
     * @return <code>true</code> 覆盖成功 <code>false</code>文件不存在或者出现异常
     */
    public static boolean overwriteFile(String path, String... content) {
        return (createFile(path) || clearFile(path)) && appendToFile(path, content);
    }

    public static void main(String[] args) throws IOException {
        String path = "a.txt";
        System.out.println(deleteFile(path));
    }
}