package com.data.recover.service;

import com.data.recover.util.Constants;
import com.data.recover.util.CustomUUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 17-6-29.
 */
public class DataHandle {

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

    public void resolveTable(Map<String, Object> message, List<Map<String, Object>> appBaseModels, List<Map<String, Object>> appExtraModels, List<Map<String, Object>> appCustomModels) {
        if (message == null || message.isEmpty() || message.get(key) == null)
            return;

        DefaultFormattingConversionService cs = new DefaultFormattingConversionService(true);
        message.put(key, cs.convert(message.get(key), Long.class));

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

        appBaseModels.add(baseTable);
        appExtraModels.add(extraTable);
        if (customTable.size() > 0) {
            Arrays.asList(customTableColumn).stream().forEach(e -> {
                if (customTable.get(e) == null || "".equals(((String) customTable.get(e)).trim()))
                    customTable.put(e, Constants.TABLE_COLUMN_TYPE.get(e));
            });
            appCustomModels.add(customTable);
        }
    }
}
