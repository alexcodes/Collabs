Collabs
=======

Download: [http://plugins.jetbrains.com/plugin/7466](http://plugins.jetbrains.com/plugin/7466?pr=idea)

##Description
This plugin gives an opportunity of **real-time** collaborative editing.

##How to start
* Install the plugin by going to <code>Settings -> Plugins -> Browse repositories</code> and then search for <code>Collabs</code>
* Run the IDEA with enabled plugin

##Run server
* Go to plugin folder (Windows: <code>/.IntelliJIdea{version}/config/plugins/</code>)
* Run the plugin from command line <code>java -jar Co-Edit-Plugin.jar</code> (default port number is <code>1348</code>)
* You may change port number by giving it as an argument

##Connecting to server
* In the IDEA left-side toolbar will appeared plugin panel
* Click <code>Connect</code> button and enter server <code>IP address</code>

##Working with plugin
If you would like to share file in opened project with your collaborator, you need to follow next steps:

* Click on the document by right mouse button
* Choose <code>Collabs</code> menu item
* Press <code>Register document</code>

After this copy of the document will be sent to server storage.<br>

Your collaborator should press <code>Refresh</code> button on plugin panel and new document will appeared in list. 
Then **collaborator** should:

* Create new or open existing file in his project (package doesn't matter)
* The file name **must be the same** with registered document
* Choose the document in document list on the plugin panel
* Click on the created file by right mouse button
* Choose <code>Collabs</code> menu item
* Press <code>Bind document</code>

After this **all** changes in both documents will be sent to server and to your collaborators.
<code></code>

##How it works
As you can see, it is simple client-server architecture. Any time you register or bind documents, 
doc listeners are created. 
One client changes some documents and another client receives all changes through server.
Of course, the connection is bidirectional. 

##What's next?
It would be nice to make this project really well-working. Who knows, may be this plugin will be in IDEA from-the-box:)