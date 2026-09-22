# Agent Instructions

## Toolchain

- Use JDK 17 or newer.
- Use the repository-pinned sbt version from `project/build.properties`.
- Plugin sources and sbt build definitions use Scala 3 syntax.

## Commands

| Task | Command |
|---|---|
| Compile | `sbt -batch compile` |
| All integration tests | `sbt -batch scripted` |
| Generator integration test | `sbt -batch "scripted sbt-openapi4s/generate"` |
| Publish locally | `sbt -batch publishLocal` |

## Key Conventions

- Keep the public plugin keys in `OpenApi4sPlugin.autoImport` prefixed with `openApi4s`.
- Keep OpenAPI4s CLI dependencies in the hidden `openapi4s` configuration; do not add them to a consumer's compile classpath.
- Preserve `openApi4sVersion := "0.9.0"` unless intentionally upgrading and updating tests/docs together.
- Generated sources default to `Compile / scalaSource` because OpenAPI4s output is additive and editable.
- Add behavior tests under `src/sbt-test/`; test through a real sbt consumer build.
- Do not edit files under `target/`, `project/target/`, or `project/project/`.

## External References

| Need | File |
|---|---|
| User setup and settings | `README.md` |
| Generator fixture | `src/sbt-test/sbt-openapi4s/generate/` |

## Commit Attribution

AI commits MUST include:

```
Co-Authored-By: (the agent's name and attribution byline)
```
