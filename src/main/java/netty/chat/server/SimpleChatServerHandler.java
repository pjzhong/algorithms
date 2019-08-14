package netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;

/**
 * @author zhongjp
 * @since 2018/7/6
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

  private SimpleChatServer server;

  public SimpleChatServerHandler(SimpleChatServer server) {
    this.server = server;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    Channel incoming = ctx.channel();
    String output = String.format("[%s]: %s\n", incoming.attr(ServerConst.NAME).get(), s);
    server.boardCast(output);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.format("SimpleChatClient:%s online\n", ctx.channel().remoteAddress());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    Attribute<String> name = ctx.channel().attr(ServerConst.NAME);
    System.out
        .format("SimpleChatClient:%s-%s offline\n", ctx.channel().remoteAddress(), name.get());
  }


  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    if (incoming.hasAttr(ServerConst.NAME)) {
      Attribute<String> name = incoming.attr(ServerConst.NAME);
      String sb = "------------------------------------------------------\n"
          + String.format("[SERVER] - %s  is leaved, %d person(s) in this room\n",
          name.get(), server.onLine())
          + "------------------------------------------------------\n";
      server.boardCast(sb);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.format("SimpleChatClient:%s error\n", ctx.channel().remoteAddress());
    cause.printStackTrace();
    ctx.close();
  }

}
