package se.tv4.test.sqloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public class Sqloader {
    
    private Class klazz;
    
    public Sqloader(Class klass){
	this.klazz = klass;
    }
   
    public String baseName(){
	return klazz.getPackage().getName().replace('.', File.separatorChar) + File.separatorChar + klazz.getSimpleName(); 
    }
    
    public String findPathToCreateSql() {
	return baseName() + "_create.sql";
    }

    public String findPathToDropSql() {
	return baseName() + "_drop.sql";    
    }
    
    BufferedReader findFileInClasspath(String fileName) {
	return new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
    }
}
