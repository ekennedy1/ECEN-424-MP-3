import java.io.*;
import java.net.*;
import java.util.*;  

public class Multithreading extends Thread {

    private int threadNumber;
    private int portNumber;
    private Socket connectionSocket;

    public Multithreading(int threadNumber, Socket connectionSocket, int portNumber){
        this.threadNumber = threadNumber;
        this.connectionSocket = connectionSocket;
        this.portNumber = portNumber;
    }

    public static void wait(int ms){

        try{
            Thread.sleep(ms);
        }

        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }

    }

    public void run() {
        String clientSentence;

        try{ 
            System.out.println("Client Connected to port " + portNumber + " from thread number " + threadNumber);

            PrintWriter pr = new PrintWriter(connectionSocket.getOutputStream());
            pr.println("Please enter a string, e.g. 'Hello World', and a positive integer.");
            pr.flush();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            clientSentence = inFromClient.readLine();
            System.out.println("Client: " + clientSentence);

            String strParts[] = clientSentence.split(" ");

            int txN = Integer.parseInt(strParts[1]);

            for(int i = 1; i < txN; i++){
                PrintWriter pr2 = new PrintWriter(connectionSocket.getOutputStream());
                pr2.println(strParts[0]);
                pr2.flush();

                wait(1000);
            }

            PrintWriter pr2 = new PrintWriter(connectionSocket.getOutputStream());
            pr2.println(strParts[0] + "\n");
            pr2.flush();

            connectionSocket.close();
        }
        catch(IOException ie){
            
        }
        
    }
}
