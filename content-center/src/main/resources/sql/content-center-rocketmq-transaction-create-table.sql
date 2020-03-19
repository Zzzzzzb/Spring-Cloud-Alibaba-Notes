USE `content_center`;

-- -----------------------------------------------------
-- Table `rocketmq_transaction_log`
-- -----------------------------------------------------
create table rocketmq_transaction_log
(
  id             int auto_increment comment 'id'
    primary key,
  transaction_Id varchar(45) not null comment '事务id',
  log            varchar(45) not null comment '日志'
)
  comment 'RocketMQ事务日志表';