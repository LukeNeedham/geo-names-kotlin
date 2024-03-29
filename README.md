# About

This project encodes the country data from:
http://download.geonames.org/export/dump/countryInfo.txt
into a Kotlin enum.

This enum can be used to access a range of information about various countries.

This project has 2 parts:
- The parser, which reads the original TSV file and writes the enum class: `WorldDataParser.kt`
- The enum class generated for the data at time of release: `CountryData.kt`

To run the parser for yourself, you might want to run the command:

`kscript WorldDataParser.kt WorldData.tsv CountryData.kt`

To just use the data in a project:
- Add the dependency via Jitpack: [![](https://jitpack.io/v/LukeNeedham/geo-names-kotlin.svg)](https://jitpack.io/#LukeNeedham/geo-names-kotlin)

- Simply use the `CountryData` enum
