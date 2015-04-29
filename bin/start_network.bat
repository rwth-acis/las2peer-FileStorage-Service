:: this script starts a las2peer node providing the example service of this project
:: pls execute it from the bin folder of your deployment by double-clicking on it

cd ..
java -cp "lib/*" i5.las2peer.tools.L2pNodeLauncher -ws -p 9011 uploadStartupDirectory('etc/startup') startService('i5.las2peer.services.fileService.DownloadService','SampleServicePass') startWebConnector interactive
pause
