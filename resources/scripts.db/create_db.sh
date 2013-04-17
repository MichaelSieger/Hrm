#!/bin/bash
# connect to Database-Server
# mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g
# if exists - drop hrm-database
#mysql DROP DATABASE if exists hrm;
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "DROP DATABASE IF EXISTS hrm;"
# create hrm database
#mysql CREATE DATABASE hrm;
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "CREATE DATABASE hrm;"
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "`cat create_tables.sql | grep -v '^--'`" hrm
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "`cat test_data.sql | grep -v '^--'`" hrm 
# jump in hrm database
#mysql USE hrm;
# create tables
#mysql \. create_tables.sql;
# fill database with dummies
#mysql \. test_data.sql;

