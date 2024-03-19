CREATE SCHEMA IF NOT EXISTS tourist_guide;
SET SCHEMA tourist_guide;

CREATE TABLE currency(
                         code VARCHAR(10) NOT NULL,
                         rate NUMERIC(19, 0),
                         PRIMARY KEY(code));

CREATE TABLE city(
                     ID INTEGER AUTO_INCREMENT,
                     name VARCHAR(255) UNIQUE,
                     PRIMARY KEY(ID));

CREATE TABLE tag(
                    ID INTEGER AUTO_INCREMENT,
                    tag VARCHAR(255) UNIQUE,
                    PRIMARY KEY(ID));

CREATE TABLE tourist_attraction(
                                   ID INTEGER AUTO_INCREMENT,
                                   name VARCHAR(250) UNIQUE,
                                   description VARCHAR(255),
                                   cityID INTEGER,
                                   price INTEGER,
                                   currencyCode VARCHAR(10),
                                   PRIMARY KEY(ID),
                                   FOREIGN KEY(cityID) REFERENCES city(ID),
                                   FOREIGN KEY (currencyCode) REFERENCES currency(code));

CREATE TABLE tourist_attraction_tag(
                                       attractionID INTEGER,
                                       tagID INTEGER,
                                       FOREIGN KEY(attractionID) REFERENCES tourist_attraction(ID),
                                       FOREIGN KEY(tagID) REFERENCES tag(ID));



------------------------------------------------------------------------------------------------------------------------
--INSERTION OF DATA / POPULATING DATABASE / SEEDING DATABASE

-- City selections
INSERT INTO city(name) VALUES('København_h2');
INSERT INTO city(name) VALUES('Odense_h2');
INSERT INTO city(name) VALUES('Kongens Lyngby_h2');

-- Tag selections
INSERT INTO tag(tag) VALUES('Børnevenlig_h2');
INSERT INTO tag(tag) VALUES('Gratis_h2');
INSERT INTO tag(tag) VALUES('Kunst_h2');
INSERT INTO tag(tag) VALUES('Museum_h2');
INSERT INTO tag(tag) VALUES('Natur_h2');

-- Currency
INSERT INTO currency(code, rate) VALUES('DKK', 1);

-- Tourist Attractions
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('SMK_h2', 'Statens Museum for Kunst_h2', 1, 99, 'DKK');
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('Odense Zoo_h2', 'Europas bedste zoo_h2', 2, 26, 'DKK');
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('Dyrehaven_h2', 'Naturpark med skovområder_h2', 3, 0, 'DKK');
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('Tivoli_h2', 'Forlystelsespark midt i København centrum_h2', 1, 250, 'DKK');

-- Tourist Attractions - tag
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(1, 3);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(1, 4);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(2, 1);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(3, 2);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(3, 5);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(4, 1);

COMMIT;