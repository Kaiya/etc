package com.example.ccps.scheduled;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV{
//    将逗号分割的CSV行分解为字段
    private static String[] split(String line) {
        List<String> list = new ArrayList<>();
        int start = 0;
        int end = -1;
        while(true) {
            start = end + 1;
            end = line.indexOf("," , start );
            if(end < 0) {
                //到最后位置
                end = line.length() ;
            }
            String p1 = line.substring(start, end);
            list.add(p1);
            if(end >= line.length() - 1) {
                break;
            }
        }
        return list.toArray(new String[0]) ;
    }

    //读取CSV文件，返回字符串列表的数组
    public static ArrayList<String[]> Read(String path){

        ArrayList<String[]> CSVData = new ArrayList<>();
        File csv = new File(path);  // CSV文件路径
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        try {
            List<String> allString = new ArrayList<>();
            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {

                everyLine = line;
                CSVData.add(split(everyLine));
                allString.add(everyLine);
            }
//            System.out.println("csv表格中所有行数："+allString.size());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CSVData;
    }

}