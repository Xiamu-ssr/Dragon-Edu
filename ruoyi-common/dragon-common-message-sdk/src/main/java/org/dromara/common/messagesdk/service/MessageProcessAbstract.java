package org.dromara.common.messagesdk.service;

import org.dromara.common.messagesdk.domain.MqMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.log.OmsLogger;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author mumu
 * @version 1.0
 * @description 消息处理抽象类
 * @date 2022/9/21 19:44
 */
@Data
public abstract class MessageProcessAbstract {

    @Autowired
    MqMessageService mqMessageService;

    protected OmsLogger omsLogger;


    /**
     * @param mqMessage 执行任务内容
     * @return boolean true:处理成功，false处理失败
     * @description 任务处理
     * @author mumu
     * @date 2022/9/21 19:47
     */
    public abstract boolean execute(MqMessage mqMessage);


    /**
     * @description 扫描消息表多线程执行任务
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param messageType  消息类型
     * @param count  一次取出任务总数
     * @param timeout 预估任务执行时间,到此时间如果任务还没有结束则强制结束 单位秒
     * @return void
     * @author mumu
     * @date 2022/9/21 20:35
    */
    public void process(TaskContext taskContext, int shardIndex, int shardTotal, String messageType, int count, long timeout) {
        omsLogger = taskContext.getOmsLogger();
        if (omsLogger == null){
            return;
        }
        String currentShardLogPre = "[" + shardIndex + " - " + shardTotal + "] ";
        try {
            //扫描消息表获取任务清单
            List<MqMessage> messageList = mqMessageService.getMessageList(shardIndex, shardTotal,messageType, count);
            //任务个数
            int size = messageList.size();
            int threadPoolSize = Math.min(size, 4);
            omsLogger.info(currentShardLogPre+"取出待处理消息"+size+"条");
            if(size == 0){
                return ;
            }

            //创建线程池
            ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);
            //计数器
            CountDownLatch countDownLatch = new CountDownLatch(size);
            messageList.forEach(message -> {
                threadPool.execute(() -> {
                    omsLogger.debug(currentShardLogPre+"开始任务:{}",message);
                    //处理任务
                    try {
                        boolean result = execute(message);
                        if(result){
                            omsLogger.debug(currentShardLogPre+"任务执行成功:{})",message);
                            //更新任务状态,删除消息表记录,添加到历史表
                            int completed = mqMessageService.completed(message.getId());
                            if (completed>0){
                                omsLogger.debug(currentShardLogPre+"任务执行成功:{}",message);
                            }else{
                                omsLogger.debug(currentShardLogPre+"任务执行失败:{}",message);
                            }
                        }else {
                            omsLogger.warn(currentShardLogPre+"任务执行失败:{}",message);
                        }
                    } catch (Exception e) {
                        omsLogger.error(currentShardLogPre+"任务出现异常:{},任务:{}",e.getMessage(), message);
                    }finally {
                        //计数
                        countDownLatch.countDown();
                    }
                    omsLogger.debug(currentShardLogPre+"结束任务:{}",message);

                });
            });

            //等待,给一个充裕的超时时间,防止无限等待，到达超时时间还没有处理完成则结束任务
            countDownLatch.await(timeout,TimeUnit.SECONDS);
            omsLogger.info("over...   :)");
        } catch (InterruptedException e) {
            omsLogger.error(currentShardLogPre+"[!!!]未知异常:{}",e.getMessage());
        }
    }
}
