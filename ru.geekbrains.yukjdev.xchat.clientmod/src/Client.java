import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        new Client().doInit();

    }

    private String getHOST() {
        return "127.0.0.1";
    }

    private int getPORT() {
        return 18442;
    }

    private void doInit()  {

        Socket socket = null;

        try {
            socket = new Socket(getHOST(), getPORT());
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    String msg = in.nextLine();
                    System.out.println("Server: " + msg);
                }
            }).start();

            while (true) {
                System.out.println("Write you message...");
                String msg = sc.nextLine();
                out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
