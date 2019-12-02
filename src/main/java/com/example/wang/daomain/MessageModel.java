package com.example.wang.daomain;

import com.example.wang.entity.ArticleItem;
import com.example.wang.util.CheckUtil;
import com.example.wang.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageModel {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = CheckUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get(Constant.MsgType);
            String mes = null;
            logger.info("接收消息：" + mes);
            // 文本消息
            switch (msgType) {
                case Constant.REQ_MESSAGE_TYPE_TEXT:
                    mes = requestMap.get(Constant.Content);
                    if ("定位".equals(mes)) {
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("定位");
                        item.setDescription("获取定位");
                        item.setPicUrl("https://mmbiz.qpic.cn/mmbiz_jpg/Om5G7fOoIW6Xq2jj9fribs3PlUdB4WGHWCm704Fg1K15kt2YRkEkicYv5Qf27Gh57TeaQbtyQxgviaSyNNHaUBIvA/0?wx_fmt=jpeg");
                        item.setUrl("http://www.wangxinxiang.club:8080/test.html");
                        items.add(item);
                        respXml = CheckUtil.sendArticleMsg(requestMap, items);
                    } else if (mes != null && mes.length() < 2) {
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("照片墙");
                        item.setDescription("阿狸照片墙");
                        item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                        item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                        items.add(item);

                        item = new ArticleItem();
                        item.setTitle("哈哈");
                        item.setDescription("一张照片");
                        item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                        item.setUrl("http://changhaiwx.pagekite.me/page/index");
                        items.add(item);

                        item = new ArticleItem();
                        item.setTitle("小游戏2048");
                        item.setDescription("小游戏2048");
                        item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                        item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                        items.add(item);

                        item = new ArticleItem();
                        item.setTitle("百度");
                        item.setDescription("百度一下");
                        item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                        item.setUrl("http://www.baidu.com");
                        items.add(item);

                        respXml = CheckUtil.sendArticleMsg(requestMap, items);
                    }
                    break;
                // 图片消息
                case Constant.REQ_MESSAGE_TYPE_IMAGE:
                    respContent = "您发送的是图片消息！";
                    respXml = CheckUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 语音消息
                case Constant.REQ_MESSAGE_TYPE_VOICE:
                    respContent = "您发送的是语音消息！";
                    respXml = CheckUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 视频消息
                case Constant.REQ_MESSAGE_TYPE_VIDEO:
                    respContent = "您发送的是视频消息！";
                    respXml = CheckUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 地理位置消息
                case Constant.REQ_MESSAGE_TYPE_LOCATION:
                    respContent = "您发送的是地理位置消息！";
                    respXml = CheckUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 链接消息
                case Constant.REQ_MESSAGE_TYPE_LINK:
                    respContent = "您发送的是链接消息！";
                    respXml = CheckUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 事件推送
                case Constant.REQ_MESSAGE_TYPE_EVENT:
                    // 事件类型
                    String eventType = (String) requestMap.get(Constant.Event);
                    // 关注
                    switch (eventType) {
                        case Constant.EVENT_TYPE_SUBSCRIBE:
                            respContent = "谢谢您的关注！";
                            respXml = CheckUtil.sendTextMsg(requestMap, respContent);
                            break;
                        // 取消关注
                        case Constant.EVENT_TYPE_UNSUBSCRIBE:
                            // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                            break;
                        // 扫描带参数二维码
                        case Constant.EVENT_TYPE_SCAN:
                            // TODO 处理扫描带参数二维码事件
                            break;
                        // 上报地理位置
                        case Constant.EVENT_TYPE_LOCATION:
                            // TODO 处理上报地理位置事件
                            break;
                        // 自定义菜单
                        case Constant.EVENT_TYPE_CLICK:
                            // TODO 处理菜单点击事件
                            break;
                    }
                    break;
            }
            mes = mes == null ? "不知道你在干嘛" : mes;
            if (respXml == null)
                respXml = CheckUtil.sendTextMsg(requestMap, mes);
            logger.info(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
