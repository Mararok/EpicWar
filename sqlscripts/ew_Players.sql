CREATE TABLE IF NOT EXISTS [ew_Players] (
  [id] INTEGER PRIMARY KEY AUTOINCREMENT, 
  [name] VARCHAR(20) NOT NULL,
  [uuid] VARCHAR(36) NOT NULL,
  [warID] INT NOT NULL,
  [factionID] INT DEFAULT 0, 
  [points] INT DEFAULT 0, 
  [kills] INT DEFAULT 0, 
  [deaths] INT DEFAULT 0
);

