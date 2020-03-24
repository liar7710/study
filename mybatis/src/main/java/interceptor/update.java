package interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler (handleResultSets, handleOutputParameters)
 * StatementHandler (prepare, parameterize, batch, update, query)
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class})
})
public class update implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Interceptor Start!");
        System.out.println("InputArgs:"+ JSON.toJSONString(invocation.getArgs()));
        System.out.println("MethodName:"+invocation.getMethod().getName());
        Object ret = invocation.proceed();
        System.out.println("ReturnValue:"+JSON.toJSONString(ret));
        System.out.println("Interceptor End!");
        return ret;
    }
}
