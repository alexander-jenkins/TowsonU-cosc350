package dev.alexjenkins.cosc350.assignment01.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
    Written by: Alexander Jenkins
    For: COSC-350 Assignment 1
 */

public class TCPServer {
    public static void main(String[] args) {
        // Listen for TCP socket connections on port "12222"
        try {
            ServerSocket socket = new ServerSocket(12222);

            // Receive the server name, IP address, and number of bytes from the client
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    // establish the connection
                    Socket connection = socket.accept();

                    // read the data from the client
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    // Print "WEB SERVER %s, %s, %d" {server name, IP address, and number of bytes}
                    System.out.printf("WEB SERVER: %s%n", fromClient.readLine());

                    // cleanup memory
                    connection.close();
                    fromClient.close();
                } catch (IOException error) {
                    System.err.printf("An error occurred:%n%s%n", error);
                }
            }

        } catch (IOException error) {
            System.err.printf("An error occurred:%n%s%n", error);
        }
    }
}