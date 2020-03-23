use testdb;
create table merchant
(
    id                  bigint unsigned auto_increment primary key,
    merchant_code       bigint unsigned not null,
    merchant_name       varchar(32)    not null,
    merchant_public_key varchar(4096),
    jd_key_id           bigint unsigned,
    remark              varchar(128),
    creator             varchar(32),
    create_time         datetime(3) default current_timestamp(3),
    modifier            varchar(32),
    update_time         datetime(3) default current_timestamp(3) on update current_timestamp(3)
);