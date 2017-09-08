package cn.bestsec.dcms.platform.utils.dlp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.WString;

import cn.bestsec.dcms.platform.utils.SystemProperties;

public class SearchTool {
    private static Logger logger = Logger.getLogger(SearchTool.class);

    private static String input_filepath_format = "%s\\scan_input_%s_%s.ini";
    private static String output_filepath_format = "%s\\scan_output_%s_%s.txt";

    public static int search(String taskId, String scanPath, String scanParam, List<DocumentScanResult> results)
            throws Exception {
        logger.info("关键字  taskid: " + taskId);
        logger.info("关键字  scanPath: " + scanPath);
        logger.info("关键字  scanParam: " + scanParam);

        File file = new File(scanPath);
        String parentFilepath = file.getParentFile().getPath();
        String scanDirName = file.getName();
        String inputFilepath = String.format(input_filepath_format, parentFilepath, scanDirName, taskId);
        String outputFilepath = String.format(output_filepath_format, parentFilepath, scanDirName, taskId);

        // 执行关键词检测
        writeInputFile(inputFilepath, scanPath, scanParam);
        IKeywordJni.instance.StartTaskToMgr(new WString(taskId + ""), new WString(inputFilepath),
                new WString(outputFilepath));

        Pointer resultv = new Memory(32);
        long startTime = System.currentTimeMillis();
        long timeout = SystemProperties.getInstance().getDlpKeywordTimeout();
        logger.info("关键字任务编号： " + taskId + " 开始执行");
        while (true) {
            Thread.sleep(1000);
            int taskStatus = IKeywordJni.instance.FindTaskStatusFromMgr(new WString(taskId + ""), resultv);

            byte[] byteArray = resultv.getByteArray(0, 32);
            char chatAt = '0';
            chatAt = new String(byteArray).charAt(0);
            if ("2".equals(chatAt + "")) {
                IKeywordJni.instance.ReleaseTaskFromList(new WString(taskId + ""));
                Thread.sleep(500);
                logger.info("关键字任务编号： " + taskId + " :处理完成");
                parseOutputFile(outputFilepath, results);
                return 2; // 执行完成
            }

            // 超时处理机制
            if (System.currentTimeMillis() > (startTime + timeout)) {
                IKeywordJni.instance.ReleaseTaskFromList(new WString(taskId + ""));
                Thread.sleep(500);
                logger.info("关键字任务编号： " + taskId + " 检测超时");
                return 3; // 执行失败
            }
        }

    }

    private static void writeInputFile(String inputFilepath, String scanPath, String scanParam) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("[TASK]");
        lines.add("");
        lines.add("scanpath=" + scanPath);
        lines.add("");
        lines.add("taskinfo=" + scanParam);
        // 约定SearchTool input文件为ANSI格式
        FileUtils.writeLines(new File(inputFilepath), "GB2312", lines);
    }

    private static void parseOutputFile(String outputFilepath, List<DocumentScanResult> results) throws IOException {
        File outputFile = new File(outputFilepath);
        if (!outputFile.exists()) {
            return;
        }
        // 约定SearchTool output文件为ANSI格式
        List<String> lines = FileUtils.readLines(outputFile, "GB2312");
        List<String> segments = new ArrayList<>();
        for (String line : lines) {
            segments.add(line);
            if (StringUtils.isBlank(line)) {
                results.add(JSON.parseObject(StringUtils.join(segments, ""), DocumentScanResult.class));
                segments.clear();
            }
        }
    }
}
