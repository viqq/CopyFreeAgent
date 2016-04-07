#!/usr/bin/env bash

cd liquibase/
mvn liquibase:dropAll -P dev
mvn liquibase:update -P dev


