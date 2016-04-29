package dictionarybuilding.web;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ControllerWrapper {

    @Around("execution(* dictionarybuilding.controller.*.*(..))")
    public Response wrapResponse(ProceedingJoinPoint joinPoint) {
        ResponseModel response = new ResponseModel();
        try {
            response.setSuccess(true);
            response.setData((Response) joinPoint.proceed());
        } catch (Throwable throwable) {
            response.setSuccess(false);
            response.setErrorMsg(throwable.getMessage());
            throwable.printStackTrace();
        }
        return response;
    }
}
