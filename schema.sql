CREATE DATABASE IF NOT EXISTS game_journal_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'game_journal_db_admin'@'localhost' IDENTIFIED BY 'very_secure_password';
GRANT ALL PRIVILEGES ON game_journal_db.* TO 'game_journal_db_admin'@'localhost';
USE game_journal_db;

-- Acts
CREATE TABLE IF NOT EXISTS Act (
  id INT AUTO_INCREMENT PRIMARY KEY,
  number INT NOT NULL,
  description TEXT,
  notes TEXT
) ENGINE=InnoDB;

-- Runs
CREATE TABLE IF NOT EXISTS Run (
  id INT AUTO_INCREMENT PRIMARY KEY,
  number INT NOT NULL,
  act_id INT,
  notes TEXT,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Puzzles
CREATE TABLE IF NOT EXISTS Puzzle (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  notes TEXT,
  reward VARCHAR(255),
  act_id INT,
  discovered_in_run_id INT,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE SET NULL,
  FOREIGN KEY (discovered_in_run_id) REFERENCES Run(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Sigils
CREATE TABLE IF NOT EXISTS Sigil (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  icon VARCHAR(1024),
  notes TEXT,
  discovered_in_run_id INT,
  FOREIGN KEY (discovered_in_run_id) REFERENCES Run(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Tribes
CREATE TABLE IF NOT EXISTS Tribe (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  narration TEXT,
  notes TEXT
) ENGINE=InnoDB;

-- Cards
CREATE TABLE IF NOT EXISTS Card (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  narration TEXT,
  notes TEXT,
  power INT,
  health INT,
  cost VARCHAR(100),
  isRare TINYINT(1) DEFAULT 0,
  isUnique TINYINT(1) DEFAULT 0,
  discovered_in_run_id INT,
  FOREIGN KEY (discovered_in_run_id) REFERENCES Run(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Items
CREATE TABLE IF NOT EXISTS Item (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  narration TEXT,
  notes TEXT,
  discovered_in_run_id INT,
  FOREIGN KEY (discovered_in_run_id) REFERENCES Run(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Characters
CREATE TABLE IF NOT EXISTS `Character` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  notes TEXT,
  canFight TINYINT(1) DEFAULT 0,
  discovered_in_run_id INT,
  FOREIGN KEY (discovered_in_run_id) REFERENCES Run(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- EncounterTypes
CREATE TABLE IF NOT EXISTS EncounterType (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  narration TEXT,
  notes TEXT,
  icon VARCHAR(1024),
  isBoss TINYINT(1) DEFAULT 0,
  character_id INT,
  discovered_in_run_id INT,
  FOREIGN KEY (character_id) REFERENCES `Character`(id) ON DELETE SET NULL,
  FOREIGN KEY (discovered_in_run_id) REFERENCES Run(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Junction tables for many-to-many relationships

-- Card <-> Tribe
CREATE TABLE IF NOT EXISTS Card_Tribe (
  card_id INT NOT NULL,
  tribe_id INT NOT NULL,
  PRIMARY KEY (card_id, tribe_id),
  FOREIGN KEY (card_id) REFERENCES Card(id) ON DELETE CASCADE,
  FOREIGN KEY (tribe_id) REFERENCES Tribe(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Sigil <-> Act (appearsInActs)
CREATE TABLE IF NOT EXISTS Sigil_Act (
  sigil_id INT NOT NULL,
  act_id INT NOT NULL,
  PRIMARY KEY (sigil_id, act_id),
  FOREIGN KEY (sigil_id) REFERENCES Sigil(id) ON DELETE CASCADE,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Card <-> Act
CREATE TABLE IF NOT EXISTS Card_Act (
  card_id INT NOT NULL,
  act_id INT NOT NULL,
  PRIMARY KEY (card_id, act_id),
  FOREIGN KEY (card_id) REFERENCES Card(id) ON DELETE CASCADE,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Card <-> Sigil
CREATE TABLE IF NOT EXISTS Card_Sigil (
  card_id INT NOT NULL,
  sigil_id INT NOT NULL,
  PRIMARY KEY (card_id, sigil_id),
  FOREIGN KEY (card_id) REFERENCES Card(id) ON DELETE CASCADE,
  FOREIGN KEY (sigil_id) REFERENCES Sigil(id) ON DELETE CASCADE
) ENGINE=InnoDB;


-- Item <-> Act
CREATE TABLE IF NOT EXISTS Item_Act (
  item_id INT NOT NULL,
  act_id INT NOT NULL,
  PRIMARY KEY (item_id, act_id),
  FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Character <-> Act
CREATE TABLE IF NOT EXISTS Character_Act (
  character_id INT NOT NULL,
  act_id INT NOT NULL,
  PRIMARY KEY (character_id, act_id),
  FOREIGN KEY (character_id) REFERENCES `Character`(id) ON DELETE CASCADE,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- EncounterType <-> Act
CREATE TABLE IF NOT EXISTS EncounterType_Act (
  encountertype_id INT NOT NULL,
  act_id INT NOT NULL,
  PRIMARY KEY (encountertype_id, act_id),
  FOREIGN KEY (encountertype_id) REFERENCES EncounterType(id) ON DELETE CASCADE,
  FOREIGN KEY (act_id) REFERENCES Act(id) ON DELETE CASCADE
) ENGINE=InnoDB;