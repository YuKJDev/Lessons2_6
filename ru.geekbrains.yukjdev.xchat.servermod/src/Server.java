import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        new Server().doInit();

    }

    private int getPORT() {
        return 18442;
    }

    private synchronized void doInit() {

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(getPORT());
            System.out.println("Server is working...");
            socket = server.accept();
            System.out.println("Client connected");
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    System.out.println("Server, write you message");
                    String msg = sc.nextLine();
                    System.out.println("The message was send");
                    out.println(msg);
                }
            }).start();
            while (true) {
                String msg = in.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;
                System.out.println("Client: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (server != null) {
                    server.close();
                }
                System.out.println("Server closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
