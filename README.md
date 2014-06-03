LAS2peer-File-Service
=======================

This service allows file hosting.
Files and directories can be placed into the ./files directory and are then accessible ower the webConnector.
The files stay on the machine, they do not become part of the p2p network shared storage.
Currently only no binary files are spported. But you could already, for example, host static/javascript based webpages.

For testing build with "ant all" and start ./bin/start_network
The Service can then be accessed directly over the browser (no login required):
http://127.0.0.1:8080/download/

To avoid confusion when multiple file services are running, use the mapping.xml in ./etx/restXML and change path="download" to path="anything". Then you can access the service by your custom prefix.
http://127.0.0.1:8080/prefix123/

