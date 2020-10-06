This project encodes the data of:
http://download.geonames.org/export/dump/countryInfo.txt
into a Kotlin enum.

This project has 2 parts:
1- The parser, which reads the original TSV file and writes the enum class
2- The enum class generated for the data at time of release

To run the parser for yourself, you might want to run the command:

`kscript WorldDataParser.kt WorldData.tsv CountryData.kt`

To just use the data, use the `CountryData` enum.
