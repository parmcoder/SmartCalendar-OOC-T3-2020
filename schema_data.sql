create database oocdb;

create table APP_ROLE
(
  ROLE_ID   bigint      not null
  primary key,
  ROLE_NAME varchar(30) not null,
constraint APP_ROLE_UK
        unique(ROLE_NAME)
);

create table APP_USER
(
  USER_ID            bigint       not null
  primary key,
  USER_NAME          varchar(36)  not null,
  ENCRYPTED_PASSWORD varchar(128) not null,
  ENABLED            bit          not null,
  USER_REAL_NAME     varchar(100) null,
  USER_REAL_SURNAME  varchar(100) null,
constraint APP_USER_UK
        unique(USER_NAME)
);

create table APP_TASKS
(
  TID         bigint auto_increment
  primary key,
  TITLE       varchar(100) null,
  DESCRIPTION varchar(300) not null,
  START_DATE  timestamp    not null,
  END_DATE    timestamp    null,
  UID         bigint       null,
constraint UID__fk
        foreign key(UID)references APP_USER(USER_ID)
  on
update cascade on
delete cascade
)
;

create table Persistent_Logins
(
  username  varchar(64) not null,
  series    varchar(64) not null
  primary key,
  token     varchar(64) not null,
  last_used timestamp   not null
);

create table USER_ROLE
(
  ID      bigint not null
  primary key,
  USER_ID bigint not null,
  ROLE_ID bigint not null,
constraint USER_ROLE_UK
        unique(USER_ID, ROLE_ID), constraint USER_ROLE_FK1
foreign key(USER_ID)references APP_USER(USER_ID),
constraint USER_ROLE_FK2
foreign key(ROLE_ID)references APP_ROLE(ROLE_ID)
);

insert into APP_ROLE(ROLE_ID, ROLE_NAME)
values (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

insert into USER_ROLE(ID, USER_ID, ROLE_ID)
values (1, 1, 1);
insert into USER_ROLE(ID, USER_ID, ROLE_ID)
values (1, 1, 2);

insert into APP_USER(USER_ID, USER_NAME, ENCRYPTED_PASSWORD, ENABLED,
USER_REAL_NAME, USER_REAL_SURNAME)
values (1, 'admindb', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1, 'boi', 'boi');
