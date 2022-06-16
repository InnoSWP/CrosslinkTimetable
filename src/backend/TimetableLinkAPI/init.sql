USE timetable;

CREATE TABLE mailingList(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    textIdentifier VARCHAR(40) UNIQUE
);

CREATE TABLE email(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emailAddress VARCHAR(60) UNIQUE
);

CREATE TABLE emailBelonging(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emailId BIGINT,
    mailingListId BIGINT,
    FOREIGN KEY (emailId) REFERENCES email (id) ON DELETE CASCADE,
    FOREIGN KEY (mailingListId) references mailingList (id) ON DELETE CASCADE
);