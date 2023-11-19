--
-- 	Database Table Creation
--
--	This file will create the tables for use.
--

drop table Team;
drop table TeamMember;
drop table Player;
drop table Age;
drop table Location;
drop table Coach;
drop table Season;
drop table Owner;
drop table Sponsor;
drop table HasPlayed;
drop table Referee;
drop table StatSheet;
drop table Game;
drop table Owns;
drop table Sponsors;


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
 FOREIGN KEY (tid) REFERENCES Team(tid) ON DELETE CASCADE,
 FOREIGN KEY (age) REFERENCES Age(age) ON DELETE CASCADE
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
 home_points INT DEFAULT 0,
 away_points INT DEFAULT 0,
 home_steals INT DEFAULT 0,
 away_steals INT DEFAULT 0
 home_assists INT DEFAULT 0,
 away_assists INT DEFAULT 0,
 home_rebounds INT DEFAULT 0,
 away_rebounds INT DEFAULT 0
);

CREATE TABLE Game (
 game_date date,
 home_tid INT,
 away_tid INT,
 score CHAR(10),
 ssid INT,
 year INT,
 rid INT,
 arena CHAR(20),
 PRIMARY KEY (game_date, home_tid, away_tid),
 FOREIGN KEY (home_tid) REFERENCES Team(tid),
 FOREIGN KEY (away_tid) REFERENCES Team(tid),
 FOREIGN KEY (ssid) REFERENCES StatSheet(ssid),
 FOREIGN KEY (year) REFERENCES Season(year),
 FOREIGN KEY (rid) REFERENCES Referee(rid),
 FOREIGN KEY (arena) REFERENCES Location(arena),
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

