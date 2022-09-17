package instance;

import java.util.List;

public class Marks implements Transferable {
    private List<Operation> listOfMarks; //list of users marks or class marks by subject/all

    public List<Operation> getListOfMarks() {
        return listOfMarks;
    }

    public void setListOfMarks(List<Operation> listOfMarks) {
        this.listOfMarks = listOfMarks;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "listOfMarks=" + listOfMarks +
                '}';
    }
}
