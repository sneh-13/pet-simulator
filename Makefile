#generated, originally had a normal make file which compile and ran, this also gives us a jar file
# Config
JAR_NAME = PetSimulator.jar
MAIN_CLASS = Main.Main
SRC_DIRS = Main Pets GUI
LIB_DIR = lib
CLASS_DIR = build

# Build a colon-separated classpath of all JARs in lib/
CLASSPATH := $(shell find $(LIB_DIR) -name '*.jar' | tr '\n' ':')

# Clean previous build
clean:
	rm -rf $(CLASS_DIR) $(JAR_NAME)

# Compile Java source files
Game:
	mkdir -p $(CLASS_DIR)
	javac --add-modules java.desktop -d $(CLASS_DIR) -cp "$(CLASSPATH)" $(foreach dir, $(SRC_DIRS), $(dir)/*.java)

# Copy resources (images, sounds, etc.)
resources:
	mkdir -p $(CLASS_DIR)/GUI
	cp -r GUI/Assets $(CLASS_DIR)/GUI

# Build executable JAR with dependencies
jar: clean resources Game
	echo "Main-Class: $(MAIN_CLASS)" > manifest.txt
	cp $(LIB_DIR)/*.jar $(CLASS_DIR)
	for jarfile in $(LIB_DIR)/*.jar; do \
		cd $(CLASS_DIR) && jar xf ../$$jarfile; \
		cd - > /dev/null; \
	done
	jar cfm $(JAR_NAME) manifest.txt -C $(CLASS_DIR) .
	rm manifest.txt

# Run from compiled .class files
run: Game
	java --add-modules java.desktop -cp "$(CLASSPATH):$(CLASS_DIR)" $(MAIN_CLASS)