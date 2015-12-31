CREATE TABLE IF NOT EXISTS [ew%s_ControlPoints] (
  [id] INT PRIMARY KEY AUTOINCREMENT,
  [name] CHAR(32) NOT NULL,
  [description] VARCHAR(255) DEFAULT "default description",
  
  [sectorId] INT NOT NULL,
  [ownerId] INT DEFAULT 0, 
  
  [x] INT NOT NULL, 
  [y] INT NOT NULL, 
  [z] INT NOT NULL,

  [radius] INT NOT NULL,
  [maxPower] INT NOT NULL
);
