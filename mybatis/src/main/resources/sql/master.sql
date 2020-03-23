SET GLOBAL master_info_repository = 'TABLE';
SET GLOBAL relay_log_info_repository = 'TABLE';

CREATE USER 'repl'@'localhost' IDENTIFIED with caching_sha2_password BY 'wh960903';
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'localhost';

FLUSH TABLES WITH READ LOCK;
show master status;
UNLOCK TABLES;