package me.swang.springplayground.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/concurrent")
public class ConcurrentController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/sync")
    public String helloWorldSync() {
        logger.info("Request received");
        String sync = processRequest();
        logger.info("Servlet thread released");
        return sync;
    }

    @GetMapping("/callable")
    public Callable<String> helloWorldCallable() {
        logger.info("Request received");
//        Callable<String> callable = this::processRequest;
//        Callable<String> callable = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return processRequest();
//            }
//        };
        Callable<String> callable = () -> processRequest(); // lambda
        logger.info("Servlet thread released");
        return callable;
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
