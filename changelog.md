# Changelog

## Version 3.0.0

- Upgraded to Java 25 (from Java 1.7).
- Migrated BioJava 3.0.7 to BioJava 7.x (`org.biojava.bio.structure` ->
  `org.biojava.nbio.structure`), removing the dead HTTP BioJava Maven
  repository; all dependencies now resolve from Maven Central.
- Fixed a superposition regression surfaced during the BioJava migration: the
  SVD rotation matrix must be transposed for BioJava's `Calc.rotate` convention.
- Moved the CLI entry points (`Main`, `PRO`, `PROBind`, `RNA`, `RNASuite`) into
  the `com.raphaelbauer.lajolla.cli` package.
- Replaced the deprecated Commons CLI `BasicParser` with `DefaultParser`.
- Removed dead code and machine-specific one-off analysis/benchmark scripts.
- Removed unused Java serialization of the sequence index; removed mutable
  static state from the scoring function and `SequenceDB`.
- Migrated the test suite to JUnit 5. `mvn test` runs the whole suite — the
  fast unit tests plus the dataset-heavy structural-alignment regression tests.
- Routed BioJava logging through SLF4J/Logback and quieted the default log level.
- Added a GitHub Actions CI build.

## Version 2.2.1

 - Fixed spelling mistake in help files.

## Version 2.2

- Bump to latest libraries (biojava 3x), Java 1.7...
- Some refactoring (removing unused stuff and so on...)
- Added proper logging support via slf4j