CREATE TABLE IF NOT EXISTS [ew%s_ControlPoints] (
  [id] INT PRIMARY KEY AUTOINCREMENT,
  [name] CHAR(32) NOT NULL,
  [description] VARCHAR(128) DEFAULT "default description",
  
  [sectorId] INT NOT NULL,
  [ownerId] INT DEFAULT 0, 
  
  [x] BIGINT NOT NULL, 
  [y] BIGINT NOT NULL, 
  [z] BIGINT NOT NULL,

  [radius] BIGINT NOT NULL,
  [maxPower] INT NOT NULL
);
