JUnit tests run
--------

To run all stories and generate result reports:

    mvn verify serenity:aggregate
    
To run specified tags and generate results report:

    mvn verify -Dtags="area:api" serenity:aggregate
