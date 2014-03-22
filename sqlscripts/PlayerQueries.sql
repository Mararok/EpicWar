- AddPlayerKill
UPDATE ew_Players SET kills = kills+1, points = points + ? WHERE id = ?;

- AddPlayerDeath
UPDATE ew_Players SET deaths = deaths+1, points = points - ? WHERE id = ?;

- RegisterPlayer
INSERT INTO ew_Players (name,warID) VALUES(?,?);

- LoadPlayerData
SELECT id,factionID,points,kills,deaths FROM ew_Players WHERE warID = ? and name = ? LIMIT 1;