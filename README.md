LT Sentiment Analysis Classifier 
===============================

Installation
------------

Copy to indian/resources files from frink:/srv/data/sentiment

How to run
----------

The code contains three projects.

1. Sentiment Analysis in Indian Languages. (Hindi and Bengali)
2. Sentiment Polarity Classification of reviews (Laptops, Restaurants and Hotels).
3. Aspect Detection in reviews. (Laptops and Restaurants)



The respective files for the three projects are:

1. SentimentWrapper.java (\sentiment\indian\src\main\java\sentimentClassifier)
2. SentimentClassificationWrapper.java (\sentiment\english\src\main\java\de\tudarmstadt\lt\sentimentClassification)
3. AspectCategorizationWrapper.java (\sentiment\english\src\main\java\de\tudarmstadt\lt\aspectCategorization)



The comman line input for every classifier is:

Arg0: Option (1/2/3 for Cross Validation or Prediction or Accuracy Check)
Arg1: Option ('h' for Hindi; 'b' for Bengali; 'r' for Restaurants, 'l' for Laptops, 'h' for Hotels)
Arg2: Training File Name
Arg3: Test File Name (Not required for Cross Validation)
Arg4: Test File Gold Label (Not required for Cross Validation or Prediction)

Sample command line inputs to get the classifier accuracy for sentiment analyis in Hindi.

3
h
hindiCleansedTraining.txt
hindiCleansedTest.txt
HI_Test_Gold.txt



Sample command line inputs to get the classifier accuracy for setiment classification of Restaurants reviews.

3
r
Train_Restaurants_Cleansed.txt
Test_Restaurants_Cleansed.txt
Test_Restaurants_Cleansed.txt


Sample command line inputs to get the aspect categorization of Laptops.

3
l
Laptops_Train_ABSA.txt
Laptops_Test_ABSA.txt
Laptops_Test_ABSA.txt




Note: Cross Validation input for aspect task has not been implemented as of now.
      For cross validation, the user need to use the score separately as mentioned in: \sentiment\english\dataset\dataset_aspectCategorization\Scorer
      The input file (either training or test or test labels) should be in the same format as mentioned in the sample files above.
Licence - Apache Software Licence (ASL) 2.0


