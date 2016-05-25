jBehave tests run
--------

To run all stories and generate result reports:

    mvn integration-test serenity:aggregate

To run all stories excluding manual:

    mvn integration-test -Dmetafilter=-manual serenity:aggregate

To run smoke tests:

    mvn integration-test -Dmetafilter=+smoke serenity:aggregate

To run layout comparison tests:

    mvn integration-test -Dmetafilter=+layout serenity:aggregate

Note: To run layout comparison tests ImageMagick executable should be installed on target machine.

JUnit tests run
--------

To run all stories and generate result reports:

    mvn verify
    
To run specified test and generate results report:

    mvn verify -Dtest=MyTestName
