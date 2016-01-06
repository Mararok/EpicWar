CREATE TABLE IF NOT EXISTS [ew%s_Factions] (
  [id] INT PRIMARY KEY,
  [name] CHAR(32) NOT NULL, 
  [shortcut] CHAR(8) NOT NULL,
  [description] VARCHAR(255) DEFAULT 'Default description',
  
  [color] CHAR(1) NOT NULL,
  [banner] VARCHAR(255) NOT NULL
  
  [spawnX] INT NOT NULL,
  [spawnY] INT NOT NULL,
  [spawnZ] INT NOT NULL,
 );
