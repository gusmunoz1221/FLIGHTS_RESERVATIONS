package com.Travel.Travel.aspects;

import com.Travel.Travel.util.BestTravelUtil;
import com.Travel.Travel.util.annotation.Notify;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Aspect
public class NotifyAspect {
    private static final String LINE_FORMAT = "At %s new %s with size page %s and order %s";
    private static final String PATH = "files/notify.txt";

  //  @Before()-->intersectado por un endpoint antes  @Around()-->intersectado por un endpoint durante
    @After("@annotation(com.Travel.Travel.util.annotation.Notify)")//intersectado por un endpoint despues
    public void notifyInFile(JoinPoint joinPoint) throws IOException {
        var args = joinPoint.getArgs();
        var size = args[1];
        var order = args[2]==null?"NONE":args[2];
        var signature = (MethodSignature)joinPoint.getSignature();
        var method = signature.getMethod();
        var annotation = method.getAnnotation(Notify.class);
        var text= String.format(LINE_FORMAT, LocalDateTime.now(),annotation.value(),size,order);
        BestTravelUtil.writeNotification(text,PATH);
    }
}
