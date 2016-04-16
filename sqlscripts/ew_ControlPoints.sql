CREATE TABLE IF NOT EXISTS `ew%s_ControlPoints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT 'Default description',
  `sectorId` int(11) NOT NULL,
  `ownerId` int(11) NOT NULL DEFAULT '0',
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `z` int(11) NOT NULL,
  `radius` int(11) NOT NULL,
  `maxPower` int(11) NOT NULL,
  `connections` varchar(512) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
