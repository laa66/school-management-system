package database;

import instance.*;
import message.*;
import message.Error;
import security.Secure;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Random;

//static helper class for sending requests to MySQL DB
//TODO trigger number_of_students counting in classes table added reading user port and hostname in client class//DONE
//TODO check error in verify users section after inserting not existing username //DONE
//TODO add functionality: read specific subject marks and presence return map/list by user
//TODO read specific marks and presence by class and subject
//TODO read average by class, subject and student
public class DatabaseHandler {

    public static boolean verifyUser(RequestResponse requestResponse) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        VerificationData verificationData = (VerificationData) requestResponse;
        String username = verificationData.getUsername();
        String password = verificationData.getHash();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser("username=" + username);
        return Secure.validatePassword(password, user.getHashPassword());
    }

    public static RequestResponse readInstance(RequestResponse requestResponse) throws SQLException {
        if (requestResponse.getRequestType() != RequestResponseType.READ_INSTANCE) return new Error(RequestResponseType.ERROR, new Exception("Wrong request type. Try again."));
        InstanceRead instanceRead = (InstanceRead) requestResponse;
        RequestResponse response = null;
        switch (instanceRead.getTableName()) {
            case "user":
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUser(instanceRead.getColumnValue());
                response = new InstanceCreate(RequestResponseType.CREATE_INSTANCE, user);
                break;
            case "class":
                ClassInstanceDAO classInstanceDAO = new ClassInstanceDAO();
                ClassInstance classInstance = classInstanceDAO.getClassInstance(instanceRead.getColumnValue());
                response = new InstanceCreate(RequestResponseType.CREATE_INSTANCE, classInstance);
                break;
            case "operation":
                OperationDAO operationDAO = new OperationDAO();
                Operation operation = operationDAO.getOperation(instanceRead.getColumnValue());
                response = new InstanceCreate(RequestResponseType.CREATE_INSTANCE, operation);
                break;
        }
        return response;
    }

    public static RequestResponse createInstance(RequestResponse requestResponse) throws SQLException {
        //generate unique id
        RequestResponse response = null;
        if (requestResponse.getRequestType() != RequestResponseType.CREATE_INSTANCE) return new Error(RequestResponseType.ERROR, new Exception("Wrong request type. Try again."));
        InstanceCreate instanceCreate = (InstanceCreate) requestResponse;
        if (instanceCreate.getUser() != null) {
            UserDAO userDAO = new UserDAO();
            response = new Default(RequestResponseType.DEFAULT, String.valueOf(userDAO.createUser(instanceCreate.getUser(), generateUniqueID(userDAO))));
        }
        if (instanceCreate.getClassInstance() != null) {
            ClassInstanceDAO classInstanceDAO = new ClassInstanceDAO();
            response = new Default(RequestResponseType.DEFAULT, String.valueOf(classInstanceDAO.createClassInstance(instanceCreate.getClassInstance())));
        }
        if (instanceCreate.getOperation() != null) {
          OperationDAO operationDAO = new OperationDAO();
          response = new Default(RequestResponseType.DEFAULT, String.valueOf(operationDAO.createOperation(instanceCreate.getOperation(), generateUniqueID(operationDAO))));
        }
        return response;
    }

    public static RequestResponse editInstance(RequestResponse requestResponse) throws SQLException {
        RequestResponse response = null;
        if (requestResponse.getRequestType() != RequestResponseType.EDIT_INSTANCE) return new Error(RequestResponseType.ERROR, new Exception(""));
        InstanceEdit instanceEdit = (InstanceEdit) requestResponse;
        switch (instanceEdit.getTableName()) {
            case "user":
                UserDAO userDAO = new UserDAO();
                response = new Default(RequestResponseType.DEFAULT, String.valueOf(userDAO.editUser(instanceEdit.getColumnNewValue(), instanceEdit.getConditionValue())));
                break;
            case "class":
                ClassInstanceDAO classInstanceDAO = new ClassInstanceDAO();
                response = new Default(RequestResponseType.DEFAULT, String.valueOf(classInstanceDAO.editClassInstance(instanceEdit.getColumnNewValue(), instanceEdit.getConditionValue())));
                break;
            case "operation":
                OperationDAO operationDAO = new OperationDAO();
                response = new Default(RequestResponseType.DEFAULT, String.valueOf(operationDAO.editOperation(instanceEdit.getColumnNewValue(), instanceEdit.getConditionValue())));
                break;
        }
        return response;
    }

    public static RequestResponse removeInstance(RequestResponse requestResponse) throws SQLException {
        RequestResponse response = null;
        if (requestResponse.getRequestType() != RequestResponseType.REMOVE_INSTANCE)
            return new Error(RequestResponseType.ERROR, new Exception("Wrong request type. Try again."));
        InstanceRemove instanceRemove = (InstanceRemove) requestResponse;
        switch (instanceRemove.getTableName()) {
            case "user":
                UserDAO userDAO = new UserDAO();
                response = new Default(RequestResponseType.DEFAULT, String.valueOf(userDAO.removeUser(instanceRemove.getColumnValue())));
                break;
            case "class":
                ClassInstanceDAO classInstanceDAO = new ClassInstanceDAO();
                response = new Default(RequestResponseType.DEFAULT, String.valueOf(classInstanceDAO.removeClassInstance(instanceRemove.getColumnValue())));
                break;
            case "operation":
                OperationDAO operationDAO = new OperationDAO();
                response = new Default(RequestResponseType.DEFAULT, String.valueOf(operationDAO.removeOperation(instanceRemove.getColumnValue())));
                break;
        }

        return response;
    }

    //helper methods
    private static int generateUniqueID(AbstractInstanceDAO abstractInstanceDAO) throws SQLException {
        int generatedID = generateID();
        while (abstractInstanceDAO.getAllID().contains(generatedID)) {
            generatedID = generateID();
        }
        return generatedID;
    }

    private static int generateID() {
        Random random = new Random();
        int generate = 0;
        while (generate < 10000) {
            generate = random.nextInt(99999);
        }
        return generate;
    }

}
