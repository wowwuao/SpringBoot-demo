# SpringBoot-demo

基于 SpringBoot 2.7.11 开发

1. 基本增删改查,数据分页
2. CORS 跨域请求配置
3. 保存登录态 与 用户权限验证。如果遇到前端每次刷新都需要重新登录,首先检查登录后的setCookie生效了吗,如果生效了,看看每次请求时是不是忘记了携带cookie...
4. knife4j 接口文档,v3版本支持 openapi ,进行快速生成前端请求代码
5. nacos 注册微服务
6. 基于 OpenFeign 进行微服务远程调用
7. gateway 网关拦截与转发
8. sentinel 流量监控



供 初学者学习 与 开发时快速CV相应功能

如果遇到问题,首先考虑是不是依赖项版本之间有冲突 或者 中文目录名



新建表user
```mysql
CREATE DATABASE demo;

USE demo;

CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;


INSERT INTO `user` VALUES (2, 'user1', '62cc2d8b4bf2d8728120d052163a77df', 'user1昵称', 'user', '2023-05-17 13:55:13', '2023-05-18 00:54:37', 0);
INSERT INTO `user` VALUES (3, 'user2', '62cc2d8b4bf2d8728120d052163a77df', 'user2昵称', 'user', '2023-05-17 14:26:28', '2023-05-17 14:26:28', 0);
INSERT INTO `user` VALUES (4, 'user3', '62cc2d8b4bf2d8728120d052163a77df', 'user3昵称', 'user', '2023-05-17 14:26:37', '2023-05-17 14:27:22', 0);
INSERT INTO `user` VALUES (5, 'user4', '62cc2d8b4bf2d8728120d052163a77df', 'user4昵称', 'user', '2023-05-17 14:26:41', '2023-05-17 14:27:25', 0);
INSERT INTO `user` VALUES (6, 'user5', '62cc2d8b4bf2d8728120d052163a77df', 'user5昵称', 'user', '2023-05-17 14:26:46', '2023-05-17 14:27:31', 0);
INSERT INTO `user` VALUES (7, 'admin', '62cc2d8b4bf2d8728120d052163a77df', 'admin昵称', 'admin', '2023-05-18 00:06:56', '2023-05-18 22:34:46', 0);
INSERT INTO `user` VALUES (8, 'user6', '62cc2d8b4bf2d8728120d052163a77df', 'user6昵称', 'user', '2023-05-18 00:22:58', '2023-05-18 00:22:58', 0);
INSERT INTO `user` VALUES (9, 'user7', '62cc2d8b4bf2d8728120d052163a77df', 'user7昵称', 'user', '2023-05-18 00:23:13', '2023-05-18 00:23:13', 0);
INSERT INTO `user` VALUES (10, 'user8', '62cc2d8b4bf2d8728120d052163a77df', 'user8昵称', 'user', '2023-05-18 00:23:38', '2023-05-18 00:50:51', 0);
INSERT INTO `user` VALUES (11, 'user9', '62cc2d8b4bf2d8728120d052163a77df', 'user9昵称', 'user', '2023-05-18 00:24:00', '2023-05-18 00:24:00', 0);
INSERT INTO `user` VALUES (12, 'user10', '62cc2d8b4bf2d8728120d052163a77df', 'user10昵称', 'user', '2023-05-18 00:24:14', '2023-05-18 00:24:14', 0);
```

## 启动前注意事项

### 文件更改

1. 修改 src/main/resources/application.yml 中的数据库配置
2. 在 feign-api 项目中,进行 maven install,使本地拥有 feign-api 依赖包

### 搭建本地 nacos 服务器

[nacos下载地址](https://github.com/alibaba/nacos/releases/download/2.2.2/nacos-server-2.2.2.zip)

解压后,修改  /conf/application.properties

第153行,自定义一个密钥

然后打开 /bin

cmd  输入

```shell
./startup.cmd -m standalone
```

默认为8848端口,打开 [http://127.0.0.1:8848/nacos](http://127.0.0.1:8848/nacos)

账号密码均默认为 nacos

### 搭建本地 Sentinel 服务器

[sentinel 下载地址](https://github.com/alibaba/Sentinel/releases/download/1.8.6/sentinel-dashboard-1.8.6.jar)

sentinel默认端口为 8080,与主项目冲突

所以cmd打开命令为

```cmd
java '-Dserver.port=8500'  '-jar' sentinel-dashboard-1.8.6.jar
```

http://127.0.0.1:8500

账号密码均默认为 sentinel

sentinel只有网关被请求后才会有服务名称与数据显示,所以进入后为空是正常现象