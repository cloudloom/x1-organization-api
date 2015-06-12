# x1-organization-api

This project provides support to model an Organizational structure. It provides
 - Organization
 - Organization units
 - Currency and timezone
 - Business lines, departments and functions
 - Address, contact persons, email and phone numbers
 

##Pre requisites##
 - JDK 1.8 or later. 
 - MySQL 5.1.x or later. 
 - The default credentials for MySQL is assumed as `root/marines` . Also create a database in MySQL called `x1-organization-api`. 

Download or clone from git and then use maven(3.*) Java(1.8 or better)

    $ git clone git@github.com:tracebucket/x1-organization-api.git
    $ mvn spring-boot:run 
    
Add as a dependency like

    <dependency>
            <groupId>com.tracebucket.x1.organization</groupId>
            <artifactId>x1-organization-api</artifactId>
            <version>0.3</version>
    </dependency>
        
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>bintray-tracebucket-X1</id>
            <name>bintray</name>
            <url>http://dl.bintray.com/tracebucket/X1</url>
        </repository>
        
 add @EnableOrganization(multiple X1 components can be enabled in the same config file) in a config class like below
 
     @Configuration
     @EnableOrganization
     public class X1Configuration{
     }
     
 
and the necessary beans for Organization will be enabled. 

 - Jpa configuration
 - Service configuration
 - Controller and Assembler configuration
