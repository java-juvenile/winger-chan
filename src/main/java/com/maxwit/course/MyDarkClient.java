import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyDarkClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        String host = "127.0.0.1";
        int port = 55533;

        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();

        String file = "/aaa";
        socket.getOutputStream().write(file.getBytes("UTF-8"));
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];

        int len;
        StringBuilder ss = new StringBuilder();

        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        File f = new File("/Users/morrow" + "/saveTest");
        OutputStream out = null;
        out = new FileOutputStream(f);

        out.write(bytes);

        outputStream.close();
        socket.close();
    }
}
