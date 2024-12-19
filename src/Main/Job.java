package Main;

public class Job {
    private String name; // Denumirea jobului
    private int salary;  // Salariul

    // Constructor
    public Job(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    // Setters (opționale, în funcție de necesitate)
    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Job{name='" + name + "', salary=" + salary + "}";
    }
}
