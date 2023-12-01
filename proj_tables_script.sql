--
-- 	Database Table Creation
--
--	This file will drop all existing tables and create the tables for use and populate them.
--
--
--
---------------------------------------------------------------------------------------

-- THIS DROPS ALL EXISTING TABLES AND VALUES BEFORE EXECUTING THE TABLE SCRIPT.

----------------------------------------------------------------------------------------

drop table Game;
drop table Coach;
drop table Player;
drop table TeamMember;
drop table Owns;
drop table Sponsors;
drop table HasPlayed;
drop table Team;
drop table Owner;
drop table Age;
drop table Location;
drop table Season;
drop table Sponsor;
drop table Referee;
drop table StatSheet;

---------------------------------------------------------------------------------------

-- TABLE CREATION STATEMENTS FOR THE DATABASE

----------------------------------------------------------------------------------------

CREATE TABLE Location (
 arena CHAR(20) PRIMARY KEY,
 city CHAR(20) NOT NULL
);

-- removed city, added foreign key ref to Location
CREATE TABLE Team (
 tid INT PRIMARY KEY,
 name CHAR(20) NOT NULL,
 arena CHAR(20) NOT NULL,
 FOREIGN KEY (arena) REFERENCES Location(arena) ON DELETE CASCADE
);

-- changed primary key to age
CREATE TABLE Age(
 dob DATE NOT NULL,
 age INT PRIMARY KEY
);

-- removed cid
CREATE TABLE TeamMember (
 tmid INT PRIMARY KEY NOT NULL,
 name CHAR(20) NOT NULL,
 tid INT NOT NULL,
 start_date DATE NOT NULL,
 end_date DATE NOT NULL,
 salary INT NOT NULL,
 age INT NOT NULL,
 FOREIGN KEY (tid) REFERENCES Team(tid) ON DELETE CASCADE
);

-- renamed tmid to pid
CREATE TABLE Player (
 pid INT PRIMARY KEY,
 position CHAR(20),
 FOREIGN KEY (pid) REFERENCES TeamMember(tmid) ON DELETE CASCADE
);


-- add tid fkey reference
CREATE TABLE Coach (
 tmid INT PRIMARY KEY,
 tid INT NOT NULL,
 FOREIGN KEY (tmid) REFERENCES TeamMember(tmid) ON DELETE CASCADE,
 FOREIGN KEY (tid) REFERENCES Team(tid)
);

CREATE TABLE Season (
 year INT PRIMARY KEY,
 start_date DATE NOT NULL,
 end_date DATE NOT NULL,
 CHECK (start_date <= end_date)
);

-- added age ref
CREATE TABLE Owner (
 name CHAR(20) PRIMARY KEY,
 age INT NOT NULL,
 net_worth INT DEFAULT 0,
 FOREIGN KEY (age) REFERENCES Age(age)
);

CREATE TABLE Owns (
 oname CHAR(20),
 tid INT,
 PRIMARY KEY (oname, tid),
 FOREIGN KEY (oname) REFERENCES Owner(name) ON DELETE CASCADE,
 FOREIGN KEY (tid) REFERENCES Team(tid)
);

CREATE TABLE Sponsor (
 name CHAR(20) PRIMARY KEY,
 contribution INT DEFAULT 0
);

CREATE TABLE Sponsors (
 sname CHAR(20),
 tid INT,
 PRIMARY KEY (sname, tid),
 FOREIGN KEY (sname) REFERENCES Sponsor(name),
 FOREIGN KEY (tid) REFERENCES Team(tid)
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
 away_steals INT DEFAULT 0,
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
 FOREIGN KEY (arena) REFERENCES Location(arena)
);

---------------------------------------------------------------------------------------

--  INSERT STATEMENTS FOR THE DATABASE

----------------------------------------------------------------------------------------

