package com.example.ccps.scheduled;

import com.example.ccps.bean.Blacklist;
import com.example.ccps.mapper.BlacklistMapper;
import com.example.ccps.bean.Blacklist;
import com.example.ccps.mapper.BlacklistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

@Component
public class UpdateBlacklist {

    @Autowired
    BlacklistMapper blacklistMapper;

    public static ArrayList<String[]> getFileList(){
        File dir = new File("D:\\迅雷下载");
        ArrayList<String[]> files = new ArrayList<>();
        for (File temp : dir.listFiles()) {
            if (temp.isFile() && temp.exists()) {
//                [文件名，大小，状态0]捆绑，写入到文件列表中
                String[] file = new String[]{temp.toString(),Long.toString(temp.length()),"0"};
                files.add(file);
            }
        }
//        System.out.println(files);
        return files;
    }

    public void updateBlacklist(String path){
//        读取CSV中的数据，返回String的
        ArrayList<String[]> CSVData = ReadCSV.Read(path);
//        遍历每一条记录
        for(int i = 0;i<CSVData.size()-1;i++){
//            Identity_number为CSVData.get(i+1)[2]，根据这个来查询是否存在
            Blacklist select_result = blacklistMapper.getBlacklistByIdentity_number(CSVData.get(i+1)[2]);


//            返回Null，不存在执行插入操作
            if (select_result == null){
                System.out.println(CSVData.get(i+1)[2]+"需要插入到数据库");
//                CSVData.get(i+1)[3].substring(1,CSVData.get(i+1)[3].length()-1)将"2019-01-01 12:13:14"的两个引号去除
//                随后执行插入操作
                blacklistMapper.insertBlacklist(CSVData.get(i+1)[0],Integer.parseInt(CSVData.get(i+1)[1]),CSVData.get(i+1)[2],
                        Timestamp.valueOf(CSVData.get(i+1)[3]),Integer.parseInt(CSVData.get(i+1)[4]));
            }
            else{
                System.out.println(CSVData.get(i+1)[2]+"记录已存在");
            }
        }

    }

    public ArrayList getFileState(ArrayList<String[]> oldState){

        ArrayList<String[]> newState = new ArrayList<>();
        ArrayList<String[]> nowState = getFileList();

        String[] stateTemp;
//        遍历当前状态的每一个文件
        for(String[] tempNow : nowState){
//            System.out.println(tempNow[0]);
            int state = 0;
            int extenceState = 0;
            stateTemp = tempNow.clone();
//            在上一状态中找到它
            for(String[] tempOld : oldState){
//                新文件列表中找到旧文件
                if(tempOld[0].equals(tempNow[0])){
//                    获取文件状态
                    state = Integer.parseInt(tempOld[2]);
                    extenceState = 1;
//                    新文件和旧文件大小相等,状态+1
                    if(tempOld[1].equals(tempNow[1])){
                        System.out.println("正在确认文件："+tempNow[0]);
                        state = state + 1;
                        stateTemp[2] = Integer.toString(state);
                        newState.add(stateTemp);
//                        大小不在变化超过5S，认为传输完成
                        if (state >= 5){
                            System.out.println(tempNow[0]+"传输完成，执行更新操作");
//                            执行更新操作
                            updateBlacklist(tempNow[0]);
                            File file = new File(tempNow[0]);
//                            更新完成后删除文件
                            file.delete();
                            System.out.println("更新完成，删除文件："+tempNow[0]);
                        }
                    }
//                    新旧文件大小不等，传输进行中
                    else {
                        System.out.println("接收文件："+tempNow[0]);
                        stateTemp[2] = "0";
                        newState.add(stateTemp);
                    }
                    break;
                }
            }
//            新文件在旧列表中找不到，记为状态0
            if( extenceState == 0 ){
                System.out.println("收到新文件"+tempNow[0]);
                stateTemp[2] = "0";
                newState.add(stateTemp);
            }
        }
        return newState;
    }
}
