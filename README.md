# DBLP

## Setup
1. Put `dblp.dtd` and `dblp.xml` under the folder `src/main/resources/`
2. Open Eclipse
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
| |`ee`|1321700|
| |`tt`|15|
| |`sub`|10616|
| |`note`|1378|
| |`editor`|31|
| |`year`|1335747|
| |`author`|3594757|
| |`i`|16448|
| |`title`|1335753|
| |`cdrom`|4003|
| |`url`|1335196|
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
| |`ee`|1767587|
| |`tt`|3|
| |`sub`|1306|
| |`note`|124|
| |`editor`|13|
| |`year`|1669343|
| |`author`|4963360|
| |`i`|4797|
| |`title`|1669343|
| |`cdrom`|8922|
| |`url`|1669342|
| |`sup`|3285|
| |`number`|379|
| |`pages`|1587529|
| |`month`|1|
| |`cite`|120822|
| |`crossref`|1617789|
| |`booktitle`|1669343|
|`phdthesis`|
| |`ee`|3665|
| |`note`|9656|
| |`year`|6961|
| |`author`|6963|
| |`isbn`|2454|
| |`title`|6961|
| |`url`|3608|
| |`volume`|10|
| |`number`|3|
| |`pages`|5335|
| |`month`|3|
| |`school`|6959|
| |`series`|12|
| |`publisher`|11|
|`www`|
| |`ee`|1|
| |`editor`|22|
| |`note`|10539|
| |`year`|17|
| |`author`|1635022|
| |`cite`|111|
| |`title`|1611675|
| |`booktitle`|1|
| |`crossref`|239|
| |`url`|29572|
|`book`|
| |`ee`|5660|
| |`editor`|2356|
| |`note`|24|
| |`sub`|3|
| |`year`|12097|
| |`author`|18163|
| |`isbn`|12830|
| |`i`|2|
| |`title`|12097|
| |`cdrom`|3|
| |`url`|2238|
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
| |`ee`|3|
| |`year`|9|
| |`school`|9|
| |`author`|9|
| |`title`|9|
| |`url`|4|
|`proceedings`|
| |`ee`|19285|
| |`editor`|63517|
| |`note`|189|
| |`sub`|1|
| |`address`|3|
| |`year`|27896|
| |`author`|4|
| |`isbn`|23662|
| |`i`|4|
| |`title`|27897|
| |`url`|27834|
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
| |`ee`|33024|
| |`sub`|5|
| |`note`|22|
| |`chapter`|2|
| |`year`|35241|
| |`author`|59891|
| |`i`|95|
| |`title`|35241|
| |`cdrom`|53|
| |`url`|35238|
| |`sup`|37|
| |`number`|20|
| |`pages`|31434|
| |`cite`|784|
| |`publisher`|91|
| |`crossref`|32221|
| |`booktitle`|35241|
