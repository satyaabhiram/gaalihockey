JCC = javac
JFLAGS = -g
MSG = src/com/gaalihockey/message
SRVR = src/com/gaalihockey/server
CLNT = src/com/gaalihockey/client
OUT = out

MODULEPATH = ${JAVAFX}
default: Server Client

Server: $(SRVR)/Server.java
	$(JCC) -d $(OUT) --module-path $(MODULEPATH) --add-modules=javafx.controls $(JFLAGS) $(MSG)/*.java $(SRVR)/game/*.java $(SRVR)/*.java

Client: $(CLNT)/Client.java
	$(JCC) -d $(OUT) --module-path $(MODULEPATH) --add-modules=javafx.controls $(JFLAGS) $(MSG)/*.java $(CLNT)/game/*.java $(CLNT)/*.java

clean:
	$(RM) *.class
	$(RM) $(OUT)/$(MSG)/*.class
	$(RM) $(OUT)/$(SRVR)/*.class
	$(RM) $(OUT)/$(SRVR)/game/*.class
	$(RM) $(OUT)/$(CLNT)/*.class
	$(RM) $(OUT)/$(CLNT)/game/*.class
