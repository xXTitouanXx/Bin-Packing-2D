# Bin-Packing-2D

## Introduction
Ce projet est un solveur de bin packing 2D, utilisant des heuristiques et des méta-heuristiques pour optimiser l'agencement d'éléments dans des conteneurs de dimensions fixes.

## Prérequis
- JDK (Java Development Kit) installé et à jour sur votre machine.

## Lancement du Projet

1. **Récupération du Projet**
    - Clonez ce repository ou téléchargez le dossier `Bin-Packing-2D` sur votre machine.
    - Assurez-vous que votre JDK est à jour.

2. **Exécution de l'Application**
    - Exécutez le fichier `BinPackingGUI.java` situé dans `Bin-Packing-2D/src/GUI/BinPackingGUI.java`.
    - Une fois l'application lancée, choisissez un jeu de données.
    - Sélectionnez une méta-heuristique ou une heuristique.
    - Cliquez sur le bouton de résolution associé pour démarrer l'algorithme.

## Ajustement des Heuristiques et Méta-Heuristiques

### Métaheuristique Génétique

Vous pouvez ajuster plusieurs paramètres de l'algorithme génétique pour affiner les résultats :

1. **Modification des Variables de l'Algorithme**
    - Localisez le fichier `DriverGenetic.java` dans `Bin-Packing-2D/src/Algorithms/Metaheuristic/GeneticAlgorithm/DriverGenetic`.
    - Dans la méthode `solveBinPacking2D` :
        - **Taille de la population (`populationSize`)** :
            - *Ce que cela fait*: Modifie le nombre d'individus (solutions potentielles) dans chaque génération.
            - *Impact*: Une population plus grande peut explorer plus d'options mais nécessitera plus de temps de calcul.
        - **Nombre de générations (`generations`)** :
            - *Ce que cela fait*: Définit combien de fois la population sera reproduite.
            - *Impact*: Un nombre de générations plus élevé permet d'itérer plus sur les solutions, potentiellement améliorant les résultats, mais augmentant le temps de calcul.
        - **Taux de mutation (`mutationRate`)** :
            - *Ce que cela fait*: Détermine la probabilité qu'un individu subisse une mutation.
            - *Impact*: Un taux de mutation plus élevé introduit plus de diversité mais peut aussi perturber des solutions bien adaptées. Cela augmente également le temps de calcul

2. **Activation de la Recherche Locale**
    - Dans la méthode `mutate`, à la fin, vous pouvez activer ou désactiver une méthode de recherche locale `hillClimb` en commentant ou décommentant le code correspondant. Ligne 193 `hillClimb(member, binWidth, binHeight);`
    - *Ce que cela fait*: La recherche locale affine les solutions en explorant les voisins immédiats d'une solution donnée.
    - *Impact*: Améliore la qualité des solutions finales mais ralenti l'algorithme.

### Métaheuristique Tabou

Pour la métaheuristique tabou, les paramètres peuvent également être ajustés :

1. **Modification des Variables de l'Algorithme**
    - Localisez le fichier `DriverTabu.java` dans `Bin-Packing-2D/src/Algorithms/Metaheuristic/TabuSearch/DriverTabu`.
    - Dans la méthode `solveBinPacking2D` :
        - **Nombre d'itérations maximum (`maxIterations`)** :
            - *Ce que cela fait*: Définit le nombre maximum de cycles de l'algorithme.
            - *Impact*: Plus d'itérations peuvent mener à de meilleures solutions mais augmentent le temps d'exécution.
        - **Taille de la liste tabou (`tabuListSize`)** :
            - *Ce que cela fait*: Détermine combien de mouvements précédents sont considérés comme "tabou" (interdits).
            - *Impact*: Une liste tabou plus grande empêche les retours en arrière fréquents mais peut limiter l'exploration des solutions voisines.

## Conclusion

Ce projet offre une interface graphique simple pour expérimenter avec différentes heuristiques et méta-heuristiques pour le problème de bin packing 2D. Les utilisateurs peuvent facilement ajuster les paramètres pour optimiser les performances de l'algorithme et observer les résultats en temps réel.

Pour toute question ou assistance, n'hésitez pas à ouvrir une issue dans le repository.
