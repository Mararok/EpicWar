CREATE TABLE IF NOT EXISTS [ew%1$s_Sectors] (
  [id] INTEGER PRIMARY KEY AUTOINCREMENT, 
  [name] CHAR(64) NOT NULL,
  [description] VARCHAR(255) DEFAULT 'Default description',
  [ownerId] INT DEFAULT 0,
);
