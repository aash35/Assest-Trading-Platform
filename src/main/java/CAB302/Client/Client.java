package CAB302.Client;

import CAB302.Common.JsonPayloadRequest;
import CAB302.Common.JsonPayloadResponse;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;

public class Client {
    public JsonPayloadResponse SendRequest(JsonPayloadRequest request) {

        Gson g = new Gson();

        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 8080);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        request.setChecksum(String.valueOf(Instant.now().getEpochSecond()));

        printWriter.println(request.getJsonString());

        BufferedReader bufferReader = null;

        try {
            bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream ()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        String stringResponse = null;

        try {
            stringResponse = bufferReader.readLine();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        printWriter.close();

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

        if (stringResponse == null) {
            return null;
        }

        JsonPayloadResponse response = null;

        try {
            response = g.fromJson(stringResponse, JsonPayloadResponse.class);
        }
        catch (Exception ex) { }

        return response;
    }
}
