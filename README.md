# x1-organization-api

This project provides support to model an Organizational structure. It provides
 - Organization
 - Organization units
 - Currency and timezone
 - Business lines, departments and functions
 - Address, contact persons, email and phone numbers

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
