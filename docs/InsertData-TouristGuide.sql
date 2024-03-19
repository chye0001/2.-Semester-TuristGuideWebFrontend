USE tourist_guide;

-- City selections
INSERT INTO city(name) VALUES('København');
INSERT INTO city(name) VALUES('Odense');
INSERT INTO city(name) VALUES('Kongens Lyngby');

-- Tag selections
INSERT INTO tag(name) VALUES('Børnevenlig');
INSERT INTO tag(name) VALUES('Gratis');
INSERT INTO tag(name) VALUES('Kunst');
INSERT INTO tag(name) VALUES('Museum');
INSERT INTO tag(name) VALUES('Natur');

-- Currency
INSERT INTO currency(code, rate) VALUES('DKK', 1);

-- Tourist Attractions
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('SMK', 'Statens Museum for Kunst', 1, 99, 'DKK');
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('Odense Zoo', 'Europas bedste zoo', 2, 26, 'DKK');
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('Dyrehaven', 'Naturpark med skovområder', 3, 0, 'DKK');
INSERT INTO tourist_attraction(name, description, cityID, price, currencyCode)
VALUES('Tivoli', 'Forlystelsespark midt i København centrum', 1, 250, 'DKK');

-- Tourist Attractions - tag
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(1, 3);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(1, 4);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(2, 1);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(3, 2);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(3, 5);
INSERT INTO tourist_attraction_tag(attractionID, tagID) VALUES(4, 1);

COMMIT;