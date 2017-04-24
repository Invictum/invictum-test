Invictum Test
--------

Based on Serenity core project.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/753cdb0b87e34947b7bb6295ad861e21)](https://www.codacy.com/app/zim182/unified-test-core?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Invictum/unified-test-core&amp;utm_campaign=Badge_Grade)

### Quick start
The esiest way to start with Invictum test framework is to use maven archetype. Just invoke command in terminal:
```
mvn archetype:generate -DarchetypeCatalog=https://oss.sonatype.org/content/groups/public/ -DarchetypeGroupId=com.github.invictum -DarchetypeArtifactId=invictum-junit-archetype -DarchetypeVersion=1.1
```
Project structure will be generated and ready for tests implementation.
For now only jUnit style archetype is prepared, but it is also possible just to add maven dependency manualy.

Refer to [Wiki section](https://github.com/Invictum/invictum-test/wiki) for more details.
