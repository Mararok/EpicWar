CREATE TABLE IF NOT EXISTS [ew_ControlPoints] (

  [id] INTEGER PRIMARY KEY AUTOINCREMENT, 
  [name] VARCHAR(32) NOT NULL, 
  [warID] INT NOT NULL,
  [sectorID] INT NOT NULL,
  [ownerID] INT DEFAULT 0, 
  
  [centerX] INT NOT NULL, 
  [centerY] INT NOT NULL, 
  [centerZ] INT NOT NULL,

  [radius] INT NOT NULL,
  [power] INT NOT NULL,
  [maxPower] INT DEFAULT 100
);
