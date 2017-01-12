# Information Retrieval Engine


## Requirements

* Maven 
* [The Porter stemming algorithm] (http://snowball.tartarus.org/)
* [Apache Commons CSV] (https://commons.apache.org/proper/commonscsv/)
* [Apache Commons IO] (http://commons.apache.org/proper/commonsio/)
* [Jsoup HTML parser library] (https://jsoup.org/)


## Execution 

``
java -jar IR-maven.jar <path corpus folder > <path stoptword file> <Max memory>
``

--

## Task 1
Modelling:	classes	and	main	methods definition.
a) Keep in mind modularity and flexibility.
b) Describe your classes, main methods, and data flow in the report.

* [report portuguese version (pdf)](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR_engine_report1.pdf) 
* [delivery zip](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR-engine-report-68779-68021.zip) 



## Task 2
Implement	a	simple	corpus	reader,	tokenizer, and	Boolean	indexer.
a) Develop your own tokenizer from scratch. Integrate the Porter stemmer (http://snowball.tartarus.org/) and a stopword filter in your code.
b) Index a small corpus (to be defined later) and submit a text file with the resulting index, following the scheme: term,document frequency,list of documents


* [report portuguese version (pdf)](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR_engine_report2.pdf) 
* [delivery zip](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR-engine-report-task2-68779-68021.zip) 

## Task 3
Implement an indexer based on the vector-space model, using the tf-idf weighting scheme and lnc.ltc strategy, as described in the slides.
a) Write your index to disk so that the searcher module can efficiently load it.
b) Index the corpus (to defined later on).

* [report portuguese version (pdf)](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR_engine_report3.pdf) 
* [delivery zip](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR-engine-report-task3-68779-68021.zip) 

## Task 4

Implement	a	ranked	retrieval	method.
a) Load the index from disk.

* [report portuguese version (pdf)](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR_engine_report4.pdf) 
* [delivery zip](https://github.com/ruipoliveira/IR-engine/blob/master/docs/IR-engine-report-task4-68779-68021.zip) 

## Authors

* Gabriel Vieira (gabriel.vieira@ua.pt)
* Rui Oliveira (ruipedrooliveira@ua.pt)
