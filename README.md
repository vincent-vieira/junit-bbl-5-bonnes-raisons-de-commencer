# JUnit : 5 bonnes raisons de commencer !

## BBL présenté par [Vincent Vieira](mailto:vincent.vieira@carbon-it.com)

### Prérequis
Afin d'avoir une expérience optimale avec ce kata, il est nécessaire *a minima* de :
- Connaître Java ainsi que Maven
- Posséder une première expérience de JUnit

### L'application TodoMVC
Il s'agit d'une version simplifiée de l'application [**TodoMVC**](https://todobackend.com/), utilisée très souvent en tant qu'entraînement afin de découvrir comment concevoir des webservices REST. Ici, nous n'implémentons que deux endpoints permettant d'avoir le minimum nécessaire de logique métier qui doit être testée avec JUnit.

### A propos du BBL
JUnit 5 est une [réécriture **complète** du framework de test](https://junit.org/junit5/docs/current/user-guide/#overview-what-is-junit-5). 

La version originale du projet utilise JUnit 4, que nous allons migrer progressivement vers la *version 5* en découvrant l'ensemble des fonctionnalités toujours disponibles, celles utiles au quotidien ont été ajoutées ainsi que comment faire cohabiter les deux écosystèmes de test pendant la durée de la migration.

#### Migrer depuis JUnit 4
Migrer vers JUnit 5 est très simple. De plus, il est tout à fait possible de faire cohabiter des tests écrits avec JUnit 4, tout en écrivant les nouveaux sur JUnit 5 sans pour autant commencer à tout refactorer, grâce à *JUnit Vintage Engine* qui va nous permettre d'étendre le modèle d'exécution des tests pour inclure nos tests "legacy".

Pour chaque fichier à migrer, [ces règles](https://junit.org/junit5/docs/current/user-guide/#migrating-from-junit4-tips) peuvent s'appliquer :
- `@org.junit.Test` devient `@org.junit.jupiter.api.Test`
- Toutes les assertions sont présentes dans la classe `org.junit.jupiter.api.Assertions` : ainsi, `org.junit.Assert.assertEquals` devient `org.junit.jupiter.api.Assertions.assertEquals`
- Toutes les assomptions sont présentes dans la classe `org.junit.jupiter.api.Assumptions`, plutôt que dans `org.junit.Assume`
- Les méthodes de cycle de vie annotées avec `@org.junit.BeforeClass` et `@org.junit.AfterClass` doivent désormais être annotées avec `@org.junit.jupiter.api.BeforeAll` et `@org.junit.jupiter.api.AfterAll`
- Les méthodes de cycle de vie annotées avec `@org.junit.Before` et `@org.junit.After` doivent désormais être annotées avec `@org.junit.jupiter.api.BeforeEach` et `@org.junit.jupiter.api.AfterEach`
- Il est plus simple **et recommandé** de remplacer l'annotation `@org.junit.Ignore` par l'annotation `@org.junit.jupiter.api.Disabled`. Néanmoins, `@Ignore` reste utilisable avec des tests JUnit 5 **si et seulement si** le support de migration pour JUnit 4 est activé pour le test dans lequel elle est utilisée (avec `@EnableJUnit4MigrationSupport` ou `@ExtendWith(IgnoreCondition.class)`).
- Le traditionnel `@org.junit.runner.RunWith`, utilisé avec Spring et/ou Mockito disparaît au profit de `@org.junit.jupiter.api.extension.ExtendWith`. De plus, les `@org.junit.Rule` et `@org.junit.ClassRule` ont totalement disparu, n'étant plus utiles.
  Néanmoins, il est toujours possible d'activer le support de certaines règles JUnit 4 au sein de tests JUnit 5 (nominativement *Verifier*, *ExternalResource* et *ExpectedException*) avec l'annotation `@EnableJUnit4MigrationSupport` (**attention, ceci est disponible uniquement en important le module de JUnit *org.junit.jupiter:junit-jupiter-migrationsupport***). 
- L'utilisation de la règle `ExpectedException` a été remplacée par une assertion nommée `org.junit.jupiter.api.AssertThrows` qui capture le résultat de l'exécution d'une fonction anonyme afin de pouvoir vérifier que l'exception est conforme à nos attentes.

#### Découvrir le nouveau système d'extensions
L'intégration avec les bibliothèques que nous utilisons au quotidien a été également changé. Le système d'extensions nous permet aussi également désormais de ne plus être contraint par l'utilisation d'un seul runner et de multiples `@org.junit.Rule` et `@org.junit.ClassRule`, mécanisme très peu flexible qui tenait de la rustine plus que d'une réelle fonctionnalité.
Il suffit de combiner les `@org.junit.jupiter.api.extension.ExtendWith` à foison. Par exemple, la fonctionnalité de *data-driven testing* par les [**tests paramétrés**](https://nipafx.dev/junit-5-parameterized-tests/)) est apportée par l'annotation `@org.junit.jupiter.params.ParameterizedTest` qui revient à annoter sa méthode de test avec `@ExtendWith(ParameterizedTestExtension.class)`.
Ceci pourra être combiné à Spring ou Mockito au sein de la classe de test, sans aucun soucis.

- Un test Spring, originalement annoté par `@RunWith(SpringJUnit4ClassRunner.class) @SpringBootTest` deviendra annoté par `@SpringBootTest` (l'annotation `@ExtendWith(SpringExtension.class)` est incluse)
- Un test Mockito, originalement annoté par `@RunWith(MockitoJUnitRunner.class)` deviendra annoté par `@ExtendWith(MockitoExtension.class)` (**attention, Mockito possède son propre module *mockito-junit-jupiter* à importer**)

#### Tests paramétrés
En parlant de test paramétrés, ils ont été grandement améliorés avec l'arrivée de JUnit 5. JUnit 4 offrait cette fonctionnalité avec l'annotation `@Parameterized.Parameters`, combinée à l'utilisation de l'exécuteur `Parameterized.class`.
Cela forçait à avoir une seule méthode de test qui se basait sur les données reçues par le constructeur, que l'exécuteur invoquait : impossible d'avoir donc de mutliples méthodes de test avec des paramètres différents.

JUnit 5 remédie à ce problème avec l'annotation `@org.junit.jupiter.params.ParameterizedTest`, à substituer à `@org.junit.jupiter.api.Test` lors de la déclaration du test qui sera paramétré et reçoit désormais ses paramètres par argument de méthodes (et non de constructeur).
Pour fonctionner, le test doit être néanmoins associé à une source de données, qui fournira les paramètres du test. La plupart des sources de données nous utiliserons au quotidien sont :
- Afin de ne fournir qu'**un seul paramètre** :
  - `@org.junit.jupiter.params.provider.NullSource` pour n'importe quelle valeur objet
  - `@org.junit.jupiter.params.provider.EmptySource` pour les chaînes de caractères ou collections vides
  - `@org.junit.jupiter.params.provider.ValueSource` pour un ensemble de valeurs en dur (l'ensemble des primitives Java, ainsi que les classes et les chaînes de caractères)
  
- Afin de fournir de **multiples paramètres** :
  - `@org.junit.jupiter.params.provider.CsvSource` pour fournir des valeurs interprétées depuis une chaîne de caractères vers un type géré par JUnit et délimitées par un séparateur (configurable, étant la virgule par défaut)
  - `@org.junit.jupiter.params.provider.MethodSource` pour fournir des valeurs construites par une méthode statique au sein du même test, qui peut retourner une `Collection` ou un `Stream` d'`Arguments` (une classe utilitaire JUnit remplaçant un tableau de paramètres)

#### Des noms de test lisibles, enfin !
Une fonctionnalité qui manquait cruellement dans JUnit 4 était de pouvoir changer l'affichage des noms de tests ainsi que des paramètres que l'on pouvait éventuellement leur passer. Ceci causa des changements de convention de nommage au sein des tests, qui parasitaient les standards édictés au sein de Java (oui, je parle des fameux tests nommés en *snake_case* au lieu du *CamelCase*).
Avec JUnit 5, il est possible de découpler le nommage du test au sein du code du nom affiché lors de la génération du rapport de tests, grâce à l'annotation `@org.junit.jupiter.api.DisplayName`. Elle peut être apposée sur une méthode ou sur la classe de test, JUnit se charge de concaténer les deux. 

Dans le cadre de tests paramétrés, il est possible de combiner `@DisplayName` avec l'attribut `name` de `@ParameterizedTest` qui permet de changer l'affichage du nom de test pour chaque jeu de paramètres avec lequel il sera joué, en les affichant au milieu d'une phrase lisible par un humain par exemple.

Une fonctionnalité de [génération dynamique de noms de tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-name-generator) est en cours d'intégration dans JUnit 5 mais reste expérimentale.

#### Tests imbriqués
Une dernière fonctionnalité majeure de JUnit 5 est la possibilité de déclarer et d'exécuter des [tests imbriqués](https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested), cela remplaçant le système contraignant de `Suite` présent dans JUnit 4.
Il suffit de créer des classes imbriquées annotées avec `@org.junit.jupiter.api.Nested`, le reste est inchangé !
