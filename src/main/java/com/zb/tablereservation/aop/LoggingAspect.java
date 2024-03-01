package com.zb.tablereservation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("within(com.zb.tablereservation.user.controller..*)")
    private void isUserController(){}

    @Pointcut("within(com.zb.tablereservation.store.controller..*)")
    private void isStoreController(){}

    @Pointcut("within(com.zb.tablereservation.reservation.controller..*)")
    private void isReservationController(){}

    @Pointcut("within(com.zb.tablereservation.review.controller..*)")
    private void isReviewController(){}

    @Around("isUserController() " +
            "|| isStoreController()" +
            "|| isReservationController()" +
            "|| isReviewController()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Around before: " + "${joinPoint.signature.name} ${joinPoint.args[0]}");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        logger.info("Around after: " + "${joinPoint.signature.name} ${joinPoint.args[0]} ${stopWatch.lastTaskTimeMillis}");
        return result;
    }
}
