JAR_NAME = Lab4_Individual_Task.jar
MAIN_CLASS = Main

SOURCES = $(wildcard src/*.java)

build: $(JAR_NAME)

classes:
	mkdir -p bin
	javac -d bin $(SOURCES)

$(JAR_NAME): classes
	jar cfe $(JAR_NAME) $(MAIN_CLASS) -C bin .

run: $(JAR_NAME)
	java -ea -jar $(JAR_NAME)

clean:
	rm -rf bin $(JAR_NAME)

.PHONY: all classes run clean