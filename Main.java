import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class TaskList implements Iterable<Task>{

    private Scanner scan = new Scanner(System.in);
    public ArrayList<Task> taskList = new ArrayList<>();

    public void addTask(){

        int taskPriority = 0;

        System.out.println("What is the task name?");
        String taskTitle = scan.nextLine();

        System.out.println("What is the  description?");
        String taskDesc = scan.nextLine();

        boolean validInput;
        do {
            try {
                System.out.println("What is the task priority(0-5)?");
                taskPriority = scan.nextInt();
                scan.nextLine();
                if (-1<taskPriority && taskPriority<6) {
                    validInput = true;
                }else{
                    validInput = false;
                    System.out.println("Invalid input.");
                }
            } catch (Exception e) {
                scan.nextLine();
                System.out.println("Invalid input.");
                validInput = false;
            }
        }while(!validInput);
        Task task = new Task(taskTitle,taskDesc,taskPriority);
        taskList.add(task);

    }

    public void remTask(){

        int taskID = 0;
        boolean validInput = false;
        do {
            try {
                listTasks();
                System.out.println("What  would like to remove?");
                taskID = Integer.parseInt(scan.nextLine());
                for (Task task : taskList){
                    if (task.getTaskNum() == taskID){
                        taskList.remove(task);
                        validInput = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                validInput = false;
            }
        }while(!validInput);
    }

    public void editTask(){
        int taskID = 0;
        boolean validInput = false;
        do {
            try {
                listTasks();
                System.out.println("What is the task you would like to edit?");
                taskID = Integer.parseInt(scan.nextLine());
                for (Task task : taskList){
                    if (task.getTaskNum() == taskID){

                        int taskPriority = 0;

                        System.out.println("What is the new title?");
                        String taskTitle = scan.nextLine();

                        System.out.println("What is the new description?");
                        String taskDesc = scan.nextLine();

                        boolean validInput2;
                        do {
                            try {
                                System.out.println("What would you like to change the priority to?(0-5)");
                                taskPriority = scan.nextInt();
                                scan.nextLine();
                                if (-1<taskPriority && taskPriority<6) {
                                    validInput2 = true;
                                }else{
                                    validInput2 = false;
                                    System.out.println("Invalid input.");
                                }
                            } catch (Exception e) {
                                scan.nextLine();
                                System.out.println("Invalid input.");
                                validInput2 = false;
                            }
                        }while(!validInput2);
                        task.setTitle(taskTitle);
                        task.setDescription(taskDesc);
                        task.setPriority(taskPriority);
                        validInput = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                validInput = false;
            }
        }while(!validInput);
    }

    public void listTasks(){
        int ID = 1;
        for (int i=5;i>=0;i--){
            for (Task task : taskList){
                if(task.getPriority()==i){
                    task.setTaskNum(ID);
                    System.out.println(task.getTaskNum() +") " + task.getTitle() + " ~ " + task.getPriority() +"\n" + task.getDescription());
                    ID++;
                }
            }
        }

    }
    public void listTasksPriority(){
        int taskPriority = 0;
        boolean validInput;
        do {
            try {
                System.out.println("What priority tasks would you like to see?(0-5)");
                taskPriority = scan.nextInt();
                scan.nextLine();
                if (-1<taskPriority && taskPriority<6) {
                    validInput = true;
                }else{
                    validInput = false;
                    System.out.println("Invalid input.");
                }
            } catch (Exception e) {
                scan.nextLine();
                System.out.println("Invalid input.");
                validInput = false;
            }
        }while(!validInput);
        for (Task task : taskList){
            if(task.getPriority()==taskPriority){
                task.setTaskNum(taskPriority);
                System.out.println(task.getTaskNum() +") " + task.getTitle() + " ~ " + task.getPriority() +"\n" + task.getDescription());
            }
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }
}
class Task implements Comparable<Task>{

    private int taskNum;
    private String title;
    private String description;
    private int priority;

    public Task(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getTaskNum() {
        return taskNum;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Task o) {
        if (priority != (o.priority)) {
            if (priority>o.priority){
                return -1;
            }else{
                return 1;
            }
        }
        else {
            return title.compareTo(o.title);
        }
    }
}
public class Main {

    private static TaskList taskList = new TaskList();
    private static boolean running = true;
    private static Scanner scan = new Scanner(System.in);

    static void serializeSimple(){
        Gson gson = new Gson();

        try {
            FileWriter writer = new FileWriter("data.json");
            gson.toJson(TaskList.class , writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void deserializeSimple(){
        Gson gson = new Gson();
        TaskList person2 = gson.fromJson("data.json", TaskList.class);

        System.out.println(person2.getClass());

    }

    private static void displayMenu(){

        System.out.println("--------------\n" +
                "Choose an option: \n" +
                "(1) Add a Task \n" +
                "(2) Remove a Task \n" +
                "(3) Edit a Task \n" +
                "(4) List Tasks \n" +
                "(5) List Tasks according to priority\n" +
                "(6) Save\n" +
                "(7) Load\n" +
                "(0) Exit");
        String input = scan.nextLine();

        switch(input){
            case "1": taskList.addTask();
                break;
            case "2": taskList.remTask();
                break;
            case "3": taskList.editTask();
                break;
            case "4":
                Collections.sort(taskList.taskList);
                for(Task task: taskList) {
                    System.out.println(task.getTaskNum() +") " + task.getTitle() + " ~ " + task.getPriority() +"\n" + task.getDescription());
                }
                break;
            case "5": taskList.listTasksPriority();
                break;

            case "6":
                serializeSimple();
                break;
            case "7":
                deserializeSimple();
                break;
            case "0": running=false;
                break;
            default:
                System.out.println("Input not valid");
        }
    }
    public static void main(String[] args) {

        while(running){
            displayMenu();
        }
    }
}
