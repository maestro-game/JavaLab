import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 10.09.2020
 * Threads
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class ThreadPool {
    private Deque<Runnable> tasks;

    private PoolWorker[] pool;

    public static ThreadPool newPool(int threadsCount) {
        ThreadPool threadPool = new ThreadPool(threadsCount);

        for (int i = 0; i < threadPool.pool.length; i++) {
            threadPool.pool[i].start();
        }

        return threadPool;
    }

    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.push(task);
            tasks.notify();
        }
    }

    private class PoolWorker extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new IllegalStateException();
                        }
                    }
                    task = tasks.pollLast();
                }
                task.run();
            }
        }
    }

    private ThreadPool() {
        new ThreadPool(1);
    }

    private ThreadPool(int threadsCount) {
        tasks = new ConcurrentLinkedDeque<>();
        pool = new PoolWorker[threadsCount];
        while (threadsCount > 0) {
            pool[--threadsCount] = new PoolWorker();
        }
    }
}
