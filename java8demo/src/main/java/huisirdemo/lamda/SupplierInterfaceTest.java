package huisirdemo.lamda;

import java.util.function.Supplier;

public class SupplierInterfaceTest {
    
    public static void testSupplier() {
        //Supplier �ӿڷ���һ�����ⷶ�͵�ֵ����Function�ӿڲ�ͬ���Ǹýӿ�û���κβ���
        // lambda ���ʽ�����ղ������������� 'sp'
        Supplier sp = () -> "sp";
        System.out.println(sp.get());
    }
    
    public static void main(String[] args) {
        SupplierInterfaceTest.testSupplier();
    }
}

