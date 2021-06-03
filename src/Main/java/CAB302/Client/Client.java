package CAB302.Client;

import CAB302.Common.PayloadRequest;
import CAB302.Common.PayloadResponse;

import java.io.*;
import java.net.Socket;
import java.time.Instant;

public class Client {
    public PayloadResponse SendRequest(PayloadRequest request) throws IOException {

        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 8080);
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
