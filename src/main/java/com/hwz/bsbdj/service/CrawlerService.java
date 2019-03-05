package com.hwz.bsbdj.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwz.bsbdj.entity.Source;
import com.hwz.bsbdj.mapper.SourceMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hwz
 * @date 2019/2/20
 */
@Service
public class CrawlerService {
    @Resource
    private SourceMapper sourceMapper;
    //生成日志
    Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    Gson gson = new Gson();

    /**
     * 抓取数据
     *
     * @param template
     * @param np
     */
    public void crawl(String template, String np, Map context, Integer channelId) {
        String url = template.replace("{np}", np);
//        String url = "http://c.api.budejie.com/topic/list/jingxuan/1/budejie-android-6.9.4/0-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00";
        /**
         * 使用OKhttp
         * 1、创建OKhttp 对象
         */
        OkHttpClient okHttpClient = new OkHttpClient();
        //2、构建请求
        Request.Builder builder = new Request.Builder().url(url);
        /**
         * 增加请求头  这个很重要
         */
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
        Request request = builder.build();

        //3、发送请求，返回响应
        String responseText = null;
        for (int i = 0; i < 10; i++) {
            try {
                Response response = okHttpClient.newCall(request).execute();
                //获取响应的数据
                responseText = response.body().string();
                //抓取成功后跳出循环
                break;
            } catch (IOException e) {
                logger.warn("爬虫连接超时，正准备第{}次重试,URL:{}", (i + 1), url);
            }
        }
        //如果10次抓取都失败，则程序中断
        if (responseText == null) {
            logger.error("爬虫抓取失败，连接超时,URL:" + url);
        }
        /**
         * 转换为map对象
         */
        Map map = gson.fromJson(responseText, new TypeToken<Map>() {
        }.getType());
        //获取info下面的np 的value
        Map info = (Map) map.get("info");
        Double dnp = (Double) info.get("np");
        /**
         *  这里的np不是我们想要的原始数据，是经过科学计数法过后的数据
         *  通过DecimalFormat进行转换
         */
        String npFormat = new DecimalFormat("##########").format(dnp);//1550639521
        /**
         * 保存数据
         */
        Source source = new Source();
        source.setChannelId(channelId);
        source.setCreateTime(new Date());
        source.setResponseText(responseText);
        source.setUrl(url);
        source.setState("WAITING");//等待被处理的状态
        sourceMapper.insert(source);

        logger.info("数据抓取成功,NP:{},URL:{}", npFormat, url);
        //只抓取最近的100条数据，每小时抓一次
        int count = (int) context.get("count");
        count = count + 20;
        context.put("count", count);
        if (count >= 100) return;
        /**
         * 利用递归进行后面的数据抓取
         */
        crawl(template, npFormat, context, channelId);
    }

    public void crawlerRunner() {
        //url模板
//        String template = "http://c.api.budejie.com/topic/list/jingxuan/1/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00";
        // URL模板
        String[] templates = new String[]{
                "http://c.api.budejie.com/topic/list/jingxuan/1/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"
                , "http://c.api.budejie.com/topic/list/jingxuan/41/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"
                , "http://c.api.budejie.com/topic/list/jingxuan/10/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"
                , "http://c.api.budejie.com/topic/list/jingxuan/29/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"
                , "http://s.budejie.com/topic/list/remen/1/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"
        };
        for (int i = 0; i < templates.length; i++) {
            logger.info("正在抓取第{}个模块的数据", i + 1);
            String template = templates[i];
            //记录抓取的总数
            Map context = new HashMap();
            context.put("count", 0);
            this.crawl(template, "0", context, (i + 1));
        }
    }
}
