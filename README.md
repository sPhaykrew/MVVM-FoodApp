# Food Recipes
Show list of food recipes and details

# Features
- Show list of food recipes
- Show details food recipes
- Can be save food recipes as favorite
- Can be sort food recipes and save sort setting by sharedpreferences
- When database is empty call api save data to room database
- Sync data from api to local db every 15 min and send notification when success (in work manager has a minimum interval of 15 minutes)

# Screenshots
| Show list | Show Details |
| --- | --- |
| ![Screenshot_20220626-225643_Food](https://user-images.githubusercontent.com/41615500/175824832-af5b85d2-e416-466f-9b13-bce8557feff7.jpg)| ![Screenshot_20220626-225651_Food](https://user-images.githubusercontent.com/41615500/175824839-bbeb217c-7fb2-49c5-94d7-4ef160e16a88.jpg) |

# Libraries
- MVVM design pattern
- LiveData for notify views when the underlying database changes.
- ViewModel store and manage UI-related data in a lifecycle conscious way
- Room for save data from api 
- Work manager for sync data in background
- Navigation for navigation between activity and fragment
- View Binding for generates a binding class for each XML layout file
- Coroutines for asynchronous 
- Retrofit2 a type-safe HTTP client
- Gilde for load image url to imageview

# ***
Can't use kotlin version 1.7.0 some issue in coroutines. Please use a version lower than 1.7.0

