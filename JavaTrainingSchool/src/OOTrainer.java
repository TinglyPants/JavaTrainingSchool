import java.io.Serializable;

public class OOTrainer extends Instructor implements Serializable {
    public OOTrainer(String name, char gender, int age) {
        super(name, gender, age);
    }

    @Override
    public boolean canTeach(Subject subject) {
        return (subject.getSpecialism() == 1) || (subject.getSpecialism() == 2) || (subject.getSpecialism() == 3);
    }
}
