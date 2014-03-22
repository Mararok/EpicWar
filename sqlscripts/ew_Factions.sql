CREATE TABLE IF NOT EXISTS [ew_Factions] (
  [id] INTEGER PRIMARY KEY AUTOINCREMENT,
  [name] VARCHAR(32) NOT NULL, 
  [desc] VARCHAR(64) DEFAULT 'Default desc',
  [warID] INT NOT NULL,
  [color] CHAR(1) NOT NULL, 
  [spawnX] INT NOT NULL, 
  [spawnY] INT NOT NULL, 
  [spawnZ] INT NOT NULL, 
  [members] INT DEFAULT 0, 
  [maxMembers] INT DEFAULT 100000, 
  [controlledSectors] INT DEFAULT 1, 
  [capitalSectorID] INT NOT NULL
 );

