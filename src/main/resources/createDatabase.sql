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