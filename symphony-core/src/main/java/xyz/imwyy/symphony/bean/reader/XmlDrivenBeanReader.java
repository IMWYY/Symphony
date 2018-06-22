package xyz.imwyy.symphony.bean.reader;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xyz.imwyy.symphony.bean.BeanDefinition;
import xyz.imwyy.symphony.bean.BeanReference;
import xyz.imwyy.symphony.bean.PropertyValue;
import xyz.imwyy.symphony.bean.factory.BeanFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 从xml文件中加载bean装入
 * create by stephen on 2018/6/21
 */
public class XmlDrivenBeanReader extends AbstractBeanDefinitionReader {

    private String location;

    public XmlDrivenBeanReader(BeanFactory beanFactory, String location) {
        super(beanFactory);
        this.location = location;
    }

    @Override
    public void loadBeanDefinitions() throws Exception {
        URL url = this.getClass().getClassLoader().getResource(location);
        if (url == null) {
            return;
        }
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        doLoadBeanDefinitions(inputStream);
    }


    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        // 解析bean
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    private void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    private void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(ele, beanDefinition);
        beanDefinition.setBeanClassName(className);
        getBeanFactory().putBean(name, beanDefinition);
    }

    /**
     * 为bean添加属性的注入
     */
    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }

}
