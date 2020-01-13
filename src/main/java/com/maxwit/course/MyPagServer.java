import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyPagServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));

        Socket socket = server.accept();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];

        int len;
        StringBuilder ss = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        // getting the working directory of the current program in Java
        String currentPath = System.getProperty("user.dir");

        // use class File to fing a file
        File f = new File(currentPath + ss);
        String message = String.valueOf(f.length());
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes("UTF-8"));

        inputStream.close();
        outputStream.close();
        socket.close();
        server.close();
    }
}
