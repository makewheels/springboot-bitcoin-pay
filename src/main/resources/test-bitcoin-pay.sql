CREATE TABLE `pay_order` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '自增',
	`uuid` VARCHAR(100) COMMENT 'id',
	`name` VARCHAR(500) COMMENT '订单名',

	`create_time` DATETIME COMMENT '订单创建时间',
	`invalid_time` DATETIME COMMENT '失效时间，必须在此时间之前支付',
	`valid_time_length` BIGINT COMMENT '有效时长',
	`pay_time` DATETIME COMMENT '支付时间',

	`pay_method` VARCHAR(100) COMMENT '支付方式，例如：CNY,BTC,ETH',

	`legal_tender_currency` VARCHAR(100) COMMENT '法币种类，例如：CNY，USD',
	`legal_tender_amount` INT COMMENT '法币数量，按最小单位计',

	`digital_currency` VARCHAR(100) COMMENT '数字货币种类，例如：BTC,ETH',
	`bitcoin_amount` BIGINT COMMENT '比特币金额按satoshi计',

	`bitcoin_price_usd` INT COMMENT '比特币美元价格',

	PRIMARY KEY (`id`)
);

CREATE TABLE `bitcoin_address` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT 'uuid',
	`uuid` VARCHAR(100) COMMENT 'id',
	`create_time` DATETIME,
	`order_id` INT,
	`address` VARCHAR(100),
	`private_key` VARCHAR(200),
	`label` VARCHAR(100),
	`satoshi` BIGINT,
	PRIMARY KEY (`id`)
);

CREATE TABLE `bitcoin_transaction` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT 'uuid',
	`uuid` VARCHAR(100) COMMENT 'id',
	`create_time` DATETIME,
	`receive_address` INT,
	`satoshi` BIGINT,

	`timereceived` DATETIME,
	`confirmations` INT,
	`txid` VARCHAR(100),

	PRIMARY KEY (`id`)
);