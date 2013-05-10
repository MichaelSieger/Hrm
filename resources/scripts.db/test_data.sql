------------------------------------------------------------
-- Insert 10 different contacts in the `Contact` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Sarpei', 'Hans', '12345', 'Musterstadt', 'Musterweg', '2', 'HS', '0121/412512', '1244/12519284', '7124/12709125', 'hans@sarpei.com');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Norris', 'Chuk', '1337', 'Black Hole', 'Roundhousestreet', '1', 'CN', '1111/111111', '2222/22222', '3333/333333', 'webmaster@internetz.com');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Bat', 'Man', '10001', 'Gotham City', 'Wayne-Tower', '1', 'BM', '0123/4567', '7654/3120', '1212/343434', 'bat@man.gc');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Leibniz', 'Gottfried Wilhelm', '30159', 'Hannover', 'Leibnizring', '1', '', '', '', '', '');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Torvalds', 'Linus', '1024', 'Helsinki', 'Tuxalle', '32', 'LT', '1024/2048', '256/5121024', '128/128128', 'linus.t@tux.com');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Jim', 'Knopf', '1111', 'Lummerland', 'Bergweg', '2', 'JK', '0102/030405', '0102/030406', '', 'jim@jk-travels.ll');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Lustig', 'Peter', '0000', 'Bärstadt', 'Bauwagen', '1', 'PL', '9987/012314', '9987/041231', '1541/125451', 'peter@isnichlustig.de');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Super', 'Richi', '5151', 'Supertown', 'Richiweg', '23', 'SR', '5125/554221', '61251/125125', '612541/124151', 'kommt@gefliegt.sr');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Schlumpf', 'Papa', '81813', 'Schlumpfdorf', 'Blueway', '2', 'PS', '3493/10981', '1251/0980915', '9009/09812', 'papa@bluemangroup.de');
INSERT INTO `hrm`.`Contact` (`Contact_Name`, `Contact_First_Name`, `Contact_Zip_Code`, `Contact_City`, `Contact_Street`, `Contact_Street_Number`, `Contact_Shortcut`, `Contact_Phone`, `Contact_Fax`, `Contact_Mobile`, `Contact_Email`) VALUES ('Schlumpf', 'Schlaubi', '81813', 'Schlumpfdorf', 'Dorfstrasse', '3', 'SS', '3493/10141', '1251/09807987', '9009/0912441', 'schlaubi@aol.de');

------------------------------------------------------------
-- Insert 5 different places in the `Place` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Mezze', '85231', 'Freising', 'Osmanische Strasse', '2', 'EG R01', 'Ogerhöhle');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('HSWT', '85232', 'Freising', 'Wissensweg', '2', 'Keller', 'Abstellraum');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Schmukki', '85234', 'Freising', 'Bierweg', '1', 'EG Raum 2', 'Schänke');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Wayne-Towers', '10001', 'Gotham City', 'Wayne-Tower', '1', '5.112', 'Aufzugskammer');
INSERT INTO `hrm`.`Place` (`Place_Name`, `Place_Zip_Code`, `Place_City`, `Place_Street`, `Place_Street_Number`, `Place_Location`, `Place_Area`) VALUES ('Roche', '82340', 'Penzberg', 'Nonnenwald', '2', 'Geb. 112 R0512', 'Automatenraum');

------------------------------------------------------------
-- Insert 3 different plants in the `Plant` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Plant` (`Plant_Place_FK`, `Plant_Inspection_Interval`, `Plant_Manufacturer`, `Plant_Year_Of_Construction`, `Plant_Type`, `Plant_Airperformance`, `Plant_Motorpower`, `Plant_Motor_Rpm`, `Plant_Ventilatorperformance`, `Plant_Current`, `Plant_Voltage`, `Plant_Note`, `Plant_Description`) VALUES ('2','10', 'Bauer', '2001', 'Phaser 3000' , '1000', '260', '4200', '1.4', '16', '230', 'gutes Teil', 'Anlage 11');
INSERT INTO `hrm`.`Plant` (`Plant_Place_FK`, `Plant_Inspection_Interval`, `Plant_Manufacturer`, `Plant_Year_Of_Construction`, `Plant_Type`, `Plant_Airperformance`, `Plant_Motorpower`, `Plant_Motor_Rpm`, `Plant_Ventilatorperformance`, `Plant_Current`, `Plant_Voltage`, `Plant_Note`, `Plant_Description`) VALUES ('5','12', 'Jimbo GmbH', '2007', 'AirLine' , '3000', '460', '5200', '9', '16', '230', 'schlechte Verarbeitung', 'Lüftung 1');
INSERT INTO `hrm`.`Plant` (`Plant_Place_FK`, `Plant_Inspection_Interval`, `Plant_Manufacturer`, `Plant_Year_Of_Construction`, `Plant_Type`, `Plant_Airperformance`, `Plant_Motorpower`, `Plant_Motor_Rpm`, `Plant_Ventilatorperformance`, `Plant_Current`, `Plant_Voltage`, `Plant_Note`, `Plant_Description`) VALUES ('3','1', 'Medion', '1999', 'PowerBlast 3.1' , '400', '160', '2200', '2', '16', '230', 'aus Plastik *würg*', 'Tower 3');

------------------------------------------------------------
-- Insert 3 different cataloges in the `Catalog` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Catalog` (`Catalog_Name`) VALUES ('Katalog_Filter');
INSERT INTO `hrm`.`Catalog` (`Catalog_Name`) VALUES ('Katalog_Motor');
INSERT INTO `hrm`.`Catalog` (`Catalog_Name`) VALUES ('Katalog_Heizung');

