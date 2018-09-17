package huisirdemo.lamda;

import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionInterfaceTest {
    
    public static void testFun() {
        //Function 接口有一个参数并且返回一个结果
        Function<String, Integer> toInteger = (t) -> Integer.valueOf(t);
        System.out.println("compose: " + toInteger.andThen(a -> a + 10).compose(str -> str + "1").apply("123"));
        
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        Function<String, Integer> f = toInteger.compose(backToString);
        
        int str = f.apply("123");
        System.out.println(str);
    }
    
    public static void main(String[] args) {
        //FunctionInterfaceTest.testFun();
        FunctionInterfaceTest.testPredicate();
    }
    
    public static void testPredicate() {
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        isEmpty.and(str -> str.equals("test"));
        System.out.println("test: " + isEmpty.and(str -> str.equals("test")).test("test"));
    }
}
