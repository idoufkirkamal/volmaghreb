# Déploiement automatique (GitHub Actions → Azure)

Ce document explique comment configurer et vérifier le déploiement automatique depuis GitHub Actions vers Azure App Service pour ce projet Spring Boot.

Résumé rapide
- Pipeline existant: `.github/workflows/deploy.yml` (trigger: `push` sur `master`).
- Méthode simple: `publish-profile` (secret GitHub `AZURE_WEBAPP_PUBLISH_PROFILE`).
- Méthode recommandée (plus sécurisée): OIDC / identité fédérée GitHub → Azure (exigences supplémentaires).

Vérifier le workflow actuel
1. Ouvrir le fichier `./.github/workflows/deploy.yml`.
2. Confirmer ces étapes présentes:
   - Checkout du code (`actions/checkout@v3`).
   - Setup JDK (17) et build Maven (`mvn clean package`).
   - Déploiement: `azure/webapps-deploy@v2` avec `package: target/*.jar` et `publish-profile: ${{ secrets.AZURE_WEBAPP_PUBLISH_PROFILE }}`.

Checklist pour que le pipeline soit fonctionnel
- [ ] Le nom de l'application dans le workflow (`app-name`) correspond exactement au nom de l'Azure Web App.
- [ ] Le secret `AZURE_WEBAPP_PUBLISH_PROFILE` est présent dans les *Repository secrets* (Settings → Secrets and variables → Actions) du dépôt GitHub.
- [ ] La branche `master` est bien celle utilisée pour déclencher le déploiement (modifier si vous utilisez `main`).

Comment créer et ajouter le Publish Profile (méthode simple)
1. Aller sur le portail Azure → votre App Service.
2. Dans le menu gauche, cliquer sur **Get publish profile** (Télécharger le profil de publication).
3. Ouvrir le fichier `.PublishSettings` téléchargé avec un éditeur de texte et copier son contenu entier.
4. Aller sur GitHub → votre dépôt → `Settings` → `Secrets and variables` → `Actions` → `New repository secret`.
   - Name: `AZURE_WEBAPP_PUBLISH_PROFILE`
   - Value: coller tout le contenu du publish profile
5. Enregistrer. Le workflow utilisera ce secret pour se connecter et déployer.

Tester le pipeline
1. Pousser un commit sur la branche `master` (ou branch configurée dans le workflow).
2. Aller dans l'onglet `Actions` du dépôt GitHub et ouvrir le run correspondant.
3. Vérifier les logs: build Maven puis étape `Deploy to Azure Web App` (success).
4. Vérifier dans le portail Azure → App Service → `Deployment Center` ou `Deployment` pour voir la publication.

Alternative recommandée : GitHub OIDC (sans secrets longue durée)
Résumé des étapes (haut niveau):
1. Créer une App Registration dans Azure AD (ou utiliser un principal de service existant).
2. Dans l'App Registration, ajouter une *Federated identity credential* pointant vers votre repo/branch (sujet: `repo:<owner>/<repo>:ref:refs/heads/<branch>`).
3. Donner au principal de service les rôles nécessaires (ex: `Contributor` sur le `Resource Group` ou `Web App` spécifique).
4. Ne pas stocker de secret dans GitHub; dans le workflow, utiliser `azure/login` avec le `client-id` (et `tenant-id`) seulement.
5. Déployer ensuite avec `az webapp deploy` ou en appelant `azure/webapps-deploy` après l'authentification.

Exemple (schématique) de commandes à exécuter dans un job GitHub Actions après authentification OIDC:
```yaml
#  - name: Login with OIDC
#    uses: azure/login@v1
#    with:
#      client-id: ${{ secrets.AZURE_CLIENT_ID }}
#      tenant-id: ${{ secrets.AZURE_TENANT_ID }}

#  - name: Deploy with Azure CLI
#    run: |
#      az webapp deploy --resource-group <MON_RG> --name <MON_APP> --src-path target/*.jar
```

Remarques et dépannage
- Si le job échoue à l'étape `Deploy to Azure Web App`, vérifier que le `publish-profile` est valide et que le `app-name` est correct.
- Si la construction Maven ne produit pas de `.jar` (p.ex. erreurs de compilation), corriger localement puis relancer.
- Vérifier la version de Java dans le workflow (ici JDK 17) et la compatibilité avec votre projet.

Prochaines actions recommandées
- Vérifier dans GitHub que le secret `AZURE_WEBAPP_PUBLISH_PROFILE` est bien présent.
- Effectuer un commit de test sur la branche configurée et observer l'exécution GitHub Actions.
- Si vous souhaitez migrer vers OIDC (recommandé), je peux fournir un workflow complet et les commandes pas-à-pas pour créer la fédération.


