------------------------------------------------------------
-- Insert 10 different contacts in the `hrm`.`Contact` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Sarpei', 'Hans', '12345', 'Musterstadt', 'Musterweg', '2', 'HS', '0121/412512', '1244/12519284', '7124/12709125', 'hans@sarpei.com');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Norris', 'Chuk', '1337', 'Black Hole', 'Roundhousestreet', '1', 'CN', '1111/111111', '2222/22222', '3333/333333', 'webmaster@internetz.com');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Bat', 'Man', '10001', 'Gotham City', 'Wayne-Tower', '1', 'BM', '0123/4567', '7654/3120', '1212/343434', 'bat@man.gc');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Leibniz', 'Gottfried Wilhelm', '30159', 'Hannover', 'Leibnizring', '1', '', '', '', '', '');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Torvalds', 'Linus', '1024', 'Helsinki', 'Tuxalle', '32', 'LT', '1024/2048', '256/5121024', '128/128128', 'linus.t@tux.com');
---------------------------
-- * Contact_Mobil is missing on purpose
---------------------------
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Jim', 'Knopf', '1111', 'Lummerland', 'Bergweg', '2', 'JK', '0102/030405', '0102/030406', '', 'jim@jk-travels.ll');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Lustig', 'Peter', '0000', 'Bärstadt', 'Bauwagen', '1', 'PL', '9987/012314', '9987/041231', '1541/125451', 'peter@isnichlustig.de');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Super', 'Richi', '5151', 'Supertown', 'Richiweg', '23', 'SR', '5125/554221', '61251/125125', '612541/124151', 'kommt@gefliegt.sr');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Schlumpf', 'Papa', '81813', 'Schlumpfdorf', 'Blueway', '2', 'PS', '3493/10981', '1251/0980915', '9009/09812', 'papa@bluemangroup.de');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Schlumpf', 'Schlaubi', '81813', 'Schlumpfdorf', 'Dorfstrasse', '3', 'SS', '3493/10141', '1251/09807987', '9009/0912441', 'schlaubi@aol.de');

------------------------------------------------------------
-- Insert 5 different places in the `hrm`.`Place` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Mezze', '85231', 'Freising', 'Osmanische Strasse', '2', 'EG R01', 'Ogerhöhle');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('HSWT', '85232', 'Freising', 'Wissensweg', '2', 'Keller', 'Abstellraum');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Schmukki', '85234', 'Freising', 'Bierweg', '1', 'EG Raum 2', 'Schänke');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Wayne-Towers', '10001', 'Gotham City', 'Wayne-Tower', '1', '5.112', 'Aufzugskammer');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Roche', '82340', 'Penzberg', 'Nonnenwald', '2', 'Geb. 112 R0512', 'Automatenraum');


