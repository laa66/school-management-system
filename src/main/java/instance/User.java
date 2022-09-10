package instance;

import java.util.Date;

public class User implements Transferable {
    private int userId;
    private String classId;
    private String username;
    private String hashPassword;
    private String firstname;
    private String lastname;
    private int personalIdNumber;
    private Date birthDate;
    private Date joinDate;
    private boolean teacher;

    public User() {

    }

    public User(int userId, String classId, String username, String hashPassword, String firstname, String lastname, int personalIdNumber, Date birthDate, Date joinDate, boolean teacher) {
        this.userId = userId;
        this.classId = classId;
        this.username = username;
        this.hashPassword = hashPassword;
        this.firstname = firstname;
        this.lastname = lastname;
        this.personalIdNumber = personalIdNumber;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.teacher = teacher;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(int personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", classId='" + classId + '\'' +
                ", username='" + username + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", personalIdNumber=" + personalIdNumber +
                ", birthDate=" + birthDate +
                ", joinDate=" + joinDate +
                ", teacher=" + teacher +
                '}';
    }
}
