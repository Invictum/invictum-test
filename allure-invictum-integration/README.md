Allure integration setup
--------

 Use following steps to setup allure reporting:
  1) Add integration dependency to your POM file
```
<dependency>
    <groupId>com.github.invictum</groupId>
    <artifactId>allure-invictum-integration</artifactId>
    <version>${invictum.version}</version>
</dependency>
```
  2) Tweak maven plugin configuration
```
<plugin>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>2.19.1</version>
    <configuration>
        <testFailureIgnore>true</testFailureIgnore>
        <argLine>
            -javaagent:${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar
        </argLine>
        <properties>
            <property>
                <name>listener</name>
                <value>com.github.invictum.AllureRunListener</value>
            </property>
        </properties>
        ...
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
    </dependencies>
    <executions>
    ...
    </executions>
</plugin>
```
  3) Run you tests in regular way
```
mvn clean verify
```
Allure files will be collected as usual. Refer to http://allure.qatools.ru for more details about report generation and usage.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/753cdb0b87e34947b7bb6295ad861e21)](https://www.codacy.com/app/zim182/unified-test-core?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Invictum/unified-test-core&amp;utm_campaign=Badge_Grade)
