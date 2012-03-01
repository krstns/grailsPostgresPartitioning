Description
===========

This is just a sample project, result of my investigation into how Grails 1.3.7 can work with Postgres Partitioning.
There are plenty of notes here and there on how to do so, but no examples.
Here's one. 

### Preparation

Before you go and run this app, make sure you have modified `DataSource.groovy` file and that you have run following on your database:

``` sql
create sequence hibernate_sequence;

create table some_table (id int8 not null, date_created date not null, some_date date, version int4, primary key (id));

create table some_table_1( check ( some_date >= DATE '2012-03-01')) inherits (some_table);

create table some_table_2( check ( some_date < DATE '2012-03-01')) inherits (some_table);

CREATE INDEX idx_some_table_2_hashtime ON some_table_2 (some_date);
CREATE INDEX idx_some_table_1_hashtime ON some_table_1 (some_date);

--create a trigger to redirect records to child table
CREATE OR REPLACE FUNCTION some_table_PT_func_insert_trigger()
RETURNS TRIGGER AS $$
BEGIN
    IF ( NEW.some_date >= DATE '2012-03-01') THEN
        INSERT INTO some_table_1 VALUES (NEW.*);
    ELSIF ( NEW.some_date < DATE '2012-03-01' ) THEN
        INSERT INTO some_table_2 VALUES (NEW.*);
    ELSE
        RAISE EXCEPTION 'Date out of range.  Fix the measurement_insert_trigger() function!';
    END IF;
    RETURN NULL;
END;
$$
LANGUAGE plpgsql;
 
CREATE TRIGGER trigger_some_table_insert
    BEFORE INSERT ON some_table
    FOR EACH ROW EXECUTE PROCEDURE some_table_PT_func_insert_trigger();
```

Now simply run `grails run-app` and it should create three entries. One in first partition and two in second partition.

### Files to look at

Here's the list of files that you need to take a look at:

* BootStrap.groovy <- how entries are created
* src/java/pl/cluain/test/grailsPartition/TestDomain.java <- Domain class
* hibernate.cfg.xml <- hibernate configuration
* BuildConfig.groovy <- added runtime dependency for postgres

That's it. Nothing else was changed.

### Check if it works

If you've done everything described here, you should be able to query `some_table` [and see 3 results], `some_table_1` and `some_table_2` and see how entries were divided between them.
