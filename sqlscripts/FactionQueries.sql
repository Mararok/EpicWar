- AddMember 
UPDATE ew_Factions SET members = members + 1 WHERE id = ?;

- RemoveMember
UPDATE ew_Factions SET members = members - 1 WHERE id = ?;

- SetPlayerFaction
UPDATE ew_Players SET factionID = ? WHERE id = ?;

- SetFactionSpawn
UPDATE ew_Factions SET spawnX = ?, spawnY = ?, spawnZ = ? WHERE id = ?;

- Update controlled sectors amount
UPDATE ew_factions SET controlledSectors = ? WHERE id = ?