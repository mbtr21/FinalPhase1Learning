package services;
import data.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadManager {
    private final Queue<Request> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private LinkedList<Request> userRequests = new LinkedList<>();
    private UserThread userThread;
    private ItemThreadManager itemThreadManager;
    private Library library;
    private final ExecutorService executorService;

    public ThreadManager() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public void setUserRequests(LinkedList<Request> userRequests) {
        this.userRequests = userRequests;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void createUserThread() {
        this.userThread = new UserThread(this.userRequests, (LinkedList<Request>) this.queue);
    }

    public void createItemThread() {
        this.itemThreadManager = new ItemThreadManager(this.library, (LinkedList<Request>) this.queue);
    }

    public void run() {
        createUserThread();
        createItemThread();

        executorService.submit(() -> {
            while (!userRequests.isEmpty()) {
                lock.lock();
                try {
                    userThread.run();
                } finally {
                    lock.unlock();
                }
            }
        });

        executorService.submit(() -> {
            while (!userRequests.isEmpty() || !queue.isEmpty()) {
                lock.lock();
                try {
                    itemThreadManager.run();
                } finally {
                    lock.unlock();
                }
            }
        });

        executorService.shutdown();
    }


}
