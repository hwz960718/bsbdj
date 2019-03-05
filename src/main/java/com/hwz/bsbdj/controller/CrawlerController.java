package com.hwz.bsbdj.controller;

import com.hwz.bsbdj.service.CrawlerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author hwz
 * @date 2019/2/19
 * Crawler:爬虫
 */
@Controller
public class CrawlerController {
    @Resource
    private CrawlerService crawlerService;

    @RequestMapping("/crawl")
    @ResponseBody
    public String crawl() {
        crawlerService.crawlerRunner();
        return null;
//        return result;
    }
}
