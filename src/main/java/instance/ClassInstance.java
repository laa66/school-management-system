package instance;

public class ClassInstance implements Transferable {
    private String classId;
    private int numberOfStudents;
    private final static int MAX_CLASS_CAPACITY = 30;
    private int teacherId;

    public ClassInstance() {

    }

    public ClassInstance(String classId, int teacherId, int numberOfStudents) throws Exception {
        this.classId = classId;
        this.teacherId = teacherId;
        if (numberOfStudents > MAX_CLASS_CAPACITY) throw new Exception("Wrong number of numberOfStudents parameter. Should be <= 30");
        this.numberOfStudents = numberOfStudents;
    }

    public String getClassId() {
        return classId;
    }
    public int getNumberOfStudents() {
        return numberOfStudents;
    }
    public int getTeacherId() {
        return teacherId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "ClassInstance{" +
                "classId='" + classId + '\'' +
                ", numberOfStudents=" + numberOfStudents +
                ", teacherId=" + teacherId +
                '}';
    }
}
