CREATE TABLE IF NOT EXISTS `ew%s_Factions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `shortcut` char(8) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT 'Default description',
  `color` char(1) COLLATE utf8_bin NOT NULL,
  `banner` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `spawnX` int(11) NOT NULL,
  `spawnY` int(11) NOT NULL,
  `spawnZ` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;