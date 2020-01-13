import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyPagServer {
    public static StringBuilder inputStream(Socket socket, InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];

        int len;
        StringBuilder ss = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        return ss;
    }

    public static void outputStream(Socket socket, OutputStream outputStream, String repHttp, int repNum, String repStatu, String message, File f)
            throws IOException {

        if (!f.exists()) {
            repNum = 404;
            repStatu = "Not Found";
        }
        String news = repHttp + " " + repNum + " " + repStatu + "\n\n" + message;
        outputStream.write(news.getBytes("UTF-8"));
    }

    public static File fileContent(StringBuilder ss) {

        String line = ss.toString();
        String[] list = line.split(" ");

        String file = list[1];
        String currentPath = System.getProperty("user.dir");
        // use class File to fing a file
        File f = new File(currentPath + file);
        
        return f;
    }

    public static void main(String[] args) throws IOException {
        String repHttp = "HTTP/1.1";
        int repNum = 200;
        String repStatu = "OK";

        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
        Socket socket = server.accept();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        
        StringBuilder ss = inputStream(socket, inputStream);
        // getting the working directory of the current program in Java      

        File file = fileContent(ss);
        String message = String.valueOf(file.length());
        outputStream(socket, outputStream, repHttp, repNum, repStatu, message, file);

        inputStream.close();
        outputStream.close();
        socket.close();
        server.close();
    }
}
