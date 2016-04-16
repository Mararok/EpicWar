CREATE TABLE IF NOT EXISTS `ew%s_Players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` char(64) COLLATE utf8_bin NOT NULL,
  `factionId` int(11) NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `kills` int(11) NOT NULL DEFAULT '0',
  `teamkills` int(11) NOT NULL DEFAULT '0',
  `deaths` int(11) NOT NULL DEFAULT '0',
  `capturedControlPoints` int(11) NOT NULL DEFAULT '0',
  `capturedSectors` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;