import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import config.jpaconfig;
import config.webconfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import java.util.stream.Stream;

@ContextConfiguration(classes = {jpaconfig.class,webconfig.class})
@DataJpaTest
public class baseTest {
    @BeforeAll
    public static void BeforeAll() {

    }

    @BeforeEach
    public void BeforeEach() {

    }

    @AfterAll
    public static void AfterAll() {

    }

    @AfterEach
    public void AfterEach() {

    }

    public static Stream<Arguments> gen() {
        return Stream.of(
                Arguments.arguments(1, 1.1f, "param1"),
                Arguments.arguments(2, 2.2f, "param2"),
                Arguments.arguments(3, 3.3f, "param3")
        );
    }

//    @CsvSource({
//            "1,1.1f,param",
//            "2,2.2f,param",
//            "3,3.3f,param3",
//    })
    @DisplayName("代理商信息")
    @ParameterizedTest
    @CsvFileSource(resources = {"test01.csv"})
    public void test01(int a, float b, String c) {
        System.out.printf("%d %f %s\n", a, b, c);
    }

    @DisplayName("代理商信息")
    @ParameterizedTest
    @MethodSource({"gen"})
    public void test02(int a, float b, String c) {
        System.out.printf("%d %f %s\n", a, b, c);
    }
}
