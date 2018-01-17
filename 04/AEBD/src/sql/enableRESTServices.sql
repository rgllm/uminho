DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_SCHEMA(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_url_mapping_type => 'BASE_PATH',
                       p_url_mapping_pattern => 'mic',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'DATAFILES',
                       p_object_type => 'TABLE',
                       p_object_alias => 'datafiles',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'DATAFILESHISTORY',
                       p_object_type => 'TABLE',
                       p_object_alias => 'datafileshistory',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'IO_READS',
                       p_object_type => 'TABLE',
                       p_object_alias => 'io_reads',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'IO_WRITES',
                       p_object_type => 'TABLE',
                       p_object_alias => 'io_writes',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'MEMORY',
                       p_object_type => 'TABLE',
                       p_object_alias => 'memory',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'SESSIONS',
                       p_object_type => 'TABLE',
                       p_object_alias => 'sessions',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'SESSIONSHISTORY',
                       p_object_type => 'TABLE',
                       p_object_alias => 'sessionshistory',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'TABLES',
                       p_object_type => 'TABLE',
                       p_object_alias => 'tables',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'TABLESHISTORY',
                       p_object_type => 'TABLE',
                       p_object_alias => 'tableshistory',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'TABLESPACES',
                       p_object_type => 'TABLE',
                       p_object_alias => 'tablespaces',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'TABLESPACESHISTORY',
                       p_object_type => 'TABLE',
                       p_object_alias => 'tablespaceshistory',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'USERS',
                       p_object_type => 'TABLE',
                       p_object_alias => 'users',
                       p_auto_rest_auth => FALSE);

    commit;

END;

DECLARE
  PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN

    ORDS.ENABLE_OBJECT(p_enabled => TRUE,
                       p_schema => 'MIC',
                       p_object => 'USERSHISTORY',
                       p_object_type => 'TABLE',
                       p_object_alias => 'usershistory',
                       p_auto_rest_auth => FALSE);

    commit;

END;

