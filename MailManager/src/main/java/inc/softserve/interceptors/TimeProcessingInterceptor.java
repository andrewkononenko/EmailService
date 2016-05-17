package inc.softserve.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TimeProcessingInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.nanoTime();

        try {
            return invocation.proceed();
        }
        finally {
            System.out.println(
                    String.format(
                            "Invocation of method %s() took %.1f ms.",
                            invocation.getMethod().getName(),
                            (System.nanoTime() - start) / 1000000.0));
        }
    }
}
