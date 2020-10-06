package com.lukeneedham.geonames

import java.io.File

internal fun main(args: Array<String>) {
    val inputFile = args.getOrNull(0) ?: error("You need to pass input file path as first argument")
    val outputFile = args.getOrNull(1) ?: error("You need to pass output file path as second argument")
    val parsedCountries = parseFile(inputFile)
    writeEnumFile(parsedCountries, outputFile)
}

private fun parseFile(inputFile: String): List<ParsedCountry> {

    val file = File(inputFile)

    val parsedCountries = mutableListOf<ParsedCountry>()

    file.forEachLine {
        if (it.startsWith("#")) {
            return@forEachLine
        }
        val entries = it.split("\t")

        val iso = entries[0]
        val iso3 = entries[1]
        val isoNumeric = entries[2]
        val fips = entries[3]
        val country = entries[4]
        val capital = entries[5]
        val areaSqKm = entries[6]
        val population = entries[7]
        val continent = entries[8]
        val tld = entries[9]
        val currencyCode = entries[10]
        val currencyName = entries[11]
        val phone = entries[12]
        val postalCodeFormat = entries[13]
        val postalCodeRegex = entries[14]
        val languages = entries[15]
        val geonameId = entries[16]
        val neighbours = entries[17]
        val equivalentFipsCode = entries[18]

        val languageList = languages.split(",").map { it.trim() }
        val neighboursList = neighbours.split(",").map { it.trim() }

        val parsedCountry = ParsedCountry(
            iso = iso,
            iso3 = iso3,
            isoNumeric = isoNumeric,
            fips = fips,
            country = country,
            capital = capital,
            areaSqKm = areaSqKm.toDouble(),
            population = population.toDouble(),
            continent = continent,
            tld = tld,
            currencyCode = currencyCode,
            currencyName = currencyName,
            phone = phone,
            postalCodeFormat = postalCodeFormat,
            postalCodeRegex = postalCodeRegex,
            languages = languageList,
            geonameId = geonameId,
            neighbours = neighboursList,
            equivalentFipsCode = equivalentFipsCode
        )
        parsedCountries.add(parsedCountry)
    }
    return parsedCountries
}

private fun writeEnumFile(parsedCountries: List<ParsedCountry>, outputFile: String) {
    val outputFile = File(outputFile)

    val text = StringBuilder()
    text.append(
        """package com.lukeneedham.geonames

enum class CountryData (
    val iso: String,
    val iso3: String,
    val isoNumeric: String,
    val fips: String,
    val country: String,
    val capital: String,
    val areaSqKm: Double,
    val population: Double,
    val continent: String,
    val tld: String,
    val currencyCode: String,
    val currencyName: String,
    val phone: String,
    val postalCodeFormat: String,
    val postalCodeRegex: String,
    val languages: List<String>,
    val geonameId: String,
    val neighbours: List<String>,
    val equivalentFipsCode: String
) {
        """
    )
    parsedCountries.forEachIndexed { index: Int, it: ParsedCountry ->

        val languagesText = it.languages.map { "\"$it\"" }.joinToString(", ")
        val neighboursText = it.neighbours.map { "\"$it\"" }.joinToString(", ")

        text.append(
            """
    ${it.iso}(
        iso = "${it.iso}",
        iso3 = "${it.iso3}",
        isoNumeric = "${it.isoNumeric}",
        fips = "${it.fips}",
        country = "${it.country}",
        capital = "${it.capital}",
        areaSqKm = ${it.areaSqKm},
        population = ${it.population},
        continent = "${it.continent}",
        tld = "${it.tld}",
        currencyCode = "${it.currencyCode}",
        currencyName = "${it.currencyName}",
        phone = "${it.phone}",
        postalCodeFormat = "${it.postalCodeFormat}",
        postalCodeRegex = ${'"'}${'"'}${'"'}${it.postalCodeRegex}${'"'}${'"'}${'"'},
        languages = listOf($languagesText),
        geonameId = "${it.geonameId}",
        neighbours = listOf($neighboursText),
        equivalentFipsCode = "${it.equivalentFipsCode}"
    )"""
        )
        if (index != parsedCountries.size - 1) {
            text.append(",\n")
        }
    }

    text.append("\n}")

    outputFile.writeText(text.toString())
}

private data class ParsedCountry(
    val iso: String,
    val iso3: String,
    val isoNumeric: String,
    val fips: String,
    val country: String,
    val capital: String,
    val areaSqKm: Double,
    val population: Double,
    val continent: String,
    val tld: String,
    val currencyCode: String,
    val currencyName: String,
    val phone: String,
    val postalCodeFormat: String,
    val postalCodeRegex: String,
    val languages: List<String>,
    val geonameId: String,
    val neighbours: List<String>,
    val equivalentFipsCode: String
)