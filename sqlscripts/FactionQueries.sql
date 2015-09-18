- Create
#INSERT INTO ew?_factions(name,shortcut,description,color,banner) VALUES(?,?,?,?,?);

- AddMember 
UPDATE ew_?Factions SET members=members+1 WHERE id = ?;

- RemoveMember
UPDATE ew_?Factions SET members=members-1 WHERE id = ?;

- SetPlayerFaction
UPDATE ew_?Players SET factionId = ? WHERE id = ?;

- SetFactionSpawn
UPDATE ew_?Factions SET spawnX = ?, spawnY = ?, spawnZ = ? WHERE id = ?;
