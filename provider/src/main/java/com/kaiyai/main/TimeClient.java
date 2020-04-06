package com.kaiyai.main;

import java.io.InputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Simple example of a client for the TimeServer.
 *
 * <p>Start the server, then run the client, specifying the server
 * hostname and port number as command-line arguments.</p>
 *
 * @author Nick Efford and Karim Djemame
 * @version 1.2 [2016-09-19]
 */

public class TimeClient {


  private String serverName;
  private int portNumber;


  /**
   * Creates a TimeClient capable of talking to the specified TimeServer.
   *
   * @param server name of machine running the TimeServer
   * @param port number of port on which TimeServer is listening
   */

  public TimeClient(String server, int port)
  {
    serverName = server;
    portNumber = port;
  }


  /**
   * Connects to the TimeServer to obtain date and time information.
   * The date and time thus obtained are printed on the console.
   *
   * @param IOException in the event of any problem connecting to the
   * server or reading data from it
   */

  public void connect() throws IOException
  {
    // Establish a connection to the TimeServer and obtain a stream
    // through which bytes can be read from that server

    Socket server = new Socket(serverName, portNumber);
    System.out.println("Connected to " + server.getInetAddress());
    InputStream in = server.getInputStream();

    // Read bytes from the server and construct a string from
    // them, then print this string on the console

    byte[] data = new byte[256];
    int numBytes = in.read(data);
    System.out.println(numBytes + " bytes read from socket");
    String now = new String(data);
    System.out.println("Server said: " + now);
  }


  /**
   * Program to create a TimeClient and connected to the specified server.
   *
   * @param argv vector of at least two command-line arguments, the first
   * being the name of the machine running the server and the second
   * the number of the port on which the server is listening
   */

  public static void main(String[] argv) throws IOException
  {

    if (argv.length > 1) {
      int port = Integer.parseInt(argv[1]);
      TimeClient client = new TimeClient(argv[0], port);
      client.connect();
    }
    else {
      System.err.println("usage: java TimeClient <server> <port>");
      System.exit(1);
    }
  }


}
