package com.oppo.seckilldemo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeInToFile(String path, String message) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        FileWriter writer = new FileWriter(file, true);
        writer.append(message);
        writer.flush();
        writer.close();
    }

    public static void main(String[] args) {
        try{
            FileUtil.writeInToFile("D:\\System\\apache-jmeter-5.4.1\\api_config_yk\\test.txt", "hello world!!!!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
