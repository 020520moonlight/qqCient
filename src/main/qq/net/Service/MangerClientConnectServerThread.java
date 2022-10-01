package main.qq.net.Service;

import java.util.HashMap;

/**
 * 管理客户端连接到服务端的线程的类
 */
public class MangerClientConnectServerThread {
    //我们将多个线程让如一个HashMap，key就是用户id，value是线程
    private static HashMap<String,ClientConnectServerThread> hashMap= new HashMap<>();
    //将某个线程加入到集合
    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
        hashMap.put(userId,clientConnectServerThread);
    }
    //通过userId可以获得对应的线程
    public static ClientConnectServerThread getCliientConnectServerThread(String userID){
        return hashMap.get(userID);
    }
}

