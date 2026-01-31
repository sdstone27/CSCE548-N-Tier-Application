-- File: db/testdata.sql
USE game_journal_db;

-- Acts
INSERT INTO Act (number, description, notes) VALUES
(1, 'The Beginning', 'Notes for Act 1'),
(2, 'The Middle', 'Notes for Act 2'),
(3, 'The End', 'Notes for Act 3');

-- Runs
INSERT INTO Run (number, act_id, notes) VALUES
(1, 1, 'First run of Act 1'),
(2, 1, 'Second run of Act 1'),
(3, 2, 'First run of Act 2');

-- Puzzles
INSERT INTO Puzzle (name, notes, reward, act_id, discovered_in_run_id) VALUES
('Puzzle of Doors', 'Solve the door sequence', 'Key of Dawn', 1, 1),
('Mirror Riddle', 'Reflect to proceed', 'Shard', 2, 3);

-- Sigils
INSERT INTO Sigil (name, description, icon, notes, discovered_in_run_id) VALUES
('Sigil of Fire', 'Gives fire power', '/icons/fire.png', 'Burns things', 1),
('Sigil of Water', 'Controls water', '/icons/water.png', 'Flowing', 2);

-- Tribes
INSERT INTO Tribe (name, narration, notes) VALUES
('Tribe of Oak', 'Sturdy and wise', 'Likes forests'),
('Tribe of Iron', 'Tough warriors', 'Metallic armor');

-- Cards
INSERT INTO Card (name, narration, notes, power, health, cost, isRare, isUnique, discovered_in_run_id) VALUES
('Oak Guardian', 'Protects the grove', 'Elder tree', 3, 5, '2G', 0, 1, 1),
('Iron Soldier', 'Frontline fighter', 'Sturdy', 4, 3, '3', 1, 0, 2);

-- Items
INSERT INTO Item (name, narration, notes, discovered_in_run_id) VALUES
('Healing Potion', 'Restores HP', 'Single use', 1),
('Rusty Sword', 'Old blade', 'Weak', 2);

-- Characters
INSERT INTO `Character` (name, notes, canFight, discovered_in_run_id) VALUES
('Old Hermit', 'Gives quests', 0, 1),
('Bandit Leader', 'Warlike', 1, 2);

-- EncounterTypes
INSERT INTO EncounterType (name, narration, notes, icon, isBoss, character_id, discovered_in_run_id) VALUES
('Ambush', 'Sudden attack', 'Small group ambush', '/icons/ambush.png', 0, 2, 2),
('Dungeon Boss', 'Major boss', 'End of dungeon boss', '/icons/boss.png', 1, NULL, 3);

-- Junctions: Card <-> Tribe
INSERT INTO Card_Tribe (card_id, tribe_id) VALUES (1, 1), (2, 2);

-- Sigil <-> Act
INSERT INTO Sigil_Act (sigil_id, act_id) VALUES (1,1), (2,2);

-- Card <-> Act
INSERT INTO Card_Act (card_id, act_id) VALUES (1,1), (2,2);

-- Item <-> Act
INSERT INTO Item_Act (item_id, act_id) VALUES (1,1), (2,2);

-- Character <-> Act
INSERT INTO Character_Act (character_id, act_id) VALUES (1,1), (2,2);

-- EncounterType <-> Act
INSERT INTO EncounterType_Act (encountertype_id, act_id) VALUES (1,1), (2,3);
