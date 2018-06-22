package xyz.imwyy.symphony.bean;

/**
 * bean的引用 用于bean的一个属性是一个bean 而不是一个普通属性值
 * create by stephen on 2018/6/22
 */
public class BeanReference {

    private String id;

    public BeanReference(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
