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