INSERT INTO Location (arena, city) VALUES ('Staples Center', 'Los Angeles');
INSERT INTO Location (arena, city) VALUES ('Chase Center', 'Golden State');
INSERT INTO Location (arena, city) VALUES ('TD Garden', 'Boston');
INSERT INTO Location (arena, city) VALUES ('United Center', 'Chicago');
INSERT INTO Location (arena, city) VALUES ('Toyota Center', 'Houston');
INSERT INTO Location (arena, city) VALUES ('Stadium A', 'New York');
INSERT INTO Location (arena, city) VALUES ('Arena B', 'L.A');
INSERT INTO Location (arena, city) VALUES ('Coliseum C', 'S.F');
INSERT INTO Location (arena, city) VALUES ('Field D', 'Miami');
INSERT INTO Location (arena, city) VALUES ('Center E', 'San Francisco');


INSERT INTO Team (tid, name, arena) VALUES (1, 'Lakers', 'Staples Center');
INSERT INTO Team (tid, name, arena) VALUES (2, 'Warriors', 'Chase Center');
INSERT INTO Team (tid, name, arena) VALUES (3, 'Celtics', 'TD Garden');
INSERT INTO Team (tid, name, arena) VALUES (4, 'Bulls', 'United Center');
INSERT INTO Team (tid, name, arena) VALUES (5, 'Rockets', 'Toyota Center');

INSERT INTO Age (dob, age) VALUES ( DATE '1990-05-15', 32);
INSERT INTO Age (dob, age) VALUES ( DATE '1985-08-22', 37);
INSERT INTO Age (dob, age) VALUES ( DATE '1995-02-10', 27);
INSERT INTO Age (dob, age) VALUES ( DATE '1980-11-05', 42);
INSERT INTO Age (dob, age) VALUES ( DATE '1992-06-18', 30);
INSERT INTO Age (dob, age) VALUES ( DATE '1988-09-30', 34);
INSERT INTO Age (dob, age) VALUES ( DATE '1998-04-25', 24);
INSERT INTO Age (dob, age) VALUES ( DATE '1982-03-12', 40);
INSERT INTO Age (dob, age) VALUES ( DATE '1993-07-08', 29);
INSERT INTO Age (dob, age) VALUES ( DATE '1987-01-20', 35);
INSERT INTO Age (dob, age) VALUES ( DATE '1960-01-04', 62);
INSERT INTO Age (dob, age) VALUES ( DATE '1957-05-26', 65);
INSERT INTO Age (dob, age) VALUES ( DATE '1959-04-21', 63);
INSERT INTO Age (dob, age) VALUES ( DATE '1963-02-17', 59);
INSERT INTO Age (dob, age) VALUES ( DATE '1956-07-29', 71);
INSERT INTO Age (dob, age) VALUES ( DATE '1990-05-12', 33);
INSERT INTO Age (dob, age) VALUES ( DATE '1995-04-05', 28);
INSERT INTO Age (dob, age) VALUES ( DATE '1991-11-18', 31);
INSERT INTO Age (dob, age) VALUES ( DATE '1974-02-15', 49);
INSERT INTO Age (dob, age) VALUES ( DATE '1996-12-08', 26);
INSERT INTO Age (dob, age) VALUES ( DATE '1972-03-30', 51);



