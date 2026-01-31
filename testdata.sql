-- File: db/testdata.sql
USE game_journal_db;

-- Acts
INSERT INTO Act (number, description, notes) VALUES
(1, 'The Beginning', 'Some kind of cabin card game');

-- Runs
INSERT INTO Run (number, act_id, notes) VALUES
(1, 1, 'First run of Act 1'),
(2, 1, 'Second run of Act 1');

-- Puzzles
INSERT INTO Puzzle (name, notes, reward, act_id, discovered_in_run_id) VALUES
('Safe', 'Need to find the sequence', 'Unknown', 1, 1),
('Knife', 'Grab the knife from the squirrel, somehow', 'Unknown', 1, 1);

-- Sigils
INSERT INTO Sigil (name, description, icon, notes, discovered_in_run_id) VALUES
('Touch of Death', 'looks like a skull', NULL, 'kills things instantly', 1),
('Infinite Sacrifice?', 'dagger with inifity sign', NULL, 'allows infinite sacrifice', 1),
('Growth Sigil?', 'sundial', NULL, 'makes things grow up', 1),
('Airborne', 'wing', NULL, 'makes creatures flying', 1);

-- Tribes
INSERT INTO Tribe (name, narration, notes) VALUES
('Squirrel', 'n/a', 'Squirrels'),
('Canine', 'n/a', 'Wolves');
('Reptile', 'n/a', 'Reptiles');
('Bird', 'n/a', 'Birds');

-- Cards
INSERT INTO Card (name, narration, notes, power, health, cost, isRare, isUnique, discovered_in_run_id) VALUES
('Squirrel', 'The Squirrel Card', '', 0, 1, '', 0, 0, 1),
('Stoat', 'The Stoat', '', 1, 3, '1 blood', 0, 1, 1),
('Wolf', 'The proud wolf. A vicious contender', '', 3, 2, '2 blood', 0, 0, 1),
('Coyote', '', '', 2, 1, '???', 0, 0, 1),
('River Snapper', 'The stalwart snapper. A near impenetrable defense.', '', 1, 6, '2 blood', 0, 0, 1),

('Adder', 'The caustic adder. Damage from its poison bite is always lethal.', 'Need to try this out', 1, 1, '2 blood', 0, 0, 1),
('Cat', 'The undying cat. Sacrificing the poor beast does not kill it.', 'Do not give the adder sigil to it.', 0, 1, '1 blood', 1, 0, 1),
('Wolf Cub', 'Mind the ambitious wolf cub. It ages swiftly.', 'Grows into a wolf after 1 turn', 1, 1, '1 blood', 0, 0, 1),
('Bat', 'The airborne bat flies over creatures to attack directly.', '', 2, 1, '???', 0, 0, 1),
('Sparrow', 'The meek sparrow. An inexpensive, if feeble, flying creature', '', 1, 2, '1 blood', 0, 0, 1);

-- Items
INSERT INTO Item (name, narration, notes, discovered_in_run_id) VALUES
('Squirrel in a bottle', 'Break in case of emergency', 'Free squirrel card', 1),
('Pliers', 'Another useful implement.', 'Add 1 damage to the scale', 1);

-- Characters
INSERT INTO `Character` (name, notes, canFight, discovered_in_run_id) VALUES
('Cabin Guy??', 'Who is he??', 1, 1),
('The Stoat??', 'It speaks??', 0, 1);

-- EncounterTypes
INSERT INTO EncounterType (name, narration, notes, icon, isBoss, character_id, discovered_in_run_id) VALUES
('Battle', 'None', 'Just a regular fight', NULL, 0, 1, 1),
('Cards', 'Two denizens of the forest approached you tentatively', 'Free cards', NULL, 0, 1, 1),
('Items', 'You came across an abandoned sack', 'Free items', NULL, 0, 1, 1),
('Alter', 'You stumble into some strange stones in the mist', 'Sacrifice a card to puts its sigils on another', NULL, 0, 1, 1),
('Totem Battle', 'Behold my totem. It inscribes my canine cards with the airborne sigil', 'Not just a regular fight', NULL, 0, 1, 1);

-- Junctions: Card <-> Tribe
INSERT INTO Card_Tribe (card_id, tribe_id) VALUES (1, 1), (3, 2), (4, 2), (5,3), (6,3), (8,2), (10,4);

-- Sigil <-> Act
INSERT INTO Sigil_Act (sigil_id, act_id) VALUES (1,1), (2,1), (3,1), (4,1);

-- Card <-> Act
INSERT INTO Card_Act (card_id, act_id) VALUES (1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1), (8,1), (9,1), (10,1);

-- Card <-> Sigil
INSERT INTO Card_Sigil (card_id, sigil_id) VALUES (6,1), (7,2), (8,3), (9,4), (10,4);

-- Item <-> Act
INSERT INTO Item_Act (item_id, act_id) VALUES (1,1), (2,1);

-- Character <-> Act
INSERT INTO Character_Act (character_id, act_id) VALUES (1,1), (2,1);

-- EncounterType <-> Act
INSERT INTO EncounterType_Act (encountertype_id, act_id) VALUES (1,1), (2,1), (3,1), (4,1), (5,1);
