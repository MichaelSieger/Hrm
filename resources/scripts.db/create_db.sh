#!/bin/bash
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "DROP DATABASE IF EXISTS hrm;"
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "CREATE DATABASE hrm;"
mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "`cat create_tables.sql | grep -v '^--'`" hrm
#mysql --host=10.154.4.20 --user=methusalem --password=01dxA5c28g --execute "`cat test_data.sql | grep -v '^--'`" hrm 


