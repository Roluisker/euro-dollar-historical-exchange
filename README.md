# Lydian Lion 

[GooglePlay](https://play.google.com/store/apps/details?id=com.sc.lydianlion)

![picture](https://i.ibb.co/Prf6MQJ/3.jpg)

Euro to dollar historical

Tech

	* Kotlin
	* Coroutines
	* Dagger 2
	* Junit
	* Espresso
	* Meterial

Special features:
	
	* InstantApp Ready
	* Available to implement on demand delivery [Reference](https://youtu.be/httqEshs_Bk)
	* Without network, the app will show the last result stored in local

## How It Works

![picture](https://cdn-images-1.medium.com/max/800/1*KnYBBZIDDeg4zVDDEcLw2A.png)

1. Fully Modular desing:

	* Core -> Main library (Retrofit, base classes, models, core Dagger components, etc)
	* App -> Installable module. (With NavHostFragment) [Reference](https://developer.android.com/guide/navigation/navigation-getting-started)
	* Convert -> Dinamyc Feature module [Reference](https://developer.android.com/studio/projects/dynamic-delivery)
	* Timeline -> Historical timeline. (Main fragment)
	* Resources -> Common resources (strings, vectors.. etc)

2. Gradle
	
	* base_libraries.gradle -> common dependencies between modules. (material desing, androidx core, junit. Etc)
	* base_dagger_libraries.gradle
	* config_module.gradle -> default config, default compile options to modules. 

## Test

Timeline

  * HistoricalFragmentTest

<a href="https://ibb.co/4VZ4Zdm"><img src="https://i.ibb.co/QKFYFdb/Screenshot-from-2019-05-26-14-33-04.png" alt="Screenshot-from-2019-05-26-14-33-04" border="0"></a>

  * HistoricalRepositoryImplTest
  
<a href="https://ibb.co/Y0JRcCL"><img src="https://i.ibb.co/SfHstj3/Screenshot-from-2019-05-26-14-29-11.png" alt="Screenshot-from-2019-05-26-14-29-11" border="0" /></a>
	 
<a href="https://imgbb.com/"><img src="https://i.ibb.co/bJgzpWp/Screenshot-from-2019-05-26-14-29-01.png" alt="Screenshot-from-2019-05-26-14-29-01" border="0" /></a>
   
	* HistoricalViewModelTest
  
<a href="https://ibb.co/J2s49r2"><img src="https://i.ibb.co/P46nd14/Screenshot-from-2019-05-26-14-34-28.png" alt="Screenshot-from-2019-05-26-14-34-28" border="0" /></a>
	  
<a href="https://imgbb.com/"><img src="https://i.ibb.co/xs5vTbV/Screenshot-from-2019-05-26-14-34-10.png" alt="Screenshot-from-2019-05-26-14-34-10" border="0" /></a>
    
  Core 

    * TimeSeriesDaoTest

<a href="https://ibb.co/3rvW4Dd"><img src="https://i.ibb.co/VjxDJ79/Screenshot-from-2019-05-26-14-50-05.png" alt="Screenshot-from-2019-05-26-14-50-05" border="0" /></a>

