package networking.ClientServer.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class SimpleServerChannel {

    public static void main(String[] args) {
        try(ServerSocketChannel serverChannel=ServerSocketChannel.open()){
            serverChannel.socket().bind(new InetSocketAddress(5000));
            serverChannel.configureBlocking(false);
            System.out.println("Server is listening on port"+serverChannel.socket().getLocalPort());
            List<SocketChannel> clientChannels=new ArrayList<>();
            while(true){
          //      System.out.println("System is waiting another  client to request connection");
                SocketChannel clientChannel=serverChannel.accept();
                if(clientChannel!=null){
                    clientChannel.configureBlocking(false);
                    clientChannels.add(clientChannel);
                    System.out.printf("Client %s connected %n",clientChannel.socket().getRemoteSocketAddress());
                }

                //ByteBuffer is the best choice for Socket Channels
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                for (SocketChannel channel : clientChannels) {
                    int readBytes = channel.read(buffer);
                    if (readBytes > 0) {
                        buffer.flip();
                        channel.write(ByteBuffer.wrap("Echo from server: ".getBytes()));
                        while (buffer.hasRemaining()) {
                            channel.write(buffer);
                        }
                        buffer.clear();
                    } else if (readBytes == -1) {
                        System.out.printf("Connection to %s lost%n", channel.socket().getRemoteSocketAddress());
                        channel.close();
                    }

                }




            }
        } catch (IOException e) {
            System.out.println("Server exception "+e.getMessage());
        }
    }
}
