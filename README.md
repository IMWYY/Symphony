# Symphony 

为了学习spring架构，自己实践写的一个轻量级web框架。

## TODO列表

- [x] MVC框架
  - @controller 控制器注解
  - @Route 路由注解
- [x] IOC 
  - @Inject 属性注入注解
  - @Service 逻辑层注解
- [x] AOP
  - @Aspect 切面注解 
  - @TransactionalBean 事务类注解
  - @TransactionalMethod 事务方法注解
- [ ] 框架优化ing
  - 引入ApplicationContext作为入口
- [ ] 集成mybatis
- [ ] symphony-cloud 分布式框架



## 使用

使用时需要引入maven并在`resource` 文件夹下添加`symphony.properties`配置文件。

```java
symphony.jdbc.driver=com.mysql.jdbc.Driver
symphony.jdbc.url=jdbc:mysql://localhost:3306/symphony
symphony.jdbc.username=wyy
symphony.jdbc.password=wyy

symphony.base_package=xyz.imwyy		
symphony.jsp_path=/view/
```

