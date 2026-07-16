LaJolla
=======

LaJolla is a command-line tool for **protein and RNA structural alignment and
substructure search**. It uses an efficient n-gram based technique to generate
alignments, and can screen a query structure against a directory of targets
(all-against-all search).

- Project page: https://raphaelbauer.github.io/lajolla/
- Introduction and background: see the project page above.

Requirements
------------

- Java 25 (or newer)
- Maven 3.9+ (only needed to build from source)

Building
--------

Build a self-contained runnable jar (`target/lajolla-<version>-jar-with-dependencies.jar`):

```
mvn clean package
```
 
Running
-------

The runnable jar bundles all dependencies. The main entry points live in the
`com.raphaelbauer.lajolla.cli` package:

| Command | Purpose |
|---------|---------|
| `com.raphaelbauer.lajolla.cli.Main`     | Prints usage / overview |
| `com.raphaelbauer.lajolla.cli.PRO`      | Protein alignment and substructure search |
| `com.raphaelbauer.lajolla.cli.PROBind`  | Protein binding-site variant |
| `com.raphaelbauer.lajolla.cli.RNA`      | RNA alignment via eta/theta dihedral angles |
| `com.raphaelbauer.lajolla.cli.RNASuite` | RNA alignment via suite codes |

Show the options for a command with `-h`, e.g.:

```
java -cp target/lajolla-3.0.0-jar-with-dependencies.jar com.raphaelbauer.lajolla.cli.PRO -h
```

Example — align all protein structures in a directory against each other and
write the aligned results to an output directory:

```
java -cp target/lajolla-3.0.0-jar-with-dependencies.jar \
     com.raphaelbauer.lajolla.cli.PRO -t path/to/pdb_dir -o results_dir
```

Common options (see `-h` for the full list):

- `-t` target pdb file or directory (required)
- `-q` query pdb file or directory (defaults to `-t`, i.e. all-against-all)
- `-o` output directory for the aligned result structures
- `-sm` score based on the smaller structure (symmetric score)
- `-nr` number of results to write out per target found
- `-ref` minimum refinement score for a result to be kept
- `-zn` n-gram window size (advanced)

Output
------

For each query chain, LaJolla creates a subdirectory containing the query
structure and the superimposed target hits. Result filenames encode the scores,
e.g. `t-<target>-...-RS-<rmsd>-TM-<tmscore>-L1-<len1>-L2-<len2>-LAL-<aligned>-<n>.pdb`.

Development
-----------

- Source layout: standard Maven (`src/main/java`, `src/test/java`).
- Core packages under `com.raphaelbauer.lajolla`: `transformation` (structure →
  n-gram string translators for protein/RNA), `ngramto3dtranslators`
  (superposition), `scoringfunctions` (TM-score based scoring), `chaingroupfilter`,
  `comparators`, `container`, `utilities`.
- Structural I/O uses BioJava (`org.biojava.nbio.*`).
- Tests: JUnit 5. `mvn test` runs the whole suite — the fast unit tests plus
  the dataset-heavy structural-alignment regression tests.

License
-------

GPL v2. See `src/main/resources/gpl.txt`.
