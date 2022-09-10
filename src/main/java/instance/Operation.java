package instance;

import java.util.Date;

public class Operation implements Transferable {
    private final static int MAX_NOTE_LENGTH = 200;
    private int operationId;
    private String classId;
    private int userId;
    private String mark;
    private Subject subject; //enum
    private Date date;
    private String note;

    public Operation() {

    }

    public Operation(int operationId, String classId, int userId, String mark, Subject subject, Date date, String note) throws Exception {
        this.operationId = operationId;
        this.classId = classId;
        this.userId = userId;
        this.mark = mark;
        this.subject = subject;
        this.date = date;
        if (note.length() > MAX_NOTE_LENGTH) throw new Exception("Too long note. Should be < 200 characters");
        this.note = note;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationId='" + operationId + '\'' +
                ", classId='" + classId + '\'' +
                ", userId='" + userId + '\'' +
                ", mark='" + mark + '\'' +
                ", subject=" + subject +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
