
## Yup, I know.
#### Local-repo or any project output should be handle by `.gitignore`. However we do need it, 
- Because we are tinkering  with Gradle build tool and its Java plugins with its predefined (Jar) tasks extensions fields; `archiveAppendix` and `archiveClassifier`
- For some reason when we want to consume the `lib` we do need a compiled binary/byte-codes/jar out of it. The Gradle subprojects "inter-dependencies" will not work such as this;
```
//REM: build.gradle.kts
//REM: This will not work.
project.dependencies {
    this.implementation( project( ":lib" ) );
}
```

## The fix.
- We need this local repo
- And the so called fully qualified module, such as the ff;
```
project.repositories {
    this.maven {
        this.url = uri( ".../local-repo/" );
    }
    ...
}
project.dependencies {
    this.implementation( "group.id:artifact-id-appendix:version:classifier" );
}
```

## [ TODO-HERE, gradle-module metadata, md5 and sha(s) are expose... ]
- We could try `.gitignore` handled it, but I think it may cause a problem.