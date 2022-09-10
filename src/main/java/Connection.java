import message.RequestResponse;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Socket getSocket() {
        return socket;
    }

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public synchronized RequestResponse receive() throws IOException, ClassNotFoundException {
        return (RequestResponse) in.readObject();
    }

    public synchronized void send(RequestResponse requestResponse) throws IOException {
        out.writeObject(requestResponse);
    }


    public synchronized void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }
}
