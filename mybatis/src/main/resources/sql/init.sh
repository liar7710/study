mysqld --initialize-insecure --user=root --datadir=../data_master
mysql_ssl_rsa_setup --datadir=../data_master

mysqld --initialize-insecure --user=root --datadir=../data_slave
mysql_ssl_rsa_setup --datadir=../data_slave
