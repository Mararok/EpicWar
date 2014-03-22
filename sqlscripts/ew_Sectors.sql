CREATE TABLE IF NOT EXISTS [ew_Sectors] (
  [id] INTEGER PRIMARY KEY AUTOINCREMENT, 
  [name] VARCHAR(32) NOT NULL, 
  [desc] VARCHAR(64) DEFAULT 'Default desc',
  [warID] INT NOT NULL,
  [ownerID] INT DEFAULT 0, 
  [centerX] INT NOT NULL, 
  [centerZ] INT NOT NULL, 
  [size] INT NOT NULL
);
