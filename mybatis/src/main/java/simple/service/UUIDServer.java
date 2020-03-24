package simple.service;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.NameBasedGenerator;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simple.repository.UUIDMapper;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UUIDServer {
    @Autowired
    private static UUIDMapper simpleUUIDMapper;
    private static final NameBasedGenerator nameBasedGenerator=Generators.nameBasedGenerator();
    private static final RandomBasedGenerator randomBasedGenerator= Generators.randomBasedGenerator();
    private static final TimeBasedGenerator timeBasedGenerator=Generators.timeBasedGenerator();
    private static final long roomCode=31;//0-31
    private static final long machineCode=31;//0-31
    private static final long timeStart= Instant.parse("2020-01-01T00:00:00.00Z").toEpochMilli();
    private static final AtomicLong count=new AtomicLong(0);//0-4095

    public static String gen32StringFromJava(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String gen32StringFromMysql(){
        return simpleUUIDMapper.selectUUID();
    }
    public static int gen32intFromMysql(){
        return simpleUUIDMapper.selectUUID_Short();
    }
    public static long snowflake(){
        return ((Instant.now().toEpochMilli()-timeStart)<<22)|((roomCode&0b11111)<<17)|((machineCode&0b11111)<<12)|(count.getAndIncrement()&0b111111111111);
    }

    public static void main(String[] args) {
        System.out.println(snowflake());
//        System.out.println(nameBasedGenerator.generate("wangheng88"));
//        System.out.println(timeBasedGenerator.generate().toString());
//        System.out.println(randomBasedGenerator.generate().toString());
//
//        System.out.println(gen32intFromMysql());
//        System.out.println(gen32StringFromMysql());
//        System.out.println(gen32StringFromJava());
    }
}
