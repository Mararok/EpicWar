CREATE TABLE IF NOT EXISTS [ew%s_Factions] (
  [id] INT PRIMARY KEY AUTOINCREMENT,
  [shortcut] CHAR(8) NOT NULL,
  [name] CHAR(32) NOT NULL, 
  [description] VARCHAR(128) DEFAULT 'Default description',
  [color] CHAR(1) NOT NULL,
  [banner] VARCHAR(255) NOT NULL,
  [spawnX] INT NOT NULL, 
  [spawnY] INT NOT NULL, 
  [spawnZ] INT NOT NULL, 
  [members] INT DEFAULT 0, 
  [capitalSectorId] INT NOT NULL
 );
