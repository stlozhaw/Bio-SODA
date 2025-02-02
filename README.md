# Bio-SODA - Question Answering for Domain Knowledge Graphs

Welcome to the Bio-SODA code repository! 

Bio-SODA is a question answering system over domain knowledge graphs, such as scientific datasets, where training data (in the form of questions and corresponding SPARQL queries) is rare or unavailable. 

The system architecture and evaluation are provided in our paper [Bio-SODA: Enabling Natural Language Question Answering over Knowledge Graphs without Training Data](https://arxiv.org/abs/2104.13744), accepted at the 33rd International Conference on Scientific and Statistical Database Management (SSDBM 2021).

This repository is a self-contained resource allowing you to setup your own installation of Bio-SODA and test it on a sample knowledge graph that describes Persons and Companies they work for. 

## Requirements

### MySQL

To start with, install [MySQL server](https://dev.mysql.com/downloads/mysql/) if needed. Create a new database, for example, `sample_biosoda_index`. The MySQL user for this database should have sufficient rights to create new tables, because the inverted index will be stored in 2 newly created tables at runtime.

### Java

Next, install Java if needed. Download or clone the Bio-SODA repository and make sure to set JAVAHOME in the [set-vars.sh](https://github.com/anazhaw/bio-soda/blob/master/set-vars.sh#L9) script and in the [config.sh](https://github.com/anazhaw/bio-soda/blob/master/scripts/config.sh#L9) script.

### Stanford coreNLP

Bio-SODA uses the Stanford coreNLP tools. For this purpose the models library needs to be downloaded from the official website [here](http://nlp.stanford.edu/software/stanford-english-corenlp-2016-10-31-models.jar) and added to the [lib/](https://github.com/anazhaw/bio-soda/blob/master/lib/) folder.

### Data input
Currently, we assume that queries are executed against a real SPARQL endpoint. For this purpose, in order to test Bio-SODA with the sample data, you can either use the link already provided in the Constants file [here](https://github.com/anazhaw/bio-soda/blob/master/src/ch/ethz/semdwhsearch/prototyp1/constants/Constants.java#L84), or setup your own repository, for example in Virtuoso or GraphDB, where the data can be queried - you will need to simply upload the two RDF files from the [sample_data](https://github.com/anazhaw/bio-soda/blob/master/sample_data) folder there. 

### Node centrality
Moreover, if you want node centrality to be included in ranking for your own data, you will need to setup a SPARQL endpoint where the RDF PageRank scores are uploaded. We currently provide a link for the sample data model PageRank scores, but in order to use your own dataset, you will need to create a new SPARQL endpoint or upload the PageRank scores to the one containing your data and change the URL provided in the constants file [here](https://github.com/anazhaw/bio-soda/blob/master/src/ch/ethz/semdwhsearch/prototyp1/constants/Constants.java#L85). One of the options for computing new PageRank scores is using the [PageRankRDF](https://github.com/QAnswer/PageRankRDF) tool.

## Compile & Run
Compile the source code using the command `./compile.sh` in the root folder of the project. For now, we mainly support install on Linux and Mac.

Next, you can start running Bio-SODA locally by running `./run-standalone.sh` from the root folder of the project. The prototype will be available at the page [http:localhost:8081/biosoda/](http:localhost:8081/biosoda/).

### Runtime Configurations
The following configuration parameters are required in the starting page of Bio-SODA:
* Database Vendor: `mysql`
* Schema: `sample_biosoda_index` (i.e. the schema name of your newly created database)
* Username: the name of the mysql user
* Password: the pass of the mysql user
* Database: `sample_biosoda_index` (the database name of your newly created database - usually schema and database are the same)

For a local MySQL, you can leave the remaining two fields (hostname and port) blank.
* Configuration Directory: `config/`
* Data Directory: `sample_data/` (location of your RDF data relative to the root folder) 

Finally, in order to compute the index over the RDF data, select the "Reload Index" checkbox. If, at a later stage, you need to add more data to the index, check also "Append to Index".

Click on "Go" and wait for the index to be computed! For the sample database provided with Bio-SODA, this should not take longer than 1 or 2 minutes. 

### Summary Graph Generation
In parallel, in the Data Directory, the Summary Graph of the RDF data will be generated, in the `summarygraph.txt` file. This is only done once and afterwards the graph file will be re-used by the system. If the schema of your data changes, delete this file (which will trigger its recomputation, regardless of whether the index is reloaded or not).

## Question Answering

Congratulations! You should now see the welcome screen where you can input a few test questions.

Possible examples:
* What is the city where James lives?
* Show companies and locations.
* Who works for Nestle?
* Show companies and locations including cities.
* Who works for a company located in Baden?
* Show all persons who live in the USA. 

### Evaluation 
Note that more advanced example on 3 real-world datasets tested with Bio-SODA are available at the demo page [here](http://biosoda.expasy.org/welcome/). The queries used for benchmarking the system are available in the [Benchmarks](https://github.com/anazhaw/Bio-SODA/tree/master/Benchmarks) folder. Furthemore, a detailed analysis of results across multiple systems tested with the 3 datasets, is available in the [Evaluation](https://github.com/anazhaw/Bio-SODA/tree/master/Evaluation) folder.

The results can be reproduced by testing questions on the demo pages of the three datasets or by uploading data in a new installation of the system and processing it in Bio-SODA as described above. For example, official datasets for the QALD4 biomedical benchmark can be downloaded from [here](https://github.com/ag-sc/QALD/tree/master/4/data).

We thank Lukas Blunschi for the code base of [SODA](https://dl.acm.org/doi/10.14778/2336664.2336667), which was the foundation for the development of Bio-SODA.

Bio-SODA is a developing research prototype, with many improvements still planned in the pipeline. Contributions are welcome in the form of Pull Requests, while bugs or suggestions for improvement can be reported using the Issues tab.
