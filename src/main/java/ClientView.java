import instance.*;
import message.*;
import security.Secure;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//console version
public class ClientView {
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Set<String> OPERATION_TYPES = new HashSet<String>(){{add("read");add("create");add("edit");add("remove");}};
    private static final Set<String> INSTANCE_TYPES = new HashSet<String>(){{add("user");add("class");add("operation");add("marks");add("average");}};

    public static RequestResponse getRequest() throws Exception {
        RequestResponse request = null;
        ConsoleHelper.write("Type INFO for request tips. Enter your request: ");
        String textRequest;
        while (true) {
            textRequest = ConsoleHelper.readString();
            if (textRequest.equalsIgnoreCase("info")) {
                ConsoleHelper.write("Your request should look like:\n"
                        + "\"read user\"\n"
                        + "\"create user\"\n"
                        + "\"edit operation\"\n"
                        + "\"remove class\"\n"
                        + "\"read marks\"\n" //NEW
                        + "\"read average\"\n" //NEW
                        + "Where first parameter is OPERATION type, second is INSTANCE type");
            } else if (OPERATION_TYPES.contains(textRequest.split(" ")[0]) && INSTANCE_TYPES.contains(textRequest.split(" ")[1])) break;
            else ConsoleHelper.write("Wrong operation type or instance. Try again.");
        }
        String[] textRequestSplit = textRequest.split(" ");
        switch (textRequestSplit[0]) {
            case "read": //read instance
                if (textRequestSplit[1].equals("marks")) {
                    String username;
                    String input;
                    Subject subject;
                    ConsoleHelper.write("Enter which grades you want to read (student/class):");
                    while (true) {
                        input = ConsoleHelper.readString();
                        if (input.equals("student") || input.equals("class")) break;
                    }
                    ConsoleHelper.write("Enter username/class_id: ");
                    while (true) {
                        username = ConsoleHelper.readString();
                        if (!username.isEmpty()) break;
                    }
                    ConsoleHelper.write("Enter subject: ");
                    while (true) {
                        try {
                            subject = Subject.valueOf(ConsoleHelper.readString().toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {e.printStackTrace();}
                    }
                    request = new InstanceRead(RequestResponseType.READ_INSTANCE, textRequestSplit[1], input, subject, username);
                } else if (textRequestSplit[1].equals("average")) {
                    ConsoleHelper.write("Enter which grades average you want to read (student/final_student):");
                    String username;
                    String input;
                    String subject;
                    while (true) {
                        input = ConsoleHelper.readString();
                        if (input.equals("student") || input.equals("final_student")) break;
                    }
                    ConsoleHelper.write("Enter username: ");
                    while (true) {
                        username = ConsoleHelper.readString();
                        if (!username.isEmpty()) break;
                    }

                    ConsoleHelper.write("Enter subject (FOR STUDENT FINAL AVERAGE GRADE ENTER NOTHING): ");
                    while (true) {
                        subject = ConsoleHelper.readString().toUpperCase();
                        try {
                            Subject.valueOf(subject);
                            break;
                        } catch (IllegalArgumentException ignored) {}
                    }
                    request = new InstanceRead(RequestResponseType.READ_INSTANCE, textRequestSplit[1], input, Subject.valueOf(subject), username);
                } else {
                    ConsoleHelper.write("Enter condition for reading (name=alex):");
                    String input;
                    do {
                        input = ConsoleHelper.readString();
                    } while (!input.contains("="));
                    request = new InstanceRead(RequestResponseType.READ_INSTANCE, textRequestSplit[1], input);
                }
                break;
            case "create": //create instance
                switch (textRequestSplit[1]) {
                    case "user":
                        request = new InstanceCreate(RequestResponseType.CREATE_INSTANCE, getUserConsole());
                        break;
                    case "class":
                        request = new InstanceCreate(RequestResponseType.CREATE_INSTANCE, getClassInstanceConsole());
                        break;
                    case "operation":
                        request = new InstanceCreate(RequestResponseType.CREATE_INSTANCE, getOperationConsole());
                        break;
                    default:
                        throw new Exception("Error. Wrong instance provided. Try again.");
                }
                break;
            case "edit": //edit instance
                ConsoleHelper.write("Enter new value (name=andrew):");
                String inputNewValue;
                do {
                    inputNewValue = ConsoleHelper.readString();
                } while (!inputNewValue.contains("="));
                ConsoleHelper.write("Enter condition for editing (name=alex):");
                String inputCondition = ConsoleHelper.readString();
                request = new InstanceEdit(RequestResponseType.EDIT_INSTANCE, textRequestSplit[1], inputNewValue, inputCondition);
                break;
            case "remove": //remove instance
                ConsoleHelper.write("Enter condition for remove operation (name=alex):");
                String inputRemove;
                do {
                    inputRemove = ConsoleHelper.readString();
                } while (!inputRemove.contains("="));
                request = new InstanceRemove(RequestResponseType.REMOVE_INSTANCE, textRequestSplit[1], inputRemove);
                break;
            default:
                break;
        }
        return request;
    }

    private static User getUserConsole() throws IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {
        ConsoleHelper.write("Create user: ");
        User user = new User();
        user.setUserId(1);
        ConsoleHelper.write("Enter class ID: ");
        user.setClassId(ConsoleHelper.readString());
        ConsoleHelper.write("Enter username: ");
        user.setUsername(ConsoleHelper.readString());
        ConsoleHelper.write("Enter password: ");
        user.setHashPassword(Secure.generateStrongPasswordHash(ConsoleHelper.readString()));
        ConsoleHelper.write("Enter first name: ");
        user.setFirstname(ConsoleHelper.readString());
        ConsoleHelper.write("Enter last name: ");
        user.setLastname(ConsoleHelper.readString());
        ConsoleHelper.write("Enter personal ID number: ");
        user.setPersonalIdNumber((int)ConsoleHelper.readNumber());
        ConsoleHelper.write("Enter birth date: ");
        Date date = DATE_FORMAT.parse(ConsoleHelper.readString());
        user.setBirthDate(date);
        user.setJoinDate(new Date());
        ConsoleHelper.write("Enter type of account (teacher/student): ");
        user.setTeacher(ConsoleHelper.readString().equalsIgnoreCase("teacher"));
        return user;
    }

    private static Operation getOperationConsole() throws IOException {
        ConsoleHelper.write("Create operation: ");
        Operation operation = new Operation();
        ConsoleHelper.write("Enter class id: ");
        operation.setClassId(ConsoleHelper.readString());
        ConsoleHelper.write("Enter user id: ");
        operation.setUserId((int) ConsoleHelper.readNumber());
        ConsoleHelper.write("Enter mark: ");
        operation.setMark(ConsoleHelper.readString());
        ConsoleHelper.write("Enter subject: ");
        operation.setSubject(Subject.valueOf(ConsoleHelper.readString().toUpperCase()));
        operation.setDate(new Date());
        ConsoleHelper.write("Enter note: ");
        operation.setNote(ConsoleHelper.readString());
        return operation;
    }

    private static ClassInstance getClassInstanceConsole() throws IOException {
        ConsoleHelper.write("Create class: ");
        ClassInstance classInstance = new ClassInstance();
        ConsoleHelper.write("Enter class id: ");
        classInstance.setClassId(ConsoleHelper.readString());
        ConsoleHelper.write("Enter teacher id: ");
        classInstance.setTeacherId((int) ConsoleHelper.readNumber());
        return classInstance;
    }
}
