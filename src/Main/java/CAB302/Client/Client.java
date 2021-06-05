package CAB302.Client;

import CAB302.Common.PayloadRequest;
import CAB302.Common.PayloadResponse;
import CAB302.Common.RuntimeSettings;

import java.io.*;
import java.net.Socket;
import java.time.Instant;

/**
 * Class allows the client side of the application to connect to the server.
 */
public class Client {

    /**
     * Creates a socket connection to the server, then sends a request payload via an object output stream.
     * The client then waits for a response payload from the server.
     * @param request request payload object being sent by the client
     * @return response payload object received from the server.
     * @throws IOException
     */
    public PayloadResponse SendRequest(PayloadRequest request) throws IOException {

        Socket socket = null;

        try {
            socket = new Socket(RuntimeSettings.IP, RuntimeSettings.Port);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        OutputStream outputStream = null;

        try {
            outputStream = socket.getOutputStream();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        request.setChecksum(String.valueOf(Instant.now().getEpochSecond()));

        objectOutputStream.writeObject(request);

        BufferedReader bufferReader = null;

        try {
            bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream ()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        InputStream inputStream = null;

        try {
            inputStream = socket.getInputStream();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        PayloadResponse response = null;

        try {
            response = (PayloadResponse)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }

        try {
            bufferReader.close ();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            socket.close ();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return response;
    }
}
