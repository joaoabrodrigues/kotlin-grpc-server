import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protoc
import com.google.protobuf.gradle.id

buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.14")
	}
}

plugins {
	// SPRING
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"

	// KOTLIN
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"

	// PROTOCOL BUFFERS
	id("com.google.protobuf") version "0.8.14"
}

group = "dev.joaorodrigues"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
	repositories {
		mavenCentral()
		jcenter()
		google()
	}
}

dependencies {
	// SPRING
	implementation("org.springframework.boot:spring-boot-starter")

	// KOTLIN
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

	// gRPC
	implementation("io.grpc:grpc-protobuf:1.35.0")
	implementation("io.grpc:grpc-stub:1.35.0")
//	implementation("io.grpc:grpc-netty:1.35.0")
	implementation("io.grpc:grpc-all:1.35.0")
	implementation("io.grpc:protoc-gen-grpc-kotlin:1.0.0")
	api("io.grpc:grpc-kotlin-stub:1.0.0")

	// PROTOCOL BUFFERS
	api("com.google.protobuf:protobuf-java-util:3.13.0")
	implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")

	// JAVA ANNOTATION
	compileOnly("javax.annotation:javax.annotation-api:1.3.2")

	// SPRING TEST
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets["main"].java {
	srcDir("build/generated/source/proto/main/java")
	srcDir("build/generated/source/proto/main/grpc")
	srcDir("build/generated/source/proto/main/grpckt")
}

protobuf {

	protoc{
		artifact = "com.google.protobuf:protoc:3.14.0"
	}

	plugins {
		id("grpc"){
			artifact = "io.grpc:protoc-gen-grpc-java:1.35.0"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0:jdk7@jar"
		}
	}

	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}
