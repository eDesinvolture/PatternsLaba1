package university.model.entities;

import java.util.List;
public class EducationalProject {
    public String name;
    public List<Task> tasks;
    public EducationalProject(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public EducationalProject addTask(Task task) {
        this.tasks.add(task);
        return this;
    }
}