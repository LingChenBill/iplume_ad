#### 插入ad_plan数据.
mysql> insert into `ad_plan` (`user_id`, `plan_name`, `plan_status`, `start_date`, `end_date`, `create_time`, `update_time`) values (10, '计划一', 1, '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00', '2021-01-01 00:00:00');  
Query OK, 1 row affected (0.00 sec)  

启动BinlogServiceTest的main():  
查看日期的格式:  
21:44:46.517 [main] INFO com.iplume.ad.service.BinlogServiceTest - Add---------------------  
21:44:46.518 [main] INFO com.iplume.ad.service.BinlogServiceTest - WriteRowsEventData{tableId=68, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
    [10, 10, 计划一, 1, Fri Jan 01 08:00:00 CST 2021, Fri Jan 01 08:00:00 CST 2021, Fri Jan 01 08:00:00 CST 2021, Fri Jan 01 08:00:00 CST 2021]
]}  
