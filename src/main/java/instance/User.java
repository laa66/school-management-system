package instance;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;

@JsonAutoDetect
public class User implements Transferable {
    private int userId;
    private String classId;
    private String username;
    private String hashPassword;
    private String firstname;
    private String lastname;
    private int personalIdNumber;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Warsaw")
    private Date birthDate;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Warsaw")
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

    @JsonGetter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonGetter
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @JsonGetter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonGetter
    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    @JsonGetter
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonGetter
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonGetter
    public int getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(int personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    @JsonGetter
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @JsonGetter
    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    @JsonGetter
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
