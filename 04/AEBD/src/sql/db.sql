CREATE TABLE tablespaces
    (   tablespace_name varchar2(50) not null,
        used_MB number not null,
        free_MB number not null,
        total_MB number not null,
        percentage_used number not null,
        timestamp timestamp not null,
        CONSTRAINT tablespaces_pk PRIMARY KEY (tablespace_name)
    );
CREATE TABLE tablespaceshistory
    (   tablespace_name varchar2(50) not null,
        used_MB number not null,
        free_MB number not null,
        total_MB number not null,
        percentage_used number not null,
        timestamp timestamp not null,
        CONSTRAINT tablespaceshistory_pk PRIMARY KEY (tablespace_name,timestamp)
    );

CREATE TABLE datafiles
    (   file_id number not null,
        datafile_name varchar2(512) not null,
        tablespace_name varchar2(50) not null,
        total_MB number not null,
        used_MB number not null,
        free_MB number not null,
        percentage_used number not null,
        timestamp timestamp not null,
        CONSTRAINT datafiles_pk PRIMARY KEY(file_id),
        CONSTRAINT fk_tablespaces FOREIGN KEY(tablespace_name)
            REFERENCES tablespaces(tablespace_name)
    );

CREATE TABLE datafileshistory
    (   file_id number not null,
        datafile_name varchar2(512) not null,
        tablespace_name varchar2(50) not null,
        total_MB number not null,
        free_MB number not null,
        used_MB number not null,
        percentage_used number not null,
        timestamp timestamp not null,
        CONSTRAINT datafileshistory_pk PRIMARY KEY(file_id,timestamp)
    );
    

CREATE TABLE users
    (   user_id number not null,
        name varchar2(50) not null,
        default_tablespace varchar2(50) not null,
        temporary_tablespace varchar2(50) not null,
        account_status varchar2(20) not null,
        quota number not null,
        privilege varchar2(50),
        cpu_usage number,
        timestamp timestamp not null,
        CONSTRAINT users_pk PRIMARY KEY(user_id),
        CONSTRAINT fk_tablespaces_users FOREIGN KEY(default_tablespace)
            REFERENCES tablespaces(tablespace_name)
    );
    
CREATE TABLE usershistory
    (   user_id number not null,
        name varchar2(50) not null,
        default_tablespace varchar2(50) not null,
        temporary_tablespace varchar2(50) not null,
        account_status varchar2(20) not null,
        quota number not null,
        privilege varchar2(50),
        cpu_usage number,
        timestamp timestamp not null,
        CONSTRAINT usershistory_pk PRIMARY KEY(user_id,timestamp)
    );
    
CREATE TABLE tables
    (   owner_name varchar2(30) not null,
        owner_id number not null,
        name varchar2(30) not null,
        correspondent_tablespace varchar2(50),
        nr_of_accesses number not null,
        nr_of_regists number not null,
        dropped varchar2(20) not null,
        timestamp timestamp not null,
        CONSTRAINT tables_pk PRIMARY KEY(owner_id,name),
        CONSTRAINT fk_users_tables FOREIGN KEY(owner_id)
            REFERENCES users(user_id)        
    );
    
CREATE TABLE tableshistory
    (   owner_name varchar2(30) not null,
        owner_id number not null,
        name varchar2(30) not null,
        correspondent_tablespace varchar2(50),
        nr_of_accesses number not null,
        nr_of_regists number not null,
        dropped varchar2(20) not null,
        timestamp timestamp not null,
        CONSTRAINT tableshistory_pk PRIMARY KEY(owner_id,name,timestamp)
    );
    
CREATE TABLE memory
    (   total_size_bytes number not null,
        free_size_bytes number not null,
        percentage_free number not null,
        timestamp timestamp not null,
        CONSTRAINT memory_pk PRIMARY KEY (timestamp)
    );

    
CREATE TABLE sessions
    (   session_id number not null,
        username varchar2(50) not null,
        user_id number not null,
        schema_name varchar2(50) not null,
        login_time varchar2(50) not null,
        timestamp timestamp not null,
        CONSTRAINT sessions_pk PRIMARY KEY (session_id,timestamp),
        CONSTRAINT fk_users FOREIGN KEY (user_id)
            REFERENCES users(user_id)
        
    );
    
