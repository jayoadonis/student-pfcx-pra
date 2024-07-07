plugins {
    `java-library`
    `maven-publish`
    `ivy-publish`
    signing
}

project.version = thisRootProject.student.pfcx.pra.lib.get().version!!;
project.group = thisRootProject.student.pfcx.pra.lib.get().group!!;

val SEPARATOR_PKG_CHAR: String = "_";
val REGEX_INNER_NAME_SYMBOL_TO_BE_REMOVE: Regex = Regex( "[/\\\\ -]+" );
val REGEX_OUTER_NAME_SYMBOL_TO_BE_REMOVE: Regex = Regex( "^[${SEPARATOR_PKG_CHAR}]+|[${SEPARATOR_PKG_CHAR}]+$" );

val PROJECT_GROUP_NAME: String = project.group.toString()
    .replace( REGEX_INNER_NAME_SYMBOL_TO_BE_REMOVE, SEPARATOR_PKG_CHAR )
    .replace( REGEX_OUTER_NAME_SYMBOL_TO_BE_REMOVE, "" );

val PROJECT_ROOT_NAME: String = project.rootProject.name
    .replace( REGEX_INNER_NAME_SYMBOL_TO_BE_REMOVE, SEPARATOR_PKG_CHAR )
    .replace( REGEX_OUTER_NAME_SYMBOL_TO_BE_REMOVE, "" );

val PROJECT_NAME: String = project.name
    .replace( REGEX_INNER_NAME_SYMBOL_TO_BE_REMOVE, SEPARATOR_PKG_CHAR )
    .replace( REGEX_OUTER_NAME_SYMBOL_TO_BE_REMOVE, "" );

project.java {
    this.modularity.inferModulePath.set( true );
    this.sourceCompatibility = JavaVersion.VERSION_11;
    this.targetCompatibility = JavaVersion.VERSION_11;
}

//project.publishing {
//    this.publications {
//        this.register<MavenPublication>( "mavenPubI" ) {
//            this.from( components["java"] );
//            this.pom {
//                this.developers {
//                    this.developer {
//                        this.name.set("MYLA")
//                    }
//                    this.developer {
//                        this.name.set("DIMZON")
//                    }
//                    this.developer {
//                        this.name.set("CAHILES")
//                    }
//                    this.developer {
//                        this.name.set("ELARCOSA")
//                    }
//                    this.developer {
//                        this.id.set("A. R. B. JAYO")
//                        this.name.set("A. R. B. JAYO")
//                        this.email.set("jayo.adonisraphael@gmail.com")
//                    }
//                }
//            }
//        };
//        this.register<IvyPublication>( "ivyPubI" ) {
//            this.from( components["java"] );
//            this.descriptor {
//                this.author {
//                    this.name.set("MYLA")
//                }
//                this.author {
//                    this.name.set("DIMZON")
//                }
//                this.author {
//                    this.name.set("CAHILES")
//                }
//                this.author {
//                    this.name.set("ELARCOSA")
//                }
//                this.author {
//                    this.name.set("A. R. B. JAYO")
//                }
//            }
//        };
//    };
//    this.repositories {
//        this.maven {
//            this.name = "maven-repo-local-I";
//            this.url = uri( "${project.rootProject.layout.projectDirectory}/${this.name}");
//        }
//        this.ivy {
//            this.name = "ivy-repo-local-I";
//            this.url = uri( "${project.rootProject.layout.projectDirectory}/${this.name}")
//        }
//    }
//};