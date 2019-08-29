package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.mapper.BlacklistMapper;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Blacklist;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kaiya Xiong
 * @date 2019-08-15
 */
@Service
@Component
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    RegisterMapper registerMapper;
    @Autowired
    BlacklistMapper blacklistMapper;

    /**
     * @return
     */
    @Override
    public Boolean batchAddBlacklist() {
        //查询所有的登记簿记录
        Boolean flag = false;
        List<Register> registers = registerMapper.selectAll();
        if (registers != null) {
            for (Register r : registers) {
                // TODO: 2019-08-20 duplicate detection
                //把从登记簿查询出来的每一条记录逐条导入到黑名单里
                Blacklist blacklist = new Blacklist();
                blacklist.setName(r.getName());
                blacklist.setIdentityType(r.getIdentityType());
                blacklist.setIdentityNumber(r.getIdentityNumber());
                blacklist.setDateBanned(r.getDateFailed());
                blacklist.setReasonBanned(0);//加入黑名单原因没有对应。。。
                if (blacklistMapper.selectByIdentityNumber(r.getIdentityNumber()) == null){
                    blacklistMapper.addBlacklist(blacklist);
                }

            }
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean batchExportToFile(String path) {

        Boolean flag = false;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileWriter writer;

        List<Blacklist> blacklists = blacklistMapper.selectAll();
        if (blacklists != null) {

            try {
                writer = new FileWriter(path);
                //for header
                writeLine(writer, Arrays.asList("name","identity_type","identity_number","date_banned","reason_banned"));
                for (Blacklist b : blacklists) {
                    List<String> list = new ArrayList<>();
                    list.add(b.getName());
                    list.add(String.valueOf(b.getIdentityType()));
                    list.add(b.getIdentityNumber());
                    list.add(dateFormat.format(b.getDateBanned()));
                    list.add(String.valueOf(b.getReasonBanned()));
                    writeLine(writer, list);
                }
                writer.flush();
                writer.close();
                flag = true;
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
            }

        }
        return flag;
    }

    private static final char DEFAULT_SEPARATOR = ',';

    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }
}
