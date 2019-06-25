package the.flash.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import the.flash.client.console.ConsoleCommandManager;
import the.flash.client.console.LoginConsoleCommand;
import the.flash.client.handler.ClientLifeCyCleTestHandler;
import the.flash.client.handler.CreateGroupResponseHandler;
import the.flash.client.handler.GroupMessageResponseHandler;
import the.flash.client.handler.JoinGroupResponseHandler;
import the.flash.client.handler.ListGroupMembersResponseHandler;
import the.flash.client.handler.LoginResponseHandler;
import the.flash.client.handler.MessageResponseHandler;
import the.flash.client.handler.QuitGroupResponseHandler;
import the.flash.codec.PacketDecoder;
import the.flash.codec.PacketEncoder;
import the.flash.codec.Spliter;
import the.flash.util.ClientSessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author 闪电侠
 */
public class NettyClient {
	private static final int MAX_RETRY = 5;
	public static final String username = "admin";
	public static final String userpass = "admin";
	public static final String wusername = "test";
	public static final String wuserpass = "admin";
	public static final AttributeKey<String> CHANNELATTRUSERNAME = AttributeKey.newInstance("USERNAME");
	public static final AttributeKey<String> CHANNELATTRUSERPASS = AttributeKey.newInstance("USERPASS");
	public static final AttributeKey<String> SEVER_RES_UUID = AttributeKey.newInstance("SEVER_RES_UUID");
	public static void main(String[] args) {
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap
				// 1.指定线程模型
				.group(workerGroup)
				// 2.指定 IO 类型为 NIO
				.channel(NioSocketChannel.class)
				// 绑定自定义属性到 channel
				.attr(CHANNELATTRUSERNAME, username)
				.attr(CHANNELATTRUSERPASS, userpass)
				// 设置TCP底层属性
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true)
				// 3.IO 处理逻辑
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
						ch.pipeline()
						.addLast(new ClientLifeCyCleTestHandler())
						.addLast(new Spliter(Integer.MAX_VALUE, 7, 4))
						.addLast(new PacketDecoder())
						.addLast(new LoginResponseHandler())
						.addLast(new MessageResponseHandler())
						.addLast(new CreateGroupResponseHandler())
						.addLast(new JoinGroupResponseHandler())
						.addLast(new QuitGroupResponseHandler())
						.addLast(new ListGroupMembersResponseHandler())
						.addLast(new GroupMessageResponseHandler())
						.addLast(new PacketEncoder());
					}
				});

		// 4.建立连接
		for (int i = 0; i < 1; i++) {
			connect(bootstrap, "localhost", 8000, MAX_RETRY);
		}
	}

	private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
		bootstrap.connect(host, port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("连接成功!");
				Channel channel = ((ChannelFuture) future).channel();
				// 连接成功之后，启动控制台线程
				startConsoleThread(channel);
			} else if (retry == 0) {
				System.err.println("重试次数已用完，放弃连接！");
			} else {
				// 第几次重连
				int order = (MAX_RETRY - retry) + 1;
				// 本次重连的间隔
				int delay = 1 << order;
				System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
				bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay,
						TimeUnit.SECONDS);
			}
		});
	}

	private static void startConsoleThread(Channel channel) {
		ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!ClientSessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
	}
}
