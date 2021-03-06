package Java_core.Lesson_7.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServ {
    private Vector<ClientHandler> clients;

    public MainServ() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AuthService.disconnect();
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }

    public void personalMsg(String sender, String name, String msg) {
        boolean res = false;
        if (sender.equals(name)) {
            isNickSend(sender,"Ошибка отправка личного сообщения самому себе");
        } else {
            for (ClientHandler o : clients) {
                if (o.getNick().equalsIgnoreCase(name)) {
                    res = true;
                    break;
                }
            }
            if (res) {
                String str = "";
                String[] tokens = msg.split(" ");
                for (int i = 2; i < tokens.length; i++) {
                    str += tokens[i]+" ";
                }
                isNickSend(sender,sender + ": " + str);
                isNickSend(name,sender + ": " + str);
            } else {
                isNickSend(sender,"Данного пользователя нет в сети");
            }
        }
    }

    public void isNickSend(String name, String msg) {
        for (ClientHandler o:clients) {
            if (o.getNick().equalsIgnoreCase(name)) {
                o.sendMsg(msg);
                break;
            }
        }
    }

    public boolean isConnect(String name) {
        boolean res = false;
        for (ClientHandler o: clients) {
            if (o.getLogin().equals(name)) {
                res=true;
                break;
            }
        }
        return res;
    }

    public void clientsOn(ClientHandler ch) {
        clients.add(ch);
    }

    public void clientDisconnet(ClientHandler ch) {
        clients.remove(ch);
    }
}
