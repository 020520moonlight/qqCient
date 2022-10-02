package main.qq.net.Service;

import main.model.Message;
import main.model.MessqgeType;

import java.io.*;

/**
 * 该类完成文件的传输服务
 */
public class FileClientService {
    /**
     *
     * @param src 源文件目录
     * @param dest 目标目录
     * @param sender 发送者
     * @param reciever 接收者
     */
    public void sendFileToOne(String src,String dest,String sender,String reciever) throws IOException {
        //读取src文件，必须存在
        Message message = new Message();
        message.setMessageType(MessqgeType.MESSAGE_FILE_MES);
        message.setSender(sender);
        message.setReciever(reciever);
        message.setSrc(src);
        message.setDest(dest);
        //
        File file = new File(src);
        byte[] fileBytes = new byte[(int)file.length()];
        FileInputStream fileInputStream = new FileInputStream(src);
        fileInputStream.read(fileBytes);
        fileInputStream.close();
        message.setFileByte(fileBytes);
        //
        System.out.println("\n"+sender+"给"+reciever+"发送文件"+src+"到对方的"+dest+"目录");
        //发送
        ObjectOutputStream oos = new ObjectOutputStream(MangerClientConnectServerThread.getCliientConnectServerThread(sender).getSocket().getOutputStream());
        oos.writeObject(message);
    }
}
