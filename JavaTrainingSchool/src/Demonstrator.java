import java.io.Serializable;

public class Demonstrator extends Instructor implements Serializable {
    public Demonstrator(String name, char gender, int age) {
        super(name, gender, age);
    }

    @Override
    public boolean canTeach(Subject subject) {
        return subject.getSpecialism() == 2;
    }
}
