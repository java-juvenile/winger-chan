import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyPagServer {
    public static void main(String[] args) throws IOException {
        int port = 55533;
        ServerSocket server = new ServerSocket(port);

        System.out.println("Server waitting...");
        Socket socket = server.accept();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];

        int len;
        StringBuilder ss = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        File f = new File("/Users/morrow" + ss);
        String message = String.valueOf(f.length());
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes("UTF-8"));
        inputStream.close();
        outputStream.close();
        socket.close();
        server.close();
    }
}
