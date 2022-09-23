import java.io.IOException;

public class SMSClient {
    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.startClient();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
