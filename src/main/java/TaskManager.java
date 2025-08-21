import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> things = new ArrayList<>(100);

    TaskManager() {

    }

    void addTask(Task curTask) {
        things.add(curTask);
        System.out.println("added: " + curTask);
    }

    void listTasks() {
        for (int i = 1; i <= things.size(); i++) {
            Task curTask = things.get(i-1);
            System.out.println(i + ". " + curTask);
        }
    }

    void markDone(int index) {
        Task markedTask = things.get(index-1);
        markedTask.markDone();
        System.out.println("Good job! I have marked this task as done:");
        System.out.println(markedTask);
    }

    void markNotDone(int index) {
        Task unmarkTask = things.get(index-1);
        unmarkTask.markNotDone();
        System.out.println("Okay, I have marked this task as not done:");
        System.out.println(unmarkTask);
    }

    void removeTask(int index) {
        Task removedTask = things.get(index-1);
        things.remove(index-1);
        System.out.println("Okay, I have deleted this task:");
        System.out.println(removedTask);
    }
}
