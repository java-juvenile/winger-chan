import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDarkClient {
    public static File findFile(String file) {
        // getting the working directory of the current program in Java
        String currentPath = System.getProperty("user.dir");

        String pattern = "(?!.*/).+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(file);
        String path = null;
        if (m.find()) {
            path = "/" + m.group(0);
        }
        // use class File to fing a file
        File f = new File(currentPath + path);

        return f;
    }

    public static void writeFile(StringBuilder ss, File f) throws IOException {
        String str = ss.toString();

        String[] strList = str.split("\n");
        str = strList[2];
        String[] firstLine = strList[0].split(" ");
        
        if (Integer.parseInt(firstLine[1]) == 200) {
            OutputStream out = null;        
            out = new FileOutputStream(f);
            byte[] b = str.getBytes("UTF-8");
            // output content and saves file
            out.write(b);
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String[] list = args[0].split(" ");
        String file = list[1];
        String reqType = "GET";
        String reqHttp = "HTTP/1.1";
        String reqLine = reqType + " " + file + " " + reqHttp;
        
        String[] url = args[1].split(":");
        String host = url[0];
        int port = Integer.parseInt(url[1]);

        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        socket.getOutputStream().write(reqLine.getBytes("UTF-8"));
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder ss = new StringBuilder();

        while ((len = inputStream.read(bytes)) != -1) {
            ss.append(new String(bytes, 0, len, "UTF-8"));
        }

        File f = findFile(file);
        writeFile(ss, f);

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
