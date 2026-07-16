# LaJolla — project notes for Claude

LaJolla is a command-line bioinformatics tool for protein and RNA structural
alignment and substructure search, using an n-gram based technique.

## Build & test

- Java 25, Maven. `mvn clean package` builds a runnable
  `target/lajolla-<version>-jar-with-dependencies.jar` (main class
  `com.raphaelbauer.lajolla.cli.Main`).
- `mvn test` runs **all tests** — the fast unit tests plus the
  **structural-alignment regression tests**, which read bundled PDB fixtures
  under `src/test/resources` and are the behavior oracle for the alignment
  algorithm.
- After changing anything in the alignment path, run `mvn test` and confirm all
  tests still pass; identical-structure alignments must yield RMSD 0.0 / TM 1.0.

## Layout

- Standard Maven (`src/main/java`, `src/test/java`).
- CLI entry points: `com.raphaelbauer.lajolla.cli.{Main,PRO,PROBind,RNA,RNASuite}`.
  Each `main` is the composition root: it parses args (Commons CLI
  `DefaultParser`) and wires the translators/scoring/superposition via
  constructor injection.
- Core packages under `com.raphaelbauer.lajolla`:
  - `transformation` — turns a structure into an n-gram string
    (`transformation.protein` phi/psi; `transformation.rna.etatheta` eta/theta;
    `transformation.rna.suite` suite codes).
  - `ngramto3dtranslators` — superimposes matched n-grams (SVD) and produces
    candidate alignments.
  - `scoringfunctions` — TM-score based scoring of a superposition.
  - `chaingroupfilter`, `comparators`, `container`, `utilities`.
- `SequenceDB` is the in-memory n-gram index. `utilities.SwissKnife` is the only
  PDB reader. `utilities.Utility` holds the phi/psi/eta/theta geometry helpers.

## BioJava (7.x) conventions & gotchas

- Structure API is `org.biojava.nbio.structure.*`.
- `Group.getAtom(String)` returns `null` (does not throw) when the atom is
  absent — `Utility.requireAtom` restores the throw-on-missing contract the
  angle helpers rely on.
- `SVDSuperimposer` was removed; use
  `org.biojava.nbio.structure.geometry.SuperPositionSVD`. When converting its
  `Matrix4d` back to a jama `Matrix` for `Calc.rotate`, **transpose** the
  rotation (Calc.rotate uses a row-vector convention). See
  `NGramToStringTranslatorBasedOnSingleMatchingNGramsManyResults.toRotationMatrix`.
- `Structure.setPDBCode` validates its argument as a real PDB id in 7.x.
- BioJava may parse an extracted single-chain `.ent` fixture into more than one
  chain with the same author name, which changes exact result counts.
- BioJava ships a Log4j2 logging backend; the pom excludes it and routes
  everything through SLF4J -> Logback (`src/main/resources/logback.xml`, quiet
  by default).

## Conventions

- Keep intentional CLI output on `System.out`; use SLF4J for library logging.
- Prefer reusing the existing interfaces (`IFileToStringTranslator`,
  `IResidueToStringTransformer`, `INGramTo3DTranslator`, `IScoringFunction`,
  `IChainGroupFilter`) and constructor injection over adding frameworks.
