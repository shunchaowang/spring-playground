package me.swang.springplayground.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

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
    // anonymous inner class
    //        Callable<String> callable = new Callable<String>() {
    //            @Override
    //            public String call() throws Exception {
    //                return processRequest();
    //            }
    //        };

    // lambda expression to replace anonymous inner class
    // Callable<String> callable = () -> processRequest();
    // method reference to replace lambda expression
    Callable<String> callable = this::processRequest;
    logger.info("Servlet thread released");
    return callable;
  }

  @GetMapping("/deferred")
  public DeferredResult<String> helloWorldDeferred() {
    logger.info("Request received");
    DeferredResult<String> result = new DeferredResult<>();
    ForkJoinPool.commonPool()
        .submit(
            () -> {
              result.setResult(processRequest());
            });
    logger.info("Servlet thread released");
    return result;
  }

  @GetMapping("/completable-future")
  public CompletableFuture<String> helloWorldCompletableFuture() {
    logger.info("Request received");
    CompletableFuture<String> future = CompletableFuture.supplyAsync(this::processRequest);
    logger.info("Servlet thread released");
    return future;
  }

  /**
   * Completable future is a preferred approach to handle deferred result. Still wants to make
   * controller return a DeferredResult to stick with Spring MVC pattern.
   *
   * @return
   */
  @GetMapping("/deferred-future")
  public DeferredResult<String> helloWorldDeferredFuture() {
    DeferredResult<String> deferredResult = new DeferredResult<>();
    logger.debug("Request received");
    CompletableFuture.supplyAsync(this::processRequest)
        .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
    logger.debug("Servlet thread released");
    return deferredResult;
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
