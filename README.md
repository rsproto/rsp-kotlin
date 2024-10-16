![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.timemates.org%2Freleases%2Forg%2Ftimemates%2Frrpc-kotlin%2Fclient-core%2Fmaven-metadata.xml)
![GitHub issues](https://img.shields.io/github/issues/timemates/rrpc-kotlin)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/timemates/rrpc-kotlin)
![GitHub License](https://img.shields.io/github/license/timemates/rrpc-kotlin)
# rRPC Kotlin

rRPC is a framework designed to provide an ability to expose your API as RPC Services.
It facilitates the creation of gRPC-like services from .proto files through code generation.
The framework also provides essential core components for both server and client.

> **Warning** <br>
Under prototyping, currently unavailable to use.

## Features
- **Gradle Plugin**: `.proto` to RSocket code generator (both client and server).
- **Server Core** (JVM only): Interceptors, Instances API and bridge between Ktor and library.
- **Client Core** (JVM, Web, iOS): Metadata and basic interface-markers.

## Documentation

You can learn more about the library in official documentation – [https://rrpc.timemates.org](https://rrpc.timemates.org/section-starting-page.html).

## Feedback

For bugs, questions and discussions please use
the [GitHub Issues](https://github.com/timemates/rrpcroto/issues).

## License

This library is licensed under [MIT License](LICENSE). Feel free to use, modify, and distribute it for any purpose.
