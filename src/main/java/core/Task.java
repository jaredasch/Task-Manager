package main.java.core;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.UnknownFormatConversionException;

/**
 * Created by jaredasch on 5/22/18.
 */
public class Task {
    private String name;
    private String description;

    private LocalDate deadline;
    private boolean completed;

    public Task(String name, String description, LocalDate deadline){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.completed = false;
    }

    /**
     *
     *  @return the time left to complete a task
     */

    public String stringStatus(){
        LocalDate now = LocalDate.now();
        if(now.isBefore(deadline))
            return toReadableDuration(now.until(deadline)) + " until the deadline";
        else if(now.isAfter(deadline))
            return toReadableDuration(deadline.until(now)) + " past the deadline";
        else
            return "Due today";
    }

    /**
     * Converts a Period instance into a readable duration
     *
     * @param p the Period to be converted
     * @return  the String representing the period
     */

    private static String toReadableDuration(Period p) {
        for (TemporalUnit tu : p.getUnits()){
            if (p.get(tu) > 0){
                String unit = tu.toString().toLowerCase();
                if(p.get(tu) == 1l)        // Make unit singular if only one
                    return p.get(tu) + " " + unit.substring(0, unit.length()-1);
                return p.get(tu) + " " + unit;
            }
        }
        throw new UnknownFormatConversionException("Cannot convert period to String");
    }



    /**
     *
     * @return a boolean representing whether or not a task is overdue
     */

    public boolean isOverdue(){
        LocalDate now = LocalDate.now();
        return now.isAfter(deadline);
    }

    public String toString(){
        return name + " : " + description + " : Due " + deadline + " : Completed - " + completed;
    }

    public String GUIString(){
        return (completed ? "[x] " : "[ ] ") + name + " : " + description + " : Due " + deadline;   // Checkbox checked if task has been completed
    }

    public static void main(String[] args){
        LocalDate deadline = LocalDate.parse("2018-05-24");
        LocalDate now = LocalDate.now();

        Task t = new Task("Study", "Study for APUSH exam", deadline);
        System.out.println(t.GUIString());
    }
}
