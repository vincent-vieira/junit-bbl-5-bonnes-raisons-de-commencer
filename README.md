# JUnit : 5 bonnes raisons de commencer !

## BBL présenté par [Vincent Vieira](mailto:vincent.vieira@carbon-it.com)

### Prérequis
Afin d'avoir une expérience optimale avec ce kata, il est nécessaire *a minima* de :
- Connaître Java ainsi que Maven
- Posséder une première expérience de JUnit
- Conceptualiser correctement l'écriture de tests unitaires. La connaissance du **data-driven testing** (qui s'exprime dans 
  JUnit sous la forme de [tests paramétrés](https://nipafx.dev/junit-5-parameterized-tests/)) est un plus.

### L'application TodoMVC
Il s'agit d'une version simplifiée de l'application [**TodoMVC**](https://todobackend.com/), utilisée très souvent en tant qu'entraînement afin de découvrir comment concevoir des webservices REST. Ici, nous n'implémentons que deux endpoints permettant d'avoir le minimum nécessaire de logique métier qui doit être testée avec JUnit.

### A propos du BBL
La version originelle du projet utilise JUnit *version 4*, Spring 5 et Mockito. Nous allons donc progressivement migrer depuis JUnit 4 vers la *version 5*, en découvrant l'ensemble des fonctionnalités toujours disponibles, celles utiles au quotidien ont été ajoutées ainsi que comment faire cohabiter les deux écosystèmes de test pendant la durée de la migration (**JUnit 5 est une réécriture complète du framework**).

#### Migrer depuis JUnit 4
Vintage engine + migration support library

Hooks cycle de vie

@Test

@Ignore -> @Disabled

@Rule
- exception handling
- Mockito and Spring

#### Découvrir le nouveau système d'extensions
Mockito
Spring

#### Tests paramétrés
Null/CSV/Value/Method source

#### Des noms de test lisibles, enfin !
@DisplayName
Name generation ??

#### Tests imbriqués
@Nested
