package com.data.recover;

import com.data.recover.conn.AttrHouse;
import com.data.recover.conn.CachedThreadPollUtil;
import com.data.recover.conn.FileStream;
import com.data.recover.service.BatchHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Victor on 17-6-30.
 */
public class ApplicationService {
    private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);
    private static int threadNum = Integer.parseInt(AttrHouse.getPropertiesValue("thread.pool.num"));

    public static void main(String[] args) {
        String file = null;
        if (args.length == 0) {
            logger.info("Parameter is null,use default location");
            file = AttrHouse.getPropertiesValue("file.recover.location");
        } else if (args.length != 1) {
            logger.info("Parameter number error, it must be one and type must be 'String.class' ");
            System.exit(-1);
        } else {
            file = args[0];
        }

        CountDownLatch latch = new CountDownLatch(threadNum + 1);
        FileStream inThread = new FileStream(file, latch);
        CachedThreadPollUtil.getThreadPool().submit(inThread);
        for (int i = 0; i < threadNum; i++) {
            BatchHandle out = new BatchHandle(latch);
            CachedThreadPollUtil.getThreadPool().submit(out);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("The program was interrupted");
        }
        CachedThreadPollUtil.getThreadPool().shutdown();
        System.exit(0);
    }
}
