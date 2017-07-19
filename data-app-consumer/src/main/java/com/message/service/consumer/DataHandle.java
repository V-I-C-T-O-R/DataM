package com.message.service.consumer;

import com.message.service.dao.datacenter.BaseDataReportMapper;
import com.message.service.dao.datacenter.CustomDataReportMapper;
import com.message.service.dao.datacenter.ExtraDataReportMapper;
import com.message.service.util.CommonUtil;
import com.message.service.util.Constants;
import com.message.service.util.CustomUUID;
import jdk.nashorn.internal.ir.ContinueNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 17-6-29.
 */
@Component
public class DataHandle {
    private Logger logger = LoggerFactory.getLogger(DataHandle.class);

    @Value("${t_data_upload_base}")
    private String tDataBase;

    @Value("${t_data_upload_extra}")
    private String tDataExtra;

    @Value("${t_data_upload_custom}")
    private String tDataCustom;

    @Value("${table.partition.key}")
    private String key;

    @Value("${table.partition.attr}")
    private String attr;

    @Value("${table.partition.index}")
    private String index;

    @Autowired
    private BaseDataReportMapper baseDataReportMapper;
    @Autowired
    private ExtraDataReportMapper extraDataReportMapper;
    @Autowired
    private CustomDataReportMapper customDataReportMapper;

    public void resolveTable(Map<String, Object> message) {
        if (message == null || message.isEmpty() || message.get(key) == null)
            return;
        DefaultFormattingConversionService cs = new DefaultFormattingConversionService(true);
        message.put(key, cs.convert(message.get(key), Long.class));
        logger.info("start to resolve message");
        String[] baseTableColumn = tDataBase.split(",");
        String[] extraTableColumn = tDataExtra.split(",");
        String[] customTableColumn = tDataCustom.split(",");
        Map<String, Object> baseTable = new HashMap<>();
        Map<String, Object> extraTable = new HashMap<>();
        Map<String, Object> customTable = new HashMap<>();
        long uuid = CustomUUID.get().nextId();
        baseTable.put(index, uuid);
        extraTable.put(index, uuid);

        baseTable.put(attr, new Date((long) message.get(key)));
        extraTable.put(attr, new Date((long) message.get(key)));

        Arrays.asList(baseTableColumn).stream().forEach(e -> {
            baseTable.put(e, message.get(e));
        });
        Arrays.asList(extraTableColumn).stream().forEach(e -> {
            extraTable.put(e, message.get(e));
        });
        Arrays.asList(customTableColumn).stream().forEach(e -> {
            if (message.get(e) == null || "".equals(((String) message.get(e)).trim())) {
                return;
            }
            customTable.put(e, message.get(e));
        });
        logger.info("start to insert DB");
        baseDataReportMapper.insertReportData(baseTable);
        extraDataReportMapper.insertReportData(extraTable);
        if (customTable.size() > 0) {
            customTable.put(index, uuid);
            customTable.put(attr, new Date((long) message.get(key)));
            Arrays.asList(customTableColumn).stream().forEach(e -> {
                if (customTable.get(e) == null || "".equals(((String) customTable.get(e)).trim()))
                    customTable.put(e, Constants.TABLE_COLUMN_TYPE.get(e));
            });
            customDataReportMapper.insertReportData(customTable);
        }
        logger.info("message insert DB successed");
    }
}
