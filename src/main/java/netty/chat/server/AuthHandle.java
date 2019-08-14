package netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

public class AuthHandle extends SimpleChannelInboundHandler<String> {

  private SimpleChatServer chatServer;

  public AuthHandle(SimpleChatServer chatServer) {
    this.chatServer = chatServer;
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel incoming = ctx.channel();
    incoming.writeAndFlush("What is Your name:\n");
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    Channel channel = ctx.channel();
    if (channel.hasAttr(ServerConst.NAME)) {
      ctx.fireChannelRead(msg);
    } else {
      if (StringUtil.isNullOrEmpty(msg)) {
        channel.writeAndFlush("What is Your name:\n");
      } else {
        chatServer.addChannel(channel);
        channel.attr(ServerConst.NAME).set(msg);
        String join = "------------------------------------------------------\n"
            + String.format("[SERVER] - Welcome %s, %d person(s) in this room\n",
            msg, chatServer.onLine())
            + "------------------------------------------------------\n";
        chatServer.boardCast(join);
        System.out.format("SimpleChatClient:%s-%s is connected\n", channel.remoteAddress(), msg);
      }
    }
  }


}
