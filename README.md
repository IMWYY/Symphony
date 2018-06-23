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
  - 支持spring风格的xml定义的bean。见类`XmlDrivenSymphonyContext`
- AOP
  - @Aspect 切面注解
  - @Before @After @Around 切入点注解，支持表达式。具体见`ExpressionPointCut`
  - @TransactionalBean 事务类注解
  - @TransactionalMethod 事务方法注解

## TODO列表

- [ ] 框架优化ing
- [ ] 数据库操作集成（mybatis）
- [ ] symphony-cloud 分布式框架

## 使用和约束

使用时需要引入maven并在`resource` 文件夹下添加`symphony.properties`配置文件。

```properties
symphony.jdbc.driver=com.mysql.jdbc.Driver
symphony.jdbc.url=jdbc:mysql://localhost:3306/symphony
symphony.jdbc.username=wyy
symphony.jdbc.password=wyy
symphony.base_package=xyz.imwyy		
symphony.jsp_path=/view/
```

- MVC框架
  - @route注解支持方法类型