-- -----------------------------------------------------
-- Table `Contact`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Contact` (
  `Contact_ID` INT NOT NULL AUTO_INCREMENT ,
  `Contact_Name` VARCHAR(255) NOT NULL ,
  `Contact_First_Name` VARCHAR(255) NULL ,
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
  `Place_Location` VARCHAR(255) NOT NULL ,
  `Place_Area` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`Place_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Plant`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Plant` (
  `Plant_ID` INT NOT NULL AUTO_INCREMENT ,
  `Plant_Place_FK` INT NOT NULL ,
  `Plant_Inspection_Interval` INT(11) NOT NULL ,
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
  PRIMARY KEY (`Plant_ID`) ,
  INDEX `Plant_Place_FK` (`Plant_Place_FK` ASC) ,
  CONSTRAINT `Plant_Place_FK`
    FOREIGN KEY (`Plant_Place_FK` )
    REFERENCES `Place` (`Place_ID` )
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
  `Category_Default_Qualifier` VARCHAR(45) NULL ,
  `Category_Default_Bool_Rating` TINYINT(1) NULL ,
  `Category_Catalog_FK` INT NULL ,
  PRIMARY KEY (`Category_ID`) ,
  INDEX `Category_Catalog_FK` (`Category_Catalog_FK` ASC) ,
  CONSTRAINT `Category_Catalog_FK`
    FOREIGN KEY (`Category_Catalog_FK` )
    REFERENCES `Catalog` (`Catalog_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Component`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Component` (
  `Component_ID` INT NOT NULL AUTO_INCREMENT ,
  `Component_Name` VARCHAR(45) NULL ,
  `Component_Symbol_LR` VARCHAR(255) NULL ,
  `Component_Symbol_RL` VARCHAR(255) NULL ,
  `Component_Symbol_UD` VARCHAR(255) NULL ,
  `Component_Symbol_DU` VARCHAR(255) NULL ,
  `Component_Quantifier` INT NULL ,
  `Component_Category_FK` INT NULL ,
  `Component_Bool_Rating` TINYINT(1) NULL ,
  PRIMARY KEY (`Component_ID`) ,
  INDEX `Component_Category_FK` (`Component_Category_FK` ASC) ,
  CONSTRAINT `Component_Category_FK`
    FOREIGN KEY (`Component_Category_FK` )
    REFERENCES `Category` (`Category_ID` )
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
-- Table `State_Current`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `State_Current` (
  `State_Current_ID` INT NOT NULL AUTO_INCREMENT ,
  `State_Current_Name` VARCHAR(100) NULL ,
  `State_Current_Text` TEXT NOT NULL ,
  PRIMARY KEY (`State_Current_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `State_Target`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `State_Target` (
  `State_Target_ID` INT NOT NULL AUTO_INCREMENT ,
  `State_Target_Name` VARCHAR(100) NULL ,
  `State_Target_Text` TEXT NOT NULL ,
  PRIMARY KEY (`State_Target_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `State_Activity`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `State_Activity` (
  `State_Activity_ID` INT NOT NULL AUTO_INCREMENT ,
  `State_Activity_Name` VARCHAR(100) NULL ,
  `State_Activity_Text` TEXT NOT NULL ,
  PRIMARY KEY (`State_Activity_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog_Current`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog_Current` (
  `Category_Current_State_Current_FK` INT NOT NULL ,
  `Category_Current_Catalog_FK` INT NOT NULL ,
  INDEX `Category_Current_State_Current_IDx` (`Category_Current_State_Current_FK` ASC) ,
  INDEX `Category_Current_Catalog_IDx` (`Category_Current_Catalog_FK` ASC) ,
  PRIMARY KEY (`Category_Current_Catalog_FK`, `Category_Current_State_Current_FK`) ,
  CONSTRAINT `Category_Current_State_Current_FK`
    FOREIGN KEY (`Category_Current_State_Current_FK` )
    REFERENCES `State_Current` (`State_Current_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Category_Current_Catalog_FK`
    FOREIGN KEY (`Category_Current_Catalog_FK` )
    REFERENCES `Catalog` (`Catalog_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog_Target`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog_Target` (
  `Category_Target_State_Target_FK` INT NOT NULL ,
  `Category_Target_Catalog_FK` INT NOT NULL ,
  INDEX `Category_Target_State_Target_IDx` (`Category_Target_State_Target_FK` ASC) ,
  INDEX `Category_Target_Catalog_IDx` (`Category_Target_Catalog_FK` ASC) ,
  PRIMARY KEY (`Category_Target_State_Target_FK`, `Category_Target_Catalog_FK`) ,
  CONSTRAINT `Category_Target_State_Target_FK`
    FOREIGN KEY (`Category_Target_State_Target_FK` )
    REFERENCES `State_Target` (`State_Target_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Category_Target_Catalog_FK`
    FOREIGN KEY (`Category_Target_Catalog_FK` )
    REFERENCES `Catalog` (`Catalog_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Catalog_Activity`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Catalog_Activity` (
  `Category_Activity_State_Activity_FK` INT NOT NULL ,
  `Category_Activity_Catalog_FK` INT NOT NULL ,
  INDEX `Category_Activity_State_Activity_IDx` (`Category_Activity_State_Activity_FK` ASC) ,
  INDEX `Category_Activity_Catalog_IDx` (`Category_Activity_Catalog_FK` ASC) ,
  PRIMARY KEY (`Category_Activity_State_Activity_FK`, `Category_Activity_Catalog_FK`) ,
  CONSTRAINT `Category_Activity_State_Activity_FK`
    FOREIGN KEY (`Category_Activity_State_Activity_FK` )
    REFERENCES `State_Activity` (`State_Activity_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Category_Activity_Catalog_FK`
    FOREIGN KEY (`Category_Activity_Catalog_FK` )
    REFERENCES `Catalog` (`Catalog_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Plant_Contact`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Plant_Contact` (
  `Plant_Contact_Plant_FK` INT NOT NULL ,
  `Plant_Contact_Contact_FK` INT NOT NULL ,
  PRIMARY KEY (`Plant_Contact_Plant_FK`, `Plant_Contact_Contact_FK`) ,
  INDEX `Plant_Contact_Plant_IDx` (`Plant_Contact_Plant_FK` ASC) ,
  INDEX `Plant_Contact_Contact_IDx` (`Plant_Contact_Contact_FK` ASC) ,
  CONSTRAINT `Plant_Contact_Plant_FK`
    FOREIGN KEY (`Plant_Contact_Plant_FK` )
    REFERENCES `Plant` (`Plant_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Plant_Contact_Contact_FK`
    FOREIGN KEY (`Plant_Contact_Contact_FK` )
    REFERENCES `Contact` (`Contact_ID` )
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
  `Lock_Timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP  ,
  PRIMARY KEY (`Lock_Table`, `Lock_Row_ID`) ,
  INDEX `Lock_Uuid_IDx` (`Lock_Uuid_FK` ASC) ,
  CONSTRAINT `Lock_Uuid_FK`
    FOREIGN KEY (`Lock_Uuid_FK` )
    REFERENCES `Session` (`Session_Uuid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

