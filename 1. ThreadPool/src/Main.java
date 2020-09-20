/**
 * 10.09.2020
 * Threads
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class Main {
    public static void main(String[] args) {
        ThreadPool executorService = ThreadPool.newPool(3);

        Runnable task1 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A");
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " B");
            }
        };

        Runnable task3 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " C");
            }
        };

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);
    }
}
