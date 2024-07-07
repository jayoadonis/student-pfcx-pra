@file:Suppress("UnstableApiUsage")

import org.gradle.internal.impldep.org.jsoup.Connection.Base
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.jvm.optionals.getOrNull

settings.rootProject.name = "student-pfcx-pra"
//settings.include(":${settings.rootProject.name}-app");
//settings.include(":${settings.rootProject.name}-lib");
settings.include(":app");
settings.include(":lib");

pluginManagement {
    this.repositories {
        this.gradlePluginPortal();
        this.mavenCentral();
    }
}

settings.dependencyResolutionManagement {
    settings.simpleLog(
        LogType.MSG,
        "settings.dependencyResolutionManagement"
    )
    this.repositories {
        this.mavenCentral();
        this.gradlePluginPortal();
    }
    this.versionCatalogs {
        this.register("thisRootProject") {
            this.from( files( "./gradle/${this.name}.versions.toml" ) )
        }
        this.register( "testLib" ) {
            this.from( files( "./gradle/${this.name}.versions.toml" ) );
        }
    }
}

settings.gradle.projectsLoaded {
    settings.simpleLog(
        LogType.MSG,
        "settings.gradle.projectLoaded"
    );
}

//REM: This is a Fully bloom JAVA project
settings.gradle.projectsEvaluated {
    settings.simpleLog(
        LogType.MSG,
        "settings.gradle.projectsEvaluated"
    );
    this.allprojects {
        if (project.plugins.hasPlugin(BasePlugin::class.java)) {
            val SOURCE_SETS_EXTENSION = project.extensions.getByType<SourceSetContainer>();
            val VERSION_CATALOGS_EXTENSION = project.extensions.getByType<VersionCatalogsExtension>();
            val VCE_TEST_LIB = VERSION_CATALOGS_EXTENSION.named( "testLib" );

            this.dependencies {
                this.add( "testImplementation", VCE_TEST_LIB
                    .findLibrary( "junit-jupiter-api" ).get()
                );
                this.add( "testRuntimeOnly", VCE_TEST_LIB
                    .findLibrary( "junit-jupiter-engine" ).get()
                );
            };

            this.tasks.withType<Test>() {
                this.useJUnitPlatform();
            }

            this.tasks.withType<Jar>() {
                this.archiveBaseName = VERSION_CATALOGS_EXTENSION.named("thisRootProject")
                    .findLibrary("${project.rootProject.name}-${project.name}").get().get().name;
                this.archiveAppendix.set(project.name);
                this.archiveClassifier.set("alpha");

                this.manifest {
                    this.attributes["Author"] = this.attributes["Author"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty("project.author")
                            .orNull?.takeIf { it.isNotBlank() }
                                ?: "A. R. B. Jayo";
                    this.attributes["Implementation-Title"] = this.attributes["Implementation-Title"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty( "project.implementation.title")
                            .orNull?.takeIf{ it.isNotBlank() }
                                ?: archiveBaseName.get();
                    this.attributes["Implementation-Vendor"] = this.attributes["Implementation-Vendor"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty( "project.implementation.vendor")
                            .orNull?.takeIf{ it.isNotBlank() }
                                ?: project.providers.gradleProperty( "project.group" )
                                    .orNull?.takeIf{ it.isNotBlank() }
                                ?: "com.student.pfcx";
                    this.attributes["Classifier"] = archiveClassifier.get().takeIf { it.isNotBlank() }
                        ?: "<undefined>"
                    this.attributes["Appendix"] = archiveAppendix.get().takeIf { it.isNotBlank() }
                        ?: "<undefined>"
                }
            }

            this.tasks.register<Jar>("fatJarI") {
                this.group = "build"

                this.duplicatesStrategy = DuplicatesStrategy.EXCLUDE;

                val BUILT_IN_JAR: Jar = tasks.named<Jar>("jar").get();
                this.dependsOn( BUILT_IN_JAR );
//                this.from( BUILT_IN_JAR.source );

                this.archiveClassifier.set("fat")

                this.from(SOURCE_SETS_EXTENSION.get("main").output);
                this.dependsOn(configurations.get("runtimeClasspath"));
                this.from(
                    configurations.get("runtimeClasspath").filter {
                        it.name.endsWith("jar")
                    }.map { j -> zipTree(j) }
                );

                this.manifest {
                    this.attributes["Author"] = this.attributes["Author"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty("project.author")
                            .orNull?.takeIf { it.isNotBlank() }
                                ?: "A. R. B. Jayo";
                    this.attributes["Classifier"] = archiveClassifier.get().takeIf { it.isNotBlank() }
                        ?: "<undefined>"
//                    this.attributes["Appendix"] = archiveAppendix.get().takeIf { it.isNotBlank() }
//                        ?: "<undefined>"
                }


            }

            if (project.plugins.hasPlugin(PublishingPlugin::class.java)) {
                val PUBLISHING_EXTENSION = project.extensions.getByType<PublishingExtension>();
                PUBLISHING_EXTENSION.publications {
                    if (project.plugins.hasPlugin(MavenPublishPlugin::class.java)) {
                        this.register<MavenPublication>("maven-pub-I") {
                            this.from(components["java"]);
                            this.pom {
                                this.developers {
                                    this.developer {
                                        this.name.set("MYLA")
                                    }
                                    this.developer {
                                        this.name.set("DIMZON")
                                    }
                                    this.developer {
                                        this.name.set("CAHILES")
                                    }
                                    this.developer {
                                        this.name.set("ELARCOSA")
                                    }
                                    this.developer {
                                        this.id.set("A. R. B. JAYO")
                                        this.name.set("A. R. B. JAYO")
                                        this.email.set("jayo.adonisraphael@gmail.com")
                                    }
                                }
                            }
                        }

                        PUBLISHING_EXTENSION.repositories {
                            this.maven {
                                this.name = "maven-repo-local-I";
                                this.url = uri("${project.rootProject.layout.projectDirectory}/${this.name}");
                            }
                        }
                    }

                    if (project.plugins.hasPlugin(IvyPublishPlugin::class.java)) {
                        this.register<IvyPublication>("ivyPubI") {
                            this.from(components["java"]);
                            this.descriptor {
                                this.author {
                                    this.name.set("MYLA")
                                }
                                this.author {
                                    this.name.set("DIMZON")
                                }
                                this.author {
                                    this.name.set("CAHILES")
                                }
                                this.author {
                                    this.name.set("ELARCOSA")
                                }
                                this.author {
                                    this.name.set("A. R. B. JAYO")
                                }
                            }
                        };

                        PUBLISHING_EXTENSION.repositories {
                            this.ivy {
                                this.name = "ivy-repo-local-I";
                                this.url = uri("${project.rootProject.layout.projectDirectory}/${this.name}")
                            }
                        }
                    }
                };
            }
        }

    }
}


fun Settings.simpleLog(logType: LogType, msg: String): Unit {
    println(
        String.format(
            "::: [%-${logType.getLenLongestCharSeq()}s]. [0x%X]" +
                    ". %-25s. %-45s. %-35s",
            logType.DESCRIPTION,
            this.rootProject.hashCode(),
            this.rootProject.name,
            msg,
            SimpleDateFormat( "yyyy-MM-dd h:mm:ss-a XXX" ).format( Date() )
        )
    )
    return;
}

enum class LogType(
    public val CODE: Byte,
    public val DESCRIPTION: String,
) {
    ERR(0x2, "ERROR"),
    WRN(0x4, "WARNING"),
    MSG(0x8, "MESSAGE");

    public fun getLenLongestCharSeq(): Int {
        return LogType.values().map { i -> i.DESCRIPTION.length }
            .max();
    }
}

settings.simpleLog(
    LogType.MSG,
    "settings.gradle.kts"
)