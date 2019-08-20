package com.icbc.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icbc.provider.mapper.RegisterMapper;
import com.icbc.provider.model.Register;
import com.icbc.provider.service.RegisterService;
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
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterMapper registerMapper;

    @Override
    public Boolean gotoDarkroom(Register register) {
        Boolean flag = false;
        if (register != null) {
            int result = registerMapper.addPayFailed(register);
            if (result >= 0) {
                flag = true;
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean batchExportToFile(String path) {
        Boolean flag = false;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Register> registers = registerMapper.selectAll();
        if (registers != null) {

            try {
                FileWriter writer = new FileWriter(path);
                //for header
                writeLine(writer, Arrays.asList("order_id", "date_failed", "name", "identity_type, identity_number"));
                for (Register r : registers) {
                    List<String> list = new ArrayList<>();
                    list.add(r.getOrderId());
                    list.add(dateFormat.format(r.getDateFailed()));
//                    System.out.println("date:"+dateFormat.format(r.getDateFailed()));
                    list.add(r.getName());
                    list.add(String.valueOf(r.getIdentityType()));
                    list.add(r.getIdentityNumber());

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
