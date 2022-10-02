package main.qqClient.View;

import main.qq.net.Service.FileClientService;
import main.qq.net.Service.MessageClientService;
import main.qq.net.Service.UserClientService;

import java.io.IOException;
import java.util.Scanner;

public class qqview {
    private boolean loop = true;//控制是否显示菜单
    private String key = "";
    //用于登陆服务和注册服务
    private UserClientService service = new UserClientService();
    //聊天方法
    private MessageClientService messageClientService = new MessageClientService();

    private FileClientService fileClientService = new FileClientService();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new qqview().mainMenu();
        System.out.println("退出系统");
    }

    public void mainMenu() throws IOException, ClassNotFoundException {
        while (loop){
            System.out.println("----------欢迎登陆到网络管理系统----------");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 登陆系统");

            System.out.print("请输入你的选择");
            Scanner scanner = new Scanner(System.in);
            key = scanner.next();
            switch (key){
                case "1":
                    System.out.println("登陆系统");
                    System.out.print("请输入用户名");
                    String userid = scanner.next();
                    System.out.print("请输入密码");
                    String pwd = scanner.next();
                    //服务端验证游湖是否合法
                    //先省略这里编写一个服务类进行登陆验证
                    if (service.checkUser(userid,pwd)){
                        System.out.println("----------欢迎"+userid+"登陆成功----------");
                        while (loop){
                            System.out.println("\n--------------网络通信二级菜单(用户"+userid+")------");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择");
                            key = scanner.next();
                            switch (key){
                                case "1":
                                    System.out.println("显示在线用户列表");
                                    //这里写方法
                                    service.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.print("请输入想对大家说的话");
                                    String content2 = new Scanner(System.in).next();
                                    //编写一个方法，将消息发送给服务器端
                                    messageClientService.sendMessageToAll(content2,userid);
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户好（在线的）");
                                    String recieverID = new Scanner(System.in).next();
                                    System.out.println("请输入私聊信息");
                                    String content = new Scanner(System.in).next();
                                    //编写一个方法，将消息发送给服务器端
                                    messageClientService.toMessage(content,userid,recieverID);
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("请输入你想发送的用户");
                                    String getterID = new Scanner(System.in).next();
                                    System.out.println("请输入发送路径，例如：d:\\xx.jpg");
                                    String src  =new Scanner(System.in).next();
                                    System.out.println("请输入发送到对方的某个路径，例如：d:\\aa.jpg");
                                    String dest  =new Scanner(System.in).next();
                                    //调用方法
                                    fileClientService.sendFileToOne(src,dest,userid,getterID);
                                    System.out.println("发送文件");
                                    break;
                                case "9" :
                                    System.out.println("退出系统");
                                    //调用一个方法，给服务器发送退出系统信息
                                    service.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    }else {
                        //登陆服务器失败
                        System.out.println("登陆失败");
                    }
                    break;
                case "9" :
                    System.out.println("退出系统");
                    loop = false;
                    break;
            }
        }
    }
}
