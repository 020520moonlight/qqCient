package main.qq.net.Service;

import main.model.Message;
import main.model.MessqgeType;

import java.io.EOFException;
import java.io.FileOutputStream;
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
            try {
                //一直要读取服务器端的消息
                System.out.println("客户端线程等待读取从服务返回的消息");
                //如果前端系统选择退出系统，客户端向服务端发送了一个message信息，服务端关闭了socket，
                //而客户端没有得到消息，还在读取信息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                 //如果服务器没有发送Object对象 ，该线程堵塞
                Message message =(Message) ois.readObject();
                //注意后面我们会使用message
                //判断message的类型，然后进行相应的业务类型
                //如果读到的是服务端 返回的在线用户列表
                if (message.getMessageType().equals(MessqgeType.MESSAGE_RET_ONLINEFRIEND)){
                    //取出在线列表，返回
                    //规定，如果在线用户列表以 xxx xxx xxx
                    String[] onlineUser = message.getContent().split(" ");
                    System.out.println("\n------当前用户列表------");
                    for (int i=0;i<onlineUser.length;i++){
                        System.out.println("用户"+onlineUser[i]);
                    }
                }else if (message.getMessageType().equals(MessqgeType.MESSAGE_COMM_MES)){
                    //接收到普通的聊天消息，把服务器端转发的消息显示到控制端
                    System.out.println("\n"+message.getSender()+"对"+
                            message.getReciever()+"说"+message.getContent());
                }else if(message.getMessageType().equals(MessqgeType.MESSAGE_CLIENT_TOALL)){
                    //显示在客户端的控制台
                    System.out.println("\n"+message.getSender()+"对大家伙说"+message.getContent());
                }else if (message.getMessageType().equals(MessqgeType.MESSAGE_FILE_MES)){
                    //如果是文件消息
                    System.out.println("\n"+message.getSender()+"给"+ message.getReciever()+
                            "发送了"+message.getSrc()+"文件我的电脑到"+message.getDest());
                    //取出messag的bytes数组，输出流到磁盘上
                    byte[] bytes = message.getFileByte();
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                    System.out.println("保存成功");
                }
                else {
                    System.out.println("其他类型的message暂不处理");
                }
            }catch (EOFException eofException){
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public Socket getSocket() {
        return socket;
    }

}