------------------------------------------------------------
-- Insert 4 different categories in the `Category` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Category` (`Category_Name`, `Category_Height`, `Category_Width`, `Category_Default_Qualifier`, `Category_Default_Bool_Rating`, `Category_Catalog_FK`) VALUES ('Filter', '4', '4', '1', '1', '1');
INSERT INTO `hrm`.`Category` (`Category_Name`, `Category_Height`, `Category_Width`, `Category_Default_Qualifier`, `Category_Default_Bool_Rating`, `Category_Catalog_FK`) VALUES ('Motor', '4', '2', '1', '1', '2');
INSERT INTO `hrm`.`Category` (`Category_Name`, `Category_Height`, `Category_Width`, `Category_Default_Qualifier`, `Category_Default_Bool_Rating`, `Category_Catalog_FK`) VALUES ('Heizung', '3', '3', '3', '0', '3');
INSERT INTO `hrm`.`Category` (`Category_Name`, `Category_Height`, `Category_Width`, `Category_Default_Qualifier`, `Category_Default_Bool_Rating`, `Category_Catalog_FK`) VALUES ('Spezial Filter', '2', '2', '3', '0', '1');
------------------------------------------------------------
-- Insert 3 different components in the `Component` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Component` (`Component_Name`, `Component_Symbol_LR`, `Component_Symbol_RL`, `Component_Symbol_UD`, `Component_Symbol_DU`, `Component_Quantifier`, `Component_Category_FK`, `Component_Bool_Rating`) VALUES ('Heizschlange', '/home/bla.pdf', '/home/bla.pdf', '/home/bla.pdf', '/home/bla.pdf', '2', '3', '1');
INSERT INTO `hrm`.`Component` (`Component_Name`, `Component_Symbol_LR`, `Component_Symbol_RL`, `Component_Symbol_UD`, `Component_Symbol_DU`, `Component_Quantifier`, `Component_Category_FK`, `Component_Bool_Rating`) VALUES ('Feinfilter', '/home/bla.pdf', '/home/bla.pdf', '/home/bla.pdf', '/home/bla.pdf', '1', '4', '');
INSERT INTO `hrm`.`Component` (`Component_Name`, `Component_Symbol_LR`, `Component_Symbol_RL`, `Component_Symbol_UD`, `Component_Symbol_DU`, `Component_Quantifier`, `Component_Category_FK`, `Component_Bool_Rating`) VALUES ('Motor', '/home/bla.pdf', '/home/bla.pdf', '/home/bla.pdf', '/home/bla.pdf', '', '2', '1');

------------------------------------------------------------
-- Insert 3 different schemes in the `Scheme` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Scheme` (`Scheme_Plant_FK`) VALUES ('1');
INSERT INTO `hrm`.`Scheme` (`Scheme_Plant_FK`) VALUES ('2');
INSERT INTO `hrm`.`Scheme` (`Scheme_Plant_FK`) VALUES ('2');

------------------------------------------------------------
-- Insert 3 different schemes in the `Scheme` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Scheme_Component` (`Scheme_Component_Scheme_FK`, `Scheme_Component_Component_FK`, `Scheme_Component_X_Position`, `Scheme_Component_Y_Position`, `Scheme_Component_Direction`) VALUES ('1', '1', '1', '1', '2');
INSERT INTO `hrm`.`Scheme_Component` (`Scheme_Component_Scheme_FK`, `Scheme_Component_Component_FK`, `Scheme_Component_X_Position`, `Scheme_Component_Y_Position`, `Scheme_Component_Direction`) VALUES ('1', '2', '1', '4', '2');
INSERT INTO `hrm`.`Scheme_Component` (`Scheme_Component_Scheme_FK`, `Scheme_Component_Component_FK`, `Scheme_Component_X_Position`, `Scheme_Component_Y_Position`, `Scheme_Component_Direction`) VALUES ('3', '3', '1', '1', '0');

