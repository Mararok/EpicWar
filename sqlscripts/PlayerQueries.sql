- RegisterPlayer <warId><uid>
INSERT INTO ew?_Players (uid) VALUES(?);

- AddPlayerPoints <warId><pointsAmount><id>
UPDATE ew?_Players SET points=points+? WHERE id=?;

- AddPlayerKill <warId><pointsAmount><id>
UPDATE ew?_Players SET kills=kills+1, points=points+? WHERE id=?;

- AddPlayerTeamKill <warId><pointsAmount><id>
UPDATE ew?_Players SET teamKills=teamKills+1, points=points-? WHERE id=?;

- AddPlayerDeath <warId><pointsAmount><id>
UPDATE ew?_Players SET deaths=deaths+1, points=points-? WHERE id=?;

- LoadPlayerData <warId><uid>
SELECT id,factionId,points,kills,teamKills,deaths,capturedControlPoints,capturedSectors FROM ew?_Players WHERE uid = ? LIMIT 1;