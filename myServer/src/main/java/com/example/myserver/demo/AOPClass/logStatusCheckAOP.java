package com.example.myserver.demo.AOPClass;

// import com.example.myserver.demo.model.User;
// import com.example.myserver.demo.staticClass.PARAMS_KEY;
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;

/**
* 切面
* */

/*@Aspect
@Configuration*/
public class logStatusCheckAOP {
    // @Pointcut("execution(* com.example.myserver.demo.controller.privateImportant..*.*(..))")
    // public void privateCutPoint() {
    // }

    // @Before("privateCutPoint()")
    // public String checkLogStatus(JoinPoint joinPoint) {
    //     HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    //     HttpSession session = request.getSession();
    //     User user = (User) session.getAttribute(PARAMS_KEY.USER_INFO);
    //     if (!user.isEmpty()) {
    //         return "";
    //     }
    //     return "";
    // }
}
