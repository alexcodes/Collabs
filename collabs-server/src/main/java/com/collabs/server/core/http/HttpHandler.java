package com.collabs.server.core.http;

import com.collabs.common.pluggable.HttpPlugin;
import com.collabs.common.pluggable.PluginInfo;
import com.collabs.server.data.PluginStorage;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Aleksey A.
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        String pluginName = getPluginName(msg.getUri());
        if (PluginStorage.get().containsId(pluginName.hashCode())) {
            PluginInfo pluginInfo = PluginStorage.get().get(pluginName.hashCode());
            if (pluginInfo.getPlugin() instanceof HttpPlugin) {
                HttpPlugin plugin = (HttpPlugin) pluginInfo.getPlugin();
                plugin.setRequest(msg.getUri());
                response.content().writeBytes(plugin.getResponse().getBytes());
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                sendError(ctx, HttpResponseStatus.FORBIDDEN);
            }
        } else {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
        }
    }

    private String getPluginName(String uri) {
        if (uri.length() > 1) {
            return uri.substring(1);
        }
        return uri;
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
