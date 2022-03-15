ALTER TABLE `game_third`
    ADD COLUMN `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识' AFTER `name`;

ALTER TABLE `game_tag`
    ADD COLUMN `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识' AFTER `name`;

ALTER TABLE `game_center_game`
    ADD COLUMN `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识' AFTER `name`;


## 删除索引
ALTER TABLE `game_third` DROP INDEX `idx_game_id`;

## 创建新索引
CREATE INDEX idx_game_id ON game_third(game_id);
CREATE INDEX idx_code ON game_third(code);
CREATE INDEX idx_code ON game_tag(code);
CREATE INDEX idx_country_id ON game_tag(country_id);