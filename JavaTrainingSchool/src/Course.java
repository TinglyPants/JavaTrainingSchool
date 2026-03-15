public class Course {
    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;

    public Course(Subject subject){
        this.subject = subject;
        this.daysToRun = subject.getDuration();
    }

    public Subject getSubject(){
        return this.subject;
    }

    public int getStatus(){
        if (this.daysUntilStarts > 0){
            return this.daysUntilStarts * -1;
        }
        else if (this.daysToRun > 0){
            return this.daysToRun;
        }
        return 0;
    }

    public void aDayPasses(){
        if (this.daysUntilStarts > 0) {
            this.daysUntilStarts--;
        }
        else if (this.daysToRun > 0) {
            this.daysToRun--;
        }
    }
}
