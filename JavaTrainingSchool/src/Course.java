import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Course implements Serializable {
    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;
    private ArrayList<Student> enrolledStudents;
    private ArrayList<Instructor> instructors;
    private boolean isCancelled;

    public Course(Subject subject, int daysUntilStarts) {
        this.subject = subject;
        this.daysUntilStarts = daysUntilStarts;
        this.daysToRun = subject.getDuration();
        this.enrolledStudents = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.isCancelled = false;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public int getStatus() {
        if (this.daysUntilStarts > 0) {
            return this.daysUntilStarts * -1;
        }
        else if (this.daysToRun > 0) {
            return this.daysToRun;
        }
        return 0;
    }

    public void aDayPasses() {
        if (this.isCancelled()){
            return;
        }
        if (this.daysUntilStarts > 0) {
            this.daysUntilStarts--;
        }
        else if (this.daysToRun > 0) {
            if (!this.hasInstructor() || this.getStudents().isEmpty()){
                this.isCancelled = true;
                return;
            }

            this.daysToRun--;
        }
        else if (this.daysToRun == 0) {
            for (Student student : this.enrolledStudents) {
                student.graduate(this.subject);
            }
            for (Instructor instructor : this.instructors) {
                instructor.unassignCourse();
            }
            this.isCancelled = true;
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

    public boolean setInstructor(Instructor instructor) {
        if (instructor.canTeach(this.subject)) {
            instructor.assignCourse(this);
            this.instructors.add(instructor);
            return true;
        }
        return false;
    }

    public boolean hasInstructor() {
        return !this.instructors.isEmpty();
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }
}
