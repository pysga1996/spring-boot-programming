package com.tutorial.quartz.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author thanhvt
 * @project spring-quartz-demo
 */
@Service
public class SampleJobService {

    private static final Logger logger = LoggerFactory.getLogger(SampleJobService.class);

    public void executeSampleJob() {
        logger.info("execute job!!!");
    }
}
