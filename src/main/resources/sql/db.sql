-- database creation script
CREATE DATABASE company
  WITH OWNER = company
       ENCODING = 'UTF8'
       CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE company TO company;