------------------------------------------------------------
-- Insert 3 different current states in the `State_Current` Table
------------------------------------------------------------
INSERT INTO `hrm`.`State_Current` (`State_Current_Name`, `State_Current_Text`) VALUES ('Ist-Status 1', 'Ich bin der erste Ist-Zustand');
INSERT INTO `hrm`.`State_Current` (`State_Current_Name`, `State_Current_Text`) VALUES ('Ist-Status 2', 'Ich bin der zweite Ist-Zustand');
INSERT INTO `hrm`.`State_Current` (`State_Current_Name`, `State_Current_Text`) VALUES ('Ist-Status 3', 'Ich bin der dritte Ist-Zustand');

------------------------------------------------------------
-- Insert 3 different activities in the `State_Activity` Table
------------------------------------------------------------
INSERT INTO `hrm`.`State_Activity` (`State_Activity_Name`, `State_Activity_Text`) VALUES ('Maßnahme 1', 'Ich bin die erste Maßnahme');
INSERT INTO `hrm`.`State_Activity` (`State_Activity_Name`, `State_Activity_Text`) VALUES ('Maßnahme 2', 'Ich bin die zweite Maßnahme');
INSERT INTO `hrm`.`State_Activity` (`State_Activity_Name`, `State_Activity_Text`) VALUES ('Maßnahme 3', 'Ich bin die dritte Maßnahme');

------------------------------------------------------------
-- Insert 3 different target states in the `State_Target` Table
------------------------------------------------------------
INSERT INTO `hrm`.`State_Target` (`State_Target_Name`, `State_Target_Text`) VALUES ('Soll-Status 1', 'Ich bin der erste Soll-Zustand');
INSERT INTO `hrm`.`State_Target` (`State_Target_Name`, `State_Target_Text`) VALUES ('Soll-Status 2', 'Ich bin der zweite Soll-Zustand');
INSERT INTO `hrm`.`State_Target` (`State_Target_Name`, `State_Target_Text`) VALUES ('Soll-Status 3', 'Ich bin der dritte Soll-Zustand');

------------------------------------------------------------
-- Connect 3 different catalog with currents in the `Catalog_Current` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Catalog_Current` (`Category_Current_State_Current_FK`, `Category_Current_Catalog_FK`) VALUES ('1', '2');
INSERT INTO `hrm`.`Catalog_Current` (`Category_Current_State_Current_FK`, `Category_Current_Catalog_FK`) VALUES ('2', '1');
INSERT INTO `hrm`.`Catalog_Current` (`Category_Current_State_Current_FK`, `Category_Current_Catalog_FK`) VALUES ('2', '3');

------------------------------------------------------------
-- Connect 3 different catalog with activities the `Catalog_Activities` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Catalog_Activity` (`Category_Activity_State_Activity_FK`, `Category_Activity_Catalog_FK`) VALUES ('1', '2');
INSERT INTO `hrm`.`Catalog_Activity` (`Category_Activity_State_Activity_FK`, `Category_Activity_Catalog_FK`) VALUES ('2', '1');
INSERT INTO `hrm`.`Catalog_Activity` (`Category_Activity_State_Activity_FK`, `Category_Activity_Catalog_FK`) VALUES ('2', '3');

------------------------------------------------------------
-- Connect 3 different catalog with targets in the `Catalog_Target` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Catalog_Target` (`Category_Target_State_Target_FK`, `Category_Target_Catalog_FK`) VALUES ('1', '2');
INSERT INTO `hrm`.`Catalog_Target` (`Category_Target_State_Target_FK`, `Category_Target_Catalog_FK`) VALUES ('2', '1');
INSERT INTO `hrm`.`Catalog_Target` (`Category_Target_State_Target_FK`, `Category_Target_Catalog_FK`) VALUES ('2', '3');

------------------------------------------------------------
-- Connect 2 different plants with contacts in the `Plant_Contact` Table
------------------------------------------------------------
INSERT INTO `hrm`.`Plant_Contact` (`Plant_Contact_Plant_FK`, `Plant_Contact_Contact_FK`) VALUES ('1','1' );
INSERT INTO `hrm`.`Plant_Contact` (`Plant_Contact_Plant_FK`, `Plant_Contact_Contact_FK`) VALUES ('2','2' );
