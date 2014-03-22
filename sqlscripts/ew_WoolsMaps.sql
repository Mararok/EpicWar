CREATE TABLE IF NOT EXISTS [ew_WoolsMaps] (
  [id] INTEGER PRIMARY KEY AUTOINCREMENT, 
  [name] VARCHAR(32) NOT NULL, 
  [warID] INT NOT NULL,
  [orientation] INT DEFAULT 0, 
  [perLine] INT DEFAULT 2,
  [x] INT NOT NULL, 
  [y] INT NOT NULL,
  [z] INT NOT NULL
);

