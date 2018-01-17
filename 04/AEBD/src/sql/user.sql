      CREATE TABLESPACE assignment_tables
    DATAFILE '\u01\app\oracle\oradata\orcl12\orcl\assignment_tables_1.dbf'
        SIZE 200M;

CREATE TEMPORARY TABLESPACE assignment_temp
    TEMPFILE '\u01\app\oracle\oradata\orcl12\orcl\assignement_temp_1.dbf'
        SIZE 50M
        AUTOEXTEND ON;
        
CREATE USER mic
    IDENTIFIED BY oracle
    DEFAULT TABLESPACE assignment_tables
    TEMPORARY TABLESPACE assignment_temp;

grant create session to mic;

GRANT CREATE SESSION TO mic;
GRANT CREATE TABLE TO mic;
GRANT CREATE VIEW TO mic;

GRANT CREATE ANY TRIGGER TO mic;
GRANT CREATE ANY PROCEDURE TO mic;
GRANT CREATE SEQUENCE TO mic;
GRANT CREATE SYNONYM TO mic;

ALTER USER mic QUOTA 200M ON assignment_tables;

