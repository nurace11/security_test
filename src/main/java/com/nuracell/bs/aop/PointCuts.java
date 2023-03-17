package com.nuracell.bs.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {
    // "execution(access type, return type, package.className.methodName(arguments))"
    @Pointcut("execution(* com.nuracell.bs.service.PlayerService.find*(..))")
    public void allFindMethods() {}

    @Pointcut("execution(* com.nuracell.bs.service.PlayerService.add*(..))")
    public void allAddMethods() {}
}
