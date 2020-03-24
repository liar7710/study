package interceptor;


import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;

/**
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler (handleResultSets, handleOutputParameters)
 * StatementHandler (prepare, parameterize, batch, update, query)
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,ResultHandler.class, CacheKey.class,BoundSql.class}),
        @Signature(type = Executor.class, method = "queryCursor", args = {MappedStatement.class, Object.class, RowBounds.class})
})
@Log4j2
public class query implements Interceptor {
    private static Field sql;
    static {
        init();
    }
    private static boolean init(){
        try {
            sql = BoundSql.class.getField("sql");
            sql.setAccessible(true);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms=(MappedStatement) invocation.getArgs()[0];
        Object parameterObject=invocation.getArgs()[1];
        BoundSql boundSql=ms.getBoundSql(parameterObject);
        String sql=boundSql.getSql();
        String newsql=alterSql(sql);
        log.info("oldSql:{} newSql{}",sql,newsql);
        query.sql.set(boundSql,newsql);
        return invocation.proceed();
    }
    private String alterSql(String sql){
        return sql;
    }
}
