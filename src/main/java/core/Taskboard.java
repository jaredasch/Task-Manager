package main.java.core;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by jaredasch on 5/22/18.
 */
public class Taskboard implements Serializable {
    private String name;
    private String description;

    private TreeSet<Task> tasks;

    public Taskboard(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = new TreeSet<>();
    }

    public boolean add(Task t) {
        return tasks.add(t);
    }

    public void clearCompleted() {
        Iterator<Task> iter = tasks.iterator();
        tasks.removeIf(task -> task.completed);
    }

    public boolean save() {
        try {
            File dataDir = new File("./src/main/data/taskboards/");
            if (!dataDir.exists())
                dataDir.mkdir();
            FileOutputStream file = new FileOutputStream("./src/main/data/taskboards/" + this.hashCode() + ".tb");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            file.close();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    public static Task load(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("./src/main/data/taskboards/" + filename);
        ObjectInputStream in = new ObjectInputStream(file);
        Task task = (Task) in.readObject();
        in.close();
        file.close();
        return task;
    }

    public String toString(){
        String result = "";
        for(Task t: tasks)  result += t.GUIString() + System.lineSeparator();
        return result;
    }
}
