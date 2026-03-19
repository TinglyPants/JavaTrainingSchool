import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class School implements Serializable {
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private ArrayList<Subject> subjects;
    private ArrayList<Course> courses;

    public School(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public void add(Student student) {
        this.students.add(student);
    }

    public void remove(Student student) {
        this.students.remove(student);
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void add(Instructor instructor) {
        this.instructors.add(instructor);
    }

    public void remove(Instructor instructor) {
        this.instructors.remove(instructor);
    }

    public ArrayList<Instructor> getInstructors() {
        return this.instructors;
    }

    public void add(Subject subject) {
        this.subjects.add(subject);
    }

    public void remove(Subject subject) {
        this.subjects.remove(subject);
    }

    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }

    public void add(Course course) {
        this.courses.add(course);
    }

    public void remove(Course course) {
        this.courses.remove(course);
    }

    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("School { name = %s }".formatted(name))
                .append('\n');

        // Students
        for (Student student : this.getStudents()) {
            sb.append("  - ")
                    .append("Student { name = %s, gender = %c, age = %d }".formatted(
                            student.getName(),
                            student.getGender(),
                            student.getAge())
                    ).append('\n');
        }

        // Instructors
        for (Instructor instructor : this.getInstructors()) {
            sb.append("  - ")
                    .append("Instructor { name = %s, gender = %c, age = %d".formatted(
                            instructor.getName(),
                            instructor.getGender(),
                            instructor.getAge())
                    );

            // Show instructor's courses if applicable
            if (instructor.getAssignedCourse() != null) {
                sb.append(", course subject ID = %d }".formatted(instructor.getAssignedCourse().getSubject().getID()));
            } else {
                sb.append(" }");
            }

            sb.append('\n');
        }

        // Subjects
        for (Subject subject : this.getSubjects()) {
            sb.append("  - ")
                    .append("Subject { id = %d, specialism = %d, duration = %d, description = %s }".formatted(
                            subject.getID(),
                            subject.getSpecialism(),
                            subject.getDuration(),
                            subject.getDescription())
                    ).append('\n');
        }

        // Courses
        for (Course course : this.getCourses()) {
            sb.append("  - ")
                    .append("Course { subject ID = %d, status = %d, size = %d, is cancelled = %b }".formatted(
                            course.getSubject().getID(),
                            course.getStatus(),
                            course.getSize(),
                            course.isCancelled())
                    ).append('\n');
        }

        return sb.toString();
    }

    public void aDayAtSchool() {
        // Create Courses for Subjects without Courses
        for (Subject subject : this.getSubjects()) {
            if (!this.subjectHasCourse(subject)) {
                this.add(new Course(subject, 2));
            }
        }

        // Assign instructors to courses that need them
        for (Course course : this.getCourses()) {
            if (!course.hasInstructor()) {
                this.assignInstructorToCourseRandomly(course);
            }
        }

        // Assign students to courses they don't have a certificate for and are not already in
        for (Student student : this.getStudents()) {
            for (Course course : this.getCourses()) {
                if (!course.getStudents().contains(student) && !student.hasCertificate(course.getSubject())){
                    course.enrolStudent(student);
                }
            }
        }

        // Advance all courses by one day
        for (Course course : this.getCourses()) {
            course.aDayPasses();
        }

        // Remove any finished or canceled courses
        Iterator<Course> courseIterator = this.getCourses().iterator();
        while (courseIterator.hasNext()) {
            Course next = courseIterator.next();
            // Courses that are finished are canceled automatically
            if (next.isCancelled()){
                courseIterator.remove();
            }
        }
    }

    private boolean subjectHasCourse(Subject subject) {
        for (Course course : this.getCourses()){
            if (course.getSubject() == subject) {
                return true;
            }
        }
        return false;
    }

    private void assignInstructorToCourseRandomly(Course course){
        ArrayList<Instructor> eligibleInstructors = new ArrayList<>();
        for (Instructor instructor : this.getInstructors()) {
            if (instructor.canTeach(course.getSubject())) {
                eligibleInstructors.add(instructor);
            }
        }
        try {
            Instructor chosenInstructor = eligibleInstructors.get(new Random().nextInt(eligibleInstructors.size()));
            course.setInstructor(chosenInstructor);
        } catch (IllegalArgumentException e) {
            // No eligible instructors, return
            return;
        }
    }
}
