CREATE DATABASE IF NOT EXISTS mbga CHARACTER
    SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE `mbga`.`user`
(
    `id`          bigint PRIMARY KEY  NOT NULL AUTO_INCREMENT,
    `username`    varchar(255) UNIQUE NOT NULL COMMENT '用户名',
    `password`    varchar(255)        NOT NULL COMMENT '密码',
    `create_time` datetime            NOT NULL COMMENT '创建时间',
    `update_time` datetime            NOT NULL COMMENT '更新时间',
    `nickname`    varchar(255)        NOT NULL COMMENT '昵称',
    `avatar`      varchar(255)        NOT NULL COMMENT '头像地址',
    `email`       varchar(255)        NOT NULL COMMENT '邮箱',
    `enable`      BOOLEAN             NOT NULL DEFAULT true
);

CREATE TABLE `mbga`.`permission`
(
    `id`   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(255)       NOT NULL,
    `name` VARCHAR(255)       NOT NULL
);

CREATE TABLE `mbga`.`role`
(
    `id`   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(255)       NOT NULL,
    `name` VARCHAR(255)       NOT NULL
);

CREATE TABLE `mbga`.`role_permission_map`
(
    `id`            BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role_id`       BIGINT             NOT NULL,
    `permission_id` BIGINT             NOT NULL
);

CREATE TABLE `mbga`.`user_role_map`
(
    `id`      BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT             NOT NULL,
    `role_id` BIGINT             NOT NULL
);

CREATE TABLE `mbga`.`about`
(
    `id`      bigint PRIMARY KEY NOT NULL,
    `name_en` varchar(255) DEFAULT null,
    `name_zh` varchar(255) DEFAULT null,
    `value`   longtext
);

CREATE TABLE `mbga`.`blog`
(
    `id`                 bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `title`              varchar(255)       NOT NULL COMMENT '文章标题',
    `first_picture`      varchar(255)       NOT NULL COMMENT '文章首图，用于随机文章展示',
    `content`            longtext           NOT NULL COMMENT '文章正文',
    `description`        longtext           NOT NULL COMMENT '描述',
    `is_published`       bit(1)             NOT NULL COMMENT '公开或私密',
    `is_recommend`       bit(1)             NOT NULL COMMENT '推荐开关',
    `is_appreciation`    bit(1)             NOT NULL COMMENT '赞赏开关',
    `is_comment_enabled` bit(1)             NOT NULL COMMENT '评论开关',
    `create_time`        datetime           NOT NULL COMMENT '创建时间',
    `update_time`        datetime           NOT NULL COMMENT '更新时间',
    `views`              int                NOT NULL COMMENT '浏览次数',
    `words`              int                NOT NULL COMMENT '文章字数',
    `read_time`          int                NOT NULL COMMENT '阅读时长(分钟)',
    `category_id`        bigint             NOT NULL COMMENT '文章分类',
    `is_top`             bit(1)             NOT NULL COMMENT '是否置顶',
    `password`           varchar(255) DEFAULT null COMMENT '密码保护',
    `user_id`            bigint             NOT NULL COMMENT '文章作者'
);

CREATE TABLE `mbga`.`blog_tag`
(
    `id`      bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `blog_id` bigint             NOT NULL,
    `tag`     varchar(255)       NOT NULL COMMENT '标记'
);

CREATE TABLE `mbga`.`category`
(
    `id`            bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `category_name` varchar(255)       NOT NULL
);

CREATE TABLE `mbga`.`city_visitor`
(
    `city` varchar(255) PRIMARY KEY NOT NULL COMMENT '城市名称',
    `uv`   int                      NOT NULL COMMENT '独立访客数量'
);

CREATE TABLE `mbga`.`comment`
(
    `id`                bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `nickname`          varchar(255)       NOT NULL COMMENT '昵称',
    `email`             varchar(255)       NOT NULL COMMENT '邮箱',
    `content`           varchar(255)       NOT NULL COMMENT '评论内容',
    `avatar`            varchar(255)       NOT NULL COMMENT '头像(图片路径)',
    `create_time`       datetime     DEFAULT null COMMENT '评论时间',
    `ip`                varchar(255) DEFAULT null COMMENT '评论者ip地址',
    `is_published`      bit(1)             NOT NULL COMMENT '公开或回收站',
    `is_admin_comment`  bit(1)             NOT NULL COMMENT '博主回复',
    `page`              int                NOT NULL COMMENT '0普通文章，1关于我页面，2友链页面',
    `is_notice`         bit(1)             NOT NULL COMMENT '接收邮件提醒',
    `blog_id`           bigint       DEFAULT null COMMENT '所属的文章',
    `parent_comment_id` bigint             NOT NULL COMMENT '父评论id，-1为根评论',
    `website`           varchar(255) DEFAULT null COMMENT '个人网站',
    `qq`                varchar(255) DEFAULT null COMMENT '如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份'
);

CREATE TABLE `mbga`.`exception_log`
(
    `id`          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `uri`         varchar(255)       NOT NULL COMMENT '请求接口',
    `method`      varchar(255)       NOT NULL COMMENT '请求方式',
    `param`       varchar(2000) DEFAULT null COMMENT '请求参数',
    `description` varchar(255)  DEFAULT null COMMENT '操作描述',
    `error`       text COMMENT '异常信息',
    `ip`          varchar(255)  DEFAULT null COMMENT 'ip',
    `ip_source`   varchar(255)  DEFAULT null COMMENT 'ip来源',
    `os`          varchar(255)  DEFAULT null COMMENT '操作系统',
    `browser`     varchar(255)  DEFAULT null COMMENT '浏览器',
    `create_time` datetime           NOT NULL COMMENT '操作时间',
    `user_agent`  varchar(2000) DEFAULT null COMMENT 'user-agent用户代理'
);

CREATE TABLE `mbga`.`friend`
(
    `id`           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `nickname`     varchar(255)       NOT NULL COMMENT '昵称',
    `description`  varchar(255)       NOT NULL COMMENT '描述',
    `website`      varchar(255)       NOT NULL COMMENT '站点',
    `avatar`       varchar(255)       NOT NULL COMMENT '头像',
    `is_published` bit(1)             NOT NULL COMMENT '公开或隐藏',
    `views`        int                NOT NULL COMMENT '点击次数',
    `create_time`  datetime           NOT NULL COMMENT '创建时间'
);

CREATE TABLE `mbga`.`login_log`
(
    `id`          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `username`    varchar(255)       NOT NULL COMMENT '用户名称',
    `ip`          varchar(255)  DEFAULT null COMMENT 'ip',
    `ip_source`   varchar(255)  DEFAULT null COMMENT 'ip来源',
    `os`          varchar(255)  DEFAULT null COMMENT '操作系统',
    `browser`     varchar(255)  DEFAULT null COMMENT '浏览器',
    `status`      bit(1)        DEFAULT null COMMENT '登录状态',
    `description` varchar(255)  DEFAULT null COMMENT '操作描述',
    `create_time` datetime           NOT NULL COMMENT '登录时间',
    `user_agent`  varchar(2000) DEFAULT null COMMENT 'user-agent用户代理'
);

CREATE TABLE `mbga`.`moment`
(
    `id`           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `content`      longtext           NOT NULL COMMENT '动态内容',
    `create_time`  datetime           NOT NULL COMMENT '创建时间',
    `likes`        int DEFAULT null COMMENT '点赞数量',
    `is_published` bit(1)             NOT NULL COMMENT '是否公开'
);

CREATE TABLE `mbga`.`operation_log`
(
    `id`          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `username`    varchar(255)       NOT NULL COMMENT '操作者用户名',
    `uri`         varchar(255)       NOT NULL COMMENT '请求接口',
    `method`      varchar(255)       NOT NULL COMMENT '请求方式',
    `param`       varchar(2000) DEFAULT null COMMENT '请求参数',
    `description` varchar(255)  DEFAULT null COMMENT '操作描述',
    `ip`          varchar(255)  DEFAULT null COMMENT 'ip',
    `ip_source`   varchar(255)  DEFAULT null COMMENT 'ip来源',
    `os`          varchar(255)  DEFAULT null COMMENT '操作系统',
    `browser`     varchar(255)  DEFAULT null COMMENT '浏览器',
    `times`       int                NOT NULL COMMENT '请求耗时（毫秒）',
    `create_time` datetime           NOT NULL COMMENT '操作时间',
    `user_agent`  varchar(2000) DEFAULT null COMMENT 'user-agent用户代理'
);

CREATE TABLE `mbga`.`schedule_job`
(
    `job_id`      bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `bean_name`   varchar(255) DEFAULT null COMMENT 'spring bean名称',
    `method_name` varchar(255) DEFAULT null COMMENT '方法名',
    `params`      varchar(255) DEFAULT null COMMENT '参数',
    `cron`        varchar(255) DEFAULT null COMMENT 'cron表达式',
    `status`      tinyint      DEFAULT null COMMENT '任务状态',
    `remark`      varchar(255) DEFAULT null COMMENT '备注',
    `create_time` datetime     DEFAULT null COMMENT '创建时间'
);

CREATE TABLE `mbga`.`schedule_job_log`
(
    `log_id`      bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
    `job_id`      bigint             NOT NULL COMMENT '任务id',
    `bean_name`   varchar(255) DEFAULT null COMMENT 'spring bean名称',
    `method_name` varchar(255) DEFAULT null COMMENT '方法名',
    `params`      varchar(255) DEFAULT null COMMENT '参数',
    `status`      tinyint            NOT NULL COMMENT '任务执行结果',
    `error`       text COMMENT '异常信息',
    `times`       int                NOT NULL COMMENT '耗时（单位：毫秒）',
    `create_time` datetime     DEFAULT null COMMENT '创建时间'
);

CREATE TABLE `mbga`.`site_setting`
(
    `id`      bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name_en` varchar(255) DEFAULT null,
    `name_zh` varchar(255) DEFAULT null,
    `value`   longtext,
    `type`    int          DEFAULT null COMMENT '1基础设置，2页脚徽标，3资料卡，4友链信息'
);

