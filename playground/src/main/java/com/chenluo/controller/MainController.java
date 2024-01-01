package com.chenluo.controller;

import com.chenluo.annotation.MyAnnotation;
import com.chenluo.service.DbService;
import com.chenluo.service.ZKConfiguration;
import com.chenluo.service.ZKManager;
import com.chenluo.service.ZKManagerImpl;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/main/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final AtomicLong atomicLong = new AtomicLong(1);
    private final DbService dbService;
    private ZKConfiguration zkConfiguration;
    private ZKManager zkManager;
    private volatile long volatileLong = 1;

    private Long longCounter = 1L;

    @Autowired
    public MainController(DbService dbService) {
        //        this.zkConfiguration = zkConfiguration;
        //        this.zkManager = zkManager;
        this.dbService = dbService;
    }

    @GetMapping("test")
    @ResponseBody
    public boolean test() {
        System.out.println(volatileLong);
        volatileLong = 2;
        return true;
    }

    @GetMapping("testZK")
    @ResponseBody
    public String testZK() {
        ZKManager zkManager = getZKManager();
        String lock = zkManager.lock("/_lock");
        if (null == lock) {
            return "failed.";
        }
        logger.info("get lock: {}", lock);
        long currentCounter = atomicLong.get();
        try {
            logger.info("processing");
//            boolean zNodeData = zkManager.isExist("/newNode");
//            if (!zNodeData) {
//                zkManager.create("/newNode", String.valueOf(currentCounter).getBytes(StandardCharsets.UTF_8));
//            } else {
//                zkManager.updateCAS("/newNode", String.valueOf(currentCounter).getBytes(StandardCharsets.UTF_8));
//            }
            atomicLong.incrementAndGet();
            return String.valueOf(atomicLong.get());
//        } catch (KeeperException | InterruptedException e) {
//            logger.error("exception: {}", currentCounter, e);
//            return "";
//        }
        } finally {
            logger.info("unlocking: {}", lock);
            zkManager.unlock(lock);
            zkManager.closeConnection();
        }
    }

    @GetMapping("getZK")
    @ResponseBody
    public String getZK(@RequestParam String path) {
        try {
            String zNodeData = (String) getZKManager().getZNodeData(path, false);
            return zNodeData;
        } catch (KeeperException | InterruptedException e) {
            logger.error("failed to get.", e);
            return "";
        }
    }

    private ZKManager getZKManager() {
        if (this.zkManager != null) {
            return this.zkManager;
        }
        ZKManager zkManager = null;
        try {
            zkManager = new ZKManagerImpl(zkConfiguration);
        } catch (IOException e) {
            logger.error("failed to connect zk", e);
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (KeeperException e) {
            e.printStackTrace();
            return null;
        }
        return zkManager;
    }

    @MyAnnotation
    @GetMapping("testMyAspect")
    public void testMyAspect() {

    }

    @GetMapping("testDB")
    public boolean testDB(@RequestParam(required = true) int loopCount) {
        try {
            dbService.tran(loopCount);
        } catch (Exception e) {
            logger.error("error", e);
            return false;
        }
        return true;
    }

    @GetMapping("tryRollback")
    public boolean tryRollback(@RequestParam() int flag) {
        dbService.execTransaction(flag);
        return true;
    }
}
