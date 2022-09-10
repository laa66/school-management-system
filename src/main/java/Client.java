import message.*;
import message.Error;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private Connection connection;

    public void startClient() throws IOException, ClassNotFoundException {
        ConsoleHelper.write("Starting client...\n" + "Enter host: ");
        String host = ConsoleHelper.readString();
        ConsoleHelper.write("Enter port: ");
        int port = (int) ConsoleHelper.readNumber();
        clientSocket = new Socket(host, port);
        connection = new Connection(clientSocket);
        ConsoleHelper.write("Connection established.");
        Thread thread = new Thread(new ClientThreadHandler());
        thread.start();
        ConsoleHelper.write("Client started.");
    }

    public class ClientThreadHandler implements Runnable {
        private String username;

        @Override
        public void run() {
            try {
                clientHandshakeLoop();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void clientHandshakeLoop() throws IOException, ClassNotFoundException {
            boolean flag = true;
            while (true) {
                RequestResponse response = connection.receive();
                if (response.getRequestType() == RequestResponseType.REQUEST_VERIFICATION) {
                    if (!flag) ConsoleHelper.write("Verification failed. Try again.");
                    ConsoleHelper.write("Enter username and password: ");
                    username = ConsoleHelper.readString();
                    String password = ConsoleHelper.readString(); //TODO hash password and send it to server
                    flag = false;
                    connection.send(new VerificationData(RequestResponseType.VERIFICATION_DATA, username, password));
                } else if (response.getRequestType() == RequestResponseType.VERIFICATION_ACCEPTED) {
                    ConsoleHelper.write("Verification successful.");
                    break;
                }
            }
        }

        //this is entry point for creating GUI request-response in special places where you want to fetch or update DB
        private void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(getRequest());
                RequestResponse response = connection.receive();
                //printing data
                switch (response.getRequestType()) {
                    case CREATE_INSTANCE:
                        InstanceCreate instanceCreate = (InstanceCreate) response;
                        ConsoleHelper.write(instanceCreate.getInstanceJSON());
                        break;
                    case DEFAULT:
                        Default d = (Default) response;
                        ConsoleHelper.write(d.getText());
                        break;
                    case ERROR:
                        Error error = (Error) response;
                        ConsoleHelper.write("Server side error: " + error.getException());
                        error.getException().printStackTrace();
                        break;
                    default:
                }
            }
        }

        private RequestResponse getRequest() throws IOException {
            try {
                return ClientView.getRequest();
            } catch (Exception e) {
                ConsoleHelper.write("Error while parsing data. " + e.getMessage());
            }
            return null;
        }
    }
}
