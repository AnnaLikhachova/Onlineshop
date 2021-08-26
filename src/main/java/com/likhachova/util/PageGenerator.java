package com.likhachova.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class PageGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PageGenerator.class);

    private static final String HTML_DIR = "/templates/";

    private static PageGenerator  INSTANCE = new PageGenerator();
    private Configuration configuration;

    public PageGenerator() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(PageGenerator.class, HTML_DIR);
    }

    public static PageGenerator getInstance(){
        return INSTANCE;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            logger.error("Error when generating template", e);
            throw new RuntimeException(e);
        }
        return stream.toString();
    }
}
