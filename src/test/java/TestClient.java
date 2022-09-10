import java.io.IOException;

public class TestClient {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startClient();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
