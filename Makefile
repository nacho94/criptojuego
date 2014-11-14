
all:
	javac Main.java

run: all
	java Main

.PHONY: clean

clean:
	rm -f *.class
