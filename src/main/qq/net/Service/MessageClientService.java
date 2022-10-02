package main.qq.net.Service;

import main.model.Message;
import main.model.MessqgeType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 该类，提供和消息相关的服务方法
 */
public class MessageClientService {
    public void toMessage(String content,String senderID,String recieverID) throws IOException {
        Message message = new Message();
        message.setContent(content);
        message.setSender(senderID);
        message.setReciever(recieverID);
        message.setMessageType(MessqgeType.MESSAGE_COMM_MES);//普通聊天消息
        message.setSendTime(new Date().toString());//发送时间
        System.out.println(senderID+"对"+recieverID+"说"+content);
        //发送给服务端
        ObjectOutputStream oos = new ObjectOutputStream(MangerClientConnectServerThread.getCliientConnectServerThread(senderID).getSocket().getOutputStream());
        oos.writeObject(message);
    }

}
