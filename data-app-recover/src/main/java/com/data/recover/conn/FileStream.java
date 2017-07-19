package com.data.recover.conn;

import com.data.recover.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Victor on 17-6-30.
 */
public class FileStream implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(FileStream.class);
    private final int threadNum = Integer.parseInt(AttrHouse.getPropertiesValue("thread.pool.num"));

    private String location;
    private CountDownLatch latch;

    public FileStream(String location, CountDownLatch latch) {
        this.location = location;
        this.latch = latch;
    }

    private void loadFIle(String location) {
        try {
            if (location.contains(",")) {
                String[] locations = location.split(",");
                for (String e : locations) {
                    checkFileType(e);
                }
            } else {
                checkFileType(location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for (int i = 0; i < threadNum; i++) {
                BufferQueue.produce(CommonUtil.RESULT_COMPLETE_SIGNAL);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.latch.countDown();
        }
    }

    private void checkFileType(String location) throws IOException, InterruptedException {
        File file = new File(location);
        if (file.isDirectory()) {
            if (!file.exists()) {
                logger.info("file directory not exists" + file.getAbsolutePath());
                return;
            }
            logger.info("start to loop file directory");
            List<File> fileList = new ArrayList<>();
            getFileList(file, fileList);
            for (File f : fileList) {
                largeFileIO(f, CommonUtil.LINE_LAUNCH);
            }
        } else {
            if (!file.exists()) {
                logger.info("file not exists" + file.getAbsolutePath());
                return;
            }
            logger.info("start to load file");
            largeFileIO(file, CommonUtil.LINE_LAUNCH);
        }
    }

    private void largeFileIO(File inputFile, int launch) throws IOException, InterruptedException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 1 * 1024 * 1024);// 1M缓存
        Long lineFlag = 0L;
        while (in.ready()) {
            if (lineFlag % launch == 0)
                Thread.sleep(100);
            String line = in.readLine();
            BufferQueue.produce(line);
            lineFlag++;
        }
        in.close();
    }

    private void getFileList(File dir, List<File> filelist) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    getFileList(files[i], filelist);
                } else {
                    filelist.add(files[i]);
                }
            }
        }
    }

    @Override
    public void run() {
        loadFIle(this.location);
    }
}
