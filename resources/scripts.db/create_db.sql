-- -----------------------------------------------------
-- Table `hrm`.`Contact`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hrm`.`Contact` (
  `Contact_ID` INT NOT NULL ,
  `Contact_Role_FK` INT NOT NULL ,
  `Contact_Name` VARCHAR(255) NOT NULL ,
  `Contact_First_Name` VARCHAR(255) NOT NULL ,
  `Contact_Zip_Code` VARCHAR(45) NOT NULL ,
  `Contact_City` VARCHAR(45) NOT NULL ,
  `Contact_Street` VARCHAR(255) NOT NULL ,
  `Contact_Street_Number` VARCHAR(5) NOT NULL ,
  `Contact_Shortcut` VARCHAR(45) NULL ,
  `Contact_Phone` VARCHAR(45) NULL ,
  `Contact_Fax` VARCHAR(45) NULL ,
  `Contact_Mobil` VARCHAR(45) NULL ,
  `Contact_Email` VARCHAR(45) NULL ,
  `Contact_Lock` VARCHAR(36) NULL ,
  PRIMARY KEY (`Contact_ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrm`.`Place`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hrm`.`Place` (
  `Place_ID` INT NOT NULL ,
  `Place_Name` VARCHAR(255) NOT NULL ,
  `Place_Zip_Code` VARCHAR(45) NOT NULL ,
  `Place_City` VARCHAR(45) NOT NULL ,
  `Place_Street` VARCHAR(255) NOT NULL ,
  `Place_Street_Number` VARCHAR(5) NOT NULL ,
  `Place_Location` VARCHAR(255) NULL ,
  `Place_Area` VARCHAR(255) NULL ,
  `Place_Lock` VARCHAR(36) NULL ,
  PRIMARY KEY (`Place_ID`) ,
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrm`.`Session`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hrm`.`Session` (
  `Session_Uuid` VARCHAR(36) NOT NULL ,
  `Session_User` VARCHAR(30) NOT NULL ,
  `Session_Timestamp` DATETIME NOT NULL ,
  PRIMARY KEY (`Session_Uuid`) ,
  UNIQUE INDEX `Session_Uuid_UNIQUE` (`Session_Uuid` ASC) )
ENGINE = InnoDB;

