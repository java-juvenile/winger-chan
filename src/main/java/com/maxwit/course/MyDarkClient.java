import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDarkClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        String[] url = args[1].split(":");
        String host = url[0];
        int port = Integer.parseInt(url[1]);

        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();

        String[] list = args[0].split(" ");
        String file = list[1];

        socket.getOutputStream().write(file.getBytes("UTF-8"));
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];

        int len;
        StringBuilder ss = new StringBuilder();

        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        // getting the working directory of the current program in Java
        String currentPath = System.getProperty("user.dir");

        String patternPath = "(?!.*/).+";
        Pattern rPath = Pattern.compile(patternPath);
        Matcher mPath = rPath.matcher(file);
        String path = null;
        if (mPath.find()) {
            path = "/" + mPath.group(0);
        }
        // use class File to fing a file
        File f = new File(currentPath + path);

        OutputStream out = null;
        // instantiation
        out = new FileOutputStream(f);
        // output content and saves file
        out.write(bytes);

        out.close();
        outputStream.close();
        socket.close();
    }
}
