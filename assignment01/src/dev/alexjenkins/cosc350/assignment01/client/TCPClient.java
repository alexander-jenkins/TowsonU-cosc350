package dev.alexjenkins.cosc350.assignment01.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

/*
    Written by: Alexander Jenkins
    For: COSC-350 Assignment 1
 */

public class TCPClient {
    public static void main(String[] args) {
        // Ask the user for a web server's name as a string [sub.name.tld]
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a web server name: ");
        final String webServer = in.nextLine().trim();
        in.close();

        try {
            // Send a request to the specified server
            URL dest = new URL(String.format("https://%s", webServer));
            HttpURLConnection con = (HttpURLConnection) dest.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Receive and print the response
            BufferedReader res = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String current;
            int numBytes = 0;
            while ((current = res.readLine()) != null) {
                System.out.println(current);
                numBytes += current.getBytes().length;
            }

            // cleanup memory
            res.close();
            con.disconnect();

            // Print: "NUMBER OF BYTES: %d"
            System.out.printf("NUMBER OF BYTES: %d%n", numBytes);

            // Make a TCP socket connection to the local server on port "12222"
            Socket socket = new Socket("localhost", 12222);
            DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

            // Send the server name, IP address, and number of bytes to the server
            final String ip = InetAddress.getByName(dest.getHost()).getHostAddress();
            String message = String.format("%s %s %d", webServer, ip, numBytes);
            toServer.writeBytes(message);

            // cleanup memory
            socket.close();
            toServer.close();

        } catch (IOException error) {
            System.err.printf("An error occurred:%n%s%n", error);
        }
    }
}
