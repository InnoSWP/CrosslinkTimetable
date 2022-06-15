use timetable;

create table mailingList(
    id bigint primary key auto_increment,
    textIdentifier varchar(40) unique
);

create table email(
    id bigint primary key auto_increment,
    emailAddress varchar(60) unique
);

create table emailBelonging(
    id bigint primary key auto_increment,
    emailId bigint,
    mailingListId bigint,
    foreign key (emailId) references email (id) on delete cascade,
    foreign key (mailingListId) references mailingList (id) on delete cascade
);