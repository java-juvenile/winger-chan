import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyDarkClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        String host = "127.0.0.1";
        int port = 55533;

        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();

        System.out.println("Please enter a file name in the current directory that you want to download!");
        Scanner input = new Scanner(System.in);
        String file = input.nextLine();

        socket.getOutputStream().write(file.getBytes("UTF-8"));
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];

        int len;
        StringBuilder ss = new StringBuilder();

        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        //getting the working directory of the current program in Java
        String currentPath = System.getProperty("user.dir");
        //use class File to fing a file
        File f = new File(currentPath + file);

        OutputStream out = null;
        //instantiation
        out = new FileOutputStream(f);
        //output content and saves file
        out.write(bytes);

        out.close();
        input.close();
        outputStream.close();
        socket.close();
    }
}
