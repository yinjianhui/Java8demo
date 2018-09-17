package huisirdemo.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 
 **********************************************************
 * @日期: 2018年9月17日
 * @描述: 利用流进行排序
  **********************************************************
 */
public class StreamTest {
    public static void main(String[] args) {
        String val = "2,4,5,8,4,8,9";
        List<String> valList = Arrays.asList(val.split(","));
        String value = valList.stream().sorted().collect(Collectors.joining(","));
        System.out.println(value);
    }
}
