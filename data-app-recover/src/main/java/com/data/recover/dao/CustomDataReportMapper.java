package com.data.recover.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 17-7-10.
 */
public interface CustomDataReportMapper {
    void insertCustomReportData(List<Map<String,Object>> list);
}
