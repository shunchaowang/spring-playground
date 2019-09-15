package me.swang.springplayground.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concurrent")
public class ConcurrentController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/sync")
    public String getValueSync() {
        logger.info("Request received");
        return processRequest();
    }

    private String processRequest() {
        logger.info("Start processing request");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Completed processing request");

        return "Hello World!";
    }
}
