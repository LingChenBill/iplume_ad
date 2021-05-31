#### mysql数据导入
```text
vi ~/.zshrc
alias mysql=/usr/local/mysql/bin/mysql
source ~/.zshrc
```

#### mysql连接
```sql
mysql -h localhost -u root -p
mysql> source /Users/xxxxxx/Documents/fork/springcloud/iplume/iplume-ad/iplume-ad-service/iplume-ad-sponsor/src/main/resources/ad-sponsor.sql
mysql> show databases;
mysql> use iplume_ad;
mysql> show tables; 
mysql> desc ad_user;
mysql> quit;
```
#### 导入测试数据
```sql
mysql> source /Users/xxxxxx/Documents/fork/springcloud/iplume/iplume-ad/iplume-ad-service/iplume-ad-sponsor/src/main/resources/iplume-ad-init-data.sql
```

