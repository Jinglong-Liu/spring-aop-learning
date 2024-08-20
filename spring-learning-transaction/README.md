# Spring 事务传播机制

## 运行测试

### 版本说明

仓库根目录的pom.xml中有说明。

不需要严格一致，只是可以跑通的一个版本

### 运行步骤

1、新建数据库tx-test，运行 sql/tx-test.sql

2、将application.yaml中的spring.datasource.url更改为你的数据库的信息

（可以通过 application-test.yaml 中修改部分mysql的属性）

3、启动test下的测试类 TxApplicationTest，运行测试用例
