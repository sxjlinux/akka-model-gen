# akka-model-gen
A tool for Generating AKKA entities for CQRS.

To view detailed issues & commits, see
[Project Site](https://github.com/apuex/akka-model-gen)


## Code Generators implemented with Scala 

### Generating project configurations

```
sbt assembly
java -jar target/scala-2.12/akka-model-gen.jar generate-project your-model.xml
```