--
-- 	Database Table Creation
--
--	This file will create the tables for use.
--
CREATE TABLE Team (
 tid INT PRIMARY KEY,
 name CHAR(20) NOT NULL,
 city CHAR(20) NOT NULL,
 arena CHAR(20) NOT NULL
);

CREATE TABLE TeamMember (
 tmid INT PRIMARY KEY NOT NULL,
 name CHAR(20) NOT NULL,
 cid INT,
 tid INT,
 start DATE NOT NULL,
 end DATE NOT NULL,
 salary INT NOT NULL,
 dob DATE NOT NULL,
 age INT,
 FOREIGN KEY (tid) REFERENCES Team(tid) ON DELETE CASCADE
);

CREATE TABLE Player (
 tmid INT PRIMARY KEY,
 position CHAR(10),
 FOREIGN KEY (tmid) REFERENCES TeamMember(tmid)
);

CREATE TABLE Age(
 dob DATE PRIMARY KEY,
 age INT NOT NULL
);

CREATE TABLE Location (
 arena CHAR(20) PRIMARY KEY,
 city CHAR(20) NOT NULL
);

CREATE TABLE Coach (
 tmid INT PRIMARY KEY,
 FOREIGN KEY (tmid) REFERENCES TeamMember(tmid)
);

CREATE TABLE Season (
 year INT PRIMARY KEY,
 start_date DATE NOT NULL,
 end_date DATE,
 CHECK (start_date <= end_date)
);

CREATE TABLE Owner (
 name CHAR(20) PRIMARY KEY,
 dob DATE NOT NULL,
 net_worth INT DEFAULT 0
);

CREATE TABLE Sponsor (
 name CHAR(20) PRIMARY KEY,
 contribution INT DEFAULT 0
);

CREATE TABLE HasPlayed (
 home_tid INT,
 away_tid INT,
 PRIMARY KEY (home_tid, away_tid),
 FOREIGN KEY (home_tid) REFERENCES Team(tid),
 FOREIGN KEY (away_tid) REFERENCES Team(tid)
);

CREATE TABLE Referee (
 rid INT PRIMARY KEY,
 name CHAR(20) NOT NULL,
 experience_years INT DEFAULT 0
);

CREATE TABLE StatSheet (
 ssid INT PRIMARY KEY,
 home-points INT DEFAULT 0,
 away-points INT DEFAULT 0,
 steals INT DEFAULT 0,
 assists INT DEFAULT 0,
 rebounds INT DEFAULT 0
);

CREATE TABLE Game (
 date date,
 home_tid INT,
 away_tid INT,
 score CHAR(10),
 ssid INT NOT NULL,
 year INT NOT NULL,
 rid INT NOT NULL,
 arena INT NOT NULL,
 PRIMARY KEY (date, home_tid, away_tid),
 FOREIGN KEY (home_tid) REFERENCES HasPlayed(home_tid),
 FOREIGN KEY (away_tid) REFERENCES HasPlayed(away_tid),
 FOREIGN KEY (ssid) REFERENCES StatSheet(ssid),
 FOREIGN KEY (year) REFERENCES Season(year),
 FOREIGN KEY (rid) REFERENCES Referee(rid)
);


CREATE TABLE Owns (
 oname CHAR(20),
 tid INT,
 PRIMARY KEY (oname, tid),
 FOREIGN KEY (oname) REFERENCES Owner(name),
 FOREIGN KEY (tid) REFERENCES Team(tid)
);


CREATE TABLE Sponsors (
 sname CHAR(20),
 tid INT,
 PRIMARY KEY (sname, tid),
 FOREIGN KEY (sname) REFERENCES Sponsor(name),
 FOREIGN KEY (tid) REFERENCES Team(tid)
); 

