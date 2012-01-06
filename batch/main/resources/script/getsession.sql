set trimspool on
set linesize 180
set pagesize 200
set newpage 1
set heading off
set term off

spool ./killsession.sql;
select   'alter   system  kill   session '''||sid|| ', '||serial# || ''';' from   v$session where   username = 'CBS';
select 'exit;' from dual;
spool off;
exit;
