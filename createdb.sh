#!/bin/sh
psql -U postgres -c "DROP DATABASE exemplodb"
psql -U postgres -c "CREATE DATABASE exemplodb"
psql -U postgres -c "DROP ROLE exemplouser";
psql -U postgres -c "CREATE ROLE exemplouser LOGIN PASSWORD '123456' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;"
psql -U postgres -d exemplodb -f schema.sql