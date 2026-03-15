import java.lang.reflect.Array;
import java.util.ArrayList;

public class Course {
    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;
    private ArrayList<Student> enrolledStudents;

    public Course(Subject subject) {
        this.subject = subject;
        this.daysToRun = subject.getDuration();
        this.enrolledStudents = new ArrayList<>();
    }

    public Subject getSubject() {
        return this.subject;
    }

    public int getStatus() {
        if (this.daysUntilStarts > 0){
            return this.daysUntilStarts * -1;
        }
        else if (this.daysToRun > 0){
            return this.daysToRun;
        }
        return 0;
    }

    public void aDayPasses() {
        if (this.daysUntilStarts > 0) {
            this.daysUntilStarts--;
        }
        else if (this.daysToRun > 0) {
            this.daysToRun--;
        }
    }

    public boolean enrolStudent(Student student) {
        if (this.daysUntilStarts > 0) {
            return false;
        }
        enrolledStudents.add(student);
        return true;
    }

    public int getSize() {
        return enrolledStudents.size();
    }

    public ArrayList<Student> getStudents() {
        return this.enrolledStudents;
    }
}
