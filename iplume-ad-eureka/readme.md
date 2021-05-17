### eureka 高可用.
http://localhost:8000/

### 配置文件.
分隔符`---`

profiles: server1

### 打包.
使maven工具生效:确认已经配置maven与java的环境变量.  
% source ~/.bash_profile  
查看mac中的java安装路径:  
%  /usr/libexec/java_home -V  

% cd ~/Documents/fork/springcloud/iplume/iplume-ad  

打包命令:  
mvn clean package -Dmaven.test.skip=true -U  

cd ~/Documents/fork/springcloud/iplume/iplume-ad/iplume-ad-eureka/target  

java -jar iplume-ad-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=server1  
java -jar iplume-ad-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=server2  
java -jar iplume-ad-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=server3  
