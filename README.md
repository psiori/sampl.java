sampl.java
==========

Install notes
----------

Add the mvn-saple-java-x.x.x.jar to your java project

If you want to make changes, 
1) install the latest maven version from here: http://maven.apache.org/download.cgi 
2) Set the path to your java home and to maven bin. To make this easier add the following code to your ~/.bash_profile or create a new file if it doesn´t exist.
   Make sure to apply the correct path values.

JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home
export JAVA_HOME

M2_HOME=/usr/local/apache-maven/apache-maven-3.2.3
export M2_HOME

PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
export PATH

3) Apply the script with source ~/.bash_profile
4) Enter the sample-java directory inside the root dir of the project
5) Run mvn package inside that folder
6) The jar will be created in the target folder. There will be two jars. You should take the sample-java-x.x.x-SNAPSHOT since this is the jar with all librarie dependencies