CREATE TABLE `mbga`.`tag`
(
    `id`       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `tag_name` varchar(255)       NOT NULL,
    `color`    varchar(255) DEFAULT null COMMENT '标签颜色(可选)'
);

CREATE TABLE `mbga`.`visit_log`
(
    `id`          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `uuid`        varchar(36)   DEFAULT null COMMENT '访客标识码',
    `uri`         varchar(255)       NOT NULL COMMENT '请求接口',
    `method`      varchar(255)       NOT NULL COMMENT '请求方式',
    `param`       varchar(2000)      NOT NULL COMMENT '请求参数',
    `behavior`    varchar(255)  DEFAULT null COMMENT '访问行为',
    `content`     varchar(255)  DEFAULT null COMMENT '访问内容',
    `remark`      varchar(255)  DEFAULT null COMMENT '备注',
    `ip`          varchar(255)  DEFAULT null COMMENT 'ip',
    `ip_source`   varchar(255)  DEFAULT null COMMENT 'ip来源',
    `os`          varchar(255)  DEFAULT null COMMENT '操作系统',
    `browser`     varchar(255)  DEFAULT null COMMENT '浏览器',
    `times`       int                NOT NULL COMMENT '请求耗时（毫秒）',
    `create_time` datetime           NOT NULL COMMENT '访问时间',
    `user_agent`  varchar(2000) DEFAULT null COMMENT 'user-agent用户代理'
);

