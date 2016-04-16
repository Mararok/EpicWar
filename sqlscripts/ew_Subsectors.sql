CREATE TABLE IF NOT EXISTS `ew%s_Subsectors` (
  `id` int(11) NOT NULL,
  `chunkX` int(11) NOT NULL,
  `chunkZ` int(11) NOT NULL,
  `controlPointId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;