package huisirdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * 
 **********************************************************
 * @作者: huisir
 * @日期: 2018年8月11日
 * @描述: 测试 ParallelStream ， 测试CompletableFutureDefaultExecutor， 测试CompletableFutureCustomExecutor
 **********************************************************
 */

public class Main {
	 
    private static int PROCESSORS = Runtime.getRuntime().availableProcessors();
 
//    public static void main(String[] args) {
//        System.out.println("Processors: " + PROCESSORS);
// 
//        Arrays.asList(/*-3, -1, 0, 1, 2, 4, 5, 10, 16, 17*/ 0).forEach(offset -> {
//            int jobNum = PROCESSORS + offset;
//            System.out.println(
//                format("When %s tasks => stream: %s, parallelStream: %s, future default: %s, future custom: %s",
//                    jobNum, testStream(jobNum), testParallelStream(jobNum),
//                    testCompletableFutureDefaultExecutor(jobNum), testCompletableFutureCustomExecutor(jobNum)));
//        });
//    	
// 
//    }
 


	private static long testStream(int jobCount) {
        List<Supplier<Integer>> tasks = new ArrayList<>();
        
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(Main::getJob));
 
        long start = System.currentTimeMillis();
        int sum = tasks.stream().map(Supplier::get).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }
 
    private static long testParallelStream(int jobCount) {
        List<Supplier<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(Main::getJob));
 
        long start = System.currentTimeMillis();
        int sum = tasks.parallelStream().map(Supplier::get).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }
    

    private static long testCompletableFutureDefaultExecutor(int jobCount) {
        List<CompletableFuture<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(CompletableFuture.supplyAsync(Main::getJob)));
 
        long start = System.currentTimeMillis();
        int sum = tasks.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }
    
    /**
     * for循环的另一种写法。
     * 给CompletableFuture 传一个线程池。
     * @param jobCount
     * @return
     */
    private static long testCompletableFutureCustomExecutor(int jobCount) {
        Executor executor = new ForkJoinPool(10);
 
        List<CompletableFuture<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(CompletableFuture.supplyAsync(Main::getJob, executor)));
 
        long start = System.currentTimeMillis();
        int sum = tasks.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }
    
    /**
     * 上面的方法的另一种写法，更容易理解
     * 用十个异步线程去做事情。
     * @param jobCount
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static long testCompletableFutureCustomExecutor2(int jobCount) throws InterruptedException, ExecutionException {
        Executor executor = new ForkJoinPool(10);
 
        long start = System.currentTimeMillis();
        ArrayList<CompletableFuture<Integer>> list = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
        	CompletableFuture<Integer> res = CompletableFuture.supplyAsync(Main::getJob, executor);
        	list.add(res);
		}
        int sum = list.stream().map(CompletableFuture :: join).mapToInt(Integer :: intValue).sum();
        System.out.println(sum);
        return System.currentTimeMillis() - start;
    }
 
    private static int getJob() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
 
        return 50;
    }
 
    private static void checkSum(int sum, int jobNum) {
        if(sum != 50 * jobNum) {
            throw new AssertionError("Result Error");
        }
    }
    
    public static void main(String[] args) {
		try {
			System.out.println(testCompletableFutureCustomExecutor2(10));
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

