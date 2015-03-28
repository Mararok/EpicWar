CREATE TABLE IF NOT EXISTS [ew%1$s_Players] (
  [id] INT PRIMARY KEY AUTOINCREMENT,
  [uuid] CHAR(64) NOT NULL,
  [factionId] INT DEFAULT 0, 
  [points] INT DEFAULT 0, 
  [kills] INT DEFAULT 0, 
  [teamkills] INT DEFAULT 0,
  [deaths] INT DEFAULT 0,
  [capturedControlPoints] INT DEFAULT 0,
  [capturedSectors] INT DEFAULT 0,
);
CREATE UNIQUE INDEX uuidIndex
on ew%1$s_Players (uuid);

