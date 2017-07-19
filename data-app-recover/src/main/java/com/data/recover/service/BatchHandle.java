package com.data.recover.service;

import com.alibaba.fastjson.JSON;
import com.data.recover.conn.AttrHouse;
import com.data.recover.conn.BufferQueue;
import com.data.recover.dao.BaseDataReportMapper;
import com.data.recover.dao.CustomDataReportMapper;
import com.data.recover.dao.ExtraDataReportMapper;
import com.data.recover.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Victor on 17-7-1.
 */

public class BatchHandle implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(BatchHandle.class);

    private CountDownLatch latch;

    private DataHandle dataHandle;

    private BaseDataReportMapper baseDataReportMapper;

    private ExtraDataReportMapper extraDataReportMapper;

    private CustomDataReportMapper customDataReportMapper;

    private final int batchNum = Integer.parseInt(AttrHouse.getPropertiesValue("db.batch.num"));

    public BatchHandle(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        String content = null;
        dataHandle = CommonUtil.applicationContext.getBean(DataHandle.class);
        baseDataReportMapper = CommonUtil.applicationContext.getBean(BaseDataReportMapper.class);
        extraDataReportMapper = CommonUtil.applicationContext.getBean(ExtraDataReportMapper.class);
        customDataReportMapper = CommonUtil.applicationContext.getBean(CustomDataReportMapper.class);

        List<Map<String, Object>> appBaseModels = new ArrayList<>();
        List<Map<String, Object>> appExtraModels = new ArrayList<>();
        List<Map<String, Object>> appCustomModels = new ArrayList<>();
        try {
            while (true) {
                content = BufferQueue.consume();
                if (content == null) {
                    Thread.sleep(100);
                    continue;
                }

                if (content.equals(CommonUtil.RESULT_COMPLETE_SIGNAL)) {
                    if (!appBaseModels.isEmpty() && !appExtraModels.isEmpty()) {
                        baseDataReportMapper.insertBaseReportData(appBaseModels);
                        extraDataReportMapper.insertExtraReportData(appExtraModels);
                        if (appCustomModels.size() > 0) {
                            customDataReportMapper.insertCustomReportData(appCustomModels);
                        }
                        appBaseModels.clear();
                        appExtraModels.clear();
                        appCustomModels.clear();
                    }
                    logger.info("received data completed , Quit !!! ");
                    return;
                }

                dataHandle.resolveTable((Map<String, Object>) JSON.parse(content), appBaseModels, appExtraModels, appCustomModels);
                if (!appBaseModels.isEmpty() && !appExtraModels.isEmpty() && appBaseModels.size() == batchNum && appExtraModels.size() == batchNum) {
                    baseDataReportMapper.insertBaseReportData(appBaseModels);
                    extraDataReportMapper.insertExtraReportData(appExtraModels);
                    if (appCustomModels.size() > 0) {
                        customDataReportMapper.insertCustomReportData(appCustomModels);
                    }
                    appBaseModels.clear();
                    appExtraModels.clear();
                    appCustomModels.clear();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.latch.countDown();
        }
    }
}
