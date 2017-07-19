package com.app.service.controller;

import com.alibaba.fastjson.JSON;
import com.app.service.common.AsyncTaskService;
import com.app.service.producer.RedisInfoPublisher;
import com.app.service.util.CharHandle;
import com.app.service.util.Constants;
import com.app.service.util.ResultModel;
import com.app.service.util.SecureUtil;
import com.app.service.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by victor on 2017/10/7.
 */
@Controller
@RequestMapping("/app")
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${data.resolve.key}")
    private String resolveKey;

    @Autowired
    private RedisInfoPublisher redisInfoPublisher;

    @ResponseBody
    @RequestMapping(value = "/data/upload", method = RequestMethod.POST)
    public ResultModel analysisUpload(@RequestParam String message) {
        logger.info("start handle request");
        ResultModel resultModel = new ResultModel();
        if (message == null || "".equals(message.trim())) {
            logger.info("参数不正确");
            resultModel.setCode(StatusCode.EXCEPTION.getCode());
            resultModel.setDescribe(StatusCode.EXCEPTION.getDesc());
            return resultModel;
        }
        try {
            String content = SecureUtil.decrypt(message);
            Map<String, Object> data = (Map<String, Object>) JSON.parse(content);

            if (data == null || data.size() < 1 || data.get("t") == null) {
                logger.info("参数不正确");
                resultModel.setCode(StatusCode.EXCEPTION.getCode());
                resultModel.setDescribe(StatusCode.EXCEPTION.getDesc());
                return resultModel;
            }
            resolveDate(data);
            resultModel.setCode(StatusCode.SUCCESS.getCode());
            resultModel.setDescribe(StatusCode.SUCCESS.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("request handle exception");
            resultModel.setCode(StatusCode.ERROR.getCode());
            resultModel.setDescribe(StatusCode.ERROR.getDesc());
            return resultModel;
        }
        logger.info("request successed");
        return resultModel;
    }

    public void resolveDate(Map<String, Object> data) {
        if (data.get(resolveKey) != null && !"".equals(((String) data.get(resolveKey)).trim())) {
            String event = (String) data.get(resolveKey);
            String[] paras = null;
            for (String s : event.split("&")) {
                paras = s.split("=");
                if (paras[0] == null || "".equals(paras[0].trim()))
                    continue;
                if ("cd".equals(paras[0]) && !"".equals(paras[1].trim())) {
                    String[] ds = paras[1].split(",");
                    for (int i = 0; i < ds.length; i++) {
                        data.put("cd" + String.valueOf(i + 1), CharHandle.filterSpecChar(ds[i]));
                    }
                    continue;
                }
                if ("cm".equals(paras[0]) && !"".equals(paras[1].trim())) {
                    String[] dm = paras[1].split(",");
                    for (int i = 0; i < dm.length; i++) {
                        data.put("cm" + String.valueOf(i + 1), CharHandle.filterSpecChar(dm[i]));
                    }
                    continue;
                }
                data.put(paras[0], CharHandle.filterSpecChar(paras[1]));
            }
        }
        data.remove(resolveKey);

        Constants.TABLE_COLUMN_TYPE.keySet().forEach(k -> {
            if (data.get(k) == null)
                data.put(k, Constants.TABLE_COLUMN_TYPE.get(k));
        });

        String message = JSON.toJSONString(data);
        redisInfoPublisher.publish(message);
        AsyncTaskService.queue.produce(message);
    }
}
