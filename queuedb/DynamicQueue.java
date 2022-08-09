package queuedb;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;


/**
 * Dyanmic Type Queue
 * @author aangar, 2022
 */
public class DynamicQueue<T> {
    private Queue<T> queue;

    DynamicQueue(List<T> values) {
        this.queue = new LinkedList<T>(values);
    }

    DynamicQueue(T val) {
        this.queue = new LinkedList<T>(List.of(val));
    }

    public Queue<T> getQueue() {
        return this.queue;
    }
    
    public int getTotalDocs() {
        return queue.size();
    }

    /**
     * Processes the next Queue Item.
     * @return The next item in queue.
     */
    public T process() {
        return queue.poll();
    }

}