package com.app.service.producer;

import com.app.service.common.BufferQueue;
import com.app.service.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Victor on 17-6-29.
 */
@Component
public class CollectData {

    private Logger logger = LoggerFactory.getLogger(CollectData.class);

    @Value("${file.dir}")
    private String fileDir;
    @Value("${data.default.dir}")
    private String defaultDir;

    public void poll(BufferQueue queue) {
        String content = null;
        while (true) {
            try {
                content = queue.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeToFile(content);
        }
    }

    private void writeToFile(String content) {
        logger.info("------file--write--begin-------");
        String fileName = "data_upload_" + Constants.format.format(new Date()) + ".log";
        String filePath = fileDir + fileName;
        FileWriter fw = null;
        try {
            fw = new FileWriter(getFile(filePath), true);
            if (content == null || "".equals(content.trim())) {
                logger.info("------file--write--done-------消息为空");
                return;
            }
            fw.write(content);
            fw.write("\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("=====file close failed===:" + e);
            }
        }
        logger.info("------file--write--done-------");
    }

    // 判断文件是否存在
    public File checkFileExists(String f) {
        File file = new File(f);
        if (file.exists())
            return file;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File getFile(String path) {
        int dirIndex = path.lastIndexOf("/");
        StringBuilder realPath = new StringBuilder();
        if (dirIndex != -1) {
            String dir = path.substring(0, dirIndex);
            judeDirExists(dir);
            realPath.append(path);
        } else {
            realPath.append(defaultDir).append(path);
        }

        return checkFileExists(realPath.toString());
    }

    // 判断文件夹是否存在
    public void judeDirExists(String fileDir) {
        File dir = new File(fileDir);
        if (dir.exists())
            return;
        dir.mkdir();
    }
}
