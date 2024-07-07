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