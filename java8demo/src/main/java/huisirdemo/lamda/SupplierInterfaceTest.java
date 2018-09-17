package huisirdemo.lamda;

import java.util.function.Supplier;

public class SupplierInterfaceTest {
    
    public static void testSupplier() {
        //Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
        // lambda 表达式不接收参数，返回整数 'sp'
        Supplier sp = () -> "sp";
        System.out.println(sp.get());
    }
    
    public static void main(String[] args) {
        SupplierInterfaceTest.testSupplier();
    }
}

