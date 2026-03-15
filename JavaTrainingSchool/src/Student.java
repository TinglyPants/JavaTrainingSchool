import java.util.ArrayList;

public class Student extends Person{
    private ArrayList<Integer> certificates;

    public Student(String name, char gender, int age) {
        super(name, gender, age);
        this.certificates = new ArrayList<>();
    }

    public void graduate(Subject subject) {
        this.certificates.add(subject.getID());
    }

    public ArrayList<Integer> getCertificates() {
        return this.certificates;
    }

    public boolean hasCertificate(Subject subject) {
        return certificates.contains(subject.getID());
    }
}
