package com.hilmirafiff.airbnb_clone_be.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("(bean(*ServiceImpl) || bean(*Component) || bean(*Controller)) "
            + "&& execution(public * com.hilmirafiff.angkatin_be..*(..)) ")
    public void everyClassExecute() {
        // Do nothing because this is a pointcut
    }

    @Around("everyClassExecute()")
    public Object onClassAround(ProceedingJoinPoint joinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        var clazz = signature.getDeclaringType();
        final long start = System.currentTimeMillis();
        final String prefix = "[" + clazz.getSimpleName() + "#" + signature.getName() + "]";

        log.info("Starting Class {} on Method {}", clazz.getSimpleName(), signature.getName());

        HashMap<String, Object> hashMapRequestInput = new HashMap<>();
        Object[] inputs = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (inputs[i] != null && inputs[i].getClass() == String.class) {
                String input = String.valueOf(inputs[i]).replaceAll(System.lineSeparator(), "~~~");
                hashMapRequestInput.put(parameterNames[i], input);
            } else {
                hashMapRequestInput.put(parameterNames[i], inputs[i]);
            }
        }
        try {
            log.debug("{} Input : {}", prefix, hashMapRequestInput);
        } catch (LazyInitializationException e) {
            log.error("Please include @ToString.Exclude on fields that are lazily initialized");
        } catch (Exception e) {
            printException(prefix, "output", e);
        }

        Object retVal;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable throwable) {
            printException(prefix, "method", throwable);
            throw throwable;
        }

        try {
            Object output;
            if (retVal == null) {
                output = "";
            } else if (retVal.getClass() == String.class) {
                output = String.valueOf(retVal).replace("\n", "~~~");
            } else {
                output = retVal;
            }
            log.debug("{} Output : {}", prefix, output);
        } catch (Exception e) {
            printException(prefix, "output", e);
        }
        log.info("Finishing Class {} on Method {} in {} millis.", clazz.getSimpleName(), signature.getName(), System.currentTimeMillis() - start);
        return retVal;
    }

    public static void printException(String prefix, String place, Throwable e) {
        log.error(prefix + " Exception on " + place + " : " + e.getCause());
        log.error(prefix + " Exception message : " + e.getMessage());

        int c = 0;
        log.error("Stack trace length : " + e.getStackTrace().length);
        for (int i = 0; i < e.getStackTrace().length; i++) {
            log.error(prefix + " Exception location : " + e.getStackTrace()[i]);
            if (e.getStackTrace()[i].getClassName().contains("com.hilmirafiff")) {
                c = c + 1;
                if (c == 2) {
                    break;
                }
            }
        }
        log.error(prefix + " End print exception");
    }
}
