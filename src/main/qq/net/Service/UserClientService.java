package main.qq.net.Service;

import main.model.Message;
import main.model.MessqgeType;
import main.model.User;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 该类完成一个登录验证
 */
public class UserClientService {
    //因为我们可能在其他地方要使用User信息
    private User user = new User();
    //因为我们可能在其他地方要使用Socket信息
    private Socket socket;

    //到服务器要整userID和pwd是否正确
    public boolean checkUser(String userID,String pwd) throws IOException, ClassNotFoundException {
        boolean result = false;
        //创建一个User对象
        user.setUserId(userID);
        user.setPassword(pwd);

        //可以连接到服务端，发送user对象"192.168.0.198"
        socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(user);

        //读取从服务端回复的message对象,在这里读取数据是，socket发生了关闭
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) objectInputStream.readObject();
        //登陆成功
        if(message.getMessageType().equals(MessqgeType.MESSAGE_LOGIN_SECCESS)){
            //创建啊一个服务器保持通信的线程 创建一个类 ClientConnectServerThread
            ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
            //启动客户端的线程
            clientConnectServerThread.start();
            //如果一个客户端于服务端只有一个联系，但是多个建议将线程加入集合中管理
            MangerClientConnectServerThread.addClientConnectServerThread(userID,clientConnectServerThread);

            result = true;
        }else{
            //如果登录失败 我们就不能启动和服务器通信线程，我们要关闭这个线程
            System.out.println("当前用户"+userID+"登陆失败");
            socket.close();
        }

        return result;
    }

    //向服务器端请求在线用户列表
    public void onlineFriendList() throws IOException {
        Message message = new Message();
        message.setMessageType(MessqgeType.MESSAGE_GET_ONLINEFRIEND);
        //发送给服务器，得到当前线程的Socket 对应的objectPutputStream
        //从管理线程的类，传入用户id得到用户id 对应的线程，获得县城里面呢的socket对象，再获得socket对象里面的输出流
        ObjectOutputStream oos
                = new ObjectOutputStream(MangerClientConnectServerThread.getCliientConnectServerThread(user.getUserId()).getSocket().getOutputStream());
        oos.writeObject(message);//发送信息向服务端获得在线用户列表



    }
}
