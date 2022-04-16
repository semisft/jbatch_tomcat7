JBatch on Tomcat 7
===
Easily run JBatch (JSR352) on Tomcat 7.

Uses IBM's reference JBatch implementation as Job Runner.
Adds Apache Batchee as frontend and REST service server.

UI
==
After running see UI on: http://localhost:8080/batch/jbatch

REST Services
== 
Call services at http://localhost:8080/batch/jbatch/rest/
ie. start job: curl http://localhost:8080/batch/jbatch/rest/start/hellojob


Inspired from: https://nozaki.me/roller/kyle/entry/using-apache-batchee-s-jax