# Sqloader

Sqloader was created out the need to add functional tests via junit
that use a database for mule 3 functional tests. The needs were few

* create one or more tables
* insert test data
* drop the tables created under test
* table are scoped to a schema

It works by loading a sql files from the classpath.

    // "this" is se.tv4.test.sqloader.CreateDropTest see the test.
    Sqloader loader = new Sqloader(this.getClass());
    loader.setJdbcDriver(JDBC_DRIVER);
    loader.setConnectionUrl(CONNECTION_URL);

    //runs se/tv4/test/sqloader/CreateDropTest_create.sql 
    loader.create();
    //runs se/tv4/test/sqloader/CreateDropTest_drop.sql 
    loader.drop();



## Why

Was completely frustrated trying to get mule 3.0 FunctionalTestCase, 
dbunit and hsqldb with schemas to work together. Most of the
problem stems from mule and dbunit stuck using junit 3.8. To use mule 
you need to extend ** its ** FunctionalTestCase. To use dbunit you need to 
use ** its ** DbUnitTest.  

Sure you can make a super class that does both
the mule configuration and the db configuration but you are left with 
another problem that's a little more subtle.  Information gets lost 
in the translation from DbUnit's xml syntax for sql to sql.  So why bother with
xml when i can SQL from the get go.

Overcoming those obstacles the last problem was getting hsqdb to work with
schemas. This turned out to be much harder than myself or team mate could find a
solution.



