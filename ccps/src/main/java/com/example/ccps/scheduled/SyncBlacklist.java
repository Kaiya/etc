package com.example.ccps.scheduled;

import com.example.ccps.bean.Blacklist;
import com.example.ccps.mapper.BlacklistMapper;
import com.example.ccps.util.SFTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Kaiya Xiong
 * @date 2019-08-28
 */
@Component
public class SyncBlacklist {
    @Autowired
    BlacklistMapper blacklistMapper;

    public Boolean syncBlacklist() {
        Boolean flag;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            String host = "worker1.kaiya.ml";
//            String host = "worker2.kaiya.ml";
            SFTPClient sftpClient = new SFTPClient(host, 2223, "test", "qwert123");
//            String ccia_csvPath = "provider/target/classes/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";
//            String ccps_csvPath = "ccps/target/classes/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";
            String ccia_csvPath = "/home/xiongkaiya/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";
            String ccps_csvPath = "/home/xiongkaiya/csv/ccia_blacklist_" + df.format(new Date()) + ".csv";

            // TODO: 2019-08-29  SFTP client download files
            sftpClient.connect();
            sftpClient.download(ccia_csvPath, ccps_csvPath);
            ArrayList<String[]> CSVData = ReadCSV.Read(ccps_csvPath);
            for (int i = 0; i < CSVData.size() - 1; i++) {
                Blacklist select_result = blacklistMapper.getBlacklistByIdentity_number(CSVData.get(i + 1)[2]);
                if (select_result == null) {
                    blacklistMapper.insertBlacklist(CSVData.get(i + 1)[0], Integer.parseInt(CSVData.get(i + 1)[1]), CSVData.get(i + 1)[2],
                            Timestamp.valueOf(CSVData.get(i + 1)[3]), Integer.parseInt(CSVData.get(i + 1)[4]));
                }

            }
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;

    }
}
