# my-money

1. From project home run following command to build jar file:
```sh
mvn clean install -DskipTests -q assembly:single
```


2. Locate the geektrust.jar file in target folder and execute
```sh
java -jar <path_to>/geektrust.jar <absolute_path_to_input_file>
```
