package message;

import instance.*;

//class for request to create all types of instances in DB
//and create responses to create all types of instances that will be later send from server to client

public class InstanceCreate extends RequestResponse {
    private User user;
    private Operation operation;
    private ClassInstance classInstance;
    private Marks marks;
    private String instanceJSON;

    //constructor for single instance operations
    public InstanceCreate(RequestResponseType requestResponseType, Transferable transferable) {
        super(requestResponseType);
        if (transferable instanceof ClassInstance) this.classInstance = (ClassInstance) transferable;
        if (transferable instanceof Operation) this.operation = (Operation) transferable;
        if (transferable instanceof User) this.user = (User) transferable;
        if (transferable instanceof Marks) this.marks = (Marks) transferable;
    }

    //getters and setters
    public User getUser() {
        return user;
    }

    public Operation getOperation() {
        return operation;
    }

    public ClassInstance getClassInstance() {
        return classInstance;
    }

    public Marks getMarks() {
        return marks;
    }

    public String getInstanceJSON() {
        return instanceJSON;
    }

    public void setInstanceJSON(String instanceJSON) {
        this.instanceJSON = instanceJSON;
    }

    @Override
    public String toString() {
        return "InstanceCreate{" +
                "user=" + user +
                ", operation=" + operation +
                ", classInstance=" + classInstance +
                ", marks=" + marks +
                ", instanceJSON='" + instanceJSON + '\'' +
                '}';
    }
}