INSERT INTO TeamMember values (1, 'LeBron James', 1, DATE '2022-01-01', DATE '2023-12-31', 40000000, 37);
INSERT INTO TeamMember values (2, 'Stephen Curry', 1, DATE'2022-01-01', DATE '2023-12-31', 43000000, 33);
INSERT INTO TeamMember values (3, 'Jayson Tatum', 1, DATE '2022-01-01', DATE '2023-12-31', 32000000, 24);
INSERT INTO TeamMember values (4, 'Zach LaVine', 1, DATE '2022-01-01', DATE '2023-12-31', 28000000, 26);
INSERT INTO TeamMember values (5, 'James Harden', 1, DATE '2022-01-01', DATE '2023-12-31', 38000000, 32);
INSERT INTO TeamMember values (6, 'Jimmy Butler', 1, DATE '2022-01-01', DATE '2023-12-31', 34000000, 32);
INSERT INTO TeamMember values (7, 'DeMar DeRozan', 1, DATE '2022-01-01', DATE '2023-12-31', 25000000, 32);
INSERT INTO TeamMember values (8, 'Pascal Siakam', 1, DATE '2022-01-01', DATE '2023-12-31', 29000000, 28);
INSERT INTO TeamMember values (9, 'Julius Randle', 1, DATE '2022-01-01', DATE '2023-12-31', 26000000, 27);
INSERT INTO TeamMember values (10, 'Gilgeous Alexander', 1, DATE '2022-01-01', DATE '2023-12-31', 22000000, 23);
INSERT INTO TeamMember values (11, 'Klay Thompson', 2, DATE '2022-01-01', DATE '2023-12-31', 38000000, 32);
INSERT INTO TeamMember values (12, 'Draymond Green', 2, DATE '2022-01-01', DATE '2023-12-31', 33000000, 31);
INSERT INTO TeamMember values (13, 'Andrew Wiggins', 2, DATE '2022-01-01', DATE '2023-12-31', 27000000, 26);
INSERT INTO TeamMember values (14, 'Kelly Oubre Jr.', 2, DATE '2022-01-01', DATE '2023-12-31', 22000000, 25);
INSERT INTO TeamMember values (15, 'James Wiseman', 2, DATE '2022-01-01', DATE '2023-12-31', 24000000, 21);
INSERT INTO TeamMember values (16, 'Jordan Poole', 2, DATE '2022-01-01', DATE '2023-12-31', 18000000, 23);
INSERT INTO TeamMember values (17, 'Juan Anderson', 2, DATE '2022-01-01', DATE '2023-12-31', 15000000, 28);
INSERT INTO TeamMember values (18, 'Nemanja Bjelica', 2, DATE '2022-01-01', DATE '2023-12-31', 12000000, 33);
INSERT INTO TeamMember values (19, 'Moses Moody', 2, DATE '2022-01-01', DATE '2023-12-31', 10000000, 20);
INSERT INTO TeamMember values (20, 'Andre Iguodala', 2, DATE '2022-01-01', DATE '2023-12-31', 12000000, 38);
INSERT INTO TeamMember values (21, 'Jaylen Brown', 3, DATE '2022-01-01', DATE'2023-12-31', 35000000, 25);
INSERT INTO TeamMember values (22, 'Marcus Smart', 3, DATE '2022-01-01', DATE '2023-12-31', 24000000, 28);
INSERT INTO TeamMember values (23, 'Al Horford', 3, DATE '2022-01-01', DATE '2023-12-31', 26000000, 35);
INSERT INTO TeamMember values (24, 'Robert Williams III', 3, DATE '2022-01-01', DATE '2023-12-31', 18000000, 24);
INSERT INTO TeamMember values (25, 'Dennis Schroder', 3, DATE '2022-01-01', DATE '2023-12-31', 15000000, 28);
INSERT INTO TeamMember values (26, 'Josh Richardson', 3, DATE '2022-01-01', DATE '2023-12-31', 12000000, 28);
INSERT INTO TeamMember values (27, 'Grant Williams', 3, DATE '2022-01-01', DATE '2023-12-31', 8000000, 23);
INSERT INTO TeamMember values (28, 'Romeo Langford', 3, DATE '2022-01-01', DATE '2023-12-31', 7000000, 22);
INSERT INTO TeamMember values (29, 'Aaron Nesmith', 3, DATE '2022-01-01', DATE '2023-12-31', 6000000, 22);
INSERT INTO TeamMember values (30, 'Payton Pritchard', 3, DATE '2022-01-01', DATE '2023-12-31', 5000000, 23);
INSERT INTO TeamMember values (31, 'DeMar DeRozan', 4, DATE '2022-01-01', DATE '2023-12-31', 33000000, 32);
INSERT INTO TeamMember values (32, 'Zach LaVine', 4, DATE '2022-01-01', DATE '2023-12-31', 28000000, 26);
INSERT INTO TeamMember values (33, 'Nikola Vucevic', 4, DATE '2022-01-01', DATE '2023-12-31', 26000000, 31);
INSERT INTO TeamMember values (34, 'Lonzo Ball', 4, DATE '2022-01-01', DATE '2023-12-31', 20000000, 24);
INSERT INTO TeamMember values (35, 'Coby White', 4, DATE '2022-01-01', DATE '2023-12-31', 15000000, 22);
INSERT INTO TeamMember values (36, 'Patrick Williams', 4, DATE '2022-01-01', DATE '2023-12-31', 12000000, 20);
INSERT INTO TeamMember values (37, 'Alex Caruso', 4, DATE'2022-01-01', DATE '2023-12-31', 10000000, 28);
INSERT INTO TeamMember values (38, 'Javonte Green', 4, DATE '2022-01-01', DATE '2023-12-31', 8000000, 28);
INSERT INTO TeamMember values (39, 'Thaddeus Young', 4, DATE '2022-01-01', DATE '2023-12-31', 12000000, 33);
INSERT INTO TeamMember values (40, 'Troy Brown Jr.', 4, DATE '2022-01-01', DATE '2023-12-31', 7000000, 22);
INSERT INTO TeamMember values (41, 'Christian Wood', 5, DATE '2022-01-01', DATE '2023-12-31', 28000000, 26);
INSERT INTO TeamMember values (42, 'John Wall', 5, DATE '2022-01-01', DATE '2023-12-31', 41000000, 31);
INSERT INTO TeamMember values (43, 'Jalen Green', 5, DATE '2022-01-01', DATE '2023-12-31', 18000000, 19);
INSERT INTO TeamMember values (44, 'Kevin Porter Jr.', 5, DATE '2022-01-01', DATE '2023-12-31', 22000000, 21);
INSERT INTO TeamMember values (45, 'Daniel Theis', 5, DATE '2022-01-01', DATE '2023-12-31', 12000000, 29);
INSERT INTO TeamMember values (46, 'Eric Gordon', 5, DATE '2022-01-01', DATE '2023-12-31', 18000000, 33);
INSERT INTO TeamMember values (47, 'David Nwaba', 5, DATE '2022-01-01', DATE '2023-12-31', 8000000, 28);
INSERT INTO TeamMember values (48, 'Armoni Brooks', 5, DATE '2022-01-01', DATE '2023-12-31', 7000000, 22);
INSERT INTO TeamMember values (49, 'Khyri Thomas', 5, DATE '2022-01-01', DATE '2023-12-31', 6000000, 25);
INSERT INTO TeamMember VALUES (50, 'John Carrington', 5, DATE '1990-03-15', DATE '2023-12-31', 6000000, 33);
INSERT INTO TeamMember VALUES (51, 'Jane Watson', 3, DATE '1995-08-22', DATE '2023-12-31', 5500000, 28);
INSERT INTO TeamMember VALUES (52, 'Michael Lock', 4, DATE '1992-05-10', DATE '2023-12-31', 5200000, 31);
INSERT INTO TeamMember VALUES (53, 'Erick White', 2, DATE '1998-11-18', DATE '2023-12-31', 4800000, 25);
INSERT INTO TeamMember VALUES (54, 'David Brownn', 1, DATE '1993-07-03', DATE '2023-12-31', 5100000, 30);
INSERT INTO TeamMember VALUES (55, 'Micheal Gray', 5, DATE '1994-06-03', DATE '2023-12-31', 3900000, 29);

