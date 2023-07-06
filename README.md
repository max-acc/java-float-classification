# <p align="center"><b>Classification of float values in Java</b></p>
### Executing the program
#### Setting up the classification model
Firstly you have to import a package called "classification" that contains all important functions for classifying a 
dataset consisting of float values:   
```import classification.ClassificationOfFloatValues;```<br>  
The next step is to create an object for this classification (ob is used as a default name for an object):  
```ClassificationOfFloatValues ob = new ClassificationOfFloatValues(dataset);```  
The ```dataset``` variable should contain the name of the dataset that should be classified as a string. 
The dataset has to be in the same folder as the main file.<br>  
If the dataset has an index or a header (or both), it has to be indecaded by the user.  
If there is a header you have to call ```ob.setIndex(true);``` or/and ```ob.setHeader(true);```.  
The default value for these is ```false``` because it is expected that the dataset does not have an index or header.  
Most datasets do have a header and an index so make sure, if your dataset has a header or an index, to include this part in your program.
<br><br>

#### Processing the data
The following functions are required for classifying the data.  
Firstly you have to call ```ob.dataProcessing();```  
```ob.dataSubdivision();```  
```ob.distanceClassification();```
<br><br>

#### Evaluating the Results
For evaluating the predicted results you can call ```ob.evaluateResults();```.
There are multiple ways to show how the results should be displayed.  
The ```ob.setEvaluation(model)``` functions sets the evaluation models which are going to be calculated and printed.
```model``` should contain one of the names below as a string.<br>  
**Confusion Matrix**: Printing a normal confusion matrix for every class (size: class x class).  
**Simple Confusion Matrix**: Printing a simplified confusion matrix for every class with true positives and false positives (size: class x 2).  
**NormalizedConfusion Matrix**: Printing a normalized confusion matrix with the format of the confusion matrix as explained
above. The values that are displayed a normalized (values between 0 and 1).


## Scripts
There is a script that explains the programs function and also explains the data manipulation in detail.  
You can find the description here.
## Help
If you need help if applying the algorithm to your projects, feel free to ask.

## Authors

Contributors names and contact info

* Max Wenk  
	* [@max-acc](https://github.com/max-acc)

## Version History
### Built v-0.1  
The current built is v-0.1.  
It is possible to classify a dataset which contains only float values.
It is important to consider that the weight for every class is the same.


## License

This project is licensed under the "GNU Affero General Public License v3.0" License - see the LICENSE.md file for details.

## Acknowledgments

* [README Template](https://gist.github.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc)
