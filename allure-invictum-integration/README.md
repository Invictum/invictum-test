Allure integration setup
--------

 >**Warning**
 >Module is marked as deprecated and scheduled to remove. Use https://github.com/Invictum/serenity-allure-integration instead

 Use following steps to setup allure reporting:
  1) Add integration dependency to your POM file
```
<dependency>
    <groupId>com.github.invictum</groupId>
    <artifactId>allure-invictum-integration</artifactId>
    <version>${invictum.version}</version>
</dependency>
```
  2) Run you tests in regular way
```
mvn clean verify
```
Allure files will be collected as usual. Refer to http://allure.qatools.ru for more details about report generation and usage.