INSERT INTO Player (pid, position) VALUES (1, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (2, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (3, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (4, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (5, 'Coach');
INSERT INTO Player (pid, position) VALUES (6, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (7, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (8, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (9, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (10, 'Center');
INSERT INTO Player (pid, position) VALUES (11, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (12, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (13, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (14, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (15, 'Center');
INSERT INTO Player (pid, position) VALUES (16, 'Coach');
INSERT INTO Player (pid, position) VALUES (17, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (18, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (19, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (20, 'Center');
INSERT INTO Player (pid, position) VALUES (21, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (22, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (23, 'Coach');
INSERT INTO Player (pid, position) VALUES (24, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (25, 'Center');
INSERT INTO Player (pid, position) VALUES (26, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (27, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (28, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (29, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (30, 'Center');
INSERT INTO Player (pid, position) VALUES (31, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (32, 'Coach');
INSERT INTO Player (pid, position) VALUES (33, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (34, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (35, 'Center');
INSERT INTO Player (pid, position) VALUES (36, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (37, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (38, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (39, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (40, 'Center');
INSERT INTO Player (pid, position) VALUES (41, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (42, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (43, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (44, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (45, 'Center');
INSERT INTO Player (pid, position) VALUES (46, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (47, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (48, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (49, 'Power Forward');
INSERT INTO Player (pid, position) VALUES (50, 'Coach');
INSERT INTO Player (pid, position) VALUES (51, 'Center');
INSERT INTO Player (pid, position) VALUES (52, 'Point Guard');
INSERT INTO Player (pid, position) VALUES (53, 'Shooting Guard');
INSERT INTO Player (pid, position) VALUES (54, 'Small Forward');
INSERT INTO Player (pid, position) VALUES (55, 'Center');

INSERT INTO Coach (tmid, tid) VALUES ( 5, 1);
INSERT INTO Coach (tmid, tid) VALUES ( 16, 2);
INSERT INTO Coach (tmid, tid) VALUES ( 23, 3);
INSERT INTO Coach (tmid, tid) VALUES ( 32, 4);
INSERT INTO Coach (tmid, tid) VALUES ( 50, 5);

INSERT INTO Season (year, start_date, end_date) VALUES (2019, DATE '2019-01-01', DATE '2019-10-15');
INSERT INTO Season (year, start_date, end_date) VALUES (2020, DATE '2020-01-25', DATE '2020-10-12');
INSERT INTO Season (year, start_date, end_date) VALUES (2021, DATE '2021-01-17', DATE '2021-10-11');
INSERT INTO Season (year, start_date, end_date) VALUES (2022, DATE '2022-01-16', DATE '2022-10-10');
INSERT INTO Season (year, start_date, end_date) VALUES (2023, DATE '2023-01-22', DATE '2023-10-15');

INSERT INTO Owner (name, age, net_worth) VALUES ('Magic Johnson', 62, 7000000);
INSERT INTO Owner (name, age, net_worth) VALUES ('Joe Lacob', 65, 5000000);
INSERT INTO Owner (name, age, net_worth) VALUES ('Wyc Grousbeck', 63, 40000000);
INSERT INTO Owner (name, age, net_worth) VALUES ('Jerry Reinsdorf', 59, 1500000);
INSERT INTO Owner (name, age, net_worth) VALUES ('Robert Pera', 71, 1100000);

INSERT INTO Owns (oname, tid) VALUES ('Magic Johnson', 1);
INSERT INTO Owns (oname, tid) VALUES ('Joe Lacob', 2);
INSERT INTO Owns (oname, tid) VALUES ('Wyc Grousbeck', 3);
INSERT INTO Owns (oname, tid) VALUES ('Jerry Reinsdorf', 4);
INSERT INTO Owns (oname, tid) VALUES ('Robert Pera', 5);

INSERT INTO Sponsor (name, contribution) VALUES ('Nike', 5000000);
INSERT INTO Sponsor (name, contribution) VALUES ('Adidas', 3000000);
INSERT INTO Sponsor (name, contribution) VALUES ('Coca-Cola', 2000000);
INSERT INTO Sponsor (name, contribution) VALUES ('Verizon', 1500000);
INSERT INTO Sponsor (name, contribution) VALUES ('IBM', 1000000);
INSERT INTO Sponsor VALUES ('AC Corporation', 500000);
INSERT INTO Sponsor VALUES ('X Corp', 7500000);
INSERT INTO Sponsor VALUES ('SportsTech Inc', 1000000);
INSERT INTO Sponsor VALUES ('City Bank', 30006770);
INSERT INTO Sponsor VALUES ('Global Motors', 80066540);


INSERT INTO Sponsors (sname, tid) VALUES ('Nike', 1);
INSERT INTO Sponsors (sname, tid) VALUES ('Adidas', 2);
INSERT INTO Sponsors (sname, tid) VALUES ('Coca-Cola', 3);
INSERT INTO Sponsors (sname, tid) VALUES ('Verizon', 4);
INSERT INTO Sponsors (sname, tid) VALUES ('IBM', 5);


UPDATE TEAMMEMBER set start_date = DATE '2017-01-01', END_DATE = DATE '2025-12-31' where (tid = 1 and age < 25);
Update TEAMMEMBER set start_date = DATE '2017-01-01', END_DATE = DATE '2025-12-31' where (tid = 2 and age < 25);
Update TEAMMEMBER set start_date = DATE '2018-01-01', END_DATE = DATE '2025-12-31' where (tid = 3 or tid = 4) and age > 25;
Update TEAMMEMBER set start_date = DATE '2018-01-01', END_DATE = DATE '2025-12-31' where (tid = 2 or tid = 5) and age > 25;
Update TEAMMEMBER set start_date = DATE '2018-01-01', END_DATE = DATE '2025-12-31' where (tmid = 52 or tmid = 53);
Update TEAMMEMBER set start_date = DATE '2018-01-01', END_DATE = DATE '2025-12-31' where  tmid = 53;
Update TEAMMEMBER set start_date = DATE '2018-01-01', END_DATE = DATE '2025-12-31' where  tmid = 54;


INSERT INTO HasPlayed (home_tid, away_tid) VALUES (1, 2);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (2, 3);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (3, 4);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (4, 5);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (5, 1);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (2, 4);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (3, 5);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (1, 3);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (4, 1);
INSERT INTO HasPlayed (home_tid, away_tid) VALUES (5, 2);

INSERT INTO Referee (rid, name, experience_years) VALUES (1, 'John Smith', 5);
INSERT INTO Referee (rid, name, experience_years) VALUES (2, 'Emily Johnson', 8);
INSERT INTO Referee (rid, name, experience_years) VALUES (3, 'Michael Davis', 6);
INSERT INTO Referee (rid, name, experience_years) VALUES (4, 'Sarah Wilson', 10);
INSERT INTO Referee (rid, name, experience_years) VALUES (5, 'David Brown', 3);
INSERT INTO Referee (rid, name, experience_years) VALUES (6, 'Jessica Miller', 7);
INSERT INTO Referee (rid, name, experience_years) VALUES (7, 'Brian Taylor', 9);
INSERT INTO Referee (rid, name, experience_years) VALUES (8, 'Amanda Clark', 4);
INSERT INTO Referee (rid, name, experience_years) VALUES (9, 'Christopher White', 2);
INSERT INTO Referee (rid, name, experience_years) VALUES (10, 'Ashley Harris', 5);

INSERT INTO StatSheet VALUES (1, 105, 100, 8, 6, 25, 20, 45, 38);
INSERT INTO StatSheet VALUES (2, 110, 112, 10, 9, 28, 24, 40, 42);
INSERT INTO StatSheet VALUES (3, 95, 98, 7, 8, 22, 18, 38, 41);
INSERT INTO StatSheet VALUES (4, 120, 118, 12, 10, 30, 26, 44, 39);
INSERT INTO StatSheet VALUES (5, 98, 105, 6, 7, 20, 22, 36, 40);
INSERT INTO StatSheet VALUES (6, 112, 108, 9, 8, 26, 23, 42, 37);
INSERT INTO StatSheet VALUES (7, 105, 102, 8, 9, 24, 21, 39, 40);
INSERT INTO StatSheet VALUES (8, 115, 112, 11, 10, 29, 25, 41, 38);
INSERT INTO StatSheet VALUES (9, 100, 96, 7, 6, 23, 19, 37, 35);
INSERT INTO StatSheet VALUES (10, 118, 115, 10, 11, 27, 24, 43, 41);
INSERT INTO StatSheet VALUES (11, 122, 120, 13, 11, 32, 28, 46, 43);
INSERT INTO StatSheet VALUES (12, 99, 104, 7, 8, 21, 20, 37, 39);
INSERT INTO StatSheet VALUES (13, 114, 110, 9, 9, 27, 23, 43, 36);
INSERT INTO StatSheet VALUES (14, 107, 104, 8, 10, 25, 22, 40, 41);
INSERT INTO StatSheet VALUES (15, 123, 122, 14, 12, 34, 30, 48, 45);
INSERT INTO StatSheet VALUES (16, 102, 100, 7, 7, 24, 19, 39, 37);
INSERT INTO StatSheet VALUES (17, 116, 114, 10, 11, 28, 25, 42, 40);
INSERT INTO StatSheet VALUES (18, 108, 106, 9, 9, 26, 23, 41, 38);
INSERT INTO StatSheet VALUES (19, 95, 98, 6, 7, 22, 18, 36, 40);
INSERT INTO StatSheet VALUES (20, 110, 115, 11, 10, 30, 26, 44, 42);

INSERT INTO Game VALUES ( DATE '2020-04-15', 1, 2, '105-100', 1, 2020, 1, 'Staples Center');
INSERT INTO Game VALUES ( DATE '2019-02-20', 2, 3, '110-112', 2, 2019, 2, 'Chase Center');
INSERT INTO Game VALUES ( DATE '2020-03-10', 3, 4, '95-98', 3, 2020, 3, 'TD Garden');
INSERT INTO Game VALUES ( DATE '2021-04-05', 4, 5, '120-118', 4, 2021, 4, 'United Center');
INSERT INTO Game VALUES ( DATE '2021-05-02', 5, 1, '98-105', 5, 2021, 5, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2023-06-18', 2, 4, '112-108', 6, 2023, 6, 'Chase Center');
INSERT INTO Game VALUES ( DATE '2022-07-12', 3, 5, '105-102', 7, 2022, 7, 'TD Garden');
INSERT INTO Game VALUES ( DATE '2022-08-28', 1, 3, '115-112', 8, 2022, 8, 'Staples Center');
INSERT INTO Game VALUES ( DATE '2023-09-14', 4, 1, '100-96', 9, 2023, 9, 'United Center');
INSERT INTO Game VALUES ( DATE '2020-05-30', 5, 2, '118-115', 10, 2020, 10, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2020-01-20', 4, 1, '118-115', 11, 2020, 3, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2020-02-10', 3, 4, '118-115', 12, 2020, 2, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2019-05-13', 1, 3, '118-115', 13, 2019, 1, 'TD Garden');
INSERT INTO Game VALUES ( DATE '2019-04-21', 4, 2, '118-115', 14, 2019, 4, 'United Center');
INSERT INTO Game VALUES ( DATE '2021-07-13', 5, 3, '118-115', 15, 2021, 5, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2021-08-16', 1, 5, '118-115', 16, 2021, 6, 'TD Garden');
INSERT INTO Game VALUES ( DATE '2022-02-25', 3, 2, '118-115', 17, 2022, 7, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2022-11-16', 1, 2, '118-115', 18, 2022, 8, 'United Center');
INSERT INTO Game VALUES ( DATE '2023-05-19', 4, 3, '118-115', 19, 2023, 9, 'Toyota Center');
INSERT INTO Game VALUES ( DATE '2023-12-21', 3, 1, '118-115', 20, 2023, 10, 'Staples Center');










