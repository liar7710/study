mysqldump --all-databases --master-data > dbdump.db
--ignore-table=db_name.tbl_name
不要转储给定的表,要忽略多个表，请多次使用此选项
--databases,-B
转储指定数据库
--all-databases,-A
转储所有数据库

mysql -h master < fulldb.dump
mysql < fulldb.dump