
----- PUZZLE_CONFIG ------

DROP TABLE PUZZLE_CONFIG IF EXISTS;

CREATE TABLE PUZZLE_CONFIG (
ID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
EXT_PUZZLE_CONFIG_ID VARCHAR(100) NOT NULL UNIQUE,
START_TIME TIMESTAMP NOT NULL
);

----- PUZZLE_FIELD ------

DROP TABLE PUZZLE_FIELD IF EXISTS;

CREATE TABLE PUZZLE_FIELD (
ID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
PUZZLE_CONFIG_ID INT NULL,
SHIFT_X INT NOT NULL,
SHIFT_Y INT NOT NULL,
FOREIGN KEY (PUZZLE_CONFIG_ID) REFERENCES PUZZLE_CONFIG(ID)
);

----- PUZZLE_DETAIL ----------
DROP TABLE PUZZLE_DETAIL IF EXISTS;

CREATE TABLE PUZZLE_DETAIL (
ID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
EXT_ID INT NOT NULL,
PUZZLE_CONFIG_ID INT NULL,
COLOR_LEFT_SIDE VARCHAR(30) NOT NULL,
PART_LEFT_SIDE VARCHAR(30) NOT NULL,
COLOR_UPPER_SIDE VARCHAR(30) NOT NULL,
PART_UPPER_SIDE VARCHAR(30) NOT NULL,
COLOR_RIGHT_SIDE VARCHAR(30) NOT NULL,
PART_RIGHT_SIDE VARCHAR(30) NOT NULL,
COLOR_LOWER_SIDE VARCHAR(30) NOT NULL,
PART_LOWER_SIDE VARCHAR(30) NOT NULL,
FOREIGN KEY (PUZZLE_CONFIG_ID) REFERENCES PUZZLE_CONFIG(ID)
);

----- ALLOWED_ROTATIONS -------
DROP TABLE ALLOWED_ROTATIONS IF EXISTS;

CREATE TABLE ALLOWED_ROTATIONS (
ID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
ROTATION INT NOT NULL,
PUZZLE_DETAIL_ID INT NULL,
FOREIGN KEY (PUZZLE_DETAIL_ID) REFERENCES PUZZLE_DETAIL(ID)
);

----- PUZZLE_STATE ---------
DROP TABLE PUZZLE_STATE IF EXISTS;

CREATE TABLE PUZZLE_STATE (
ID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
PUZZLE_CONFIG_ID INT NOT NULL,
FOREIGN KEY (PUZZLE_CONFIG_ID) REFERENCES PUZZLE_CONFIG(ID)
)

----- PUZZLE_STATE_FIELD ---------
DROP TABLE PUZZLE_STATE_FIELD IF EXISTS;

CREATE TABLE PUZZLE_STATE_FIELD (
ID INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
ROTATION INT NOT NULL,
PUZZLE_STATE_ID INT NOT NULL,
PUZZLE_DETAIL_ID INT NOT NULL,
PUZZLE_FIELD_ID INT NOT NULL,
FOREIGN KEY (PUZZLE_STATE_ID) REFERENCES PUZZLE_STATE(ID),
FOREIGN KEY (PUZZLE_DETAIL_ID) REFERENCES PUZZLE_DETAIL(ID),
FOREIGN KEY (PUZZLE_FIELD_ID) REFERENCES PUZZLE_FIELD(ID)
)





