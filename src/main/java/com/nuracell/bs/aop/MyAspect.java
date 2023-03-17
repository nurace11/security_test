package com.nuracell.bs.aop;

import com.nuracell.bs.entity.Player;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {

    @Around("PointCuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (methodSignature.getName().equals("addPlayer")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof Player) {
                    log.info("Try to add a player: " + arg);
                }
            }
        }

        Object player = joinPoint.proceed();
        log.info("Player has been saved: " + player);
        return player;
    }

    @Around("PointCuts.allFindMethods()")
    public Object afterFindAdvice(ProceedingJoinPoint joinPoint ) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Long id = null;

        String methodName = methodSignature.getName();
        if (methodName.equals("findAll")) {
            log.info("Try to get all players");
        } else if (methodName.equals("findById")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object argId : arguments) {
                if (argId instanceof Long) {
                    id = (Long) argId;
                    log.info("Try to get a player with id: " + id);
                }
            }
        }

        Object result = joinPoint.proceed();

        if (methodName.equals("findAll")) {
            log.info("All players successfully sent");
        } else if (methodName.equals("findById")) {
            log.info("Player with id: {} has been found", id);
        }

        return result;
    }

}
