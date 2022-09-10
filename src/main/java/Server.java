import database.DatabaseHandler;
import message.*;
import message.Error;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<Socket, Connection> mapOfClients = new HashMap<>();
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(50);

    public void startServer() {
        try {
            ConsoleHelper.write("Starting server...");
            serverSocket = new ServerSocket(8080);
            connectionListening();
        } catch (IOException e) {
            ConsoleHelper.write("Error while starting server.");
        }
    }

    private void connectionListening() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Connection connection = new Connection(clientSocket);
            mapOfClients.put(clientSocket, connection);
            ConsoleHelper.write("New client connected: " + clientSocket.getRemoteSocketAddress());
            EXECUTOR_SERVICE.submit(new ServerThreadHandler(connection));
        }
    }

    public class ServerThreadHandler implements Runnable {
        private final Connection connection;
        private String username;

        public ServerThreadHandler(Connection connection) {
            this.connection = connection;
        }

        private void serverHandshakeLoop() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
            while (true) {
                connection.send(new Default(RequestResponseType.REQUEST_VERIFICATION));
                RequestResponse response = connection.receive();
                if (response.getRequestType() == RequestResponseType.VERIFICATION_DATA) {
                    try {
                        if (DatabaseHandler.verifyUser(response)) {
                            RequestResponse request = new Default(RequestResponseType.VERIFICATION_ACCEPTED);
                            connection.send(request);
                            VerificationData verificationData = (VerificationData) response;
                            username = verificationData.getUsername();
                            ConsoleHelper.write("User " + verificationData.getUsername() + " verified.");
                            break;
                        }
                    } catch (SQLException e) {
                        ConsoleHelper.write(e.getMessage());
                    }
                }
            }
        }

        private void serverMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                RequestResponse request = connection.receive();
                RequestResponse response;
                switch (request.getRequestType()) {
                    case READ_INSTANCE:
                        try {
                            response = DatabaseHandler.readInstance(request);
                        } catch (SQLException e) {
                            response = new Error(RequestResponseType.ERROR, e);
                        }
                        break;
                    case CREATE_INSTANCE:
                        try {
                            response = DatabaseHandler.createInstance(request);
                        } catch (SQLException e) {
                            response = new Error(RequestResponseType.ERROR, e);
                        }
                        break;
                    case EDIT_INSTANCE:
                        try {
                            response = DatabaseHandler.editInstance(request);
                        } catch (SQLException e) {
                            response = new Error(RequestResponseType.ERROR, e);
                        }
                        break;
                    case REMOVE_INSTANCE:
                        try {
                            response = DatabaseHandler.removeInstance(request);
                        } catch (SQLException e) {
                            response = new Error(RequestResponseType.ERROR, e);
                        }
                        break;
                    default:
                        response = new Error(RequestResponseType.ERROR, new Exception("Wrong request type. Try again."));
                        break;
                }
                connection.send(response);
            }
        }

        @Override
        public void run() {
            try {
                serverHandshakeLoop();
                serverMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    }
}
