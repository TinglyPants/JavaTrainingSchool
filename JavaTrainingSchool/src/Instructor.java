public abstract class Instructor extends Person {
    private Course assignedCourse;

    public Instructor(String name, char gender, int age) {
        super(name, gender, age);
    }

    public abstract void assignCourse(Course course);

    public abstract void unassignCourse();

    public abstract Course getAssignedCourse();

    public abstract boolean canTeach(Subject subject);
}
