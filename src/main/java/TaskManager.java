import java.util.ArrayList;

public class TaskManager {
    static ArrayList<Task> things = new ArrayList<>(100);

    TaskManager() {

    }

    void addTask(Task curTask) {
        things.add(curTask);
        System.out.println("added: " + curTask);
    }

    void listTasks() {
        for (int i = 1; i <= things.size(); i++) {
            System.out.println(i + ". " + things.get(i - 1));
        }
    }
}
