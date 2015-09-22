# DBLP

## Rules

1. You cannot push directly to this main repo.
2. Fork this project to your repository
3. Then create a pull request to this repo. I will do the code review.

## Setup
1. Put `dblp.dtd` and `dblp.xml` under the folder `src/main/resources/`
2. Open `Eclipse` (You can download it [here](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/marsr))
3. Go to `File` -> `Import`
4. Select `Existing Maven Project`
5. Select the root directory of the dblp project
6. When you want to run SAX parser on `dblp.xml`, we must remove the `entityExpansionLimit`.
7. Go to `Run` -> `Run Configuration`
8. Select the `Arguments` tab
9. Paste `-DentityExpansionLimit=0` in VM arguments.
10. Tada!

## Requirement Analysis

### Publication Attributes (Common Attributes)
`ee`, `year`, `author`, `title`, `url`

### Attributes List of DBLP data with counts
|Element|Attribute|Count|
|---|---|---|
|`article`|
| |`tt`|15|
| |`sub`|10616|
| |`note`|1378|
| |`editor`|31|
| |`i`|16448|
| |`cdrom`|4003|
| |`sup`|7138|
| |`volume`|1335034|
| |`number`|1135761|
| |`pages`|1194032|
| |`journal`|1335524|
| |`month`|2476|
| |`cite`|47384|
| |`publisher`|228|
| |`crossref`|1578|
| |`booktitle`|223|
|`inproceedings`|
| |`tt`|3|
| |`sub`|1306|
| |`note`|124|
| |`editor`|13|
| |`i`|4797|
| |`cdrom`|8922|
| |`sup`|3285|
| |`number`|379|
| |`pages`|1587529|
| |`month`|1|
| |`cite`|120822|
| |`crossref`|1617789|
| |`booktitle`|1669343|
|`phdthesis`|
| |`note`|9656|
| |`isbn`|2454|
| |`volume`|10|
| |`number`|3|
| |`pages`|5335|
| |`month`|3|
| |`school`|6959|
| |`series`|12|
| |`publisher`|11|
|`www`|
| |`editor`|22|
| |`note`|10539|
| |`cite`|111|
| |`booktitle`|1|
| |`crossref`|239|
|`book`|
| |`editor`|2356|
| |`note`|24|
| |`sub`|3|
| |`isbn`|12830|
| |`i`|2|
| |`cdrom`|3|
| |`sup`|10|
| |`volume`|2396|
| |`pages`|9370|
| |`month`|1|
| |`school`|10|
| |`series`|5186|
| |`publisher`|12083|
| |`cite`|3319|
| |`booktitle`|1014|
|`mastersthesis`|
| |`school`|9|
|`proceedings`|
| |`editor`|63517|
| |`note`|189|
| |`sub`|1|
| |`address`|3|
| |`isbn`|23662|
| |`i`|4|
| |`sup`|55|
| |`volume`|13242|
| |`number`|15|
| |`pages`|8|
| |`journal`|4|
| |`series`|13499|
| |`publisher`|27127|
| |`cite`|212|
| |`booktitle`|27430|
| |`crossref`|16|
|`incollection`|
| |`sub`|5|
| |`note`|22|
| |`chapter`|2|
| |`i`|95|
| |`cdrom`|53|
| |`sup`|37|
| |`number`|20|
| |`pages`|31434|
| |`cite`|784|
| |`publisher`|91|
| |`crossref`|32221|
| |`booktitle`|35241|

### Key Patterns
|Element|Key|
|---|---|
|`article`| `reference` `persons` `dblpnote` `conf` `journals` `tr` |
|`book`| `reference` `persons` `books` `series` `conf` `phd` `tr` |
|`incollection`| `reference` `books` `series` `conf` `journals` |
|`inproceedings`| `persons` `series` `conf` `journals` |
|`proceedings`| `series` `conf` `journals` `tr` |
|`www`| `persons` `www` `homepages` |
|`phdthesis`| `books` `phd` |
|`mastersthesis`| `ms` `phd` |