CREATE TABLE `mbga`.`visit_record`
(
    `id`   bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `pv`   int                NOT NULL COMMENT '访问量',
    `uv`   int                NOT NULL COMMENT '独立用户',
    `date` varchar(255)       NOT NULL COMMENT '日期\"02-23\"'
);

CREATE TABLE `mbga`.`visitor`
(
    `id`          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `uuid`        varchar(36)        NOT NULL COMMENT '访客标识码',
    `ip`          varchar(255)  DEFAULT null COMMENT 'ip',
    `ip_source`   varchar(255)  DEFAULT null COMMENT 'ip来源',
    `os`          varchar(255)  DEFAULT null COMMENT '操作系统',
    `browser`     varchar(255)  DEFAULT null COMMENT '浏览器',
    `create_time` datetime           NOT NULL COMMENT '首次访问时间',
    `last_time`   datetime           NOT NULL COMMENT '最后访问时间',
    `pv`          int           DEFAULT null COMMENT '访问页数统计',
    `user_agent`  varchar(2000) DEFAULT null COMMENT 'user-agent用户代理'
);

CREATE INDEX `type_id` ON `mbga`.`blog` (`category_id`) USING BTREE;

CREATE INDEX `user_id` ON `mbga`.`blog` (`user_id`) USING BTREE;

CREATE UNIQUE INDEX `idx_uuid` ON `mbga`.`visitor` (`uuid`) USING BTREE;

ALTER TABLE `mbga`.`role_permission_map`
    ADD FOREIGN KEY (`role_id`) REFERENCES `mbga`.`role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `mbga`.`role_permission_map`
    ADD FOREIGN KEY (`permission_id`) REFERENCES `mbga`.`permission` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `mbga`.`user_role_map`
    ADD FOREIGN KEY (`user_id`) REFERENCES `mbga`.`user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `mbga`.`user_role_map`
    ADD FOREIGN KEY (`role_id`) REFERENCES `mbga`.`role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `mbga`.`blog`
    ADD FOREIGN KEY (`user_id`) REFERENCES `mbga`.`user` (`id`);

ALTER TABLE `mbga`.`blog_tag`
    ADD FOREIGN KEY (`blog_id`) REFERENCES `mbga`.`blog` (`id`);

ALTER TABLE `mbga`.`comment`
    ADD FOREIGN KEY (`blog_id`) REFERENCES `mbga`.`blog` (`id`);
