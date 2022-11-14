ZHE_FOLDER = ./Zhefuscator/
AGENT_FOLDER = $(ZHE_FOLDER)Agent/
TEST_FOLDER = $(ZHE_FOLDER)TestApplication/
TEXTS_FOLDER = $(ZHE_FOLDER)/Texts/
JC = javac

all: clean ZheAgent Agentjar Inspect Test

ZheAgent:
	@$(JC) $(AGENT_FOLDER)ZheAgent.java

Agentjar:
	@jar -cmf $(ZHE_FOLDER)agent-manifest.txt agent.jar $(AGENT_FOLDER)ZheAgent.class

Inspect:
	@jar tf ./agent.jar

Test:
	@java -javaagent:agent.jar -cp . $(TEST_FOLDER)Main.java

Decrypt:
	@$(JC) Decrypt.java
	@java Decrypt $(TEXTS_FOLDER)encrypted.txt $(TEXTS_FOLDER)decrypted.txt

clean:
	@rm -rf $(AGENT_FOLDER)*.class *.class agent.jar