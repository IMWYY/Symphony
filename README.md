# Symphony 

为了学习spring架构，自己实践写的一个轻量级web框架。

## 功能介绍

- MVC框架
  - @controller 控制器注解
  - @Route 路由注解
- IOC 
  - @Inject 属性注入注解
  - @Service 逻辑层注解
  - @Component 普通bean注解
  - 支持spring风格的xml定义的bean。见类[XmlDrivenSymphonyContext](https://github.com/IMWYY/Symphony/blob/master/symphony-core/src/main/java/xyz/imwyy/symphony/context/XmlDrivenSymphonyContext.java)
- AOP
  - @Aspect 切面注解
  - @Before @After @Around 切入点注解，支持表达式。具体见[ExpressionPointCut](https://github.com/IMWYY/Symphony/blob/master/symphony-core/src/main/java/xyz/imwyy/symphony/aop/advisor/ExpressionPointCut.java)
  - @TransactionalBean 事务类注解
  - @TransactionalMethod 事务方法注解

## TODO列表

- [ ] 框架优化ing
- [ ] 数据库操作集成（mybatis）
- [ ] symphony-cloud 分布式框架

## 使用和约束

- 使用时需要引入maven并在`resource` 文件夹下添加`symphony.properties`配置文件。

```properties
symphony.jdbc.driver=com.mysql.jdbc.Driver
symphony.jdbc.url=jdbc:mysql://localhost:3306/symphony
symphony.jdbc.username=wyy
symphony.jdbc.password=wyy		
symphony.base_package=xyz.imwyy		// 需要扫描的包
symphony.jsp_path=/view/			
```

- 所有的jsp文件应放在`symphony.jsp_path`指定的文件夹内，默认为`/`
- @Before @After @Around 需要指定类的约束`classExpression`和方法的约束`methodExpression`。目前仅支持`*`用于匹配任意字符要求。
- @TransactionalBean 和 @TransactionalMethod 注解必须同时使用，否则不起作用。

