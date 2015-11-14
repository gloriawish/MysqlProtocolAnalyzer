# 项目简介
MysqlProtocolAnalyzer 是一个对Mysql的通讯协议的包进行解析的库，纯java编写，输入文件可以是任意的16进制的文本文件，当然需要一定的预处理才能使用。本库是默认处理的是tcpdump产生的16进制的数据文件
## 详细介绍
http://blog.csdn.net/zhujunxxxxx/article/details/49837335
## 协议解析
能解析客户端与mysql服务交互的所有包，例如客户端与服务器连接的握手协议的包，所有COM_QUERY类型的语句，COM_STMT_PREPARE和COM_STMT_EXECUTE，还有ok包，以及resultset包等。
是根据官方的http://dev.mysql.com/doc/internals/en/text-protocol.html 协议进行解析的。
## 作者介绍
作者是一名软件工程学生党。目前在上海某985高校就读研究生，热爱新技术，热爱编程，为人幽默，热爱开源，研究方向有分布式数据库、高性能网络编程、java中间件 邮箱:zhujunxxxxx@163.com 博客: http://blog.csdn.net/zhujunxxxxx 
