package cn.bestsec.dcms.platform.utils.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

public class CSVUtils {

    public static File createCSVFile(List<Map<String, String>> data, LinkedHashMap<String, String> header, String outPutPath,
            String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            // 定义文件名格式并创建
            csvFile = new File(outPutPath + File.separator + fileName + ".csv");
            csvFile.createNewFile();
            // csvFile = File.createNewFile(fileName, ".csv", new
            // File(outPutPath));
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"),
                    1024);
            // 写入文件头部
            for (Iterator<Entry<String, String>> propertyIterator = header.entrySet().iterator(); propertyIterator
                    .hasNext();) {
                Entry<String, String> propertyEntry = propertyIterator.next();
                csvFileOutputStream.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
                csvFileOutputStream.write(
                        "" + (String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "" + "");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator<Map<String, String>> iterator = data.iterator(); iterator.hasNext();) {
                Map<String, String> row = iterator.next();
                for (Iterator<Entry<String, String>> propertyIterator = header.entrySet().iterator(); propertyIterator.hasNext();) {
                    Entry<String, String> propertyEntry = propertyIterator.next();
                    csvFileOutputStream.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
                    csvFileOutputStream.write((String) BeanUtils.getProperty(row, (String) propertyEntry.getKey()));
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
}
