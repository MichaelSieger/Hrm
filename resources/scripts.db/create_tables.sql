-- -----------------------------------------------------
-- This script generates the HRM Database Scheme 
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `Contact`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Contact` (
  `Contact_ID` INT NOT NULL AUTO_INCREMENT ,
  `Contact_Name` TEXT NOT NULL ,
  `Contact_Zip_Code` VARCHAR(45) NOT NULL ,
  `Contact_City` VARCHAR(45) NOT NULL ,
  `Contact_Street` VARCHAR(255) NOT NULL ,
  `Contact_Street_Number` VARCHAR(5) NOT NULL ,
  `Contact_Shortcut` VARCHAR(45) NULL ,
  `Contact_Phone` VARCHAR(45) NULL ,
  `Contact_Fax` VARCHAR(45) NULL ,
  `Contact_Mobile` VARCHAR(45) NULL ,
  `Contact_Email` VARCHAR(45) NULL ,
  PRIMARY KEY (`Contact_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Place`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Place` (
  `Place_ID` INT NOT NULL AUTO_INCREMENT ,
  `Place_Name` VARCHAR(255) NOT NULL ,
  `Place_Zip_Code` VARCHAR(45) NOT NULL ,
  `Place_City` VARCHAR(45) NOT NULL ,
  `Place_Street` VARCHAR(255) NOT NULL ,
  `Place_Street_Number` VARCHAR(5) NOT NULL ,
  PRIMARY KEY (`Place_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Plant`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Plant` (
  `Plant_ID` INT NOT NULL AUTO_INCREMENT ,
  `Plant_Place_FK` INT NULL ,
  `Plant_Manufacturer` VARCHAR(45) NULL ,
  `Plant_Year_Of_Construction` YEAR NULL ,
  `Plant_Type` VARCHAR(255) NULL ,
  `Plant_Airperformance` VARCHAR(255) NULL ,
  `Plant_Motorpower` VARCHAR(25) NULL ,
  `Plant_Motor_Rpm` VARCHAR(25) NULL ,
  `Plant_Ventilatorperformance` VARCHAR(25) NULL ,
  `Plant_Current` VARCHAR(25) NULL ,
  `Plant_Voltage` VARCHAR(25) NULL ,
  `Plant_Note` TEXT NULL ,
  `Plant_Description` VARCHAR(255) NOT NULL ,
  `Plant_Area` VARCHAR(45) NULL ,
  `Plant_Location` VARCHAR(45) NULL ,
  PRIMARY KEY (`Plant_ID`) ,
  INDEX `Plant_Place_FK` (`Plant_Place_FK` ASC) ,
  CONSTRAINT `Plant_Place_FK`
    FOREIGN KEY (`Plant_Place_FK` )
    REFERENCES `Place` (`Place_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Layout`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Layout` (
  `Layout_ID` INT NOT NULL AUTO_INCREMENT ,
  `Layout_Name` VARCHAR(45) NOT NULL ,
  `Layout_Filename` VARCHAR(255) NULL ,
  PRIMARY KEY (`Layout_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Picture`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Picture` (
  `Picture_ID` INT NOT NULL AUTO_INCREMENT ,
  `Picture_Blob` LONGBLOB NULL ,
  `Picture_Name` VARCHAR(255) NULL ,
  `Picture_Label` VARCHAR(100) NULL ,
  PRIMARY KEY (`Picture_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Scheme`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Scheme` (
  `Scheme_ID` INT NOT NULL AUTO_INCREMENT ,
  `Scheme_Timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `Scheme_Plant_FK` INT NULL ,
  PRIMARY KEY (`Scheme_ID`) ,
  INDEX `Scheme_Plant_IDx` (`Scheme_Plant_FK` ASC) ,
  CONSTRAINT `Scheme_Plant_FK`
    FOREIGN KEY (`Scheme_Plant_FK` )
    REFERENCES `Plant` (`Plant_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Report`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Report` (
  `Report_ID` INT NOT NULL AUTO_INCREMENT ,
  `Report_Layout_FK` INT NULL ,
  `Report_Plant_FK` INT NULL ,
  `Report_Requester_FK` INT NULL ,
  `Report_Contractor_FK` INT NULL ,
  `Report_Checker_FK` INT NULL ,
  `Report_Jobdate` DATE NOT NULL ,
  `Report_Reportdate` DATE NOT NULL ,
  `Report_Nextdate` DATE NOT NULL ,
  `Report_Airtemperature` FLOAT NULL ,
  `Report_Humidity` FLOAT NULL ,
  `Report_Summary` TEXT NULL ,
  `Report_Titel` VARCHAR(255) NULL ,
  `Report_Humidity_Rating` INT NULL ,
  `Report_Humidity_Quantifier` INT NULL ,
  `Report_Airtemperature_Rating` INT NULL ,
  `Report_Airtemperature_Quantifier` INT NULL ,
  `Report_Frontpicture_FK` INT NULL ,
  `Report_Plantpicture_FK` INT NULL ,
  `Report_Airtemperature_Comment` TEXT NULL ,
  `Report_Humidity_Comment` TEXT NULL ,
  `Report_Legionella` FLOAT NULL ,
  `Report_Legionella_Rating` INT NULL ,
  `Report_Legionella_Quantifier` INT NULL ,
  `Report_Legionella_Comment` TEXT NULL ,
  `Report_Germs` FLOAT NULL ,
  `Report_Germs_Rating` INT NULL ,
  `Report_Germs_Quantifier` INT NULL ,
  `Report_Germs_Comment` TEXT NULL ,
  `Report_Scheme_FK` INT NULL ,
  PRIMARY KEY (`Report_ID`) ,
  INDEX `Report_Layout_IDx` (`Report_Layout_FK` ASC) ,
  INDEX `Report_Plant_IDx` (`Report_Plant_FK` ASC) ,
  INDEX `Report_Contact_Req_IDx` (`Report_Requester_FK` ASC) ,
  INDEX `Report_Contact_Con_IDx` (`Report_Contractor_FK` ASC) ,
  INDEX `Report_Contact_Che_IDx` (`Report_Checker_FK` ASC) ,
  INDEX `Report_Frontpicture_FK` (`Report_Frontpicture_FK` ASC) ,
  INDEX `Report_Plantpicture_FK` (`Report_Plantpicture_FK` ASC) ,
  INDEX `Report_Scheme_FK` (`Report_Scheme_FK` ASC) ,
  CONSTRAINT `Report_Layout_FK`
    FOREIGN KEY (`Report_Layout_FK` )
    REFERENCES `Layout` (`Layout_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Plant_FK`
    FOREIGN KEY (`Report_Plant_FK` )
    REFERENCES `Plant` (`Plant_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Contact_Req_FK`
    FOREIGN KEY (`Report_Requester_FK` )
    REFERENCES `Contact` (`Contact_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Contact_Con_FK`
    FOREIGN KEY (`Report_Contractor_FK` )
    REFERENCES `Contact` (`Contact_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Contact_Che_FK`
    FOREIGN KEY (`Report_Checker_FK` )
    REFERENCES `Contact` (`Contact_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Frontpicture_FK`
    FOREIGN KEY (`Report_Frontpicture_FK` )
    REFERENCES `Picture` (`Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Plantpicture_FK`
    FOREIGN KEY (`Report_Plantpicture_FK` )
    REFERENCES `Picture` (`Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Report_Scheme_FK`
    FOREIGN KEY (`Report_Scheme_FK` )
    REFERENCES `Scheme` (`Scheme_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog` (
  `Catalog_ID` INT NOT NULL AUTO_INCREMENT ,
  `Catalog_Name` VARCHAR(45) NULL ,
  PRIMARY KEY (`Catalog_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Category`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Category` (
  `Category_ID` INT NOT NULL AUTO_INCREMENT ,
  `Category_Name` VARCHAR(45) NULL ,
  `Category_Height` VARCHAR(45) NULL ,
  `Category_Width` VARCHAR(45) NULL ,
  `Category_Default_Quantifier` VARCHAR(45) NULL ,
  `Category_Default_Bool_Rating` TINYINT(1) NULL ,
  `Category_Catalog_FK` INT NULL DEFAULT NULL ,
  PRIMARY KEY (`Category_ID`) ,
  INDEX `Category_Catalog_FK` (`Category_Catalog_FK` ASC) ,
  CONSTRAINT `Category_Catalog_FK`
    FOREIGN KEY (`Category_Catalog_FK` )
    REFERENCES `Catalog` (`Catalog_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Component_Picture`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Component_Picture` (
  `Component_Picture_ID` INT NOT NULL AUTO_INCREMENT ,
  `Component_Picture_Blob` LONGBLOB NULL ,
  `Component_Picture_Filename` VARCHAR(255) NULL ,
  PRIMARY KEY (`Component_Picture_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Component`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Component` (
  `Component_ID` INT NOT NULL AUTO_INCREMENT ,
  `Component_Name` VARCHAR(45) NULL ,
  `Component_Symbol_LR_FK` INT NULL ,
  `Component_Symbol_RL_FK` INT NULL ,
  `Component_Symbol_UD_FK` INT NULL ,
  `Component_Symbol_DU_FK` INT NULL ,
  `Component_Quantifier` INT NULL DEFAULT -1 ,
  `Component_Category_FK` INT NULL ,
  `Component_Bool_Rating` TINYINT(1) NULL ,
  PRIMARY KEY (`Component_ID`) ,
  INDEX `Component_Category_FK` (`Component_Category_FK` ASC) ,
  INDEX `Component_Symbol_LR_FK` (`Component_Symbol_LR_FK` ASC) ,
  INDEX `Component_Symbol_RL_FK` (`Component_Symbol_RL_FK` ASC) ,
  INDEX `Component_Symbol_UD_FK` (`Component_Symbol_UD_FK` ASC) ,
  INDEX `Component_Symbol_DU_FK` (`Component_Symbol_DU_FK` ASC) ,
  CONSTRAINT `Component_Category_FK`
    FOREIGN KEY (`Component_Category_FK` )
    REFERENCES `Category` (`Category_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Symbol_LR_FK`
    FOREIGN KEY (`Component_Symbol_LR_FK` )
    REFERENCES `Component_Picture` (`Component_Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Symbol_RL_FK`
    FOREIGN KEY (`Component_Symbol_RL_FK` )
    REFERENCES `Component_Picture` (`Component_Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Symbol_UD_FK`
    FOREIGN KEY (`Component_Symbol_UD_FK` )
    REFERENCES `Component_Picture` (`Component_Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Symbol_DU_FK`
    FOREIGN KEY (`Component_Symbol_DU_FK` )
    REFERENCES `Component_Picture` (`Component_Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Scheme_Component`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Scheme_Component` (
  `Scheme_Component_ID` INT NOT NULL AUTO_INCREMENT ,
  `Scheme_Component_Scheme_FK` INT NULL ,
  `Scheme_Component_Component_FK` INT NULL ,
  `Scheme_Component_X_Position` INT NULL ,
  `Scheme_Component_Y_Position` INT NULL ,
  `Scheme_Component_Direction` INT NULL ,
  PRIMARY KEY (`Scheme_Component_ID`) ,
  INDEX `Scheme_Component_Scheme_FK` (`Scheme_Component_Scheme_FK` ASC) ,
  INDEX `Scheme_Component_Component_FK` (`Scheme_Component_Component_FK` ASC) ,
  CONSTRAINT `Scheme_Component_Scheme_FK`
    FOREIGN KEY (`Scheme_Component_Scheme_FK` )
    REFERENCES `Scheme` (`Scheme_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Scheme_Component_Component_FK`
    FOREIGN KEY (`Scheme_Component_Component_FK` )
    REFERENCES `Component` (`Component_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Biological_Rating`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Biological_Rating` (
  `Biological_Rating_ID` INT NOT NULL AUTO_INCREMENT ,
  `Biological_Rating_Component_FK` INT NOT NULL ,
  `Biological_Rating_Report_FK` INT NOT NULL ,
  `Biological_Rating_Bacteria_Count` INT NULL ,
  `Biological_Rating_Rating` INT NULL ,
  `Biological_Rating_Quantifier` INT NULL ,
  `Biological_Rating_Comment` VARCHAR(255) NULL ,
  `Biological_Rating_Flag` VARCHAR(100) NULL ,
  `Biological_Rating_Sampling_Type_Point` INT NULL ,
  PRIMARY KEY (`Biological_Rating_ID`) ,
  INDEX `Biological_Rating_Component_IDx` (`Biological_Rating_Component_FK` ASC) ,
  INDEX `Biological_Rating_Report_IDx` (`Biological_Rating_Report_FK` ASC) ,
  CONSTRAINT `Biological_Rating_Component_FK`
    FOREIGN KEY (`Biological_Rating_Component_FK` )
    REFERENCES `Scheme_Component` (`Scheme_Component_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Biological_Rating_Report_FK`
    FOREIGN KEY (`Biological_Rating_Report_FK` )
    REFERENCES `Report` (`Report_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Session`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Session` (
  `Session_Uuid` VARCHAR(36) NOT NULL ,
  `Session_User` VARCHAR(30) NOT NULL ,
  `Session_Timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`Session_Uuid`) ,
  UNIQUE INDEX `Session_Uuid_UNIQUE` (`Session_Uuid` ASC) )
ENGINE = InnoDB
PACK_KEYS = DEFAULT;


-- -----------------------------------------------------
-- Table `State_Current`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `State_Current` (
  `State_Current_ID` INT NOT NULL AUTO_INCREMENT ,
  `State_Current_Name` VARCHAR(100) NULL ,
  `State_Current_Text` TEXT NULL ,
  PRIMARY KEY (`State_Current_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `State_Target`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `State_Target` (
  `State_Target_ID` INT NOT NULL AUTO_INCREMENT ,
  `State_Target_Name` VARCHAR(100) NULL ,
  `State_Target_Text` TEXT NULL ,
  PRIMARY KEY (`State_Target_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `State_Activity`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `State_Activity` (
  `State_Activity_ID` INT NOT NULL AUTO_INCREMENT ,
  `State_Activity_Name` VARCHAR(100) NULL ,
  `State_Activity_Text` TEXT NULL ,
  PRIMARY KEY (`State_Activity_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog_Current`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog_Current` (
  `Catalog_Current_State_Target_FK` INT NOT NULL ,
  `Catalog_Current_State_Current_FK` INT NOT NULL ,
  INDEX `Category_Current_State_Current_IDx` (`Catalog_Current_State_Current_FK` ASC) ,
  INDEX `Category_Current_Catalog_IDx` (`Catalog_Current_State_Target_FK` ASC) ,
  PRIMARY KEY (`Catalog_Current_State_Target_FK`, `Catalog_Current_State_Current_FK`) ,
  CONSTRAINT `Catalog_Current_State_Current_FK`
    FOREIGN KEY (`Catalog_Current_State_Current_FK` )
    REFERENCES `State_Current` (`State_Current_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Catalog_Current_State_Target_FK`
    FOREIGN KEY (`Catalog_Current_State_Target_FK` )
    REFERENCES `State_Target` (`State_Target_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog_Target`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog_Target` (
  `Catalog_Target_State_Target_FK` INT NOT NULL ,
  `Catalog_Target_Catalog_FK` INT NOT NULL ,
  INDEX `Category_Target_State_Target_IDx` (`Catalog_Target_State_Target_FK` ASC) ,
  INDEX `Category_Target_Catalog_IDx` (`Catalog_Target_Catalog_FK` ASC) ,
  PRIMARY KEY (`Catalog_Target_State_Target_FK`, `Catalog_Target_Catalog_FK`) ,
  CONSTRAINT `Catalog_Target_State_Target_FK`
    FOREIGN KEY (`Catalog_Target_State_Target_FK` )
    REFERENCES `State_Target` (`State_Target_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Catalog_Target_Catalog_FK`
    FOREIGN KEY (`Catalog_Target_Catalog_FK` )
    REFERENCES `Catalog` (`Catalog_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog_Activity`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog_Activity` (
  `Catalog_Activity_State_Current_FK` INT NOT NULL ,
  `Catalog_Activity_State_Activity_FK` INT NOT NULL ,
  INDEX `Category_Activity_State_Activity_IDx` (`Catalog_Activity_State_Activity_FK` ASC) ,
  INDEX `Category_Activity_Catalog_IDx` (`Catalog_Activity_State_Current_FK` ASC) ,
  PRIMARY KEY (`Catalog_Activity_State_Current_FK`, `Catalog_Activity_State_Activity_FK`) ,
  CONSTRAINT `Catalog_Activity_State_Activity_FK`
    FOREIGN KEY (`Catalog_Activity_State_Activity_FK` )
    REFERENCES `State_Activity` (`State_Activity_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Catalog_Activity_State_Current_FK`
    FOREIGN KEY (`Catalog_Activity_State_Current_FK` )
    REFERENCES `State_Current` (`State_Current_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Lock`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Lock` (
  `Lock_Table` VARCHAR(45) NOT NULL ,
  `Lock_Row_ID` INT NOT NULL ,
  `Lock_Uuid_FK` VARCHAR(36) NOT NULL ,
  `Lock_Timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`Lock_Table`, `Lock_Row_ID`) ,
  INDEX `Lock_Uuid_IDx` (`Lock_Uuid_FK` ASC) ,
  CONSTRAINT `Lock_Uuid_FK`
    FOREIGN KEY (`Lock_Uuid_FK` )
    REFERENCES `Session` (`Session_Uuid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Component_Physical_Rating`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Component_Physical_Rating` (
  `Component_Physical_Rating_ID` INT NOT NULL AUTO_INCREMENT ,
  `Component_Physical_Rating_Component_FK` INT NULL ,
  `Component_Physical_Rating_Rating` INT NULL ,
  `Component_Physical_Rating_Note` TEXT NULL ,
  `Component_Physical_Rating_Report_FK` INT NULL ,
  `Component_Physical_Rating_Quantifier` INT NULL ,
  `Component_Physical_Rating_Sampling_Type_Point` INT NULL ,
  PRIMARY KEY (`Component_Physical_Rating_ID`) ,
  INDEX `Component_Physical_Rating_Component_FK` (`Component_Physical_Rating_Component_FK` ASC) ,
  INDEX `Component_Physical_Rating_Report_FK` (`Component_Physical_Rating_Report_FK` ASC) ,
  CONSTRAINT `Component_Physical_Rating_Component_FK`
    FOREIGN KEY (`Component_Physical_Rating_Component_FK` )
    REFERENCES `Scheme_Component` (`Scheme_Component_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Physical_Rating_Report_FK`
    FOREIGN KEY (`Component_Physical_Rating_Report_FK` )
    REFERENCES `Report` (`Report_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Priority`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Priority` (
  `Priority_Id` INT NOT NULL AUTO_INCREMENT ,
  `Priority_Name` VARCHAR(45) NULL ,
  `Priority_Text` TEXT NULL ,
  `Priority_Priority` INT NULL ,
  PRIMARY KEY (`Priority_Id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Performance`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Performance` (
  `Performance_ID` INT NOT NULL AUTO_INCREMENT ,
  `Performance_Component_FK` INT NULL ,
  `Performance_Target_FK` INT NULL ,
  `Performance_Current_FK` INT NULL ,
  `Performance_Activity_FK` INT NULL ,
  `Performance_Picture_FK` INT NULL ,
  `Performance_Priority_FK` INT NULL ,
  PRIMARY KEY (`Performance_ID`) ,
  INDEX `Component_Catalog_Component_FK` (`Performance_Component_FK` ASC) ,
  INDEX `Component_Catalog_Target_FK` (`Performance_Target_FK` ASC) ,
  INDEX `Component_Catalog_Current_FK` (`Performance_Current_FK` ASC) ,
  INDEX `Component_Catalog_Activity_FK` (`Performance_Activity_FK` ASC) ,
  INDEX `Component_Catalog_Priority_FK` (`Performance_Priority_FK` ASC) ,
  CONSTRAINT `Component_Catalog_Component_FK`
    FOREIGN KEY (`Performance_Component_FK` )
    REFERENCES `Scheme_Component` (`Scheme_Component_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Catalog_Target_FK`
    FOREIGN KEY (`Performance_Target_FK` )
    REFERENCES `State_Target` (`State_Target_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Catalog_Current_FK`
    FOREIGN KEY (`Performance_Current_FK` )
    REFERENCES `State_Current` (`State_Current_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Catalog_Activity_FK`
    FOREIGN KEY (`Performance_Activity_FK` )
    REFERENCES `State_Activity` (`State_Activity_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Component_Catalog_Priority_FK`
    FOREIGN KEY (`Performance_Priority_FK` )
    REFERENCES `Priority` (`Priority_Id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Picture_Catalog`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Picture_Catalog` (
  `Picture_Catalog_Picture_FK` INT NOT NULL ,
  `Picture_Catalog_Performance_FK` INT NOT NULL ,
  PRIMARY KEY (`Picture_Catalog_Picture_FK`, `Picture_Catalog_Performance_FK`) ,
  INDEX `Picture_Catalog_Picture_FK` (`Picture_Catalog_Picture_FK` ASC) ,
  INDEX `Picture_Catalog_Component_Catalog_FK` (`Picture_Catalog_Performance_FK` ASC) ,
  CONSTRAINT `Picture_Catalog_Picture_FK`
    FOREIGN KEY (`Picture_Catalog_Picture_FK` )
    REFERENCES `Picture` (`Picture_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Picture_Catalog_Component_Catalog_FK`
    FOREIGN KEY (`Picture_Catalog_Performance_FK` )
    REFERENCES `Performance` (`Performance_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Target_Current`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Target_Current` (
  `Target_Current_Report_FK` INT NOT NULL ,
  `Target_Current_Performance_FK` INT NOT NULL ,
  PRIMARY KEY (`Target_Current_Report_FK`, `Target_Current_Performance_FK`) ,
  INDEX `Target_Current_Performance_FK` (`Target_Current_Performance_FK` ASC) ,
  INDEX `Target_Current_Report_FK` (`Target_Current_Report_FK` ASC) ,
  CONSTRAINT `Target_Current_Component_Catalog_FK`
    FOREIGN KEY (`Target_Current_Performance_FK` )
    REFERENCES `Performance` (`Performance_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Target_Current_Report_FK`
    FOREIGN KEY (`Target_Current_Report_FK` )
    REFERENCES `Report` (`Report_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Attribute`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Attribute` (
  `Attribute_ID` INT NOT NULL AUTO_INCREMENT ,
  `Attribute_Name` VARCHAR(255) NULL ,
  `Attribute_Component_FK` INT NULL ,
  PRIMARY KEY (`Attribute_ID`) ,
  INDEX `Attribute_Component_FK` (`Attribute_Component_FK` ASC) ,
  CONSTRAINT `Attribute_Component_FK`
    FOREIGN KEY (`Attribute_Component_FK` )
    REFERENCES `Component` (`Component_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Scheme_Component_Attribute`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Scheme_Component_Attribute` (
  `Scheme_Component_Attribute_Component_FK` INT NOT NULL ,
  `Scheme_Component_Attribute_Attribute_FK` INT NOT NULL ,
  `Scheme_Component_Attribute_Value` TEXT NULL ,
  PRIMARY KEY (`Scheme_Component_Attribute_Component_FK`, `Scheme_Component_Attribute_Attribute_FK`) ,
  INDEX `Scheme_Component_Attribute_Component_FK` (`Scheme_Component_Attribute_Component_FK` ASC) ,
  INDEX `Scheme_Component_Attribute_Attribute_FK` (`Scheme_Component_Attribute_Attribute_FK` ASC) ,
  CONSTRAINT `Scheme_Component_Attribute_Component_FK`
    FOREIGN KEY (`Scheme_Component_Attribute_Component_FK` )
    REFERENCES `Scheme_Component` (`Scheme_Component_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Scheme_Component_Attribute_Attribute_FK`
    FOREIGN KEY (`Scheme_Component_Attribute_Attribute_FK` )
    REFERENCES `Attribute` (`Attribute_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Summary`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Summary` (
  `Summary_ID` INT NOT NULL AUTO_INCREMENT ,
  `Summary_Name` VARCHAR(45) NULL ,
  `Summary_Text` TEXT NULL ,
  PRIMARY KEY (`Summary_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Note`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Note` (
  `Note_ID` INT NOT NULL AUTO_INCREMENT ,
  `Note_Name` VARCHAR(45) NULL ,
  `Note_Text` TEXT NULL ,
  PRIMARY KEY (`Note_ID`) )
ENGINE = InnoDB;

