package main.qq.net.Service;

import main.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 线程类
 */
public class ClientConnectServerThread extends Thread{
    //该线程需要持有Socket
    private Socket socket;
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        //因为我们这个线程需要在后台和服务器通讯，因此我们while循环
        while (true){
            //一直要读取服务器端的消息
            System.out.println("客户端线程等待读取从服务返回的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                 //如果服务器没有发送Object对象 ，该线程堵塞
                Message message =(Message) ois.readObject();
                //注意后面我们会使用message

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Socket getSocket() {
        return socket;
    }

}