CREATE TABLE sessionshistory
    (   session_id number not null,
        username varchar2(50) not null,
        user_id number not null,
        schema_name varchar2(50) not null,
        login_time varchar2(50) not null,
        timestamp timestamp not null,
        CONSTRAINT sessionshistory_pk PRIMARY KEY (session_id,timestamp)
    );
    
CREATE TABLE io_reads
    (
    metric_name varchar2(64) not null,
    begin_time timestamp not null,
    end_time timestamp not null,
    value number not null,
    timestamp timestamp not null,
    CONSTRAINT io_reads_pk PRIMARY KEY (timestamp)
    );

CREATE TABLE io_writes
    (
    metric_name varchar2(64) not null,
    begin_time timestamp not null,
    end_time timestamp not null,
    value number not null,
    timestamp timestamp not null,
    CONSTRAINT io_writes_pk PRIMARY KEY (timestamp)
    );
    

--------------------------------------------
     ------------ TRIGGERS ------------
--------------------------------------------

CREATE OR REPLACE TRIGGER insert_into_tablespaceshistory
AFTER UPDATE ON tablespaces FOR EACH ROW
BEGIN
    INSERT INTO tablespaceshistory
        (tablespace_name,used_MB,free_MB,total_MB,percentage_used,timestamp)
    VALUES
        (:OLD.tablespace_name,:OLD.used_MB,:OLD.free_MB,:OLD.total_MB,:OLD.percentage_used,:OLD.timestamp);   
END insert_into_tablespaceshistory;
/


-- DATAFILES --

CREATE OR REPLACE TRIGGER insert_into_datafileshistory
AFTER UPDATE
    ON datafiles
FOR EACH ROW
BEGIN
    INSERT INTO datafileshistory(file_id,datafile_name,tablespace_name,total_MB,free_MB,used_MB,percentage_used,timestamp)
    VALUES(:OLD.file_id,:OLD.datafile_name,:OLD.tablespace_name,:OLD.total_MB,:OLD.free_MB,:OLD.used_MB,:OLD.percentage_used,:OLD.timestamp);
END;
/
-- USERS --

CREATE OR REPLACE TRIGGER insert_into_usershistory
BEFORE UPDATE
    ON users
FOR EACH ROW
BEGIN
    INSERT INTO usershistory(user_id,name,default_tablespace,temporary_tablespace,account_status,quota,privilege,cpu_usage,timestamp)
    VALUES(:OLD.user_id,:OLD.name,:OLD.default_tablespace,:OLD.temporary_tablespace,:OLD.account_status,:OLD.quota,:OLD.privilege,:OLD.cpu_usage,:OLD.timestamp);
END;
/
-- TABLES --

CREATE OR REPLACE TRIGGER insert_into_tableshistory
AFTER UPDATE
    ON tables
FOR EACH ROW
BEGIN
    INSERT INTO tableshistory(owner_name,owner_id,name,correspondent_tablespace,nr_of_accesses,nr_of_regists,dropped,timestamp)
    VALUES(:OLD.owner_name,:OLD.owner_id,:OLD.name,:OLD.correspondent_tablespace,:OLD.nr_of_accesses,:OLD.nr_of_regists,:OLD.dropped,:OLD.timestamp);
END;
/

-- SESSIONS --

CREATE OR REPLACE TRIGGER insert_into_sessionshistory
AFTER UPDATE
    ON sessions
FOR EACH ROW
BEGIN
    INSERT INTO sessionshistory(session_id,username,user_id,schema_name,login_time,timestamp)
    VALUES(:OLD.session_id,:OLD.username,:OLD.user_id,:OLD.schema_name,:OLD.login_time,:OLD.timestamp);
END;
/



/*select * from tablespaces;
select * from tablespaceshistory;
select * from datafiles;
select * from datafileshistory;
select * from users;
select * from usershistory;
select * from sessions;
select * from sessionshistory;
select * from memory;
select * from io_reads;
select * from io_writes;
*/

        
/*
alter table users drop constraint fk_tablespaces_users;
drop table tablespaces;
drop table tablespaceshistory;
drop table datafiles;
drop table datafileshistory;
drop table tables;
drop table tableshistory;
drop table users;
drop table usershistory;
drop table memory;
drop table sessions;
drop table sessionshistory;
drop table io_reads;
drop table io_writes;
*/
