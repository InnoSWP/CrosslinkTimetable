CREATE DATABASE IF NOT EXISTS timetable;
USE timetable;

CREATE TABLE IF NOT EXISTS mailingList (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    textIdentifier VARCHAR(40) UNIQUE
);

CREATE TABLE IF NOT EXISTS email(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emailAddress VARCHAR(60) UNIQUE
);

CREATE TABLE IF NOT EXISTS emailBelonging(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emailId BIGINT,
    mailingListId BIGINT,
    FOREIGN KEY (emailId) REFERENCES email (id) ON DELETE CASCADE,
    FOREIGN KEY (mailingListId) references mailingList (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event(
    outlookAppointmentId varchar(400) PRIMARY KEY
);