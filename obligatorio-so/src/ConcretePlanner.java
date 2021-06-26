import Interfaces.IPlanner;
import Interfaces.IVaccinationRequest;
import Utils.Priority;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class ConcretePlanner<T extends IVaccinationRequest> implements IPlanner<T> {
    protected final Map<String, ConcreteMLQ<T>> queues;
    protected final Map<String, Semaphore> semaphores;

    public ConcretePlanner() {
        this.queues = new HashMap<>();
        this.semaphores = new HashMap<>();
    }

    public Semaphore getSemaphore(String queueKey) {
        return semaphores.get(queueKey);
    }

    public void addQueue(String queueType) {
        ConcreteMLQ<T> queue = new ConcreteMLQ<>(queueType);
        queues.put(queueType, queue);
    }

    public boolean addElement(T element) {
        ConcreteMLQ<T> queue = queues.get(element.getVaccine().name());
        if (Objects.isNull(queue))
            return false;
        return queue.addElement(element);
    }

    public T getElement(String queueType) {
        ConcreteMLQ<T> queue = queues.get(queueType);
        if (Objects.isNull(queue))
            return null;
        return queue.getHighestPriorityElement();
    }

    private class ConcreteMLQ<T extends IVaccinationRequest> {
        private final String queueKey;
        private final Map<Priority, TreeMap<String, T>> mlq;

        ConcreteMLQ(String queueType) {
            this.queueKey = queueType;
            this.mlq = new HashMap<>();
            for (Priority priority : Priority.values()) {
                TreeMap<String, T> priorityQueue = new TreeMap<>();
                this.mlq.put(priority, priorityQueue);
            }
        }

        private T getHighestPriorityElement() {
            for (Priority priority : Priority.values()) {
                TreeMap<String, T> priorityQueue = mlq.get(priority);
                if (!priorityQueue.isEmpty()) {
                    return priorityQueue.pollLastEntry().getValue();
                }
            }
            return null;
        }

        private boolean addElement(T element) {
            TreeMap<String, T> priorityQueue = mlq.get(element.getPriority());
            if (Objects.isNull(priorityQueue))
                return false;
            priorityQueue.put(element.getKey(), element);
            return true;
        }
    }
}