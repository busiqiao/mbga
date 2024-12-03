CREATE DATABASE IF NOT EXISTS mjga CHARACTER
    SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE mjga.`user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `username`    VARCHAR(255) NOT NULL UNIQUE,
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `password`    VARCHAR(255) NOT NULL,
    `enable`      BOOLEAN      NOT NULL DEFAULT true,
    PRIMARY KEY (`id`)
);

CREATE TABLE mjga.`permission`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE mjga.`role`
(
    `id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE mjga.`role_permission_map`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `role_id`       BIGINT NOT NULL,
    `permission_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES mjga.`role` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`permission_id`) REFERENCES mjga.`permission` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE mjga.`user_role_map`
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES mjga.`user` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`role_id`) REFERENCES mjga.`role` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
);
