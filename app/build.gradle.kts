plugins {
    java
    application
    `maven-publish`
    `ivy-publish`
    signing
}

project.version = thisRootProject.student.pfcx.pra.app.get().version!!
project.group = thisRootProject.student.pfcx.pra.app.get().group!!

project.java {
    this.modularity.inferModulePath.set( true );
    this.sourceCompatibility = JavaVersion.VERSION_11;
    this.targetCompatibility = JavaVersion.VERSION_11;
}

project.application {
    this.mainClass.set(
        "com.student.pfcx.pra.app.MainExe"
    );
    this.mainModule.set(
        "student.pfcx.pra.app"
    )
}

project.dependencies {
    this.implementation( "com.student.pfcx:student-pfcx-pra-lib:0.0.1:alpha" );
}