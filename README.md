`DataM轻量数据实时处理服务`
========================
#使用Redis做消息队列，实时数据上报<br>
###data-app-service用于接收http上报数据<br>
###data-app-consumer用于接收Redis队列数据<br>
###data-app-recover用于恢复数据，直接导入数据库<br>

##DataM<br/>
---------------
DataM是基于Spring boot,Redis,Mysql,阻塞队列组成的实时数据采集服务，采用Spring boot作为运行框架，多线程异步执行任务，
并通过线程安全队列缓冲达到较高可用的目的
![](https://github.com/V-I-C-T-O-R/DataM/blob/master/2015031713531123860292.jpg) <br>

###技能点:<br>
* Spring boot异步事件<br>
* Redis发布/订阅服务<br>
* 多线程读取任务<br>
* 简单数据容灾恢复<br>
* 数据在线实时采集<br>
* 消息队列缓冲<br>
* 数据库表切分<br>
* 数据库分区表<br>

###整体流程：<br>
![](https://github.com/V-I-C-T-O-R/DataM/blob/master/data.png) <br>
